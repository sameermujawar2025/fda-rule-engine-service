package com.tao.fda.dynamic;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tao.fda.core.RuleEvaluationContext;
import com.tao.fda.model.RuleHit;

@Service
public class NoOpDynamicRuleService implements DynamicRuleService {

	@Override
	public List<RuleHit> evaluateDynamicRules(RuleEvaluationContext context) {
		// TODO Auto-generated method stub
		// Later we can replace this bean with your real DB-backed implementation.
		return null;
	}

}
