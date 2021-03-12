package com.stoldo.accounting_software.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.stoldo.accounting_software.model.SecuritySystemHistoryType;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "security_system_history")
public class SecuritySystemHistoryEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "datetime", nullable = false)
	private Date datetime;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private SecuritySystemHistoryType type;
	
	@NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id_fk")
    private UserEntity user;
	
	@NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="security_system_id_fk")
    private SecuritySystemEntity securitySystem;

}
