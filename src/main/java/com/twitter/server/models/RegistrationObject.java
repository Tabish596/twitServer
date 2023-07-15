package com.twitter.server.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class RegistrationObject {
	
	private String firstName;
	private String lastName;
	private String email;
	private Date dob;
	
}
