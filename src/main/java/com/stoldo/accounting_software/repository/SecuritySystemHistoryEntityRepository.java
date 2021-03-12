package com.stoldo.accounting_software.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stoldo.accounting_software.model.entity.SecuritySystemEntity;
import com.stoldo.accounting_software.model.entity.SecuritySystemHistoryEntity;


@Repository
public interface SecuritySystemHistoryEntityRepository extends JpaRepository<SecuritySystemHistoryEntity, Integer> {
	
	public List<SecuritySystemHistoryEntity> findBySecuritySystem(SecuritySystemEntity sse);
	
}
