/* 
 * ===========================================================================
 * File Name LoginController.java
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
 * $Log: LoginController.java,v $
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
import org.uhc.controller.envelop.request.LoginRequest;
import org.uhc.exception.LockedUserLoginException;
import org.uhc.service.LoginService;

/**
 * @author nehas3
 * @date May 25, 2018
 * @Description : This is LoginController class to move the control of the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class LoginController {
	private static final Logger LOGGER = LogManager.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;

	/**
	 * This is login service API to call the login service
	 * @author nehas3
	 * @date May 25, 2018
	 * @return Object 
	 * @param loginRequest
	 * @return loginResponse
	 * @throws LockedUserLoginException 
	 */	
	@PostMapping(value = "/login")
	public Object loginAPI(@RequestBody LoginRequest loginRequest) throws LockedUserLoginException {
		LOGGER.info("{\"loginAPI\": {} }", loginRequest);
		return loginService.login(loginRequest);
	}

}
