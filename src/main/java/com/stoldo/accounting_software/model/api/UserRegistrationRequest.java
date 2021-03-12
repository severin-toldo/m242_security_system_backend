package com.stoldo.accounting_software.model.api;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.stoldo.accounting_software.shared.util.CommonUtils;

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

}
