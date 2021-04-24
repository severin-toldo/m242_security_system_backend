package com.stoldo.m242_security_system_backend.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stoldo.m242_security_system_backend.model.ErrorCode;
import com.stoldo.m242_security_system_backend.model.ErrorCodeException;

@Service
public class MqttService {
	
	@Autowired
	private MqttClient mqttClient;
	
	
	public void publish(String topic, String payload) {
		try {
			mqttClient.connect();
			mqttClient.publish(topic, payload.getBytes(), 2, true);
			mqttClient.disconnect();
		} catch (Exception e) {
			throw new ErrorCodeException(ErrorCode.E1006, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
