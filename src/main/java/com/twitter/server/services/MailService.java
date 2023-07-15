package com.twitter.server.services;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.sun.mail.iap.ByteArray;
import com.twitter.server.exceptions.EmailFailedToSendException;

@Service
public class MailService {
	private final Gmail gmail;
	
	@Autowired
	public MailService(Gmail gmail) {
		this.gmail = gmail;
	}
	
	public void sendEmail(String toAddress, String subject, String content) throws Exception {
		Properties prop = new Properties();
		Session session = Session.getInstance(prop,null);
		MimeMessage email = new MimeMessage(session);
		try {
			email.setFrom(new InternetAddress("tabishakhtarsr@gmail.com"));
			email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toAddress));
			email.setSubject(subject);
			email.setContentID(content);
			
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			email.writeTo(buffer);
			byte[] rawMessageBytes = buffer.toByteArray();
			String encodedEmail = Base64.encodeBase64String(rawMessageBytes);
			
			Message message = new Message();
			message.setRaw(encodedEmail);
			message = gmail.users().messages().send(encodedEmail, message).execute();		
			}
		catch( GoogleJsonResponseException e) {
			GoogleJsonError error = e.getDetails();
			if(error.getCode() == 403) {
				throw e;
			}
		}
		catch(Exception e) {
			throw new EmailFailedToSendException();
		}
	}
	
}
