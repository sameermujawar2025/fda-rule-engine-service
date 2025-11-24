package com.tao.fda.model;

public class RuleEvaluationRequest {

	private String transactionId;
	private String userId;
	private String cardNumber;
	private FraudFeaturesDto features;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public FraudFeaturesDto getFeatures() {
		return features;
	}

	public void setFeatures(FraudFeaturesDto features) {
		this.features = features;
	}

}
