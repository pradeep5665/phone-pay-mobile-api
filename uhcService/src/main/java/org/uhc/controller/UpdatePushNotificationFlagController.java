/* 
 * ===========================================================================

 * File Name UpdatePushNotificationFlagController.java
 * 
 * Created on Oct 22, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdatePushNotificationFlagController.java,v $
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
import org.uhc.controller.envelop.request.UpdatePushNotificationFlagRequest;
import org.uhc.service.UpdatePushNotificationFlagService;

@RestController
@RequestMapping(value = "/")
public class UpdatePushNotificationFlagController {
	private static final Logger LOGGER = LogManager.getLogger(UpdatePushNotificationFlagController.class);

	@Autowired
	private UpdatePushNotificationFlagService updatePushNotificationFlagService;

	/**
	 * UpdateIncorrectBankingInfoResponse UpdateIncorrectBankingInfo Service API to
	 * call the UpdateIncorrectBankingInfo Service
	 * @author nehas3
	 * @date September 23, 2019
	 * @return Object
	 * @param UpdateIncorrectBankingInfoRequest
	 * @return
	 */
	@PostMapping(value = "/updatePushNotificationFlag")
	public Object updatePushNotificationFlagAPI(
			@RequestBody UpdatePushNotificationFlagRequest updatePushNotificationFlagRequest) {
		LOGGER.info("UpdatePushNotificationFlag API is called: {}",
				updatePushNotificationFlagRequest != null ? updatePushNotificationFlagRequest.toString() : "");
		return updatePushNotificationFlagService.updatePushNotificationFlag(updatePushNotificationFlagRequest);
	}
}
