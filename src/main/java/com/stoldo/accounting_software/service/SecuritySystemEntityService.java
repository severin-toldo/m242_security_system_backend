package com.stoldo.accounting_software.service;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stoldo.accounting_software.model.entity.SecuritySystemEntity;
import com.stoldo.accounting_software.repository.SecuritySystemEntityRepository;


@Service
public class SecuritySystemEntityService {
	
	@Autowired
	private SecuritySystemEntityRepository securitySystemEntityRepository;
	
	
	public SecuritySystemEntity getById(Integer id) {
		return securitySystemEntityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("SecuritySystemEntity with id " + id + " does not exist!"));
	}
	
	public SecuritySystemEntity getByIdAndAuthToken(Integer id, String authToken) {
		SecuritySystemEntity sse = getById(id);
		
		if (StringUtils.equals(sse.getAuthToken(), authToken)) {
			return sse;	
		}
		
		throw new IllegalArgumentException("security system id or auth_token is wrong!");
	}
    
}
