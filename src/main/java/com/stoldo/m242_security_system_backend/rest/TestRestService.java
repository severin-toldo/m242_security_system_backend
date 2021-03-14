package com.stoldo.m242_security_system_backend.rest;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.stoldo.m242_security_system_backend.model.api.TestRequest;


@RestController
@RequestMapping("/api/test/{var}")
public class TestRestService {

	
    @RequestMapping(method = RequestMethod.GET)
    public TestRequest get(@PathVariable String var, @RequestHeader("my_header") String header, @RequestParam(required = false) String param) {
    	System.err.println("var: " + var);
    	System.err.println("header: " + header);
    	System.err.println("param: " + param);
    	
    	TestRequest tr = new TestRequest();
    	tr.setNumber(1);
    	tr.setText("hello world");
    	
    	return tr;
    }
	
    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public TestRequest post(@RequestBody @Valid TestRequest tr) {
    	System.err.println("text: " + tr.getText());
    	System.err.println("number: " + tr.getNumber());
    	
    	tr.setNumber(245);
    	
    	return tr;
    }
    
    @RequestMapping(value="date", method = RequestMethod.GET)
    public TestRequest get(@RequestBody @Valid TestRequest tr) {
    	System.err.println("date: " + tr.getDate());
    	return null;
    }
    
}
