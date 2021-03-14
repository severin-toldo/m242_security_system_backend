package com.stoldo.m242_security_system_backend.model.api;


import javax.validation.constraints.NotNull;

import com.stoldo.m242_security_system_backend.model.SecuritySystemHistoryType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecuritySystemHistoryCreateRequest {
	
	@NotNull
	private SecuritySystemHistoryType type;
	
	@NotNull
    private String userRfidUUID;

}
