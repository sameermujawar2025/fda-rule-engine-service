package com.tao.fda.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RuleEvaluationRequest {

	private String clientId;
	
	@JsonProperty("transaction_id")
	private String transactionId;
	
	private FraudFeaturesDto features;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public FraudFeaturesDto getFeatures() {
		return features;
	}

	public void setFeatures(FraudFeaturesDto features) {
		this.features = features;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
