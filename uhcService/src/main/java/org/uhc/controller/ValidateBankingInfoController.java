/* 
 * ===========================================================================
 * File Name ValidateBankingInfoController.java
 * 
 * Created on Aug 23, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateBankingInfoController.java,v $
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
import org.uhc.controller.envelop.request.ValidateBankingInfoRequest;
import org.uhc.service.ValidateBankingInfoService;

/**
 * @author nehas3
 * @date August 23, 2019
 * @Description : This is ValidateBankingInfoController class to move the
 *              control of the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class ValidateBankingInfoController {

	private static final Logger LOGGER = LogManager.getLogger(ValidateBankingInfoController.class);

	@Autowired
	private ValidateBankingInfoService validateBankingInfoService;

	/**
	 * ValidateBankingInfo Service API to call the ValidateBankingInfo Service
	 * 
	 * @author nehas3
	 * @date August 23, 2019
	 * @return Object
	 * @param validateBankingInfoReq
	 * @return validateBankingInfoRes
	 */
	@PostMapping(value = "/validateBankingInfo")
	public Object validateBankingInfoAPI(@RequestBody ValidateBankingInfoRequest validateBankingInfoReq) {
		LOGGER.info("ValidateBankingInfo API is called: {}",
				validateBankingInfoReq != null ? validateBankingInfoReq.toString() : "");
		return validateBankingInfoService.validateBankingInfo(validateBankingInfoReq);
	}

}
