/* 
 * ===========================================================================
 * File Name UpdateUserNameController.java
 * 
 * Created on May 29, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateUserNameController.java,v $
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
import org.uhc.controller.envelop.request.UpdateUserNameRequest;
import org.uhc.service.UpdateUserNameService;

/**
 * @author nehas3
 * @date May 29, 2018
 * @Description : This is UpdateUserNameController class to move the control of
 *              the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class UpdateUserNameController {

	private static final Logger LOGGER = LogManager.getLogger(UpdateUserNameController.class);

	@Autowired
	private UpdateUserNameService updateUserNameService;

	/**
	 * This is UpdateUserName service API to update the userName of user
	 * profile @author nehas3 @date May 29, 2018 @return Object @param
	 * updateUserNameRequest @return @exception
	 */
	@PostMapping(value = "/updateUserName")
	public Object updateUserNameAPI(@RequestBody UpdateUserNameRequest updateUserNameRequest) {
		LOGGER.info("UpdateUserName Service API is called: {}",
				updateUserNameRequest != null ? updateUserNameRequest.toString() : "");
		return updateUserNameService.updateUserName(updateUserNameRequest);
	}
}
