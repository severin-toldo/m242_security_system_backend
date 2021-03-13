package com.stoldo.accounting_software.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.stoldo.accounting_software.model.api.SecuritySystemFinishPairRequest;
import com.stoldo.accounting_software.model.api.SecuritySystemStartPairRequest;
import com.stoldo.accounting_software.model.entity.SecuritySystemEntity;
import com.stoldo.accounting_software.repository.SecuritySystemEntityRepository;
import com.stoldo.accounting_software.shared.util.CommonUtils;


@Service
public class SecuritySystemEntityService {
	
	@Autowired
	private SecuritySystemEntityRepository securitySystemEntityRepository;
	
	private Map<Integer, SecuritySystemEntity> securitySystemsReadyForPairing = new HashMap<>();
	
	
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
	
	public void startPairing(Integer securitySystemId, SecuritySystemStartPairRequest sstrp) {
		SecuritySystemEntity sse = new SecuritySystemEntity();
		sse.setId(securitySystemId);
		sse.setName(sstrp.getName());

		securitySystemsReadyForPairing.put(sse.getId(), sse);
    }
	
	public SecuritySystemEntity finishPairing(@PathVariable Integer securitySystemId, @RequestBody @Valid SecuritySystemFinishPairRequest ssfpr) {
		SecuritySystemEntity sse = securitySystemsReadyForPairing.get(securitySystemId);
		CommonUtils.nullThenThrow(sse, new IllegalArgumentException("passed security system is not ready for pairing!"));
		securitySystemsReadyForPairing.remove(sse.getId());
		sse.setAuthToken(CommonUtils.generateAuthToken());
		
		return save(sse);
	}
	
	private SecuritySystemEntity save(SecuritySystemEntity sse) {
		return securitySystemEntityRepository.save(sse);
	}
    
}
