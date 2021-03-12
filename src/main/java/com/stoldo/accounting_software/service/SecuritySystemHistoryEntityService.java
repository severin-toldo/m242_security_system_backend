package com.stoldo.accounting_software.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stoldo.accounting_software.model.SecuritySystemHistoryType;
import com.stoldo.accounting_software.model.api.SecuritySystemHistoryCreateRequest;
import com.stoldo.accounting_software.model.entity.SecuritySystemEntity;
import com.stoldo.accounting_software.model.entity.SecuritySystemHistoryEntity;
import com.stoldo.accounting_software.repository.SecuritySystemHistoryEntityRepository;

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
    	sshe.setDatetime(sshcr.getDatetime());
    	sshe.setType(sshcr.getType());
    	sshe.setUser(userEntityService.getByEmail(sshcr.getUserEmail()));
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
