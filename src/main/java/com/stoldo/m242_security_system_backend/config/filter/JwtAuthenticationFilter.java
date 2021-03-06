package com.stoldo.m242_security_system_backend.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stoldo.m242_security_system_backend.model.ErrorCode;
import com.stoldo.m242_security_system_backend.model.ErrorCodeException;
import com.stoldo.m242_security_system_backend.model.api.UserLoginRequest;
import com.stoldo.m242_security_system_backend.model.entity.UserEntity;
import com.stoldo.m242_security_system_backend.service.UserEntityService;
import com.stoldo.m242_security_system_backend.shared.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;


@Log
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserEntityService userEntityService;
	private final String jwtSecret;
	private final Integer jwtTokenValidityInHours;
	
	private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
        	UserLoginRequest ulr = objectMapper.readValue(req.getInputStream(), UserLoginRequest.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(ulr.getEmail(), ulr.getPassword(), Collections.emptyList()));
        } catch (IOException ex) {
        	throw new ErrorCodeException(ErrorCode.E1001, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
    	User user = (User) auth.getPrincipal();
        UserEntity ue = userEntityService.getByEmail(user.getUsername());
    	
        String token = Constants.JWT_TOKEN_PREFIX + JWT.create()
        		.withSubject(ue.getEmail())
                .withExpiresAt(DateUtils.addHours(new Date(), jwtTokenValidityInHours))
                .sign(Algorithm.HMAC256(jwtSecret.getBytes()));

		res.addHeader(Constants.AUTH_HEADER_NAME, token);
		res.getWriter().write(objectMapper.writeValueAsString(ue));
		res.getWriter().flush();
		res.getWriter().close();
        
        log.log(Level.INFO, "Generated JWT token " + token + " for user " + user.getUsername());
    }
    
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException ae) throws IOException, ServletException {
        throw new ErrorCodeException(ErrorCode.E1005, HttpStatus.BAD_REQUEST, ae);
    }
}
