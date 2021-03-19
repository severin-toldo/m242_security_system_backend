package com.stoldo.m242_security_system_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.stoldo.m242_security_system_backend.config.filter.JwtAuthorizationFilter;
import com.stoldo.m242_security_system_backend.config.filter.JwtAuthenticationFilter;
import com.stoldo.m242_security_system_backend.config.filter.RequestLogFilter;
import com.stoldo.m242_security_system_backend.service.UserEntityService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserEntityService userEntityService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Value("${security.jwt.secret}")
	private String jwtSecret;

	@Value("${security.jwt.token-validity-in-hours}")
	private Integer jwtTokenValidityInHours;

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.headers().frameOptions().sameOrigin();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/login").permitAll()
			.antMatchers(HttpMethod.POST, "/api/users/register").permitAll()
			.antMatchers(HttpMethod.POST, "/api/security-system/{securitySystemId}/history").permitAll() // end point for device, it will be authenticated via auth token
			.antMatchers(HttpMethod.POST, "/api/security-system/{securitySystem}/pair/finish").permitAll() // end point for device, it will be authenticated via auth token
			.antMatchers("/api/test/**").permitAll()
			.anyRequest()
			.authenticated();

		http
			.addFilter(new JwtAuthenticationFilter(authenticationManager(), userEntityService, jwtSecret, jwtTokenValidityInHours))
			.addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtSecret, userEntityService))
			.addFilterBefore(new RequestLogFilter(), BasicAuthenticationFilter.class);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				return userEntityService.getUserDetailsByEmail(email);
			}
		}).passwordEncoder(passwordEncoder);
	}
}
