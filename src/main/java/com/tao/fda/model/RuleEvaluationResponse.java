package com.tao.fda.model;

import java.util.List;

public class RuleEvaluationResponse {

	private String clientId;
	private String transactionId;
	private int totalScore;
	private Decision decision;
	private List<RuleHit> hits;

	public RuleEvaluationResponse() {
	}

	public RuleEvaluationResponse(String clientId, String transactionId, int totalScore, Decision decision,
			List<RuleHit> hits) {
		super();
		this.clientId = clientId;
		this.transactionId = transactionId;
		this.totalScore = totalScore;
		this.decision = decision;
		this.hits = hits;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public Decision getDecision() {
		return decision;
	}

	public void setDecision(Decision decision) {
		this.decision = decision;
	}

	public List<RuleHit> getHits() {
		return hits;
	}

	public void setHits(List<RuleHit> hits) {
		this.hits = hits;
	}

}
