package com.tao.fda.dto;

public record RuleDto(

		Long id, String clientId, String ruleCode, String name, String description, String type, String configJson,
		Integer priority, Boolean active, String version

) {

}
