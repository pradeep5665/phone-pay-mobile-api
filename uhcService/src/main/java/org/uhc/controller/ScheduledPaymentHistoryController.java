/* 
 * ===========================================================================
 * File Name ScheduledPaymentHistoryController.java
 * 
 * Created on July 13, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: ScheduledPaymentHistoryController.java,v $
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
import org.uhc.controller.envelop.request.ScheduledPaymentHistoryByUserIdAndLoanNumberReq;
import org.uhc.controller.envelop.request.ScheduledPaymentHistoryReq;
import org.uhc.service.ScheduledPaymentHistoryService;

/**
 * @author nehas3
 * @date July 13, 2018
 * @Description : This is ScheduledPaymentHistoryController class to move the
 *              control of the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class ScheduledPaymentHistoryController {

	private static final Logger LOGGER = LogManager.getLogger(ScheduledPaymentHistoryController.class);

	@Autowired
	private ScheduledPaymentHistoryService scheduledPaymentsHistoryService;

	/**
	 * This is getScheduledPaymentHistory Service API to Get History of Scheduled Payments.
	 * @author nehas3
	 * @date July 13, 2018
	 * @return Object
	 * @param escrowResquest
	 * @return escrowResponse
	 */
	@PostMapping(value = "/getScheduledPaymentHistoryByUserIdAndLoanNumber")
	public Object getScheduledPaymentHistoryAPIByLoanNumber(@RequestBody ScheduledPaymentHistoryByUserIdAndLoanNumberReq scheduledPaymentHistoryReq) {
		LOGGER.info("Get Scheduled Payment API ByUserIdAndLoanNumber is called: {}", scheduledPaymentHistoryReq);
		return scheduledPaymentsHistoryService.getScheduledPaymentHistoryByLoanNumber(scheduledPaymentHistoryReq);
	}
	
	@PostMapping(value = "/getScheduledPaymentHistory")
	public Object getScheduledPaymentHistoryAPI(@RequestBody ScheduledPaymentHistoryReq scheduledPaymentHistoryReq) {
		LOGGER.info("Get Scheduled Payment API is called" + scheduledPaymentHistoryReq.toString());
		return scheduledPaymentsHistoryService.getScheduledPaymentHistory(scheduledPaymentHistoryReq);
	}
}
