package com.tao.fda.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tao.fda.core.RuleEvaluationContext;
import com.tao.fda.dynamic.DynamicRuleService;
import com.tao.fda.model.Decision;
import com.tao.fda.model.RuleEvaluationRequest;
import com.tao.fda.model.RuleEvaluationResponse;
import com.tao.fda.model.RuleHit;

@Service
public class RuleEngineServiceImpl implements RuleEngineService {

	private final DynamicRuleService dynamicRuleService;

	// thresholds can be externalized via application.yml later
	private final int challengeThreshold = 45;
	private final int blockThreshold = 80;

	public RuleEngineServiceImpl(DynamicRuleService dynamicRuleService) {
		this.dynamicRuleService = dynamicRuleService;
	}

	@Override
	public RuleEvaluationResponse evaluate(RuleEvaluationRequest request) {
		System.out.print("RuleEvaluationRequest"+request.toString());

		RuleEvaluationContext ctx = new RuleEvaluationContext(request.getClientId(), request.getTransactionId(),
				request.getFeatures());

		List<RuleHit> hits = dynamicRuleService.evaluateDynamicRules(ctx);
		int totalScore = hits.stream().mapToInt(RuleHit::getRiskPoints).sum();

		Decision decision = toDecision(totalScore);

		return new RuleEvaluationResponse(request.getClientId(), request.getTransactionId(), totalScore, decision,
				hits);
	}

	private Decision toDecision(int totalScore) {
		if (totalScore >= blockThreshold) {
			return Decision.BLOCK;
		}
		if (totalScore >= challengeThreshold) {
			return Decision.CHALLENGE;
		}
		return Decision.APPROVE;
	}
}
