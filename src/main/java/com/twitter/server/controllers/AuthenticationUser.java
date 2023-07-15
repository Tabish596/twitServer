package com.twitter.server.controllers;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.server.exceptions.EmailAlreadyTakenException;
import com.twitter.server.exceptions.EmailFailedToSendException;
import com.twitter.server.exceptions.UserDoesNotExistException;
import com.twitter.server.models.RegistrationObject;
import com.twitter.server.models.User;
import com.twitter.server.services.UserServices;

@RestController
@RequestMapping("/auth")
public class AuthenticationUser {
	
	private final UserServices userService;
	
	@Autowired
	public AuthenticationUser(UserServices userService) {
		this.userService=userService;
	}
	
	@ExceptionHandler({EmailAlreadyTakenException.class})
	public ResponseEntity<String> handleEmailTaken(){
		return new ResponseEntity<String>("Email Provided is already taken",HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler({UserDoesNotExistException.class})
	public ResponseEntity<String> handleUserNotFound(){
		return new ResponseEntity<String>("User Does not exist",HttpStatus.CONFLICT);
	}
	
	@PostMapping("/register")
	public User registerUser(@RequestBody RegistrationObject regOb) {
		return userService.registerUser(regOb);
	}
	
	@PutMapping("/update/phone")
	public User updatePhone(@RequestBody LinkedHashMap<String, String> body) {
		String phone = body.get("phone");
		String userName = body.get("userName");
		User user = userService.getUserByUserName(userName);
		
		user.setPhone(phone);
		return userService.updateUser(user);
	}
	
	@ExceptionHandler({EmailFailedToSendException.class})
	public ResponseEntity<String> handleFailedEmail(){
		return new ResponseEntity<String>("Email failed to send Exception",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/email/code")
	public ResponseEntity<String> createEmailVerification(@RequestBody LinkedHashMap<String, String> body){
		userService.generateEmailVerification(body.get("username"));
		return new ResponseEntity<String>("Email verification sent",HttpStatus.OK);
	}
	
	
}
