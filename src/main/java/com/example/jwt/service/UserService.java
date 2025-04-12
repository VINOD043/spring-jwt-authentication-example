package com.example.jwt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt.model.Users;
import com.example.jwt.repository.UserRepository;

@Service
public class UserService {
	
	Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserRepository repository;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JWTService jwtService;
	
	public Users register(Users users) {
		users.setPassword(encoder.encode(users.getPassword()));
		return repository.save(users);
	}

	public String verify(Users users) {
		logger.info("check if user can be authenticated");
		Authentication authentication = 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
		
		if(authentication.isAuthenticated()) {
			logger.info("user is authenticated");
			return jwtService.generateToken(users.getUsername());
		}
		logger.info("user cannot be authenticated");
		return "fail";
	}
	
}
