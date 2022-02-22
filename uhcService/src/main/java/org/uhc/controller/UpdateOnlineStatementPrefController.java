/* 
 * ===========================================================================
 * File Name UpdateOnlineStatementPrefController.java
 * 
 * Created on May 31, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateOnlineStatementPrefController.java,v $
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
import org.uhc.controller.envelop.request.UpdateOnlineStatementPrefRequest;
import org.uhc.service.UpdateOnlineStatementPrefService;

/**
 * @author nehas3
 * @date May 31, 2018
 * @Description : This is UpdateOnlineStatementPrefController class to move the
 *              control of the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class UpdateOnlineStatementPrefController {

	private static final Logger LOGGER = LogManager.getLogger(UpdateOnlineStatementPrefController.class);

	@Autowired
	private UpdateOnlineStatementPrefService statementPrefService;

	/**
	 * This is UpdateEmail service API to update theEmail of user profile
	 * @author nehas3
	 * @date May 28, 2018
	 * @return Object
	 * @param updateEmailRequest
	 * @return updateEmailResponse
	 */
	@PostMapping(value = "/updateOnlineStatementPreference")
	public Object updateOnlineStatemntPrefAPI(@RequestBody UpdateOnlineStatementPrefRequest statementPrefRequest) {
		LOGGER.info("UpdateOnlineStatemntPref Service API is called: {}",
				statementPrefRequest != null ? statementPrefRequest.toString() : "");
		return statementPrefService.updateOnlineStatementPref(statementPrefRequest);
	}
}
