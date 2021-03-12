package com.stoldo.accounting_software.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.stoldo.accounting_software.model.api.SecuritySystemHistoryCreateRequest;
import com.stoldo.accounting_software.model.entity.SecuritySystemEntity;
import com.stoldo.accounting_software.model.entity.SecuritySystemHistoryEntity;
import com.stoldo.accounting_software.service.SecuritySystemEntityService;
import com.stoldo.accounting_software.service.SecuritySystemHistoryEntityService;


@RestController
@RequestMapping("/api/security-system/{securitySystemId}/history")
public class SecuritySystemHistoryEntityRestService {
	
	@Autowired
    private SecuritySystemHistoryEntityService securitySystemHistoryEntityService;

	@Autowired
    private SecuritySystemEntityService securitySystemEntityService;

	
	// TODO secure
    @RequestMapping(method = RequestMethod.GET)
    public List<SecuritySystemHistoryEntity> getHistory(@PathVariable Integer securitySystemId) {
    	SecuritySystemEntity sse = securitySystemEntityService.getById(securitySystemId);
    	return securitySystemHistoryEntityService.getHistory(sse);
    }
	
    // TODO permitt all
    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public SecuritySystemHistoryEntity addHistory(@RequestHeader("auth_token") String authToken, @PathVariable Integer securitySystemId, @RequestBody @Valid SecuritySystemHistoryCreateRequest sshcr) {
    	SecuritySystemEntity sse = securitySystemEntityService.getByIdAndAuthToken(securitySystemId, authToken);
    	return securitySystemHistoryEntityService.addHistory(sse, sshcr);
    }
    
}
