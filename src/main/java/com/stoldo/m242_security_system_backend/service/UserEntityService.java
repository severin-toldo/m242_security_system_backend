package com.stoldo.m242_security_system_backend.service;

import java.util.Collections;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.stoldo.m242_security_system_backend.model.api.UserRegistrationRequest;
import com.stoldo.m242_security_system_backend.model.entity.UserEntity;
import com.stoldo.m242_security_system_backend.repository.UserEntityRepository;

@Service
public class UserEntityService {

	@Autowired
    private UserEntityRepository userEntityRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    
    public UserEntity getById(Integer id) {
        return userEntityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + "not found!"));
    }
    
    public UserEntity getByEmail(String email) {
        return userEntityRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found!"));
    }
    
    public UserEntity getByRfidUUID(String rfidUUID) {
        return userEntityRepository.findByRfidUUID(rfidUUID).orElseThrow(() -> new EntityNotFoundException("User with rfidUUID " + rfidUUID + " not found!"));
    }

    public UserEntity saveUser(UserEntity ue) {
        return userEntityRepository.saveAndFlush(ue);
    }
    
    public UserEntity register(UserRegistrationRequest urr) {
		UserEntity ue = new UserEntity();
		ue.setFirstname(urr.getFirstname());
		ue.setLastname(urr.getLastname());
		ue.setEmail(urr.getEmail());
		ue.setPassword(passwordEncoder.encode(urr.getPassword()));
		
	    return saveUser(ue);	
    }
    
    public UserDetails getUserDetailsByEmail(String email) {
        UserEntity ue = getByEmail(email);
        return new User(ue.getEmail(), ue.getPassword(), Collections.emptyList());
    }
 }
