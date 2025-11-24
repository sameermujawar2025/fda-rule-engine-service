package com.tao.fda.core.rules;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tao.fda.core.RuleCalculator;
import com.tao.fda.core.RuleEvaluationContext;
import com.tao.fda.model.FraudFeaturesDto;
import com.tao.fda.model.RiskRuleId;
import com.tao.fda.model.RuleHit;

@Component
public class R7NightTimeRiskCalculator implements RuleCalculator {

	private static final int RISK_POINTS = 20;

	@Override
	public Optional<RuleHit> evaluate(RuleEvaluationContext context) {

		FraudFeaturesDto f = context.getFeatures();
		Integer hour = f.getHourOfDay();

		if (hour != null && (hour >= 22 || hour < 5)) {
			RuleHit hit = new RuleHit(RiskRuleId.R7_NIGHT_TIME_RISK, "Night Time Risk", RISK_POINTS,
					"hour_of_day=" + hour + " in [22-23] or [0-4]");
			return Optional.of(hit);
		}
		return Optional.empty();
	}

}
