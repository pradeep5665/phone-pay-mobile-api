/* 
 * ===========================================================================
 * File Name UpdateEmailController.java
 * 
 * Created on May 28, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateEmailController.java,v $
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
import org.uhc.controller.envelop.request.UpdateEmailRequest;
import org.uhc.service.UpdateEmailService;

/**
 * @author nehas3
 * @date May 25, 2018
 * @Description : This is UpdateEmailController class to move the control of the
 *              concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class UpdateEmailController {

	private static final Logger LOGGER = LogManager.getLogger(UpdateEmailController.class);

	@Autowired
	private UpdateEmailService updateEmailService;

	/**
	 * updateEmailResponse This is UpdateEmail service API to update the Email of user profile
	 * @author nehas3
	 * @date May 28, 2018
	 * @return Object
	 * @param updateEmailRequest
	 * @return
	 */
	@PostMapping(value = "/updateEmail")
	public Object updateEmailAPI(@RequestBody UpdateEmailRequest updateEmailRequest) {
		LOGGER.info("UpdateEmail Service API is called: {}",
				updateEmailRequest != null ? updateEmailRequest.toString() : "");
		return updateEmailService.updateEmail(updateEmailRequest);
	}
}
