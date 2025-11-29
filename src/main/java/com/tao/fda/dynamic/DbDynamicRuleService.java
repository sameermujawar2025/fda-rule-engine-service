package com.tao.fda.dynamic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tao.fda.core.RuleEvaluationContext;
import com.tao.fda.model.FraudFeaturesDto;
import com.tao.fda.model.RuleHit;
import com.tao.fda.persistence.entity.RuleEntity;
import com.tao.fda.persistence.repository.RuleRepository;

@Service
public class DbDynamicRuleService implements DynamicRuleService {

	private final RuleRepository ruleRepository;
	private final ObjectMapper objectMapper;

	public DbDynamicRuleService(RuleRepository ruleRepository, ObjectMapper objectMapper) {
		this.ruleRepository = ruleRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public List<RuleHit> evaluateDynamicRules(RuleEvaluationContext context) {
		List<RuleEntity> rules = ruleRepository.findByClientIdAndActiveTrueOrderByPriorityAsc(context.getClientId());

		List<RuleHit> hits = new ArrayList<>();

		for (RuleEntity rule : rules) {
			RuleHit maybeHit = evaluateSingleRule(rule, context);
			if (maybeHit != null) {
				hits.add(maybeHit);
			}
		}

		return hits;
	}

	private RuleHit evaluateSingleRule(RuleEntity rule, RuleEvaluationContext context) {
		try {
			JsonNode config = objectMapper.readTree(rule.getConfigJson());
			String type = rule.getType();

			FraudFeaturesDto f = context.getFeatures();

			switch (type) {
			case "VELOCITY":
				return evaluateVelocityRule(rule, config, f);
			case "AMOUNT_THRESHOLD":
				return evaluateAmountRule(rule, config, f);
			case "BOOLEAN_FLAG":
				return evaluateBooleanFlagRule(rule, config, f);
			default:
				// Unknown type → ignore rule
				return null;
			}
		} catch (Exception ex) {
			// bad configJson, ignore this rule
			return null;
		}
	}

	// ----------------- VELOCITY -----------------

	private RuleHit evaluateVelocityRule(RuleEntity rule, JsonNode config, FraudFeaturesDto f) {

	    String featureKey = config.path("featureKey").asText();
	    BigDecimal threshold = config.has("threshold") ? config.path("threshold").decimalValue() : null;
	    int score = config.path("score").asInt();

	    // 1️⃣ First try INTEGER velocity features
	    Integer intVal = getIntegerFeature(f, featureKey);
	    if (intVal != null && threshold != null) {
	        if (BigDecimal.valueOf(intVal).compareTo(threshold) >= 0) {
	            return toHit(rule, score, featureKey + "=" + intVal + " >= " + threshold);
	        }
	    }

	    // 2️⃣ Then try DECIMAL velocity features (like declineRatioLast10min)
	    BigDecimal decVal = getDecimalFeature(f, featureKey);
	    if (decVal != null && threshold != null) {
	        if (decVal.compareTo(threshold) >= 0) {
	            return toHit(rule, score, featureKey + "=" + decVal + " >= " + threshold);
	        }
	    }

	    return null;
	}


	// ----------------- AMOUNT_THRESHOLD -----------------

	private RuleHit evaluateAmountRule(RuleEntity rule, JsonNode config, FraudFeaturesDto f) {

	    String featureKey = config.path("featureKey").asText(null);
	    BigDecimal threshold = config.has("threshold") ? config.path("threshold").decimalValue() : null;
	    int score = config.path("score").asInt();

	    if (featureKey != null && threshold != null) {
	        BigDecimal value = getDecimalFeature(f, featureKey);
	        if (value != null && value.compareTo(threshold) > 0) {
	            return toHit(rule, score, featureKey + "=" + value + " > " + threshold);
	        }
	    }

	    return null;
	}


	// ----------------- BOOLEAN_FLAG -----------------

	private RuleHit evaluateBooleanFlagRule(RuleEntity rule, JsonNode config, FraudFeaturesDto f) {

		String flagName = config.path("flagName").asText();
		int score = config.path("score").asInt();

		Boolean flag = getBooleanFlag(f, flagName);
		if (Boolean.TRUE.equals(flag)) {
			return toHit(rule, score, flagName + "=true");
		}
		return null;
	}

	// ----------------- Helpers -----------------

	private Integer getIntegerFeature(FraudFeaturesDto f, String key) {
		switch (key) {
		case "txnCountLast10min":
			return f.getTxnCountLast10min();
		case "uniqueCardsOnIp":
			return f.getUniqueCardsOnIp();
		default:
			return null;
		}
	}

	private BigDecimal getDecimalFeature(FraudFeaturesDto f, String key) {
		switch (key) {
		case "declineRatioLast10min":
			return f.getDeclineRatioLast10min();
		case "distanceFromLastLocationKm":
			return f.getDistanceFromLastLocationKm();
		case "speedKmph":
			return f.getSpeedKmph();
		case "amountZscore":
			return f.getAmountZscore();
		default:
			return null;
		}
	}

	private Boolean getBooleanFlag(FraudFeaturesDto f, String flagName) {
		switch (flagName) {
		case "countryChangeFlag":
			return f.getCountryChangeFlag();
		case "blacklistFlag":
			return f.getBlacklistFlag();
		case "nightHourFlag":
			// derived from hour_of_day [0–4]
			Integer h = f.getHourOfDay();
			if (h == null) {
				return false;
			}
			return (h >= 0 && h <= 4);
		default:
			return false;
		}
	}

	private RuleHit toHit(RuleEntity rule, int score, String reason) {
		RuleHit hit = new RuleHit();
		hit.setRuleCode(rule.getRuleCode()); // <-- Store ruleCode from DB
		hit.setRuleName(rule.getName());
		hit.setRiskPoints(score);
		hit.setReason(reason);
		return hit;
	}
}
