package com.tao.fda.service;

import com.tao.fda.model.RuleEvaluationRequest;
import com.tao.fda.model.RuleEvaluationResponse;

public interface RuleEngineService {

	RuleEvaluationResponse evaluate(RuleEvaluationRequest request);

}
