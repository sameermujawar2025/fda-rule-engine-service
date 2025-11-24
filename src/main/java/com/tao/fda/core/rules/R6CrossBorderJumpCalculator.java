package com.tao.fda.core.rules;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tao.fda.core.RuleCalculator;
import com.tao.fda.core.RuleEvaluationContext;
import com.tao.fda.model.FraudFeaturesDto;
import com.tao.fda.model.RiskRuleId;
import com.tao.fda.model.RuleHit;

@Component
public class R6CrossBorderJumpCalculator implements RuleCalculator {

	private static final int RISK_POINTS = 25;

	@Override
	public Optional<RuleHit> evaluate(RuleEvaluationContext context) {

		FraudFeaturesDto f = context.getFeatures();
		Boolean changed = f.getCountryChangeFlag();

		if (Boolean.TRUE.equals(changed)) {
			RuleHit hit = new RuleHit(RiskRuleId.R6_CROSS_BORDER_JUMP, "Cross-Border Jump", RISK_POINTS,
					"country_change_flag=true");
			return Optional.of(hit);
		}
		return Optional.empty();
	}

}
