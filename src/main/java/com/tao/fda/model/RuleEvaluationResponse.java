package com.tao.fda.model;

import java.util.List;

public class RuleEvaluationResponse {

	private Decision decision;
	private int totalRiskScore;
	private List<RuleHit> ruleHits;

	public RuleEvaluationResponse() {
	}

	public RuleEvaluationResponse(Decision decision, int totalRiskScore, List<RuleHit> ruleHits) {
		this.decision = decision;
		this.totalRiskScore = totalRiskScore;
		this.ruleHits = ruleHits;
	}

	public Decision getDecision() {
		return decision;
	}

	public void setDecision(Decision decision) {
		this.decision = decision;
	}

	public int getTotalRiskScore() {
		return totalRiskScore;
	}

	public void setTotalRiskScore(int totalRiskScore) {
		this.totalRiskScore = totalRiskScore;
	}

	public List<RuleHit> getRuleHits() {
		return ruleHits;
	}

	public void setRuleHits(List<RuleHit> ruleHits) {
		this.ruleHits = ruleHits;
	}
}
