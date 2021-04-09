package com.stoldo.m242_security_system_backend.service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;


import com.stoldo.m242_security_system_backend.model.entity.UserEntity;
import lombok.extern.java.Log;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stoldo.m242_security_system_backend.model.SecuritySystemHistoryType;
import com.stoldo.m242_security_system_backend.model.api.SecuritySystemHistoryCreateRequest;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemEntity;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemHistoryEntity;
import com.stoldo.m242_security_system_backend.repository.SecuritySystemHistoryEntityRepository;
import com.stoldo.m242_security_system_backend.shared.Constants;

@Log
@Service
public class SecuritySystemHistoryEntityService {

    @Autowired
    private SecuritySystemHistoryEntityRepository securitySystemHistoryEntityRepository;
	
    @Autowired
    private UserEntityService userEntityService;
    
    @Autowired
    private EmailService emailService;

	
    public List<SecuritySystemHistoryEntity> getHistory(SecuritySystemEntity sse) {
		return securitySystemHistoryEntityRepository.findBySecuritySystemOrderByDatetimeDesc(sse);
    }

    public SecuritySystemHistoryEntity getLatestHistory(SecuritySystemEntity sse) {
    	return securitySystemHistoryEntityRepository.findTop1BySecuritySystemOrderByDatetimeDesc(sse).orElse(null);
	}

	public void deleteBySecuritySystem(SecuritySystemEntity sse) {
		securitySystemHistoryEntityRepository.deleteBySecuritySystem(sse);
	}

    public SecuritySystemHistoryEntity addHistory(SecuritySystemEntity sse, SecuritySystemHistoryCreateRequest sshcr) {
		Date now = new Date();
		UserEntity ue = userEntityService.getByRfidUUID(sshcr.getUserRfidUUID());

		SecuritySystemHistoryEntity sshe = new SecuritySystemHistoryEntity();
    	sshe.setDatetime(now);
    	sshe.setType(sshcr.getType());
    	sshe.setUser(userEntityService.getByRfidUUID(sshcr.getUserRfidUUID()));
    	sshe.setUser(ue);
    	sshe.setSecuritySystem(sse);
    	
    	if (sshe.getType() == SecuritySystemHistoryType.ALARM) {
    		sendAlarmEmails(sse, now);	
    	}
    	
    	return save(sshe);
    }

    private SecuritySystemHistoryEntity save(SecuritySystemHistoryEntity sshe) {
    	return securitySystemHistoryEntityRepository.save(sshe);
    }
    
    private void sendAlarmEmails(SecuritySystemEntity sse, Date date) {
    	String subject = MessageFormat.format("Alarm! ({0})", sse.getName());
		String body = MessageFormat.format("Achtung! Ein Alarm für das Sicherheitssystem «{0}» wurde am {1} ausgelöst!", sse.getName(), date);
		
    	List<UserEntity> users = userEntityService.getAll();
    	
    	for (UserEntity ue : users) {
    		if (!StringUtils.equals(ue.getEmail(), Constants.SYSTEM_USER_EMAIL)) {
    			try {
        			emailService.sendEmail(subject, body, ue.getEmail());
    			} catch (Exception e) {
    				log.log(Level.SEVERE, e.getMessage());
    				e.printStackTrace();
    			}
    		}
    	}
    }
}
