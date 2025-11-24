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
public class R3DeclineSpikesCalculator implements RuleCalculator {

	private static final BigDecimal THRESHOLD_DECLINE_RATIO = new BigDecimal("0.5");
	private static final int RISK_POINTS = 30;

	@Override
	public Optional<RuleHit> evaluate(RuleEvaluationContext context) {

		FraudFeaturesDto f = context.getFeatures();
		BigDecimal ratio = f.getDeclineRatioLast10min();

		if (ratio != null && ratio.compareTo(THRESHOLD_DECLINE_RATIO) >= 0) {
			RuleHit hit = new RuleHit(RiskRuleId.R3_DECLINE_SPIKES, "Decline Spikes", RISK_POINTS,
					"decline_ratio_last_10min=" + ratio + " >= " + THRESHOLD_DECLINE_RATIO);
			return Optional.of(hit);
		}
		return Optional.empty();
	}

}
