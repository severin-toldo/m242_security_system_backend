package com.stoldo.accounting_software.model.api;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecuritySystemFinishPairRequest {
	
	@NotNull
    private String authToken;

}
