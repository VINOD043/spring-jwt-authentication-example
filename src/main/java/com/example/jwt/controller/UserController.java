package com.example.jwt.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.model.Users;
import com.example.jwt.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public Users postMethodName(@RequestBody Users users) {
		logger.info("post request received for /register");
		return userService.register(users);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Users users) {
		logger.info("post request received for /login");
		return userService.verify(users);
	}
}
