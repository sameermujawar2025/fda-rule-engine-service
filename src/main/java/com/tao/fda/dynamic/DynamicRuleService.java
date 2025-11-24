package com.tao.fda.dynamic;

import java.util.List;

import com.tao.fda.core.RuleEvaluationContext;
import com.tao.fda.model.RuleHit;

public interface DynamicRuleService {

	List<RuleHit> evaluateDynamicRules(RuleEvaluationContext context);

}
