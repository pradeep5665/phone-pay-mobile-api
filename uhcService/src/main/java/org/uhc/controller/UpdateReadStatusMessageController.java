/* 
 * ===========================================================================
 * File Name UpdateReadStatusMessageController.java
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
 * $Log: UpdateReadStatusMessageController.java,v $
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
import org.uhc.controller.envelop.request.UpdateReadStatusMessageRequest;
import org.uhc.service.UpdateReadStatusService;

/**
 * @author nehas3
 * @date Aug 22, 2018
 * @Description : This is UpdateReadStatusMessageController class to move the
 *              control of the concerned Requested URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class UpdateReadStatusMessageController {

	private static final Logger LOGGER = LogManager.getLogger(UpdateReadStatusMessageController.class);

	@Autowired
	private UpdateReadStatusService readStatusService;

	/**
	 *  This is update read status API to call the Account Service
	 * @author nehas3
	 * @date Aug 22, 2018
	 * @return Object
	 * @param readStatusResquest
	 * @return UpdateReadStatusMessageResponse
	 */
	@PostMapping(value = "/updateReadStatusOfMessage")
	public Object updateReadStatusOfMessageAPI(@RequestBody UpdateReadStatusMessageRequest readStatusResquest) {
		LOGGER.info("Update Read Status Of Message API is called: {}",
				readStatusResquest != null ? readStatusResquest.toString() : "");
		return readStatusService.updateReadStatusOfMessage(readStatusResquest);
	}
}
