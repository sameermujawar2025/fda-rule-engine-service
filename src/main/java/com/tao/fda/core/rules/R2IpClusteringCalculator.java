package com.tao.fda.core.rules;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tao.fda.core.RuleCalculator;
import com.tao.fda.core.RuleEvaluationContext;
import com.tao.fda.model.FraudFeaturesDto;
import com.tao.fda.model.RiskRuleId;
import com.tao.fda.model.RuleHit;

@Component
public class R2IpClusteringCalculator implements RuleCalculator {

	private static final int THRESHOLD_UNIQUE_CARDS = 4;
	private static final int RISK_POINTS = 35;

	@Override
	public Optional<RuleHit> evaluate(RuleEvaluationContext context) {

		FraudFeaturesDto f = context.getFeatures();
		Integer unique = f.getUniqueCardsOnIp();

		if (unique != null && unique > THRESHOLD_UNIQUE_CARDS) {
			RuleHit hit = new RuleHit(RiskRuleId.R2_IP_CLUSTERING, "IP Clustering", RISK_POINTS,
					"unique_cards_on_ip=" + unique + " > " + THRESHOLD_UNIQUE_CARDS);
			return Optional.of(hit);
		}
		return Optional.empty();
	}

}
