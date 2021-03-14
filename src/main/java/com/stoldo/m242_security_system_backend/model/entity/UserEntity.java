package com.stoldo.m242_security_system_backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stoldo.m242_security_system_backend.shared.util.CommonUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
	
	@JsonIgnore
	@NotNull
	@Column(name="password")
	private String password;
	
	@NotNull
	@Email(regexp = CommonUtils.EMAIL_REGEXP, message = CommonUtils.EMAIL_REGEXP_MESSAGE)
	@Column(name = "email", nullable = true, unique = true)
	private String email;
	
	@NotNull
	@Column(name = "rfid_uuid", nullable = true, unique = true)
	private String rfidUUID;
    
}
