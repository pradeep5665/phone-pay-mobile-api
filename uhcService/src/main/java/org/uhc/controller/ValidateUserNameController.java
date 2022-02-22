/* 
 * ===========================================================================
 * File Name ValidateLoanNumberController.java
 * 
 * Created on Aug 13, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateLoanNumberController.java,v $
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
import org.uhc.controller.envelop.request.ValidateUserNameReq;
import org.uhc.service.ValidateUserNameService;

/**
 * @author nehas3
 * @date Aug 13, 2019
 * @Description : This is ValidateUserNameController class to Validate the user
 *              name entered by user while registering in the application
 */
@RestController
@RequestMapping(value = "/")
public class ValidateUserNameController {

	private static final Logger LOGGER = LogManager.getLogger(ValidateUserNameController.class);

	@Autowired
	private ValidateUserNameService validateUserNameService;

	/**
	 * @author nehas3
	 * @date Aug 13, 2019
	 * @return Object
	 * @param accountResquest
	 * @return accountResponse Account Service API to call the Account Service
	 */
	@PostMapping(value = "/validateUserName")
	public Object validateLoanNumberServiceAPI(@RequestBody ValidateUserNameReq validateUserNameReq) {
		LOGGER.info("validateUserNameAPI: userName: {}", validateUserNameReq.getUserName());
		return validateUserNameService.validateUserName(validateUserNameReq);
	}
}
