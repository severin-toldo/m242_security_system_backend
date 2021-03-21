package com.stoldo.m242_security_system_backend.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.stoldo.m242_security_system_backend.model.api.SecuritySystemFinishPairRequest;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemEntity;


@RestController
@RequestMapping("/api/security-system/pair")
public class SecuritySystemPairRestService {

	@Autowired
    private SecuritySystemPairRestService securitySystemPairRestService;

	
	@RequestMapping(value = "{securitySystemId}/status", method = RequestMethod.GET)
    public boolean isPaired(@PathVariable Integer securitySystemId, @RequestHeader("auth_token") String securitySystemAuthToken) {
    	return securitySystemPairRestService.isPaired(securitySystemId, securitySystemAuthToken);
    }
	
    @RequestMapping(value = "{securitySystemId}/start", method = RequestMethod.POST)
    public String startPairing(@PathVariable Integer securitySystemId, @RequestHeader("auth_token") String securitySystemAuthToken) {
    	return securitySystemPairRestService.startPairing(securitySystemId, securitySystemAuthToken);
    }
    
    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "finish", method = RequestMethod.POST)
    public SecuritySystemEntity finishPairing(@RequestBody @Valid SecuritySystemFinishPairRequest ssfpr) {
    	return securitySystemPairRestService.finishPairing(ssfpr);
    }
    
}
