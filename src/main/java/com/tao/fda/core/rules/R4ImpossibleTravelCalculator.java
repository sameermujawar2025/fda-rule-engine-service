package com.tao.fda.core.rules;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tao.fda.core.RuleCalculator;
import com.tao.fda.core.RuleEvaluationContext;
import com.tao.fda.model.FraudFeaturesDto;
import com.tao.fda.model.RiskRuleId;
import com.tao.fda.model.RuleHit;

@Component
public class R4ImpossibleTravelCalculator implements RuleCalculator {

	private static final BigDecimal SPEED_THRESHOLD = new BigDecimal("900");
	private static final int RISK_POINTS = 50;

	@Override
	public Optional<RuleHit> evaluate(RuleEvaluationContext context) {

		FraudFeaturesDto f = context.getFeatures();
		BigDecimal speed = f.getSpeedKmph();

		if (speed != null && speed.compareTo(SPEED_THRESHOLD) > 0) {
			RuleHit hit = new RuleHit(RiskRuleId.R4_IMPOSSIBLE_TRAVEL, "Impossible Travel", RISK_POINTS,
					"speed_kmph=" + speed + " > " + SPEED_THRESHOLD);
			return Optional.of(hit);
		}
		return Optional.empty();
	}

}
