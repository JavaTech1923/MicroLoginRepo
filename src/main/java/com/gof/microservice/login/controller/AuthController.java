package com.gof.microservice.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gof.microservice.login.dto.AuthRequest;
import com.gof.microservice.login.entity.UserCredential;
import com.gof.microservice.login.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public String addNewUser(@RequestBody UserCredential credential) {
		return authService.saveUser(credential);
		
	}
	
	@PostMapping("/token")
	public String getToken(@RequestBody AuthRequest authRequest) {
		System.out.println("authRequest.getUserName()   :"+authRequest.getName());
		System.out.println("authRequest.getPassword()  :"+authRequest.getPassword());
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
		if(authenticate.isAuthenticated()) {
			System.out.println("authRequest.getUserName()   :"+authRequest.getName());
			System.out.println("authRequest.getPassword()  :"+authRequest.getPassword());
			return authService.generateToken(authRequest.getName());
		}else {
			throw new RuntimeException("Invalid Access");
		}
		
	}
	
	@GetMapping("/validate")
	public String validateToken(@RequestParam("token") String token) {
		System.out.println("token   :"+token);
		authService.validateToken(token);
		
		return "validated token";
		
		
	}

}
