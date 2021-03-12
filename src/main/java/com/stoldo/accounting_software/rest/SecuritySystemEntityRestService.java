package com.stoldo.accounting_software.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.stoldo.accounting_software.model.api.UserRegistrationRequest;
import com.stoldo.accounting_software.model.entity.UserEntity;
import com.stoldo.accounting_software.service.UserEntityService;


@RestController
@RequestMapping("/api/security-system/{securitySystem}")
public class SecuritySystemEntityRestService {

	@Autowired
    private UserEntityService userEntityService;

	
    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public UserEntity register(@RequestBody @Valid UserRegistrationRequest urr) {
    	return userEntityService.register(urr);
    }
    
}
