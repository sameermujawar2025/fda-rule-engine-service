package com.tao.fda.core;

import java.util.Optional;

import com.tao.fda.model.RuleHit;

public interface RuleCalculator {

	Optional<RuleHit> evaluate(RuleEvaluationContext context);

}
