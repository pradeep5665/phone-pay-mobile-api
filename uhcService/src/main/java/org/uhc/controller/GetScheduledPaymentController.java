/* 
 * ===========================================================================
 * File Name GetScheduledPaymentController.java
 * 
 * Created on July 2, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetScheduledPaymentController.java,v $
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
import org.uhc.controller.envelop.request.GetScheduledPaymentRequest;
import org.uhc.service.GetScheduledPaymentService;

/**
 * @author nehas3
 * @date Jul 2, 2018
 * @Description : This is GetScheduledPaymentController class to move the control of the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class GetScheduledPaymentController {

	private static final Logger LOGGER = LogManager.getLogger(GetScheduledPaymentController.class);
	
	@Autowired
	private GetScheduledPaymentService scheduledPaymentsService;
	
	/**
	 * This is getScheduledPayment Service API to Get Scheduled Payments info
	 * @author nehas3
	 * @date Jul 2, 2018
	 * @return Object 
	 * @param scheduledPaymentRequest
	 * @return scheduledPaymentResponse
	 */
	@PostMapping(value = "/getScheduledPayments")
	public Object getScheduledPaymentAPI(@RequestBody GetScheduledPaymentRequest scheduledPaymentRequest) {
		LOGGER.info("Get Scheduled Payment API is called: {}", scheduledPaymentRequest!=null ? scheduledPaymentRequest.toString():"");
		return scheduledPaymentsService.getScheduledPayments(scheduledPaymentRequest);
	}
}
