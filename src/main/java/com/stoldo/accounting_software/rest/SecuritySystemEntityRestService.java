package com.stoldo.accounting_software.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.stoldo.accounting_software.model.api.SecuritySystemFinishPairRequest;
import com.stoldo.accounting_software.model.api.SecuritySystemStartPairRequest;
import com.stoldo.accounting_software.model.entity.SecuritySystemEntity;
import com.stoldo.accounting_software.service.SecuritySystemEntityService;


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
    public void startPairing(@PathVariable Integer securitySystemId, @RequestBody @Valid SecuritySystemStartPairRequest sstrp) {
    	securitySystemEntityService.startPairing(securitySystemId, sstrp);
    }
    
    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "{securitySystem}/pair/finish", method = RequestMethod.POST)
    public SecuritySystemEntity finishPairing(@PathVariable Integer securitySystemId, @RequestBody @Valid SecuritySystemFinishPairRequest ssfpr) {
    	return securitySystemEntityService.finishPairing(securitySystemId, ssfpr);
    }
    
}
