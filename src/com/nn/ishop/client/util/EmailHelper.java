package com.nn.ishop.client.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.nn.ishop.license.PropertyUtil;

/**
 * @author MiMoon
 *
 */
public class EmailHelper {
	public static EmailHelper instance;
	public static PropertyUtil systemProps = new PropertyUtil();
	public static String fromMail = systemProps.getProperty("system.email");
	public static String password = systemProps.getProperty("system.email.password");
	public static String MAIL_SMTP_HOST = systemProps.getProperty("MAIL.SMTP.HOST");
	public static String MAIL_SMTP_PORT = systemProps.getProperty("MAIL.SMTP.PORT");
	public static String MAIL_SMTP_AUTH = systemProps.getProperty("MAIL.SMTP.AUTH");
	public static String MAIL_SMTP_STARTTLS_ENABLE = systemProps.getProperty("MAIL.SMTP.STARTTLS.ENABLE");
	
	/**
	 * Send email using default mail of system
	 * @param toMail To mail
	 * @param subject Subject of mail
	 * @param message Message of mail
	 * @return true if sending successfully/false if sending fail
	 */
	public boolean sendEmail(String toMail, String subject, String message){
		boolean isOK = false;
		Properties properties = new Properties();
		properties.put("mail.smtp.host", MAIL_SMTP_HOST);
		properties.put("mail.smtp.port", MAIL_SMTP_PORT);
		properties.put("mail.smtp.auth", MAIL_SMTP_AUTH);
		properties.put("mail.smtp.starttls.enable", MAIL_SMTP_STARTTLS_ENABLE);
		try {
			Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromMail, password);
				}
			});
			// Create a default MimeMessage object.
			MimeMessage mimeMessage = new MimeMessage(session);
			// Set From: header field of the header.
			mimeMessage.setFrom(new InternetAddress(fromMail));
			// Set To: header field of the header.
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
			// Set Subject: header field
			mimeMessage.setSubject(subject);
			// Now set the actual message
			mimeMessage.setText(message);
			// Send message
			Transport.send(mimeMessage);
			isOK = true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
			isOK = false;
		} catch (Exception ex){
			ex.printStackTrace();
			isOK = false;
		}
		return isOK;
	}
	
	public static EmailHelper getInstance(){
		if(instance == null){
			instance = new EmailHelper();
		}
		return instance;
	}

}
