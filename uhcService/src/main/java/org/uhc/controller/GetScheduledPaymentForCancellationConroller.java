/* 
 * ===========================================================================
 * File Name GetScheduledPaymentForCancellationConroller.java
 * 
 * Created on Jul 9, 2018
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
import org.uhc.controller.envelop.request.GetScheduledPaymentForCancellationReq;
import org.uhc.service.GetScheduledPaymentForCancellationService;

/**
 * @author nehas3
 * @date Jul 9, 2018
 * @Description : This is GetScheduledPaymentForCancellationConroller class to
 *              move the control of the concerned Request URL to the correct
 *              Service.
 */
@RestController
@RequestMapping(value = "/")
public class GetScheduledPaymentForCancellationConroller {

	private static final Logger LOGGER = LogManager.getLogger(GetScheduledPaymentForCancellationConroller.class);

	@Autowired
	private GetScheduledPaymentForCancellationService scheduledPaymentsService;

	/**
	 * This is getScheduledPaymentsForCancellation Service API to Get already
	 * Scheduled Payments info
	 * @author nehas3
	 * @date Jul 9, 2018
	 * @return Object
	 * @param scheduledPaymentRequest
	 * @return scheduledPaymentResponse
	 */
	@PostMapping(value = "/getScheduledPaymentsForCancellation")
	public Object getScheduledPymentForCancellationAPI(
			@RequestBody GetScheduledPaymentForCancellationReq scheduledPaymentRequest) {
		LOGGER.info("Get Scheduled Payments For Cancellation API is called: {}",
				scheduledPaymentRequest != null ? scheduledPaymentRequest.toString() : "");
		return scheduledPaymentsService.getScheduledPayments(scheduledPaymentRequest);
	}

}
