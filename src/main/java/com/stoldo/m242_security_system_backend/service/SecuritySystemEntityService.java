package com.stoldo.m242_security_system_backend.service;

import java.util.List;

import com.stoldo.m242_security_system_backend.model.SecuritySystemHistoryType;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemHistoryEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stoldo.m242_security_system_backend.model.ErrorCode;
import com.stoldo.m242_security_system_backend.model.ErrorCodeException;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemEntity;
import com.stoldo.m242_security_system_backend.repository.SecuritySystemEntityRepository;


@Service
public class SecuritySystemEntityService {
	
	@Autowired
	private SecuritySystemEntityRepository securitySystemEntityRepository;

	@Autowired
	private SecuritySystemHistoryEntityService securitySystemHistoryEntityService;
	
	
	public List<SecuritySystemEntity> getAll() {
    	return securitySystemEntityRepository.findAll();
    }
	
	public SecuritySystemEntity getById(Integer id) {
		SecuritySystemEntity sse = securitySystemEntityRepository
				.findById(id)
				.orElseThrow(() -> new ErrorCodeException(ErrorCode.E1002, HttpStatus.BAD_REQUEST, "SecuritySystemEntity with id " + id + " does not exist!"));

		SecuritySystemHistoryEntity sshe = securitySystemHistoryEntityService.getLatestHistory(sse);

		if (sshe != null) {
			sse.setStatus(sshe.getType());
		}

		return sse;
	}
	
	public SecuritySystemEntity getByIdAndAuthToken(Integer id, String authToken) {
		SecuritySystemEntity sse = getById(id);
		
		if (StringUtils.equals(sse.getAuthToken(), authToken)) {
			return sse;	
		}
		
		throw new ErrorCodeException(ErrorCode.E1003, HttpStatus.BAD_REQUEST);
	}
	
	public SecuritySystemEntity save(SecuritySystemEntity sse) {
		return securitySystemEntityRepository.save(sse);
	}

	public void delete(int id) {
		securitySystemEntityRepository.deleteById(id);
	}


}
