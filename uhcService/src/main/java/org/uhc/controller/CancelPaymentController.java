/* 
 * ===========================================================================
 * File Name CancelPaymentController.java
 * 
 * Created on July 11, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: CancelPaymentController.java,v $
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
import org.uhc.controller.envelop.request.CancelPaymentRequest;
import org.uhc.service.CancelPaymentService;

/**
 *  @author nehas3
 *  @date July 11, 2018
 *  @Description : This is CancelPaymentController class to move the control of the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class CancelPaymentController {

	private static final Logger LOGGER = LogManager.getLogger(CancelPaymentController.class);
	
	@Autowired
	private CancelPaymentService cancelPaymentService;
	
	/**
	 * cancelPaymentAPI API have been created to cancel the payment on basis of payment id
	 * @author nehas3
	 * @date July 11, 2018
	 * @return response of cancel payment API 
	 * @param cancelPaymentRequest
	 * @return cancelPaymentResponse
	 */
	@PostMapping(value = "/cancelPayment")
	public Object cancelPaymentAPI(@RequestBody CancelPaymentRequest cancelPaymentRequest) {
		LOGGER.info("Cancel Payment Service API is called: {}",cancelPaymentRequest!=null ? cancelPaymentRequest.toString(): "");
		return cancelPaymentService.cancelPayment(cancelPaymentRequest);
	}
}
