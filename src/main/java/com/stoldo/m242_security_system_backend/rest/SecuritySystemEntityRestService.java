package com.stoldo.m242_security_system_backend.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.stoldo.m242_security_system_backend.model.api.SecuritySystemFinishPairRequest;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemEntity;
import com.stoldo.m242_security_system_backend.service.SecuritySystemEntityService;


@RestController
@RequestMapping("/api/security-system")
public class SecuritySystemEntityRestService {

	@Autowired
    private SecuritySystemEntityService securitySystemEntityService;

	
	@RequestMapping(method = RequestMethod.GET)
    public List<SecuritySystemEntity> getAll() {
    	return securitySystemEntityService.getAll();
    }
	
    @RequestMapping(value = "{securitySystem}/pair/start", method = RequestMethod.POST)
    public String startPairing(@PathVariable Integer securitySystemId, @RequestHeader("auth_token") String securitySystemAuthToken) {
    	return securitySystemEntityService.startPairing(securitySystemId, securitySystemAuthToken);
    }
    
    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "{securitySystem}/pair/finish", method = RequestMethod.POST)
    public SecuritySystemEntity finishPairing(@PathVariable Integer securitySystemId, @RequestBody @Valid SecuritySystemFinishPairRequest ssfpr) {
    	return securitySystemEntityService.finishPairing(securitySystemId, ssfpr);
    }
    
}
