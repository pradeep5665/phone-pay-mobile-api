/* 
 * ===========================================================================
 * File Name RecoverAccountServiceImpl.java
 * 
 * Created on Jan 28, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: RecoverAccountServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.RecoverAccountReq;
import org.uhc.controller.envelop.request.RecoveryAccountReq;
import org.uhc.controller.envelop.response.RecoverAccountRes;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.AccountRecoveryDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.RecoverAccountService;
import org.uhc.util.EmailBean;
import org.uhc.util.EmailService;
import org.uhc.util.EmailTemplate;
import org.uhc.util.MessageReader;
import org.uhc.util.PasswordValidation;

@PropertySource("classpath:email.properties")
@Service
public class RecoverAccountServiceImpl implements RecoverAccountService {

	private static final Logger LOGGER = LogManager.getLogger(RecoverAccountServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private EmailService emailService;

	/**
	 * recoverAccountByKey have been created to make user recover their account on basis of key they used, if it is valid.
	 * @author nehas3
	 * @since Oct 13, 2020
	 * @param recoveryAccReq
	 * @return response of actual account recovery process
	 */
	@Override
	public RecoverAccountRes recoverAccountByKey(RecoverAccountReq recoverAccountReq) {
		LOGGER.info("Entering into recoverAccountByKey()");
		RecoverAccountRes recoverAccountRes = new RecoverAccountRes();
		UserDto userDto;
		boolean isAccountUpdated = false;
		boolean isKeyReset = false;
		try {
			if (recoverAccountReq.getNewPassword() != null) {
				boolean isPasswordValid = PasswordValidation.validatePassword(recoverAccountReq.getNewPassword());
				if (isPasswordValid) {
					AccountRecoveryDto accRecoveryDto = userDao.getAccountRecoveryKeyDetails(recoverAccountReq.getAccRecoveryKey());
					if (accRecoveryDto != null) {
						userDto = userDao.getUserByUserId(accRecoveryDto.getUserId());
						if(userDto!=null) {
							String oldPwd = userDao.getOldPassword(userDto.getUserId());
							if(userDto.getEmail().equals(recoverAccountReq.getRecoveryEmail())) {
								LOGGER.info("Updating only pwd for userId:{}", userDto.getUserId());
								isAccountUpdated = userDao.resetPassword(userDto.getUserId(), recoverAccountReq);
							}else {
								LOGGER.info("Updating pwd and email for userId: {}", userDto.getUserId());
								isAccountUpdated = userDao.resetAccount(userDto.getUserId(), recoverAccountReq);
								if(isAccountUpdated) {
									String accountRecoveryNotificationEmailHTML = loadAccountRecoveryNotificationEmail();
								    EmailTemplate template = new EmailTemplate(accountRecoveryNotificationEmailHTML);
								    Map<String, String> replacement = new HashMap<>();
								    if(oldPwd.equals(recoverAccountReq.getNewPassword()) && !userDto.getEmail().equals(recoverAccountReq.getRecoveryEmail())) {
								    	replacement.put("CHANGED_FIELD", "email");
								    }else if(!oldPwd.equals(recoverAccountReq.getNewPassword()) && userDto.getEmail().equals(recoverAccountReq.getRecoveryEmail())){
								    	replacement.put("CHANGED_FIELD", "password");
								    }else {
								    	replacement.put("CHANGED_FIELD", "email and password");
								    }
									String email = userDto.getEmail();
									EmailBean emailBean = new EmailBean();
									emailBean.setSubject("Utah Housing Account Access Attempt");
									emailBean.setMessageBody(template.getEmailMessage(replacement));
									emailBean.addRecipient(email);
									boolean isNotificationEmailSent = emailService.sendEmail(emailBean);
									if(isNotificationEmailSent) {
										LOGGER.info("Notification email sent to old email for userId: {}", userDto.getUserId());
									}
								}
							}
							if(isAccountUpdated) {
								isKeyReset = userDao.resetRecoveryKeyForSuccess(accRecoveryDto.getId());
								if(isKeyReset) {
									String loadAccountRecoveryConfirmationEmailHTML = loadAccountRecoveryConfirmationEmail();
									EmailTemplate template = new EmailTemplate(loadAccountRecoveryConfirmationEmailHTML);
									Map<String, String> replacement = new HashMap<>();
									if(oldPwd.equals(recoverAccountReq.getNewPassword()) && !userDto.getEmail().equals(recoverAccountReq.getRecoveryEmail())) {
								    	replacement.put("CHANGED_FIELD", "email");
								    }else if(!oldPwd.equals(recoverAccountReq.getNewPassword()) && userDto.getEmail().equals(recoverAccountReq.getRecoveryEmail())){
								    	replacement.put("CHANGED_FIELD", "password");
								    }else {
								    	replacement.put("CHANGED_FIELD", "email and password");
								    }
									String email = recoverAccountReq.getRecoveryEmail();
									EmailBean emailBean = new EmailBean();
									emailBean.setSubject("Utah Housing Account Changes Completed");
									emailBean.setMessageBody(template.getEmailMessage(replacement));
									emailBean.addRecipient(email);
									boolean isConfirmationEmailSent = emailService.sendEmail(emailBean);
									if(isConfirmationEmailSent) {
										LOGGER.info("Account Recovery Confirmation email sent to email for userId: {}", userDto.getUserId());
									}
									recoverAccountRes.setIsSuccessFull(true);
									recoverAccountRes.setStatusCode(messageReader.getStatusCode().get("Account recovery done successfully"));
									recoverAccountRes.setMessage("Account recovery done successfully");	
								}else {
									LOGGER.info("Problem occurred during reset key for userId: {}", userDto.getUserId());
									isKeyReset = userDao.resetRecoveryKeyForFailure(accRecoveryDto.getId());
									recoverAccountRes.setIsSuccessFull(false);
									recoverAccountRes.setStatusCode(messageReader.getStatusCode().get("Could not complete account recovery process because of exception"));
									recoverAccountRes.setMessage("Could not complete account recovery process because of exception");	
								}
							}else {
								isKeyReset = userDao.resetRecoveryKeyForFailure(accRecoveryDto.getId());
								recoverAccountRes.setIsSuccessFull(false);
								recoverAccountRes.setStatusCode(messageReader.getStatusCode().get("Could not complete account recovery process because of exception"));
								recoverAccountRes.setMessage("Could not complete account recovery process because of exception");	
							}
						}else {
							isKeyReset = userDao.resetRecoveryKeyForFailure(accRecoveryDto.getId());
							recoverAccountRes.setIsSuccessFull(false);
							recoverAccountRes.setStatusCode(messageReader.getStatusCode().get("User does not exists"));
							recoverAccountRes.setMessage("User does not exists");
						}
					}else {
						recoverAccountRes.setIsSuccessFull(false);
						recoverAccountRes.setStatusCode(messageReader.getStatusCode().get("Key does not exist"));// not available in
						recoverAccountRes.setMessage("Key does not exist");
					}
				}else {
					recoverAccountRes.setIsSuccessFull(false);
					recoverAccountRes.setStatusCode(messageReader.getStatusCode().get("Invalid password"));// not available in
					recoverAccountRes.setMessage("Invalid password");
				}
			}else {
				recoverAccountRes.setIsSuccessFull(false);
				recoverAccountRes.setStatusCode(messageReader.getStatusCode().get("Invalid password"));// not available in
				recoverAccountRes.setMessage("Invalid password");
			}
		} catch (Exception exp) {
			recoverAccountRes.setIsSuccessFull(false);
			recoverAccountRes.setStatusCode(messageReader.getStatusCode().get("Account recovery could not be because of exception"));
			recoverAccountRes.setMessage("Account recovery could not be because of exception");
		}

		return recoverAccountRes;
	}
	
	/**
	 * loadAccountRecoveryNotificationEmail have been created to load recoverAccountNotificationEmail file from resource folder
	 * Provide method Description here
	 * @author nehas3
	 * @since Oct 13, 2020
	 * @return recoverAccountNotificationEmail template data
	 */
	private String loadAccountRecoveryNotificationEmail() {
		LOGGER.info("Entering into loadAccountRecoveryNotificationEmail method");
		String resourceText = null;
		Resource resource = resourceLoader.getResource("classpath:recoverAccountNotificationEmail.html");
		try (InputStream resetPwHtmlStream = resource.getInputStream()) {
			StringWriter stringWriter = new StringWriter();
			IOUtils.copy(resetPwHtmlStream, stringWriter, "UTF-8");
			resourceText = stringWriter.toString();
		} catch (IOException ex) {
			LOGGER.error("Problem opening {}", resource.getFilename(), ex);
		}
		LOGGER.info("Exit from loadAccountRecoveryNotificationEmail method");
		return resourceText;
	}

	/**
	 * loadAccountRecoveryConfirmationEmail have been created to load accountRecoveryConfirmationEmail file from resource folder
	 * Provide method Description here
	 * @author nehas3
	 * @since Oct 13, 2020
	 * @return accountRecoveryConfirmationEmail template data
	 */
	private String loadAccountRecoveryConfirmationEmail() {
		LOGGER.info("Entering into loadAccountRecoveryConfirmationEmail method");
		String resourceText = null;
		Resource resource = resourceLoader.getResource("classpath:accountRecoveryConfirmationEmail.html");
		try (InputStream resetPwHtmlStream = resource.getInputStream()) {
			StringWriter stringWriter = new StringWriter();
			IOUtils.copy(resetPwHtmlStream, stringWriter, "UTF-8");
			resourceText = stringWriter.toString();
		} catch (IOException ex) {
			LOGGER.error("Problem opening {}", resource.getFilename(), ex);
		}
		LOGGER.info("Exit from loadAccountRecoveryConfirmationEmail method");
		return resourceText;
	}
	
	
	/**
	 * recoverAccountByUserId have been created to make user recover their account on basis of userId they used.
	 * @author pradeepy
	 * @since May 25, 2021
	 * @param RecoveryAccountReq
	 * @return response of actual account recovery process
	 */
	@Override
	public RecoverAccountRes recoverAccountByUserId(RecoveryAccountReq recoverAccountReq)  {
		LOGGER.info("Entering into recoverAccountByKey()");
		RecoverAccountRes recoverAccountRes = new RecoverAccountRes();
		UserDto userDto;
		boolean isAccountUpdated = false;
		try {
			if (recoverAccountReq.getNewPassword() != null) {
				boolean isPasswordValid = PasswordValidation.validatePassword(recoverAccountReq.getNewPassword());
				if (isPasswordValid) {
						userDto = userDao.getUserByUserId(recoverAccountReq.getUserId());
						if(userDto!=null) {
							String oldPwd = userDao.getOldPassword(userDto.getUserId());
							if(userDto.getEmail().equals(recoverAccountReq.getRecoveryEmail())) {
								LOGGER.info("Updating only pwd for userId:{}", userDto.getUserId());
								isAccountUpdated = userDao.updatePassword(userDto.getUserId(), recoverAccountReq);
							}else {
								LOGGER.info("Updating pwd and email for userId: {}", userDto.getUserId());
								isAccountUpdated = userDao.updateAccount(userDto.getUserId(), recoverAccountReq);
								if(isAccountUpdated) {
									String accountRecoveryNotificationEmailHTML = loadAccountRecoveryNotificationEmail();
								    EmailTemplate template = new EmailTemplate(accountRecoveryNotificationEmailHTML);
								    Map<String, String> replacement = new HashMap<>();
								    if(oldPwd.equals(recoverAccountReq.getNewPassword()) && !userDto.getEmail().equals(recoverAccountReq.getRecoveryEmail())) {
								    	replacement.put("CHANGED_FIELD", "email");
								    }else if(!oldPwd.equals(recoverAccountReq.getNewPassword()) && userDto.getEmail().equals(recoverAccountReq.getRecoveryEmail())){
								    	replacement.put("CHANGED_FIELD", "password");
								    }else {
								    	replacement.put("CHANGED_FIELD", "email and password");
								    }
									String email = userDto.getEmail();
									EmailBean emailBean = new EmailBean();
									emailBean.setSubject("Utah Housing Account Access Attempt");
									emailBean.setMessageBody(template.getEmailMessage(replacement));
									emailBean.addRecipient(email);
									boolean isNotificationEmailSent = emailService.sendEmail(emailBean);
									if(isNotificationEmailSent) {
										LOGGER.info("Notification email sent to old email for userId: {}", userDto.getUserId());
									}
								}
							}
							if(isAccountUpdated) {
									String loadAccountRecoveryConfirmationEmailHTML = loadAccountRecoveryConfirmationEmail();
									EmailTemplate template = new EmailTemplate(loadAccountRecoveryConfirmationEmailHTML);
									Map<String, String> replacement = new HashMap<>();
									if(oldPwd.equals(recoverAccountReq.getNewPassword()) && !userDto.getEmail().equals(recoverAccountReq.getRecoveryEmail())) {
								    	replacement.put("CHANGED_FIELD", "email");
								    }else if(!oldPwd.equals(recoverAccountReq.getNewPassword()) && userDto.getEmail().equals(recoverAccountReq.getRecoveryEmail())){
								    	replacement.put("CHANGED_FIELD", "password");
								    }else {
								    	replacement.put("CHANGED_FIELD", "email and password");
								    }
									String email = recoverAccountReq.getRecoveryEmail();
									EmailBean emailBean = new EmailBean();
									emailBean.setSubject("Utah Housing Account Changes Completed");
									emailBean.setMessageBody(template.getEmailMessage(replacement));
									emailBean.addRecipient(email);
									boolean isConfirmationEmailSent = emailService.sendEmail(emailBean);
									if(isConfirmationEmailSent) {
										LOGGER.info("Account Recovery Confirmation email sent to email for userId: {}", userDto.getUserId());
									}
									recoverAccountRes.setIsSuccessFull(true);
									recoverAccountRes.setStatusCode(messageReader.getStatusCode().get("Account recovery done successfully"));
									recoverAccountRes.setMessage("Account recovery done successfully");	
							}else {
								recoverAccountRes.setIsSuccessFull(false);
								recoverAccountRes.setStatusCode(messageReader.getStatusCode().get("Could not complete account recovery process because of exception"));
								recoverAccountRes.setMessage("Could not complete account recovery process because of exception");	
							}
						}else {
							recoverAccountRes.setIsSuccessFull(false);
							recoverAccountRes.setStatusCode(messageReader.getStatusCode().get("User does not exists"));
							recoverAccountRes.setMessage("User does not exists");
						}
				}else {
					recoverAccountRes.setIsSuccessFull(false);
					recoverAccountRes.setStatusCode(messageReader.getStatusCode().get("Invalid password"));// not available in
					recoverAccountRes.setMessage("Invalid password");
				}
			}else {
				recoverAccountRes.setIsSuccessFull(false);
				recoverAccountRes.setStatusCode(messageReader.getStatusCode().get("Invalid password"));// not available in
				recoverAccountRes.setMessage("Invalid password");
			}
		} catch (Exception exp) {
			LOGGER.error("Unplanned for exception", exp);
			recoverAccountRes.setIsSuccessFull(false);
			recoverAccountRes.setStatusCode(messageReader.getStatusCode().get("Account recovery could not be because of exception"));
			recoverAccountRes.setMessage("Account recovery could not be because of exception");
		}

		return recoverAccountRes;
	}
}
