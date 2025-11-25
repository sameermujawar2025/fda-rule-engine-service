package com.tao.fda.model;

public class RuleHit {

	private String ruleCode; // from DB
	private String ruleName; // from DB
	private int riskPoints; // from configJson
	private String reason; // explanation

	public RuleHit() {
	}

	public RuleHit(String ruleCode, String ruleName, int riskPoints, String reason) {
		this.ruleCode = ruleCode;
		this.ruleName = ruleName;
		this.riskPoints = riskPoints;
		this.reason = reason;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
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
