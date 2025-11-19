package com.tao.fda.evaluation;

public record EvaluationContext() {
	
	// CLASS  (contains triggeredRules + totalScore + context)
	// Could be RECORD but CLASS is better due to mutable score accumulation

}
