package com.stoldo.m242_security_system_backend.model.api;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestRequest {
	
	private String text;
	private int number;
	private Date date;

}
