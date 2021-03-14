package com.stoldo.m242_security_system_backend.model.api;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecuritySystemStartPairRequest {
	
	@NotNull
    private String name;

}
