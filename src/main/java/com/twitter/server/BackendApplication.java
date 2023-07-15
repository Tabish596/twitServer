package com.twitter.server;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.twitter.server.models.Role;
import com.twitter.server.models.User;
import com.twitter.server.repository.RoleRepository;
import com.twitter.server.repository.UserRepository;
import com.twitter.server.services.UserServices;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	
	@Bean
	CommandLineRunner run(RoleRepository roleRepo, UserServices userService) {
		return arg -> {
			roleRepo.save(new Role(1,"User"));
//			User user = new User();
//			user.setFirstName("Tabish");
//			user.setLastName("Akhtar");
//			userService.registerUser(user);
		};
	}
}
