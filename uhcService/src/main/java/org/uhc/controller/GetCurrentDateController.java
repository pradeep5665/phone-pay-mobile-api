/* 
 * ===========================================================================
 * File Name GetCurrentDateController.java
 * 
 * Created on Nov 6, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetCurrentDateController.java,v $
 * ===========================================================================
 */
 package org.uhc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class GetCurrentDateController {
	
	private static final Logger LOGGER = LogManager.getLogger(GetCurrentDateController.class);

	/**
	 * getCurrentDateAPI have been created to get server date time.
	 * @author nehas3
	 * @since Oct 13, 2020
	 * @return current date from server
	 */
	@PostMapping(value = "/getCurrentTime")
	 public String getCurrentDateAPI() {
		LOGGER.info("getCurrentDateAPI() called");
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		dateFormat.setTimeZone(TimeZone.getTimeZone("MST"));
	    return dateFormat.format(date);
		}
}
