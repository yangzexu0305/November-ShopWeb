package com.november.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.november.system.domain.User;
import com.november.system.service.UserService;

@RestController
public class AuthController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String login(String username) throws JsonProcessingException{
		username = "yangzexu";
		User user = userService.findUserByName(username);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(user);
		return json;
	}
}