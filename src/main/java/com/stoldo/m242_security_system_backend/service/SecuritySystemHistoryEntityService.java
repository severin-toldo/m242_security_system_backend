package com.stoldo.m242_security_system_backend.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;


import com.stoldo.m242_security_system_backend.model.entity.UserEntity;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.stoldo.m242_security_system_backend.model.SecuritySystemHistoryType;
import com.stoldo.m242_security_system_backend.model.api.SecuritySystemHistoryCreateRequest;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemEntity;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemHistoryEntity;
import com.stoldo.m242_security_system_backend.repository.SecuritySystemHistoryEntityRepository;

@Log
@Service
public class SecuritySystemHistoryEntityService {

    @Autowired
    private SecuritySystemHistoryEntityRepository securitySystemHistoryEntityRepository;
	
    @Autowired
    private UserEntityService userEntityService;
	
    @Autowired
    private JavaMailSender javaMailSender;

	
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
			try {
				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom("noreply@security-system.com");
				message.setTo(ue.getEmail());
				message.setSubject("Alarm! (" + sse.getName() + ")");
				message.setText("Attention! Alarm has been triggered for Security System \"" + sse.getName() + "\" at " + now);

				javaMailSender.send(message);
			} catch (Exception e) {
				log.log(Level.SEVERE, "E-Mail on alarm could not be sent!");
				e.printStackTrace();
			}
    	}
    	
    	return save(sshe);
    }

    private SecuritySystemHistoryEntity save(SecuritySystemHistoryEntity sshe) {
    	return securitySystemHistoryEntityRepository.save(sshe);
    }
    
}
