package com.stoldo.m242_security_system_backend.service;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;



@Service
public class EmailService {
	
	@Value("${mail.mailgun.api.user}")
	private String mailMailgunApiUser;
	
	@Value("${mail.mailgun.api.key}")
	private String mailMailgunApiKey;
	
	@Value("${mail.mailgun.api.domain}")
	private String mailMailgunApiDomain;
	
	@Value("${mail.mailgun.api.base.url}")
	private String mailMailgunApiBaseUrl;
	
	private static final String NOREPLY_FROM_ADDRESS = "noreply@security-system.com";
	private static final String MESSAGES_PATH = "messages";
	private static final String PATH_SEPERATOR = "/";

	
	private RestTemplate mailgunRestTemplate;

	
	@PostConstruct
	private void init() {
		mailgunRestTemplate = new RestTemplateBuilder()
				.basicAuthentication(mailMailgunApiUser, mailMailgunApiKey)
				.rootUri(mailMailgunApiBaseUrl)
				.additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
				.additionalMessageConverters(new FormHttpMessageConverter())
				.build();
	}
	
	public void sendEmail(String subject, String body, String to) {
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("subject", subject);
		map.add("text", body);
		map.add("to", to);
		map.add("from", NOREPLY_FROM_ADDRESS);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		String mailgunSendEmailApiPath = PATH_SEPERATOR + mailMailgunApiDomain + PATH_SEPERATOR + MESSAGES_PATH;
		
		ResponseEntity<String> response = mailgunRestTemplate.exchange(mailgunSendEmailApiPath, HttpMethod.POST, request, String.class);
		
		if (response.getStatusCode() != HttpStatus.OK) {
			String errorMsg = MessageFormat.format("E-Mail to {0} could not be sent! Status code: {1} Message: {2}", to, response.getStatusCodeValue(), response.getBody());
			throw new IllegalArgumentException(errorMsg);
		}
	}
}
