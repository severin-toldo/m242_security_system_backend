package com.stoldo.accounting_software.model.api;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecuritySystemStartPairRequest {
	
	@NotNull
    private String name;

}
