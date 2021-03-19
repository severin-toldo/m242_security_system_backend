package com.stoldo.m242_security_system_backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.stoldo.m242_security_system_backend.model.PairableSecuritySystem;
import com.stoldo.m242_security_system_backend.model.api.SecuritySystemFinishPairRequest;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemEntity;
import com.stoldo.m242_security_system_backend.repository.SecuritySystemEntityRepository;


@Service
public class SecuritySystemEntityService {
	
	@Autowired
	private SecuritySystemEntityRepository securitySystemEntityRepository;
	
	private Map<Integer, PairableSecuritySystem> pairableSecuirtySystems = new HashMap<>();
	
	
	public List<SecuritySystemEntity> getAll() {
    	return securitySystemEntityRepository.findAll();
    }
	
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
	
	public String startPairing(Integer securitySystemId, String securitySystemAuthToken) {
		String pairingCode = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
		
		PairableSecuritySystem pss = new PairableSecuritySystem();
		pss.setId(securitySystemId);
		pss.setAuthToken(securitySystemAuthToken);
		pss.setPairingCode(pairingCode);
		
		pairableSecuirtySystems.put(pss.getId(), pss);
		
		return pairingCode;
    }
	
	public SecuritySystemEntity finishPairing(@PathVariable Integer securitySystemId, @RequestBody @Valid SecuritySystemFinishPairRequest ssfpr) {
		PairableSecuritySystem pss = pairableSecuirtySystems.get(securitySystemId);
		
		if (!StringUtils.equals(pss.getPairingCode(), ssfpr.getPairingCode())) {
			throw new IllegalArgumentException("pairing codes do not match!");
		}
		
		SecuritySystemEntity sse = new SecuritySystemEntity();
		sse.setId(pss.getId());
		sse.setName(ssfpr.getName());
		sse.setAuthToken(pss.getAuthToken());
		
		pairableSecuirtySystems.remove(sse.getId());
		
		return save(sse);
	}
	
	private SecuritySystemEntity save(SecuritySystemEntity sse) {
		return securitySystemEntityRepository.save(sse);
	}
	
    
}
