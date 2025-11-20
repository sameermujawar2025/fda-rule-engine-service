package com.tao.fda.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tao.fda.persistence.entity.RuleEntity;

public interface RuleRepository extends JpaRepository<RuleEntity, Long>{
	
	List<RuleEntity> findByClientIdAndActiveTrueOrderByPriorityAsc(String clientId);

}
