package com.tao.fda.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tao.fda.core.RuleCalculator;
import com.tao.fda.core.RuleEvaluationContext;
import com.tao.fda.dynamic.DynamicRuleService;
import com.tao.fda.model.Decision;
import com.tao.fda.model.RuleEvaluationRequest;
import com.tao.fda.model.RuleEvaluationResponse;
import com.tao.fda.model.RuleHit;

@Service
public class RuleEvaluationServiceImpl implements RuleEvaluationService {

	private final List<RuleCalculator> calculators;
	private final DynamicRuleService dynamicRuleService;

	public RuleEvaluationServiceImpl(List<RuleCalculator> calculators, DynamicRuleService dynamicRuleService) {
		this.calculators = calculators;
		this.dynamicRuleService = dynamicRuleService;
	}

	@Override
	public RuleEvaluationResponse evaluate(RuleEvaluationRequest request) {

		RuleEvaluationContext context = new RuleEvaluationContext(request);
		List<RuleHit> hits = new ArrayList<>();

		// 1) Built-in rules (R1–R9)
		for (RuleCalculator calculator : calculators) {
			calculator.evaluate(context).ifPresent(hits::add);
		}

		// 2) Dynamic DB rules (if implemented)
		hits.addAll(dynamicRuleService.evaluateDynamicRules(context));

		int totalScore = hits.stream().mapToInt(RuleHit::getRiskPoints).sum();

		Decision decision;
		if (totalScore < 45) {
			decision = Decision.APPROVE;
		} else if (totalScore < 80) {
			decision = Decision.CHALLENGE;
		} else {
			decision = Decision.BLOCK;
		}

		return new RuleEvaluationResponse(decision, totalScore, hits);
	}
}
