package com.stoldo.m242_security_system_backend;

import java.io.IOException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
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

}
