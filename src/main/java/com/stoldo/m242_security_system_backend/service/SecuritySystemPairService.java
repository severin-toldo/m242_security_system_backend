package com.stoldo.m242_security_system_backend.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.stoldo.m242_security_system_backend.model.ErrorCode;
import com.stoldo.m242_security_system_backend.model.ErrorCodeException;
import com.stoldo.m242_security_system_backend.model.PairableSecuritySystem;
import com.stoldo.m242_security_system_backend.model.api.SecuritySystemFinishPairRequest;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemEntity;


@Service
public class SecuritySystemPairService {
	
	@Autowired
	private SecuritySystemEntityService securitySystemEntityService;
	
	private static final int TWO_MINUTES = 120000;

	private Map<String, PairableSecuritySystem> pairableSecuirtySystems = new HashMap<>();
	
	
	@Scheduled(fixedDelay = TWO_MINUTES)
	public void clearPairableSecuirtySystems() {
		pairableSecuirtySystems.clear();
	}
	
	public boolean isPaired(Integer securitySystemId, String securitySystemAuthToken) {
		try {
			securitySystemEntityService.getByIdAndAuthToken(securitySystemId, securitySystemAuthToken);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String startPairing(Integer securitySystemId, String securitySystemAuthToken) {
		String pairingCode = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
		
		PairableSecuritySystem pss = new PairableSecuritySystem();
		pss.setId(securitySystemId);
		pss.setAuthToken(securitySystemAuthToken);
		pss.setPairingCode(pairingCode);
		
		pairableSecuirtySystems.put(pairingCode, pss);
		
		return pairingCode;
    }
	
	public SecuritySystemEntity finishPairing(SecuritySystemFinishPairRequest ssfpr) {
		PairableSecuritySystem pss = pairableSecuirtySystems.get(ssfpr.getPairingCode());
		
		if (pss == null) {
			throw new ErrorCodeException(ErrorCode.E1002, HttpStatus.BAD_REQUEST);
		}
		
		if (!StringUtils.equalsIgnoreCase(pss.getPairingCode(), ssfpr.getPairingCode())) {
			throw new ErrorCodeException(ErrorCode.E1004, HttpStatus.BAD_REQUEST);
		}
		
		SecuritySystemEntity sse = new SecuritySystemEntity();
		sse.setId(pss.getId());
		sse.setName(ssfpr.getName());
		sse.setAuthToken(pss.getAuthToken());
		
		pairableSecuirtySystems.remove(ssfpr.getPairingCode());
		
		return securitySystemEntityService.save(sse);
	}
    
}
