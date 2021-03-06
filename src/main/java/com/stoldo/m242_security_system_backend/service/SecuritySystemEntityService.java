package com.stoldo.m242_security_system_backend.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import com.stoldo.m242_security_system_backend.model.SecuritySystemHistoryType;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemHistoryEntity;
import com.stoldo.m242_security_system_backend.model.entity.UserEntity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stoldo.m242_security_system_backend.model.ErrorCode;
import com.stoldo.m242_security_system_backend.model.ErrorCodeException;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemEntity;
import com.stoldo.m242_security_system_backend.repository.SecuritySystemEntityRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SecuritySystemEntityService {
	
	@Autowired
	private SecuritySystemEntityRepository securitySystemEntityRepository;

	@Autowired
	private SecuritySystemHistoryEntityService securitySystemHistoryEntityService;
	
	@Autowired
	private MqttService mqttService;
	
	private static final String MQTT_CHANGE_STATUS_TOPIC = "security-system/{0}/status/";
	
	
	public List<SecuritySystemEntity> getAll() {
    	return securitySystemEntityRepository.findAll().stream()
				.peek(sse -> sse.setStatus(getStatus(sse)))
				.collect(Collectors.toList());
    }

    private SecuritySystemHistoryType getStatus(SecuritySystemEntity sse) {
		SecuritySystemHistoryEntity sshe = securitySystemHistoryEntityService.getLatestHistory(sse);

		return sshe != null ? sshe.getType() : null;
	}
	
	public SecuritySystemEntity getById(Integer id) {
		SecuritySystemEntity sse = securitySystemEntityRepository
				.findById(id)
				.orElseThrow(() -> new ErrorCodeException(ErrorCode.E1002, HttpStatus.BAD_REQUEST, "SecuritySystemEntity with id " + id + " does not exist!"));

		sse.setStatus(getStatus(sse));

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

	@Transactional
	public void delete(int id) {
		SecuritySystemEntity sse = getById(id);
		securitySystemHistoryEntityService.deleteBySecuritySystem(sse);
		securitySystemEntityRepository.delete(sse);
	}
	
	public void changeStatus(SecuritySystemEntity sse, UserEntity ue, SecuritySystemHistoryType status) throws Exception {
		String topic = MessageFormat.format(MQTT_CHANGE_STATUS_TOPIC, sse.getId());
		mqttService.publish(topic, ue.getRfidUUID());	
	}

}
