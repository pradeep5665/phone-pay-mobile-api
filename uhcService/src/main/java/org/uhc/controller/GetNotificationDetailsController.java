/* 
 * ===========================================================================
 * File Name GetNotificationDetailsController.java
 * 
 * Created on Aug 22, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetNotificationDetailsController.java,v $
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
import org.uhc.controller.envelop.request.GetNotificationDetailsRequest;
import org.uhc.service.GetNotificationDetailsService;

/**
 * @author nehas3 
 * @date Aug 22, 2018
 * @Description : This is GetNotificationDetailsController class to move the control of the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class GetNotificationDetailsController {

	private static final Logger LOGGER = LogManager.getLogger(GetNotificationDetailsController.class);
	
	@Autowired
	private GetNotificationDetailsService notificationService;
	
	/**
	 * Get Notification Details API to call GetNotificationDetails Service 
	 * @author nehas3
	 * @date Aug 23, 2018
	 * @return Object 
	 * @param notificationResquest
	 * @return getNotificationDetailsResponse
	 */
	@PostMapping(value = "/getNotificationDetails")
	public Object getNotificationDetailsAPI(@RequestBody GetNotificationDetailsRequest notificationResquest) {
		LOGGER.info("{\"getNotificationDetailsAPI\": {}", notificationResquest.getUserId());
		return notificationService.getNotificationDetails(notificationResquest);
	}
}
