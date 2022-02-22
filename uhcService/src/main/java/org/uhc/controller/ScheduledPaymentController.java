/* 
 * ===========================================================================
 * File Name ScheduledPaymentController.java
 * 
 * Created on Jun 26, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: ScheduledPaymentController.java,v $
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
import org.uhc.controller.envelop.request.LoanPaymentRequest;
import org.uhc.service.ScheduledPaymentService;

/**
 * @author nehas3
 * @date Jun 26, 2018
 * @Description : This is ScheduledPaymentController class to move the control of the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class ScheduledPaymentController {

	private static final Logger LOGGER = LogManager.getLogger(ScheduledPaymentController.class);
	
	@Autowired
	private ScheduledPaymentService scheduledPaymentService;

	/**
	 * This is schedulePayment service API to schedule a Payment.
	 * @author nehas3
	 * @date Jun 26, 2018
	 * @return Object 
	 * @param scheduledLoanPaymentRequest
	 * @return scheduledLoanPaymentResponse
	 */
	@PostMapping(value = "/schedulePayment")
	public Object schedulePayment(@RequestBody LoanPaymentRequest scheduledLoanPaymentRequest) {
		LOGGER.info("schedulePayment Service API is called: {}", scheduledLoanPaymentRequest!=null ? scheduledLoanPaymentRequest.toString():"");
		return scheduledPaymentService.schedulePayment(scheduledLoanPaymentRequest);
	}
}
