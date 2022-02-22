/* 
 * ===========================================================================
 * File Name EscrowController.java
 * 
 * Created on May 25, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: EscrowController.java,v $
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
import org.uhc.controller.envelop.request.EscrowRequest;
import org.uhc.controller.envelop.request.EscrowRequestByLoanNumber;
import org.uhc.service.EscrowService;

/**
 * @author nehas3
 * @date May 25, 2018
 * @Description : This is EscrowController class to move the control of the
 *              concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class EscrowController {

	private static final Logger LOGGER = LogManager.getLogger(EscrowController.class);

	@Autowired
	private EscrowService escrowService;

	/**
	 * esxcrowAPI have been created to get escrow payment info for user
	 * @author nehas3
	 * @date May 25, 2018
	 * @return Object
	 * @param escrowResquest
	 * @return 
	 */

	@PostMapping(value = "/escrowInfoByLoanNumber")
	public Object esxcrowAPI(@RequestBody EscrowRequestByLoanNumber escrowRequest) {
		LOGGER.info("Escrow API is called: {}", escrowRequest!=null ? escrowRequest.toString():"");
		return escrowService.getEscrowInfoByLoanNumber(escrowRequest);
	}

	@PostMapping(value = "/escrowInfo")
	public Object esxcrowAPI(@RequestBody EscrowRequest escrowRequest) {
		LOGGER.info("Escrow API is called: {}", escrowRequest.toString());
		return escrowService.getEscrowInfo(escrowRequest);
	}
}
