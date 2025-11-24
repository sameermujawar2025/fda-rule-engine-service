package com.tao.fda.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FraudFeaturesDto {

	@JsonProperty("txn_count_last_10min")
	private Integer txnCountLast10min;

	@JsonProperty("unique_cards_on_ip")
	private Integer uniqueCardsOnIp;

	@JsonProperty("decline_ratio_last_10min")
	private BigDecimal declineRatioLast10min;

	@JsonProperty("distance_from_last_location_km")
	private BigDecimal distanceFromLastLocationKm;

	@JsonProperty("speed_kmph")
	private BigDecimal speedKmph;

	@JsonProperty("amount_zscore")
	private BigDecimal amountZscore;

	@JsonProperty("country_change_flag")
	private Boolean countryChangeFlag;

	@JsonProperty("hour_of_day")
	private Integer hourOfDay;

	@JsonProperty("high_value_flag")
	private Boolean highValueFlag;

	@JsonProperty("blacklist_flag")
	private Boolean blacklistFlag;

	@JsonProperty("blacklist_matches")
	private List<String> blacklistMatches;

	public Integer getTxnCountLast10min() {
		return txnCountLast10min;
	}

	public void setTxnCountLast10min(Integer txnCountLast10min) {
		this.txnCountLast10min = txnCountLast10min;
	}

	public Integer getUniqueCardsOnIp() {
		return uniqueCardsOnIp;
	}

	public void setUniqueCardsOnIp(Integer uniqueCardsOnIp) {
		this.uniqueCardsOnIp = uniqueCardsOnIp;
	}

	public BigDecimal getDeclineRatioLast10min() {
		return declineRatioLast10min;
	}

	public void setDeclineRatioLast10min(BigDecimal declineRatioLast10min) {
		this.declineRatioLast10min = declineRatioLast10min;
	}

	public BigDecimal getDistanceFromLastLocationKm() {
		return distanceFromLastLocationKm;
	}

	public void setDistanceFromLastLocationKm(BigDecimal distanceFromLastLocationKm) {
		this.distanceFromLastLocationKm = distanceFromLastLocationKm;
	}

	public BigDecimal getSpeedKmph() {
		return speedKmph;
	}

	public void setSpeedKmph(BigDecimal speedKmph) {
		this.speedKmph = speedKmph;
	}

	public BigDecimal getAmountZscore() {
		return amountZscore;
	}

	public void setAmountZscore(BigDecimal amountZscore) {
		this.amountZscore = amountZscore;
	}

	public Boolean getCountryChangeFlag() {
		return countryChangeFlag;
	}

	public void setCountryChangeFlag(Boolean countryChangeFlag) {
		this.countryChangeFlag = countryChangeFlag;
	}

	public Integer getHourOfDay() {
		return hourOfDay;
	}

	public void setHourOfDay(Integer hourOfDay) {
		this.hourOfDay = hourOfDay;
	}

	public Boolean getHighValueFlag() {
		return highValueFlag;
	}

	public void setHighValueFlag(Boolean highValueFlag) {
		this.highValueFlag = highValueFlag;
	}

	public Boolean getBlacklistFlag() {
		return blacklistFlag;
	}

	public void setBlacklistFlag(Boolean blacklistFlag) {
		this.blacklistFlag = blacklistFlag;
	}

	public List<String> getBlacklistMatches() {
		return blacklistMatches;
	}

	public void setBlacklistMatches(List<String> blacklistMatches) {
		this.blacklistMatches = blacklistMatches;
	}

}
