package com.stoldo.m242_security_system_backend.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.stoldo.m242_security_system_backend.model.api.UserRegistrationRequest;
import com.stoldo.m242_security_system_backend.model.entity.UserEntity;
import com.stoldo.m242_security_system_backend.service.UserEntityService;


@RestController
@RequestMapping("/api/users")
public class UserEntityRestService {

	@Autowired
    private UserEntityService userEntityService;

	
    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public UserEntity register(@RequestBody @Valid UserRegistrationRequest urr) {
    	return userEntityService.register(urr);
    }
    
}
