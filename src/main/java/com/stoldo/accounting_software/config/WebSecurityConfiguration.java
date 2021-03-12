package com.stoldo.accounting_software.config;

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

import com.stoldo.accounting_software.config.filter.JwtAuthorizationFilter;
import com.stoldo.accounting_software.config.filter.JwtLoginFilter;
import com.stoldo.accounting_software.config.filter.RequestLogFilter;
import com.stoldo.accounting_software.service.UserEntityService;

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
			.anyRequest()
			.authenticated();

		http
			.addFilter(new JwtLoginFilter(authenticationManager(), userEntityService, jwtSecret, jwtTokenValidityInHours))
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
