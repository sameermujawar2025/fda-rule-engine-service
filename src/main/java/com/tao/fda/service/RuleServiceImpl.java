package com.tao.fda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tao.fda.dto.RuleDto;
import com.tao.fda.persistence.entity.RuleEntity;
import com.tao.fda.persistence.repository.RuleRepository;
import com.tao.fda.service.mapper.RuleMapper;

@Service
@Transactional
public class RuleServiceImpl implements RuleService{
	
	private final RuleRepository repository;
	private final RuleMapper mapper;
	
	 public RuleServiceImpl(RuleRepository repository,RuleMapper mapper) {
	        this.repository = repository;
	        this.mapper=mapper;
	    }

	 @Override
	    public RuleDto create(RuleDto dto) {
	        RuleEntity entity = mapper.mapToEntity(dto);
	        entity.setId(null);
	        RuleEntity saved = repository.save(entity);
	        return mapper.mapToDto(saved);
	    }

	    @Override
	    public RuleDto update(Long id, RuleDto dto) {
	        RuleEntity existing = repository.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("Rule not found: " + id));

	        existing.setClientId(dto.clientId());
	        existing.setRuleCode(dto.ruleCode());
	        existing.setName(dto.name());
	        existing.setDescription(dto.description());
	        existing.setType(dto.type());
	        existing.setConfigJson(dto.configJson());
	        existing.setPriority(dto.priority());
	        existing.setActive(dto.active());
	        existing.setVersion(dto.version());

	        RuleEntity saved = repository.save(existing);
	        return mapper.mapToDto(saved);
	    }

	    @Override
	    @Transactional(readOnly = true)
	    public RuleDto get(Long id) {
	        return repository.findById(id)
	                .map(mapper::mapToDto)
	                .orElseThrow(() -> new IllegalArgumentException("Rule not found: " + id));
	    }

	    @Override
	    @Transactional(readOnly = true)
	    public List<RuleDto> listByClient(String clientId) {
	        return repository.findByClientIdAndActiveTrueOrderByPriorityAsc(clientId)
	                .stream()
	                .map(mapper::mapToDto)
	                .toList();
	    }

	    @Override
	    public void delete(Long id) {
	        repository.deleteById(id);
	    }
	
	 
	 
	
}
