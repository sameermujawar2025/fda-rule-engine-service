package com.tao.fda.core;

import com.tao.fda.model.FraudFeaturesDto;
import com.tao.fda.model.RuleEvaluationRequest;

public class RuleEvaluationContext {

	private final String clientId;
	private final String transactionId;
	private final FraudFeaturesDto features;

	public RuleEvaluationContext(String clientId, String transactionId, FraudFeaturesDto features) {
		this.clientId = clientId;
		this.transactionId = transactionId;
		this.features = features;
	}

	public String getClientId() {
		return clientId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public FraudFeaturesDto getFeatures() {
		return features;
	}

}
