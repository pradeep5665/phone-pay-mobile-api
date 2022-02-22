/* 
 * ===========================================================================
 * File Name UpdateEmailServiceImpl.java
 * 
 * Created on May 28, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateEmailServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.UpdateEmailRequest;
import org.uhc.controller.envelop.response.UpdateEmailResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.UpdateEmailService;
import org.uhc.util.EmailBean;
import org.uhc.util.EmailService;

/**
 * @author nehas3
 * @date May 28, 2018 
 * @description Implementing UpdateEmailService to get the UpdateEmailResponse. 
 */
@Service
public class UpdateEmailServiceImpl implements UpdateEmailService {

	private static final Logger LOGGER = LogManager.getLogger(UpdateEmailServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private EmailService emailService;

	/**
	 * 
	 * @author nehas3
	 * @date May 30, 2018
	 * @return UpdateEmailResponse 
	 * @param updateEmailRequest
	 * @return success or failure on update email for user
	 * Description : this is updateEmail method that returns success or failure on update email for user.
	 */
	@Override
	public UpdateEmailResponse updateEmail(UpdateEmailRequest updateEmailRequest) {
		LOGGER.info("Entering into updateEmail method");
		
		UpdateEmailResponse updateEmailResponse = new UpdateEmailResponse();
		try {
			if (updateEmailRequest.getNewEmail() != null && updateEmailRequest.getNewEmail().trim().length() > 0) {
				/*Checking if user exist on the basis of entered userId */
					UserDto user = userDao.getUserByUserIdAndPWD(updateEmailRequest.getUserId(), updateEmailRequest.getPassword());
					/*updating email only if user is valid or exists*/
					if (user != null) {
						/*Sending email to user on their previous email address, to make sure they made this changes */
						boolean isEmailUpdated = userDao.updateEmail(updateEmailRequest);
						if (isEmailUpdated) {
							String registrationEmailHTML = loadTextResource();
							EmailBean emailBean = new EmailBean();
					        emailBean.setSubject("Utah Housing Online Account Changes");
					        emailBean.setMessageBody(registrationEmailHTML);
					        emailBean.addRecipient(user.getEmail());
					        boolean isEmailSent = emailService.sendEmail(emailBean);
					        if(isEmailSent) {
					        	LOGGER.info("Email notification sent successfully");
					        }
							updateEmailResponse.setIsSuccessful(true);
							updateEmailResponse.setMessage("200"); // success
						} else {
							updateEmailResponse.setIsSuccessful(false);
							updateEmailResponse.setMessage("403"); // Miscellaneous error 
						}
					} else {
						updateEmailResponse.setIsSuccessful(false);
						updateEmailResponse.setMessage("405"); // user not found
					}
				
			} else {
				updateEmailResponse.setIsSuccessful(false);
				updateEmailResponse.setMessage("402"); // empty email
			}
		} catch (Exception exp) {
			updateEmailResponse.setIsSuccessful(false);
			updateEmailResponse.setMessage("403"); // Miscellaneous error 
			LOGGER.error("User's email could not be updated because of " , exp);
		}
		LOGGER.info("Exit From updateEmail method");
		return updateEmailResponse;
	}
	
	private String loadTextResource() {
		LOGGER.info("Entering into loadTextResource method");
		String resourceText = null;
		Resource resource = resourceLoader.getResource("classpath:userChangesWereMade.html");
		try (InputStream resetEmail = resource.getInputStream()) {
			StringWriter stringWriter = new StringWriter();
			IOUtils.copy(resetEmail, stringWriter, "UTF-8");
			resourceText = stringWriter.toString();
		} catch (IOException ex) {
			LOGGER.error("Problem opening {}", resource.getFilename(), ex);
		}
		LOGGER.info("Exit from loadTextResource method");
		return resourceText;
	}	
	
	
}
