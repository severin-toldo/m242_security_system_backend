package com.stoldo.m242_security_system_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemEntity;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemHistoryEntity;


@Repository
public interface SecuritySystemHistoryEntityRepository extends JpaRepository<SecuritySystemHistoryEntity, Integer> {
	
	public List<SecuritySystemHistoryEntity> findBySecuritySystem(SecuritySystemEntity sse);
	
}