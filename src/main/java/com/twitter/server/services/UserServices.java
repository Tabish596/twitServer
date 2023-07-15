package com.twitter.server.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.server.exceptions.EmailAlreadyTakenException;
import com.twitter.server.exceptions.EmailFailedToSendException;
import com.twitter.server.exceptions.UserDoesNotExistException;
import com.twitter.server.models.RegistrationObject;
import com.twitter.server.models.Role;
import com.twitter.server.models.User;
import com.twitter.server.repository.RoleRepository;
import com.twitter.server.repository.UserRepository;

@Service
public class UserServices {
	
	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final MailService mailService;
	
	@Autowired
	UserServices(UserRepository userRepo,RoleRepository roleRepo,MailService mailService){
		this.userRepo=userRepo;
		this.roleRepo=roleRepo;
		this.mailService=mailService;
	}
	
	public User registerUser(RegistrationObject regOb) {
		
		User user = new User();
		user.setFirstName(regOb.getFirstName());
		user.setLastName(regOb.getLastName());
		user.setEmail(regOb.getEmail());
		user.setDob(regOb.getDob());
		
		String name = user.getFirstName().toLowerCase()+'.'+user.getLastName().toLowerCase();
		String tempName = "";
		while(true) {
			tempName = generateUserName(name);
			if(userRepo.findByUserName(tempName).isEmpty()) {
				break;
			}
		}
		user.setUserName(tempName);
		
		Set<Role> roles = user.getAuthorities();
		roles.add(roleRepo.findByAuthority("USER").get());
		user.setAuthorities(roles);
		try{
			return userRepo.save(user);
		}
		catch(Exception e) {
			throw new EmailAlreadyTakenException();
		}
		
	}
	
	public User getUserByUserName(String userName) {
		return userRepo.findByUserName(userName).orElseThrow(UserDoesNotExistException::new);
	}
	
	private String generateUserName(String name) {
		long generateNumber = (long) Math.floor(Math.random()*1000000);
		return name+generateNumber;
	}
	
	private Long generateVarificationCode() {
		long generateNumber = (long) Math.floor(Math.random()*1000000);
		return generateNumber;
	}
	
	public void generateEmailVerification(String userName) {
		User user = userRepo.findByUserName(userName).orElseThrow(UserDoesNotExistException::new);
		user.setVerification(generateVarificationCode());
		try {
			mailService.sendEmail(user.getEmail(), "Your verification code", "Here is your verification code "+user.getVerification());
			userRepo.save(user);
		} catch (Exception e) {
			throw new EmailFailedToSendException();
		}
	}

	public User updateUser(User user) {
		try {			
			return userRepo.save(user);
		}
		catch(Exception e) {
			throw new UserDoesNotExistException();
		}
	}
}
