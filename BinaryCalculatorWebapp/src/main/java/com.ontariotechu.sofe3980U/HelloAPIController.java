package com.ontariotechu.sofe3980U;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloAPIController {

	@GetMapping("/helloAPI")
	public String hello(@RequestParam(name="name", required=false, defaultValue="World") String name) {
		return  "Hello "+name+"!";
	}
	
	@GetMapping("/emailAPI")
	public APIResult getSuggestedEmail(@RequestParam(name="fname", required=false, defaultValue="John") String firstName,
                       @RequestParam(name="lname", required=false, defaultValue="Doe") String lastName) {
		String name=firstName+ " " +lastName;
		String suggestedEmail=firstName+ "."+lastName+"@OntarioTechU.net";
		return  new APIResult(name,suggestedEmail);
	}

}