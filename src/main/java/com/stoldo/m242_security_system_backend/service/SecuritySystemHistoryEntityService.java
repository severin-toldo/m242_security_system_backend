package com.stoldo.m242_security_system_backend.service;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stoldo.m242_security_system_backend.model.SecuritySystemHistoryType;
import com.stoldo.m242_security_system_backend.model.api.SecuritySystemHistoryCreateRequest;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemEntity;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemHistoryEntity;
import com.stoldo.m242_security_system_backend.repository.SecuritySystemHistoryEntityRepository;

@Service
public class SecuritySystemHistoryEntityService {

	@Autowired
    private SecuritySystemHistoryEntityRepository securitySystemHistoryEntityRepository;
	
	@Autowired
	private UserEntityService userEntityService;

	
    public List<SecuritySystemHistoryEntity> getHistory(SecuritySystemEntity sse) {
    	return securitySystemHistoryEntityRepository.findBySecuritySystem(sse);
    }
	
    public SecuritySystemHistoryEntity addHistory(SecuritySystemEntity sse, SecuritySystemHistoryCreateRequest sshcr) {
    	SecuritySystemHistoryEntity sshe = new SecuritySystemHistoryEntity();
    	sshe.setDatetime(new Date());
    	sshe.setType(sshcr.getType());
    	sshe.setUser(userEntityService.getByRfidUUID(sshcr.getUserRfidUUID()));
    	sshe.setSecuritySystem(sse);
    	
    	if (sshe.getType() == SecuritySystemHistoryType.ALARM) {
    		// TODO send alarm email
    	}
    	
    	return save(sshe);
    }
    
    private SecuritySystemHistoryEntity save(SecuritySystemHistoryEntity sshe) {
    	return securitySystemHistoryEntityRepository.save(sshe);
    }
    
}
