package com.stoldo.m242_security_system_backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "security_system")
public class SecuritySystemEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@NotNull
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@NotNull
	@JsonIgnore
	@Column(name = "auth_token", nullable = false)
	private String authToken;

}
