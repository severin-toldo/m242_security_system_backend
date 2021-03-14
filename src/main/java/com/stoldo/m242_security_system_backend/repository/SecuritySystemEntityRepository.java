package com.stoldo.m242_security_system_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemEntity;

@Repository
public interface SecuritySystemEntityRepository extends JpaRepository<SecuritySystemEntity, Integer> {
}
