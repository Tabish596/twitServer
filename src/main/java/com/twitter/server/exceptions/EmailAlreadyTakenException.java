package com.twitter.server.exceptions;

public class EmailAlreadyTakenException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public EmailAlreadyTakenException() {
		super("Email provided is already taken");
	}
}
