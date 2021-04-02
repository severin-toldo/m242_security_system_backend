package com.stoldo.m242_security_system_backend.config.listener;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import com.stoldo.m242_security_system_backend.shared.HerokuConfigVarKeys;
import com.stoldo.m242_security_system_backend.shared.util.CommonUtils;

import lombok.extern.java.Log;
import static com.pivovarit.function.ThrowingSupplier.unchecked;

@Log
public class ApplicationEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
	
	private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
	
	
	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		ConfigurableEnvironment env = event.getEnvironment();
		
		String herokuDatabaseUrl = System.getenv(HerokuConfigVarKeys.DATABASE_URL);
		String herokuMailgunApiKey = System.getenv(HerokuConfigVarKeys.MAILGUN_API_KEY);
		String herokuMailgunDomain = System.getenv(HerokuConfigVarKeys.MAILGUN_DOMAIN);

		if (CommonUtils.isProd(env.getActiveProfiles())) {
			Properties props = new Properties();
			
			if (herokuDatabaseUrl != null) {
				URI dbUri = (unchecked(() -> new URI(herokuDatabaseUrl))).get();
				String username = dbUri.getUserInfo().split(":")[0];
				String password = dbUri.getUserInfo().split(":")[1];
				String host = dbUri.getHost();
				String port = String.valueOf(dbUri.getPort());
				String databaseName = dbUri.getPath().substring(1);
				String url = MessageFormat.format("jdbc:postgresql://{0}:{1}/{2}?sslmode=require&serverTimezone=Europe/Berlin", host, port, databaseName);
				
				props.put("spring.datasource.driverClassName", POSTGRESQL_DRIVER);
				props.put("spring.datasource.username", username);
			    props.put("spring.datasource.password", password);
			    props.put("spring.datasource.url", url);
			    props.put("datasource.name", databaseName);
			}
			
			if (herokuMailgunApiKey != null && herokuMailgunDomain != null) {
				props.put("mail.mailgun.api.key", herokuMailgunApiKey);
				props.put("mail.mailgun.api.domain", herokuMailgunDomain);	
			}
			
		    env.getPropertySources().addFirst(new PropertiesPropertySource("customProperties", props));
		}
		
		log.log(Level.INFO, "Database URL is configured to: " + CommonUtils.getSpringProperty("spring.datasource.url", env.getPropertySources()));
	}
}
