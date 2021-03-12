package com.stoldo.accounting_software.model.api;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.stoldo.accounting_software.model.SecuritySystemHistoryType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecuritySystemHistoryCreateRequest {
	
	@NotNull
	private Date datetime;
	
	@NotNull
	private SecuritySystemHistoryType type;
	
	@NotNull
    private String userEmail;

}
