package com.tao.fda.core.rules;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tao.fda.core.RuleCalculator;
import com.tao.fda.core.RuleEvaluationContext;
import com.tao.fda.model.FraudFeaturesDto;
import com.tao.fda.model.RiskRuleId;
import com.tao.fda.model.RuleHit;

@Component
public class R9BlacklistCheckCalculator implements RuleCalculator {

	private static final int RISK_POINTS = 80;

	@Override
	public Optional<RuleHit> evaluate(RuleEvaluationContext context) {

		FraudFeaturesDto f = context.getFeatures();
		Boolean flag = f.getBlacklistFlag();

		if (Boolean.TRUE.equals(flag)) {
			List<String> matches = f.getBlacklistMatches();
			String reason = "blacklist_flag=true; matches=" + (matches == null ? "[]" : matches);
			RuleHit hit = new RuleHit(RiskRuleId.R9_BLACKLIST_CHECK, "Blacklist Check", RISK_POINTS, reason);
			return Optional.of(hit);
		}
		return Optional.empty();
	}

}
