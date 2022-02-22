/* 
 * ===========================================================================
 * File Name UpdateIncorrectBankingInfoController.java
 * 
 * Created on Sep 23, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateIncorrectBankingInfoController.java,v $
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
import org.uhc.controller.envelop.request.UpdateIncorrectBankingInfoRequest;
import org.uhc.service.UpdateIncorrectBankingInfoService;

/**
 * @author nehas3
 * @date September 23, 2019
 * @Description : This is UpdateIncorrectBankingInfoController class to move the
 *              control of the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class UpdateIncorrectBankingInfoController {
	private static final Logger LOGGER = LogManager.getLogger(UpdateIncorrectBankingInfoController.class);
	@Autowired
	private UpdateIncorrectBankingInfoService updateIncorrectBankingInfo;

	/**
	 * UpdateIncorrectBankingInfoResponse UpdateIncorrectBankingInfo Service API to call the UpdateIncorrectBankingInfo Service
	 * @author nehas3
	 * @date September 23, 2019
	 * @return Object
	 * @param UpdateIncorrectBankingInfoRequest
	 * @return 
	 */
	@PostMapping(value = "/updateIncorrectBankingInfo")
	public Object validateBankingInfoAPI(
			@RequestBody UpdateIncorrectBankingInfoRequest updateIncorrectBankingInfoRequest) {
		LOGGER.info("UpdateIncorrectBankingInfo API is called: {}",
				updateIncorrectBankingInfoRequest != null ? updateIncorrectBankingInfoRequest.toString() : "");
		return updateIncorrectBankingInfo.updateIncorrectBankingInfo(updateIncorrectBankingInfoRequest);
	}
}
