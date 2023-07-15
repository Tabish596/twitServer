package com.twitter.server.exceptions;

public class EmailFailedToSendException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public EmailFailedToSendException() {
		super("Error while sending email");
	}
}
