package com.tao.fda.core;

import com.tao.fda.model.FraudFeaturesDto;
import com.tao.fda.model.RuleEvaluationRequest;

public class RuleEvaluationContext {

	private final RuleEvaluationRequest request;
	private final FraudFeaturesDto features;

	public RuleEvaluationContext(RuleEvaluationRequest request) {
		this.request = request;
		this.features = request.getFeatures();
	}

	public RuleEvaluationRequest getRequest() {
		return request;
	}

	public FraudFeaturesDto getFeatures() {
		return features;
	}

}
