package com.example.jwt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jwt.model.UserPrincipal;
import com.example.jwt.model.Users;
import com.example.jwt.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("check username in database: "+username);
		Users users = userRepository.findByUsername(username);
		
		if(users == null) {
			logger.info("User not found in database");
			throw new UsernameNotFoundException(username);
		}
		
		logger.info("fetched user from database: "+users);
		return new UserPrincipal(users);
	}

}
