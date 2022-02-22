/* 
 * ===========================================================================
 * File Name RegistrationController.java
 * 
 * Created on May 25, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: RegistrationController.java,v $
 * ===========================================================================
 */
package org.uhc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uhc.controller.envelop.request.RegistrationRequest;
import org.uhc.controller.envelop.request.ValidateRegistrationReq;
import org.uhc.controller.envelop.request.ValidateRegistrationWithSecureCodeReq;
import org.uhc.service.RegistrationService;
import org.uhc.service.ValidateRegistrationService;

/**
 * @author nehas3
 * @date May 25, 2018
 * @Description : This is RegistrationController class to move the control of
 *              the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class RegistrationController {

	private static final Logger LoGGER = LogManager.getLogger(RegistrationController.class);

	@Autowired
	private RegistrationService regService;

	@Autowired
	private ValidateRegistrationService validateRegService;

	/**
	 * This is Registration service API to call Registration API
	 * @author nehas3
	 * @date May 25, 2018
	 * @return Object
	 * @param regRequest
	 * @return registrationResponse
	 */
	@PostMapping(value = "/registration")
	public Object registrationAPI(@RequestBody RegistrationRequest regRequest) {
		LoGGER.info("{\"registrationAPI\": {} }", regRequest);
		return regService.registerUser(regRequest);
	}

	@PostMapping(value = "/validateRegistration")
	public Object validateRegistrationAPI(@RequestBody ValidateRegistrationReq regRequest) {
		LoGGER.info("{\"validateRegistrationAPI\": {} }", regRequest);
		return validateRegService.validateRegisteringUser(regRequest);
	}
	
	/**
	 * This is New Registration service API to call Registration API
	 * @author pradeepy
	 * @date May 21, 2021
	 * @return Object
	 * @param regRequest
	 * @return registrationResponse
	 */
	@PostMapping(value = "/registrationAPI")
	public Object saveRegistrationAPI(@RequestBody RegistrationRequest regRequest) {
		LoGGER.info("{\"registrationAPI\": {} }", regRequest);
		return regService.registerUserWithSecureCode(regRequest);
	}
	
	/**
	 * This is New Registration service API to call Registration API
	 * @author pradeepy
	 * @date May 21, 2021
	 * @return Object
	 * @param regRequest
	 * @return registrationResponse
	 */
	@PostMapping(value = "/validateRegistrationWithSecureCodeAPI")
	public Object validateRegistrationWithSecureCode(@RequestBody ValidateRegistrationWithSecureCodeReq regRequest) {
		LoGGER.info("{\"validateRegistrationWithSecureCodeAPI\": {} }", regRequest);
		return validateRegService.validateRegisteringUserWithSecureCode(regRequest);
	}
}
