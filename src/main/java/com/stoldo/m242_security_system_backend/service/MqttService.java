package com.stoldo.m242_security_system_backend.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttService {
	
	@Autowired
	private MqttClient mqttClient;
	
	
	@PostConstruct
    private void onInit() throws Exception {
		mqttClient.connect();
    }
	
	public void publish(String topic, String payload) throws MqttPersistenceException, MqttException {
		mqttClient.publish(topic, payload.getBytes(), 2, true);
	}
	
	@PreDestroy
    private void onDestroy() throws Exception {
		mqttClient.disconnect();
    }

}
