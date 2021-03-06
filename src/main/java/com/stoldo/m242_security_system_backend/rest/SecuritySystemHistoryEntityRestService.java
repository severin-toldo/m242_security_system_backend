package com.stoldo.m242_security_system_backend.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.stoldo.m242_security_system_backend.model.api.SecuritySystemHistoryCreateRequest;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemEntity;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemHistoryEntity;
import com.stoldo.m242_security_system_backend.service.SecuritySystemEntityService;
import com.stoldo.m242_security_system_backend.service.SecuritySystemHistoryEntityService;


@RestController
@RequestMapping("/api/security-system/{securitySystemId}/history")
public class SecuritySystemHistoryEntityRestService {
	
	@Autowired
    private SecuritySystemHistoryEntityService securitySystemHistoryEntityService;

	@Autowired
    private SecuritySystemEntityService securitySystemEntityService;

	
    @RequestMapping(method = RequestMethod.GET)
    public List<SecuritySystemHistoryEntity> getHistory(@PathVariable Integer securitySystemId) {
    	SecuritySystemEntity sse = securitySystemEntityService.getById(securitySystemId);
    	return securitySystemHistoryEntityService.getHistory(sse);
    }
	
    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public boolean addHistory(@RequestHeader("auth_token") String authToken, @PathVariable Integer securitySystemId, @RequestBody @Valid SecuritySystemHistoryCreateRequest sshcr) {
    	SecuritySystemEntity sse = securitySystemEntityService.getByIdAndAuthToken(securitySystemId, authToken);
    	SecuritySystemHistoryEntity savedHistory = securitySystemHistoryEntityService.addHistory(sse, sshcr);
    	
    	return savedHistory != null;
    }
    
}
