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
		List<RuleEntity> rules = ruleRepository.findByClientIdAndActive(context.getClientId(), true);

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
		int threshold = config.path("threshold").asInt();
		int score = config.path("score").asInt();

		Integer value = getIntegerFeature(f, featureKey);
		if (value != null && value > threshold) {
			return toHit(rule, score, featureKey + "=" + value + " > " + threshold);
		}
		return null;
	}

	// ----------------- AMOUNT_THRESHOLD -----------------

	private RuleHit evaluateAmountRule(RuleEntity rule, JsonNode config, FraudFeaturesDto f) {

		String featureKey = config.path("featureKey").asText(null);
		BigDecimal threshold = config.has("threshold") ? config.path("threshold").decimalValue() : null;
		BigDecimal multiplier = config.has("multiplier") ? config.path("multiplier").decimalValue() : null;
		int score = config.path("score").asInt();

		// Example 1: simple threshold, e.g. R8 High Value Block
		if (featureKey != null && threshold != null) {
			BigDecimal value = getDecimalFeature(f, featureKey);
			if (value != null && value.compareTo(threshold) > 0) {
				return toHit(rule, score, featureKey + "=" + value + " > " + threshold);
			}
		}

		// Example 2: z-score or ratio style (e.g. R5 Last Amount Spike using
		// amount_zscore)
		if ("amountZscore".equals(featureKey) && threshold != null) {
			BigDecimal value = f.getAmountZscore();
			if (value != null && value.compareTo(threshold) > 0) {
				return toHit(rule, score, "amount_zscore=" + value + " > " + threshold);
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
