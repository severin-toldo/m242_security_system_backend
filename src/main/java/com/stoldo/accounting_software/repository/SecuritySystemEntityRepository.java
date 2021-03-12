package com.stoldo.accounting_software.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stoldo.accounting_software.model.entity.SecuritySystemEntity;

@Repository
public interface SecuritySystemEntityRepository extends JpaRepository<SecuritySystemEntity, Integer> {
}
