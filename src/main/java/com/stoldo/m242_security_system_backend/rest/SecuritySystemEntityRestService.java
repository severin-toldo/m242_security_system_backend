package com.stoldo.m242_security_system_backend.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stoldo.m242_security_system_backend.model.api.SecuritySystemChangeStatusRequest;
import com.stoldo.m242_security_system_backend.model.entity.SecuritySystemEntity;
import com.stoldo.m242_security_system_backend.model.entity.UserEntity;
import com.stoldo.m242_security_system_backend.service.CurrentSessionService;
import com.stoldo.m242_security_system_backend.service.SecuritySystemEntityService;


@RestController
@RequestMapping("/api/security-system")
public class SecuritySystemEntityRestService {

	@Autowired
    private SecuritySystemEntityService securitySystemEntityService;
	
	@Autowired
    private CurrentSessionService currentSessionService;

	
	@RequestMapping(method = RequestMethod.GET)
    public List<SecuritySystemEntity> getAll() {
    	return securitySystemEntityService.getAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public SecuritySystemEntity getById(@PathVariable int id) {
        return securitySystemEntityService.getById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        securitySystemEntityService.delete(id);
    }
    
    @RequestMapping(value = "{id}/status", method = RequestMethod.POST)
    public void changeStatus(@PathVariable int id, @RequestBody SecuritySystemChangeStatusRequest sscsr) throws Exception {
    	UserEntity ue = currentSessionService.getLoggedInUserEntity();
    	SecuritySystemEntity sse = securitySystemEntityService.getById(id);
    	
    	securitySystemEntityService.changeStatus(sse, ue, sscsr.getStatus());
    }
    
}
