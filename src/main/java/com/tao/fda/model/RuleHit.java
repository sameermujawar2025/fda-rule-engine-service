package com.tao.fda.model;

public class RuleHit {

	private RiskRuleId ruleId;
	private String ruleName;
	private int riskPoints;
	private String reason;

	public RuleHit() {
	}

	public RuleHit(RiskRuleId ruleId, String ruleName, int riskPoints, String reason) {
		this.ruleId = ruleId;
		this.ruleName = ruleName;
		this.riskPoints = riskPoints;
		this.reason = reason;
	}

	public RiskRuleId getRuleId() {
		return ruleId;
	}

	public void setRuleId(RiskRuleId ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public int getRiskPoints() {
		return riskPoints;
	}

	public void setRiskPoints(int riskPoints) {
		this.riskPoints = riskPoints;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
