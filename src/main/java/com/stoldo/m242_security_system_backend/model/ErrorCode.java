package com.stoldo.m242_security_system_backend.model;


public enum ErrorCode {
	E1000("Unknown Error!"),
	E1001("Authentication attempt failed!"),
	E1002("Not found!"),
	E1003("Security system id or auth_token is wrong!"),
	E1004("Pairing codes do not match!"),
	E1005("E-Mail or Password is wrong!"),
	E1006("Error while sending MQTT message!");
	
	
	private String message;

	private ErrorCode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
