/* 
 * ===========================================================================
 * File Name UpdatePasswordController.java
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
 * $Log: UpdatePasswordController.java,v $
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
import org.uhc.controller.envelop.request.UpdatePasswordRequest;
import org.uhc.service.UpdatePasswordService;

/**
 * @author nehas3
 * @date May 29, 2018
 * @Description : This is UpdatePasswordController class to move the control of
 *              the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class UpdatePasswordController {

	private static final Logger LOGGER = LogManager.getLogger(UpdatePasswordController.class);

	@Autowired
	private UpdatePasswordService updatePasswordService;

	/**
	 * updatePasswordResponse This is UpdatePassword service API to update the
	 * Password of user profile
	 * @author nehas3
	 * @date May 29, 2018
	 * @return Object
	 * @param updatePasswordRequest
	 * @return response of updatePassword API
	 */
	@PostMapping(value = "/updatePassword")
	public Object updatePasswordAPI(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
		LOGGER.info("UpdatePassword Service API is called: {}",
				updatePasswordRequest != null ? updatePasswordRequest.toString() : "");
		return updatePasswordService.updatePassword(updatePasswordRequest);
	}
}
