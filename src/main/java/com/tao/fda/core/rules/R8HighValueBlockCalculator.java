package com.tao.fda.core.rules;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tao.fda.core.RuleCalculator;
import com.tao.fda.core.RuleEvaluationContext;
import com.tao.fda.model.FraudFeaturesDto;
import com.tao.fda.model.RiskRuleId;
import com.tao.fda.model.RuleHit;

@Component
public class R8HighValueBlockCalculator implements RuleCalculator {

	private static final int RISK_POINTS = 100;

	@Override
	public Optional<RuleHit> evaluate(RuleEvaluationContext context) {

		FraudFeaturesDto f = context.getFeatures();
		Boolean flag = f.getHighValueFlag();

		if (Boolean.TRUE.equals(flag)) {
			RuleHit hit = new RuleHit(RiskRuleId.R8_HIGH_VALUE_BLOCK, "High Value Block", RISK_POINTS,
					"high_value_flag=true");
			return Optional.of(hit);
		}
		return Optional.empty();
	}

}
