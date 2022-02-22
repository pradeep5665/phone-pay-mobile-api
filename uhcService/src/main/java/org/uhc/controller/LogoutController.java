/* 
 * ===========================================================================
 * File Name LogoutController.java
 * 
 * Created on Aug 24, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: LogoutController.java,v $
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
import org.uhc.controller.envelop.request.LogoutRequest;
import org.uhc.service.LogoutService;

/**
 * @author nehas3
 * @date Aug 24, 2018
 * @Description : This is LogoutController class to move the control of the concerned Requested URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class LogoutController {

	private static final Logger LOGGER = LogManager.getLogger(LogoutController.class);
	
	@Autowired
	private LogoutService logoutService;

	/**
	 * This is logout API to call the Log out Service
	 * @author nehas3
	 * @date Aug 22, 2018
	 * @return Object 
	 * @param readStatusResquest
	 * @return UpdateReadStatusMessageResponse
	 */
	@PostMapping(value = "/doLogout")
	public Object logoutAPI(@RequestBody LogoutRequest logoutRequest) {
		LOGGER.info("Logout API is called : {}" ,logoutRequest!=null ? logoutRequest.toString() :"");
		return logoutService.logout(logoutRequest);
	}
}
