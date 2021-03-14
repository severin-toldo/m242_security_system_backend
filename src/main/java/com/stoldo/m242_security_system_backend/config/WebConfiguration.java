package com.stoldo.m242_security_system_backend.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.stoldo.m242_security_system_backend.shared.Constants;

import org.springframework.format.datetime.DateFormatter;


@Configuration
public class WebConfiguration implements WebMvcConfigurer {
	
	@Value("${security.allowed-origins}")
	private String allowedOrigins;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedMethods("*")
            .allowedHeaders("*")
            .exposedHeaders(
                  "Access-Control-Allow-Headers",
                  "Access-Control-Allow-Origin",
                  "Access-Control-Expose-Headers",
                  "Authorization",
                  "Cache-Control",
                  "Content-Type",
                  "Origin"
    		)
            .allowedOrigins(allowedOrigins.split(","))
            .allowCredentials(true);
    }
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new DateFormatter(Constants.DATE_FORMAT));
	}
	
}
