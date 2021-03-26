package com.stoldo.m242_security_system_backend;

import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.stoldo.m242_security_system_backend.config.listener.ApplicationEnvironmentPreparedEventListener;


@SpringBootApplication
public class ServerMainController {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(ServerMainController.class)
	            .listeners(new ApplicationEnvironmentPreparedEventListener())
	            .run();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("backuptool24@gmail.com");
		mailSender.setPassword("?m6X7RgwH[3^6>E9E4gQnXFE*r,ENkaUL236,)Dykwcg2@Fxv&");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

}
