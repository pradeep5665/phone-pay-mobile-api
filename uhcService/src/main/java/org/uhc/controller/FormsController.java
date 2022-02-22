/* 
 * ===========================================================================
 * File Name FormsController.java
 * 
 * Created on Aug 9, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: FormsController.java,v $
 * ===========================================================================
 */
package org.uhc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uhc.service.FormService;

@RestController
@RequestMapping(value = "/")
public class FormsController {

	private static final Logger LOGGER = LogManager.getLogger(FormsController.class);

	@Autowired
	private FormService formService;

	/**
	 * This is Form List Service API to call formsss Service
	 * @author nehas3
	 * @date Aug 28, 2018
	 * @return list of forms
	 */
	@RequestMapping(value = "/forms")
	public Object formListAPI() {
		LOGGER.info("FormService API is called");
		return formService.getAllForms();
	}
}
