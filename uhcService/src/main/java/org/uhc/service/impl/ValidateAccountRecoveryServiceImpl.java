/* 
 * ===========================================================================
 * File Name ValidateAccountRecoveryServiceImpl.java
 * 
 * Created on Jan 24, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateAccountRecoveryServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.ValidateAccountRecoveryReq;
import org.uhc.controller.envelop.request.ValidateAccountRecoverySecureCodeReq;
import org.uhc.controller.envelop.response.ValidateAccountRecoveryBySecureCodeRes;
import org.uhc.controller.envelop.response.ValidateAccountRecoveryRes;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.ValidateAccountRecoveryService;
import org.uhc.util.ConfirmationNumberHelper;
import org.uhc.util.EmailBean;
import org.uhc.util.EmailService;
import org.uhc.util.EmailTemplate;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date Oct 13, 2020
 * @description Implementing ValidateAccountRecoveryService to get the ValidateAccountRecoveryResponse
 */
@PropertySource("classpath:email.properties")
@Service
public class ValidateAccountRecoveryServiceImpl implements ValidateAccountRecoveryService {

	private static final Logger LOGGER = LogManager.getLogger(ValidateAccountRecoveryServiceImpl.class.getName());

	@Value("${homeowner.registration.url}")
	private String serverURL;

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private EmailService emailService;

