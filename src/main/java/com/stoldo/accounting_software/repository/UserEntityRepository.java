package com.stoldo.accounting_software.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stoldo.accounting_software.model.entity.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
    
	public Optional<UserEntity> findByEmail(String email);
	
}
