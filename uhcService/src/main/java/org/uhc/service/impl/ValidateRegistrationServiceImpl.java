/* 
 * ===========================================================================
 * File Name ValidateRegistrationServiceImpl.java
 * 
 * Created on Jun 12, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateRegistrationServiceImpl.java,v $
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
import org.uhc.controller.envelop.request.ValidateRegistrationReq;
import org.uhc.controller.envelop.request.ValidateRegistrationWithSecureCodeReq;
import org.uhc.controller.envelop.response.ValidateRegistrationRes;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.ValidateRegistrationService;
import org.uhc.util.EmailBean;
import org.uhc.util.EmailService;
import org.uhc.util.EmailTemplate;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date Jun 12, 2019
 * @description Implementing ValidateRegistrationService to get the ValidateRegistrationResponse
 */
@PropertySource("classpath:email.properties")
@Service
public class ValidateRegistrationServiceImpl implements ValidateRegistrationService {

	private static final Logger LOGGER = LogManager.getLogger(ValidateRegistrationServiceImpl.class.getName());

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
	 * @author nehas3
	 * @date Jun 12, 2019
	 * @return ValidateRegistrationRes
	 * @param validateRegReq
	 * @return status of user if they are already exist or new to register
	 * @Description : this is validateRegisteringUser method that returns ValidateRegistrationRes
	 *              on basis of user's input
	 */
	@Override
	public ValidateRegistrationRes validateRegisteringUser(ValidateRegistrationReq validateRegReq) {
		ValidateRegistrationRes validateRegistrationRes = new ValidateRegistrationRes();
		UserDto userDto;
		UserDto userDtoByUserName;
		try {
			if (validateRegReq.getRegistrationKey() != null && !validateRegReq.getRegistrationKey().equals("")) {
				if (validateRegReq.getSsn().length() != 4) {
					validateRegistrationRes.setIsSuccessFull(false);
					validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
					validateRegistrationRes.setMessage("The information provided doesn’t match our records.");
					LOGGER.info("validateRegisteringUser(): userName: {}, SSN length should be 4.", validateRegReq.getUsername());
					return validateRegistrationRes;
				}
				
				if (validateRegReq.getZip().length() != 5) {
					validateRegistrationRes.setIsSuccessFull(false);
					validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
					validateRegistrationRes.setMessage("The information provided doesn’t match our records.");
					LOGGER.info("validateRegisteringUser(): userName: {}, zip length should be 5. zip: {}", validateRegReq.getUsername(), validateRegReq.getZip());
					return validateRegistrationRes;
				}
				if (!(StringUtils.isNumeric(validateRegReq.getLoanNumber()) && StringUtils.isNumeric(validateRegReq.getSsn()) && StringUtils.isNumeric(validateRegReq.getZip()))) {
					validateRegistrationRes.setIsSuccessFull(false);
					validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
					validateRegistrationRes.setMessage("The information provided doesn’t match our records.");
					LOGGER.info("validateRegisteringUser(): userName: {}, Non-numeric characters received in zip: {} or loanNumber: {} or ssn: {}",
						validateRegReq.getUsername(), validateRegReq.getZip(), validateRegReq.getLoanNumber(), validateRegReq.getSsn());
					return validateRegistrationRes;
				}
				
				List<String> ssnList = userDao.checkLoanAcExistNew(validateRegReq.getLoanNumber(),
						validateRegReq.getSsn(), validateRegReq.getZip());
				
				if (ssnList != null && ssnList.size() == 1) {
					userDto = userDao.getUserBySsn(ssnList.get(0));

					/*Checking if user details already existed*/
					if (userDto == null) {
						userDtoByUserName = userDao.getUserByUsername(validateRegReq.getUsername().toLowerCase());

						/*Checking if user name is already existed*/
						if(userDtoByUserName==null) {
							String registrationEmailHTML = loadTextResource();
							String registrationUrl = serverURL.concat("/verifyRegistration?key=").concat(validateRegReq.getRegistrationKey());

							EmailTemplate template = new EmailTemplate(registrationEmailHTML);
							Map<String, String> replacement = new HashMap<>();
							replacement.put("REGISTER_LINK", registrationUrl);
							String email = validateRegReq.getEmail();
							EmailBean emailBean = new EmailBean();
							emailBean.setSubject("New Registered User Verification");
							emailBean.setMessageBody(template.getEmailMessage(replacement));
							emailBean.addRecipient(email);
							boolean isEmailSent = emailService.sendEmail(emailBean);
							if (isEmailSent) {
								LOGGER.info("Email sent to user");
								validateRegistrationRes.setIsSuccessFull(true);
								validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("User is validated successfully"));
								validateRegistrationRes.setMessage("User is validated successfully");
							} else {
								validateRegistrationRes.setIsSuccessFull(false);
								validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("Email address does not exist"));
								validateRegistrationRes.setMessage("Email address does not exist");
							}
						}else {
							validateRegistrationRes.setIsSuccessFull(false);
							validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("Username already exist"));
							validateRegistrationRes.setMessage("User name already exist");
						}
						
					} else {
						validateRegistrationRes.setIsSuccessFull(false);
						validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("User already exists"));
						validateRegistrationRes.setMessage("User already exists");
					}
				} else if(ssnList != null && ssnList.size() > 1){
					validateRegistrationRes.setIsSuccessFull(false);
					validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("The Profile can not be created at this time, please contact UHC"));
					validateRegistrationRes.setMessage("The Profile can not be created at this time, please contact UHC");
					LOGGER.error( "validateRegisteringUser(): Data integrity error! Found multiple ssn's for userName: {}", validateRegReq.getUsername());

				} else {
					validateRegistrationRes.setIsSuccessFull(false);
					validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
					validateRegistrationRes.setMessage("The information provided doesn’t match our records.");
				}
			} else {
				validateRegistrationRes.setIsSuccessFull(false);
				validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("Invalid key provided"));
				validateRegistrationRes.setMessage("Invalid key provided");
				LOGGER.error("validateRegisteringUser(): null or empty registration key provided. userName: {}", validateRegReq.getUsername());
			}
		} catch (Exception exp) {
			validateRegistrationRes.setIsSuccessFull(false);
			validateRegistrationRes
					.setStatusCode(messageReader.getStatusCode().get("Could not validate user because of exception"));
			validateRegistrationRes.setMessage("Could not validate user because of exception");
			LOGGER.error("validateRegisteringUser(): Unhandled exception: ", exp);
		}
		return validateRegistrationRes;
	}

	/**
	 * @author nehas3
	 * @date Jun 12, 2019
	 * @return String 
	 * @exception null pointer
	 * @Description This method is created to load template of email registration from class path
	 */
	private String loadTextResource() {
		LOGGER.info("Entering into loadTextResource method");
		String resourceText = null;
		Resource resource = resourceLoader.getResource("classpath:registrationEmail.html");
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
	 * @author pradeepy
	 * @date May 21, 2021
	 * @return ValidateRegistrationRes
	 * @param validateRegReq
	 * @return status of user if they are already exist or new to register
	 * @Description : this is validateRegisteringUser method that returns ValidateRegistrationRes
	 *              on basis of user's input
	 */
	@Override
	public ValidateRegistrationRes validateRegisteringUserWithSecureCode(
			ValidateRegistrationWithSecureCodeReq validateRegReq)  {
		ValidateRegistrationRes validateRegistrationRes = new ValidateRegistrationRes();
		UserDto userDto;
		UserDto userDtoByUserName;
		try {
			if (validateRegReq.getSecurityCode() != null && !validateRegReq.getSecurityCode().equals("")) {
				if (validateRegReq.getSsn().length() != 4) {
					validateRegistrationRes.setIsSuccessFull(false);
					validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
					validateRegistrationRes.setMessage("The information provided doesn’t match our records.");
					LOGGER.info("validateRegisteringUser(): userName: {}, SSN length should be 4.", validateRegReq.getUsername());
					return validateRegistrationRes;
				}
				
				if (validateRegReq.getZip().length() != 5) {
					validateRegistrationRes.setIsSuccessFull(false);
					validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
					validateRegistrationRes.setMessage("The information provided doesn’t match our records.");
					LOGGER.info("validateRegisteringUser(): userName: {}, zip length should be 5. zip: {}", validateRegReq.getUsername(), validateRegReq.getZip());
					return validateRegistrationRes;
				}
				if (!(StringUtils.isNumeric(validateRegReq.getLoanNumber()) && StringUtils.isNumeric(validateRegReq.getSsn()) && StringUtils.isNumeric(validateRegReq.getZip()))) {
					validateRegistrationRes.setIsSuccessFull(false);
					validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
					validateRegistrationRes.setMessage("The information provided doesn’t match our records.");
					LOGGER.info("validateRegisteringUser(): userName: {}, Non-numeric characters received in zip: {} or loanNumber: {} or ssn: {}",
						validateRegReq.getUsername(), validateRegReq.getZip(), validateRegReq.getLoanNumber(), validateRegReq.getSsn());
					return validateRegistrationRes;
				}
				
				List<String> ssnList = userDao.checkLoanAcExistNew(validateRegReq.getLoanNumber(),
						validateRegReq.getSsn(), validateRegReq.getZip());
				
				if (ssnList != null && ssnList.size() == 1) {
					userDto = userDao.getUserBySsn(ssnList.get(0));

					/*Checking if user details already existed*/
					if (userDto == null) {
						userDtoByUserName = userDao.getUserByUsername(validateRegReq.getUsername().toLowerCase());

						/*Checking if user name is already existed*/
						if(userDtoByUserName==null) {
							String registrationEmailHTML = loadTextResource();
							
							EmailTemplate template = new EmailTemplate(registrationEmailHTML);
							Map<String, String> replacement = new HashMap<>();
							String email = validateRegReq.getEmail();
							EmailBean emailBean = new EmailBean();
							DateFormat dateFormat2 = new SimpleDateFormat("EEEEEEEEEE, MMMMMMMMMM d, yyyy");
							DateFormat dateFormat3 = new SimpleDateFormat("hh:mm aa");
							String dateString2 = dateFormat2.format(new Date()).toString();
							String dateString3 = dateFormat3.format(new Date()).toString();
							emailBean.setSubject("Utah Housing Online Access Registration Request");
							emailBean.setMessageBody("<html><head><style>.heder {background-color: #c33737; color: #ffffff; text-align: center; font-size: 19px; padding: 0px;}.textStyle{color: #d61414; font-size: 20px;padding-left: 270px;}</style></head><body><div class=\"heder\"> <p>Utah Housing Corporation-Registration Request</p></div>A request to create an online account was made on "+dateString2+" at "+dateString3+" Mountain time. If you made this request enter the following security code on the Security Code page.<br/><br/><br/><p class=\"textStyle\">"+validateRegReq.getSecurityCode()+"</p><br/><br/><br/>If you did not make this request please call Utah Housing Corporation Mortgage Servicing at 801-902-8250 or 800-344-0452.</body></html>");
							emailBean.addRecipient(email);
							boolean isEmailSent = emailService.sendEmail(emailBean);
							if (isEmailSent) {
								LOGGER.info("Email sent to user");
								validateRegistrationRes.setIsSuccessFull(true);
								validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("User is validated successfully"));
								//validateRegistrationRes.setMessage("User is validated successfully");
								validateRegistrationRes.setMessage("Secure code sent successfully");
							} else {
								validateRegistrationRes.setIsSuccessFull(false);
								validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("Email address does not exist"));
								validateRegistrationRes.setMessage("Email address does not exist");
							}
						}else {
							validateRegistrationRes.setIsSuccessFull(false);
							validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("Username already exist"));
							validateRegistrationRes.setMessage("User name already exist");
						}
						
					} else {
						validateRegistrationRes.setIsSuccessFull(false);
						validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("User already exists"));
						validateRegistrationRes.setMessage("User already exists");
					}
				} else if(ssnList != null && ssnList.size() > 1){
					validateRegistrationRes.setIsSuccessFull(false);
					validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("The Profile can not be created at this time, please contact UHC"));
					validateRegistrationRes.setMessage("The Profile can not be created at this time, please contact UHC");
					LOGGER.error( "validateRegisteringUser(): Data integrity error! Found multiple ssn's for userName: {}", validateRegReq.getUsername());

				} else {
					validateRegistrationRes.setIsSuccessFull(false);
					validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("The information provided doesn’t match our records."));
					validateRegistrationRes.setMessage("The information provided doesn’t match our records.");
				}
			} else {
				validateRegistrationRes.setIsSuccessFull(false);
				validateRegistrationRes.setStatusCode(messageReader.getStatusCode().get("Invalid key provided"));
				validateRegistrationRes.setMessage("Invalid key provided");
				LOGGER.error("validateRegisteringUser(): null or empty registration key provided. userName: {}", validateRegReq.getUsername());
			}
		} catch (Exception exp) {
			validateRegistrationRes.setIsSuccessFull(false);
			validateRegistrationRes
					.setStatusCode(messageReader.getStatusCode().get("Could not validate user because of exception"));
			validateRegistrationRes.setMessage("Could not validate user because of exception");
			LOGGER.error("validateRegisteringUser(): Unhandled exception: ", exp);
		}
		return validateRegistrationRes;
	}
}
