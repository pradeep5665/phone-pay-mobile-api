package org.uhc.util;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:email.properties")
public class EmailService {
	private static final Logger LOG = LogManager.getLogger(EmailService.class.getName());

	private Properties properties = System.getProperties();

	@Value("${homeowner.smtp.username}")
	private String smtpUsername;

	@Value("${homeowner.smtp.host}")
	private String smtpHost;

	public EmailService() {
		// default constructor
	}

	public boolean sendEmail(EmailBean emailBean) {
		boolean isEmailSent = false;
		try {
			MimeMessage message = new MimeMessage(Session.getInstance(properties));
			properties.setProperty("mail.smtp.host", smtpHost);
			message.setFrom(new InternetAddress(smtpUsername));

			message.setRecipients(javax.mail.Message.RecipientType.TO, emailBean.getRecipients());
			message.setSentDate(new Date());
			message.setSubject(emailBean.getSubject());
			message.setContent(emailBean.getMessageBody(), "text/html; charset=utf-8");
			javax.mail.Transport.send(message);
			isEmailSent = true;
			LOG.info("Success - : {}", getLogMessage(emailBean));
		} catch (MessagingException ex) {
			isEmailSent = false;
			LOG.error("Problem - : {}", getLogMessage(emailBean), ex);
		}
		return isEmailSent;
	}

	private String getLogMessage(EmailBean emailBean) {
		String sendTo = "";

		if (emailBean.getRecipients() == null) {
			sendTo = "null";
		} else {
			StringBuilder str = new StringBuilder();
			for (InternetAddress recipient : emailBean.getRecipients()) {
				str = str.append(recipient.toString() + ". ");
			}
			sendTo = str.toString();
		}
		return "Email: subject=" + emailBean.getSubject() + ", sendTo=" + sendTo;
	}
}