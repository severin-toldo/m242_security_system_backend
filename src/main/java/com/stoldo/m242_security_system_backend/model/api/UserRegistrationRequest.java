package com.stoldo.m242_security_system_backend.model.api;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.stoldo.m242_security_system_backend.shared.util.CommonUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {
	
	@NotNull
	private String firstname;
	
	@NotNull
	private String lastname;
	
	@NotNull
	@Email(regexp = CommonUtils.EMAIL_REGEXP, message = CommonUtils.EMAIL_REGEXP_MESSAGE)
    private String email;

	@NotNull
    private String password;
	
	@NotNull
	private String rfidUUID;
    
}