	/** 
	 * validateAccountRecoveryDetails have been created to check if user, who are trying
	 * to recover their account, have correct details
	 * Provide method Description here
	 * @author nehas3
	 * @since Oct 13, 2020
	 * @param validateAccountRecoveryReq
	 * @return
	 */
	@Override
	public ValidateAccountRecoveryRes validateAccountRecoveryDetails(
			ValidateAccountRecoveryReq validateAccountRecoveryReq) {
		LOGGER.info("Entering into validateAccountRecoveryDetails method");
		ValidateAccountRecoveryRes validateAccountRecoveryRes = new ValidateAccountRecoveryRes();
		UserDto userDto;
		String key = null;
		try {
			if (validateAccountRecoveryReq.getSsn().length() != 4) {
				validateAccountRecoveryRes.setIsSuccessFull(false);
				validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
				validateAccountRecoveryRes.setMessage("The information provided doesn’t match our records.");
				return validateAccountRecoveryRes;
			}
			
			if (validateAccountRecoveryReq.getZip().length() != 5) {
				validateAccountRecoveryRes.setIsSuccessFull(false);
				validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
				validateAccountRecoveryRes.setMessage("The information provided doesn’t match our records.");
				return validateAccountRecoveryRes;
			}
			if (!(StringUtils.isNumeric(validateAccountRecoveryReq.getLoanNumber()) && StringUtils.isNumeric(validateAccountRecoveryReq.getSsn()) && StringUtils.isNumeric(validateAccountRecoveryReq.getZip()))) {
				validateAccountRecoveryRes.setIsSuccessFull(false);
				validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
				validateAccountRecoveryRes.setMessage("The information provided doesn’t match our records.");
				return validateAccountRecoveryRes;
			}
			
			List<String> ssnList = userDao.checkLoanAcExistNew(validateAccountRecoveryReq.getLoanNumber(),
					validateAccountRecoveryReq.getSsn(), validateAccountRecoveryReq.getZip());
			
			if (ssnList != null && !ssnList.isEmpty() && ssnList.size() == 1) {
				userDto = userDao.getUserBySsn(ssnList.get(0));
				if (userDto != null) {
					key = ConfirmationNumberHelper.generateAutoKey();
					String accountRecoveryEmailHTML = loadTextResource();
					String registrationUrl = serverURL.concat("/verifyAccountRecovery?key=").concat(key);
					EmailTemplate template = new EmailTemplate(accountRecoveryEmailHTML);
					Map<String, String> replacement = new HashMap<>();
					replacement.put("RECOVER_LINK", registrationUrl);
					String email = validateAccountRecoveryReq.getEmail();
					EmailBean emailBean = new EmailBean();
					emailBean.setSubject("Utah Housing Online Access Account Recovery");
					emailBean.setMessageBody(template.getEmailMessage(replacement));
					emailBean.addRecipient(email);
					boolean isEmailSent = emailService.sendEmail(emailBean);
					if (isEmailSent) {
						LOGGER.info("Email sent to user");
						boolean doesKeyExisting = userDao.isAccountRecoveryKeyExisting(userDto.getUserId(), key);
						if (!doesKeyExisting) {
							boolean isKeyInserted = userDao.insertAccountRecoveryKey(userDto.getUserId(), key);
							if (isKeyInserted) {
								validateAccountRecoveryRes.setIsSuccessFull(true);
								validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("User is validated successfully"));
								validateAccountRecoveryRes.setMessage("User is validated successfully");
							} else {
								LOGGER.error("Account recovery key could not be stored");
								validateAccountRecoveryRes.setIsSuccessFull(false);
								validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("Could not validate user because of exception"));
								validateAccountRecoveryRes.setMessage("Could not validate user because of exception");
							}
						} else {
							validateAccountRecoveryRes.setIsSuccessFull(true);
							validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("User is validated successfully"));
							validateAccountRecoveryRes.setMessage("User is validated successfully");
						}
					} else {
						validateAccountRecoveryRes.setIsSuccessFull(false);
						validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("Email address does not exist"));
						validateAccountRecoveryRes.setMessage("Email address does not exist");
					}
				} else {
					validateAccountRecoveryRes.setIsSuccessFull(false);
					validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("User does not exists"));
					validateAccountRecoveryRes.setMessage("User could not be found");
				}
			}else if(ssnList != null && !ssnList.isEmpty() && ssnList.size() > 1){
				validateAccountRecoveryRes.setIsSuccessFull(false);
				validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("The Profile can not be created at this time, please contact UHC"));
				validateAccountRecoveryRes.setMessage("The Profile can not be created at this time, please contact UHC");

			}else {
				validateAccountRecoveryRes.setIsSuccessFull(false);
				validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
				validateAccountRecoveryRes.setMessage("The information provided doesn’t match our records.");
			}
		} catch (Exception exp) {
			validateAccountRecoveryRes.setIsSuccessFull(false);
			validateAccountRecoveryRes
					.setStatusCode(messageReader.getStatusCode().get("Could not validate user because of exception"));
			validateAccountRecoveryRes.setMessage("Could not validate user because of exception");
		}

		LOGGER.info("Exit from validateAccountRecoveryDetails method");
		return validateAccountRecoveryRes;
	}

	/**
	 * loadTextResource method have been used to load recover account email template from resource folder
	 * @author nehas3
	 * @since Oct 13, 2020
	 * @return
	 */
	private String loadTextResource() {
		LOGGER.info("Entering into loadTextResource method");
		String resourceText = null;
		Resource resource = resourceLoader.getResource("classpath:recoverAccountEmail.html");
		try (InputStream resetPwHtmlStream = resource.getInputStream()) {
			StringWriter stringWriter = new StringWriter();
			IOUtils.copy(resetPwHtmlStream, stringWriter, "UTF-8");
			resourceText = stringWriter.toString();
		} catch (IOException ex) {
			LOGGER.error("Problem opening {}", resource.getFilename(), ex);
		}
		LOGGER.info("Exit from loadTextResource method");
		return resourceText;
	}
	
	/** 
	 * validateAccountRecoveryDetailsWithSecureCode have been created to check if user, who are trying
	 * to recover their account, have correct details
	 * Provide method Description here
	 * @author pradeepy
	 * @since May 21, 2021
	 * @param ValidateAccountRecoverySecureCodeReq
	 * @return
	 */

	@Override
	public ValidateAccountRecoveryBySecureCodeRes validateAccountRecoveryDetailsWithSecureCode(
			ValidateAccountRecoverySecureCodeReq validateAccountRecoveryReq) {
		LOGGER.info("Entering into validateAccountRecoveryDetails method");
		ValidateAccountRecoveryBySecureCodeRes validateAccountRecoveryRes = new ValidateAccountRecoveryBySecureCodeRes();
		UserDto userDto;

		try {
			if (validateAccountRecoveryReq.getSsn().length() != 4) {
				validateAccountRecoveryRes.setIsSuccessFull(false);
				validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
				validateAccountRecoveryRes.setMessage("The information provided doesn’t match our records.");
				return validateAccountRecoveryRes;
			}
			
			if (validateAccountRecoveryReq.getZip().length() != 5) {
				validateAccountRecoveryRes.setIsSuccessFull(false);
				validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
				validateAccountRecoveryRes.setMessage("The information provided doesn’t match our records.");
				return validateAccountRecoveryRes;
			}
			if (!(StringUtils.isNumeric(validateAccountRecoveryReq.getLoanNumber()) && StringUtils.isNumeric(validateAccountRecoveryReq.getSsn()) && StringUtils.isNumeric(validateAccountRecoveryReq.getZip()))) {
				validateAccountRecoveryRes.setIsSuccessFull(false);
				validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
				validateAccountRecoveryRes.setMessage("The information provided doesn’t match our records.");
				return validateAccountRecoveryRes;
			}
			
			List<String> ssnList = userDao.checkLoanAcExistNew(validateAccountRecoveryReq.getLoanNumber(),
					validateAccountRecoveryReq.getSsn(), validateAccountRecoveryReq.getZip());
			
			if (ssnList != null && !ssnList.isEmpty() && ssnList.size() == 1) {
				userDto = userDao.getUserBySsn(ssnList.get(0));
				if (userDto != null) {
					String accountRecoveryEmailHTML = loadTextResource();
					EmailTemplate template = new EmailTemplate(accountRecoveryEmailHTML);
					String email = validateAccountRecoveryReq.getEmail();
					EmailBean emailBean = new EmailBean();
					DateFormat dateFormat2 = new SimpleDateFormat("EEEEEEEEEE, MMMMMMMMMM d, yyyy");
					DateFormat dateFormat3 = new SimpleDateFormat("hh:mm aa");
					String dateString2 = dateFormat2.format(new Date()).toString();
					String dateString3 = dateFormat3.format(new Date()).toString();
					emailBean.setSubject("Utah Housing Online Access Account Recovery");
					emailBean.setMessageBody("<html><head><style>.heder {background-color: #c33737; color: #ffffff; text-align: center; font-size: 19px; padding: 0px;}.textStyle{color: #d61414; font-size: 20px;padding-left: 270px;}</style></head><body><div class=\"heder\"> <p>Utah Housing Corporation-Password Reset Request</p></div>A request to reset your password was made on "+dateString2+" at "+dateString3+" Mountain time. A notification email was sent to the email address on file for your loan. If you made this request enter the following security code on the Reset Code page.<br/><br/><br/><p class=\"textStyle\">"+validateAccountRecoveryReq.getSecurityCode()+"</p><br/><br/><br/>If you did not make this request please call Utah Housing Corporation Mortgage Servicing at 801-902-8250 or 800-344-0452.</body></html>");
					emailBean.addRecipient(email);
					boolean isEmailSent = emailService.sendEmail(emailBean);
					if (isEmailSent) {
						LOGGER.info("Email sent to user");
						validateAccountRecoveryRes.setIsSuccessFull(true);
						validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("User is validated successfully"));
						validateAccountRecoveryRes.setMessage("Secure code sent successfully");
						validateAccountRecoveryRes.setUserId(userDto.getUserId());
						validateAccountRecoveryRes.setUserName(userDto.getUsername());
						validateAccountRecoveryRes.setEmail(userDto.getEmail());
					} else {
						validateAccountRecoveryRes.setIsSuccessFull(false);
						validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("Email address does not exist"));
						validateAccountRecoveryRes.setMessage("Email address does not exist");
					}
				} else {
					validateAccountRecoveryRes.setIsSuccessFull(false);
					validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("User does not exists"));
					validateAccountRecoveryRes.setMessage("User could not be found");
				}
			}else if(ssnList != null && !ssnList.isEmpty() && ssnList.size() > 1){
				validateAccountRecoveryRes.setIsSuccessFull(false);
				validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("The Profile can not be created at this time, please contact UHC"));
				validateAccountRecoveryRes.setMessage("The Profile can not be created at this time, please contact UHC");

			}else {
				validateAccountRecoveryRes.setIsSuccessFull(false);
				validateAccountRecoveryRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
				validateAccountRecoveryRes.setMessage("The information provided doesn’t match our records.");
			}
		} catch (Exception exp) {
			validateAccountRecoveryRes.setIsSuccessFull(false);
			validateAccountRecoveryRes
					.setStatusCode(messageReader.getStatusCode().get("Could not validate user because of exception"));
			validateAccountRecoveryRes.setMessage("Could not validate user because of exception");
		}

		LOGGER.info("Exit from validateAccountRecoveryDetails method");
		return validateAccountRecoveryRes;
	}
}
