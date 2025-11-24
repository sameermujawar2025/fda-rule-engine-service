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
public class R5LastAmountSpikeCalculator implements RuleCalculator {

	private static final BigDecimal ZSCORE_THRESHOLD = new BigDecimal("3.0");
	private static final int RISK_POINTS = 45;

	@Override
	public Optional<RuleHit> evaluate(RuleEvaluationContext context) {

		FraudFeaturesDto f = context.getFeatures();
		BigDecimal zscore = f.getAmountZscore();

		if (zscore != null && zscore.compareTo(ZSCORE_THRESHOLD) >= 0) {
			RuleHit hit = new RuleHit(RiskRuleId.R5_LAST_AMOUNT_SPIKE, "Last Amount Spike", RISK_POINTS,
					"amount_zscore=" + zscore + " >= " + ZSCORE_THRESHOLD);
			return Optional.of(hit);
		}
		return Optional.empty();
	}

}
