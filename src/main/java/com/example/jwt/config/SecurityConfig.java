package com.example.jwt.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.websocket.Session;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JWTFilter jwtFilter;
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    	logger.info("securityFilterChain bean");
    	return httpSecurity
    		.csrf(customizer -> customizer.disable())
    		.authorizeHttpRequests(request -> request
    				.requestMatchers("/register", "/login")
    				.permitAll()
    				.anyRequest().authenticated())
    		//.formLogin(Customizer.withDefaults())
    		.httpBasic(Customizer.withDefaults())
    		.sessionManagement(session -> 
    				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
    		.build();
    	
	}

    @Bean
    public AuthenticationProvider authenticationProvider() {
    	logger.info("creating bean authenticationProvider");
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setPasswordEncoder(new BCryptPasswordEncoder());
    	provider.setUserDetailsService(userDetailsService);
    	return provider;
    }
		
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    	logger.info("creating bean AuthenticationManager");
    	return configuration.getAuthenticationManager();
    }
}
