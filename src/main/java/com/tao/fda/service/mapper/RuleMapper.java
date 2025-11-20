package com.tao.fda.service.mapper;

import org.springframework.stereotype.Component;

import com.tao.fda.dto.RuleDto;
import com.tao.fda.persistence.entity.RuleEntity;

@Component
public class RuleMapper {

	public RuleEntity mapToEntity(RuleDto dto) {
		RuleEntity e = new RuleEntity();
		e.setId(dto.id());
		e.setClientId(dto.clientId());
		e.setRuleCode(dto.ruleCode());
		e.setName(dto.name());
		e.setDescription(dto.description());
		e.setType(dto.type());
		e.setConfigJson(dto.configJson());
		e.setPriority(dto.priority());
		e.setActive(dto.active() != null && dto.active());
		e.setVersion(dto.version());
		return e;
	}

	public RuleDto mapToDto(RuleEntity e) {
		return new RuleDto(e.getId(), e.getClientId(), e.getRuleCode(), e.getName(), e.getDescription(), e.getType(),
				e.getConfigJson(), e.getPriority(), e.isActive(), e.getVersion());
	}

}
