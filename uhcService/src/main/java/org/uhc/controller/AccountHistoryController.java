/* 
 * ===========================================================================
 * File Name AccountHistoryController.java
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
 * $Log: AccountHistoryController.java,v $
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
import org.uhc.controller.envelop.request.AccountHistoryRequest;
import org.uhc.service.AccountHistoryService;

/**
 * @author nehas3
 * @date May 25, 2018
 * @Description : This is AccountHistoryController class to move the control of
 *              the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class AccountHistoryController {

	private static final Logger LOGGER = LogManager.getLogger(AccountHistoryController.class);
	
	@Autowired
	private AccountHistoryService accountHistoryService;

	/**
	 * Account History API to call Account History Service to get account history of user
	 * @author nehas3
	 * @date May 25, 2018
	 * @return Object
	 * @param accountHistoryRequest
	 * @return accountHistoryResponse
	 */
	@PostMapping(value = "/accountHistory")
	public Object accountHistoryAPI(@RequestBody AccountHistoryRequest accountHistoryRequest) {
		LOGGER.info("AccountHistoryService API is called: {}", accountHistoryRequest!=null? accountHistoryRequest.toString(): "");
		return accountHistoryService.getAccountHistoryByLoanNumber(accountHistoryRequest);
	}

}
