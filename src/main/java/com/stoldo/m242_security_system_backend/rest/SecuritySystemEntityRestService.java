package com.stoldo.m242_security_system_backend.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        securitySystemEntityService.delete(id);
    }
    
}
