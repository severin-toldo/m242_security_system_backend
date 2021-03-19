package com.stoldo.m242_security_system_backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PairableSecuritySystem {
    private Integer id;
    private String pairingCode;
    private String authToken;
}
