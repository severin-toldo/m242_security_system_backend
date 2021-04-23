package com.stoldo.m242_security_system_backend.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MqttConfig {
	
	@Value("${mqtt.client.uri}")
	private String mqttClientUri;

	@Value("${mqtt.client.id}")
	private String mqttClientId;
	
	
	@Bean
	public MqttClient mqttClient() throws MqttException {
		return new MqttClient(mqttClientUri, mqttClientId);
	}

}
