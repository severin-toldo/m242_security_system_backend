package com.stoldo.m242_security_system_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stoldo.m242_security_system_backend.model.entity.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
    
	public Optional<UserEntity> findByEmail(String email);
	
	public Optional<UserEntity> findByRfidUUID(String rfidUUID);
	
}
