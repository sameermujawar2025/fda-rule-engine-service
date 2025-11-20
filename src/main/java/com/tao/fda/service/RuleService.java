package com.tao.fda.service;

import java.util.List;
import com.tao.fda.dto.RuleDto;


public interface RuleService {

	RuleDto create(RuleDto dto);

	RuleDto update(Long id, RuleDto dto);

	RuleDto get(Long id);

	List<RuleDto> listByClient(String clientId);

	void delete(Long id);

}
