package com.stoldo.accounting_software.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.stoldo.accounting_software.model.entity.UserEntity;

@Service
public class CurrentSessionService {
	
	@Autowired
	private UserEntityService userEntityService;
	
	public UserEntity getLoggedInUserEntity() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return userEntityService.getByEmail(authentication.getName());
	}

	public boolean isRequestorSameAsUser(Integer userId) {
		UserEntity loggedInUserEntity = getLoggedInUserEntity();
		return loggedInUserEntity.getId().equals(userId);
	}
	
	public boolean isRequestorSameAsUser(String email) {
		UserEntity loggedInUserEntity = getLoggedInUserEntity();
		return StringUtils.equals(loggedInUserEntity.getEmail(), email);
	}
}
