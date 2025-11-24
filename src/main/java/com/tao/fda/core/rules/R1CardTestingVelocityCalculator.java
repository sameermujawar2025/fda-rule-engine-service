package com.tao.fda.core.rules;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tao.fda.core.RuleCalculator;
import com.tao.fda.core.RuleEvaluationContext;
import com.tao.fda.model.FraudFeaturesDto;
import com.tao.fda.model.RiskRuleId;
import com.tao.fda.model.RuleHit;
@Component
public class R1CardTestingVelocityCalculator implements RuleCalculator {

	private static final int THRESHOLD_TXN_COUNT = 5;
	private static final int RISK_POINTS = 40;

	@Override
	public Optional<RuleHit> evaluate(RuleEvaluationContext context) {

		FraudFeaturesDto f = context.getFeatures();
		Integer count = f.getTxnCountLast10min();

		if (count != null && count > THRESHOLD_TXN_COUNT) {
			RuleHit hit = new RuleHit(RiskRuleId.R1_CARD_TESTING_VELOCITY, "Card Testing Velocity", RISK_POINTS,
					"txn_count_last_10min=" + count + " > " + THRESHOLD_TXN_COUNT);
			return Optional.of(hit);
		}

		return Optional.empty();
	}
}
