/* 
 * ===========================================================================
 * File Name UserProfileController.java
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
 * $Log: UserProfileController.java,v $
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
import org.uhc.controller.envelop.request.UserProfileRequest;
import org.uhc.service.UserProfileService;

/**
 * @author nehas3
 * @date May 25, 2018
 * @Description : This is UserProfileController class to move the control of the
 *              concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class UserProfileController {

	private static final Logger LOGGER = LogManager.getLogger(UserProfileController.class);

	@Autowired
	private UserProfileService userProfileService;

	/**
	 * This is userProfileAPI service to call User Profile Service API
	 * @author nehas3
	 * @date May 25, 2018
	 * @return Object
	 * @param userProfileRequest
	 * @return userProfileResponse
	 */
	@PostMapping(value = "/userProfile")
	public Object userProfileAPI(@RequestBody UserProfileRequest userProfileRequest) {
		LOGGER.info("{\"userProfileAPI\": {} }", userProfileRequest);
		return userProfileService.getUserProfile(userProfileRequest);
	}
}
