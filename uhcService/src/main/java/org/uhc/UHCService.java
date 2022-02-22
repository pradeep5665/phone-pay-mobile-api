/* 
 * ===========================================================================
 * File Name UHCSerivce.java
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
 * $Log: UHCSerivce.java,v $
 * ===========================================================================
 */
package org.uhc;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author nehas3
 * @date May 25, 2018
 */
@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
public class UHCService extends SpringBootServletInitializer {

	private static final Logger LOG = LogManager.getLogger(UHCService.class);

	@PostConstruct
	public void init() {
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("America/Denver"));
	}

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return void
	 * @param application
	 * @description Configure the application. Normally all you would need to do is
	 *              to add sources because other settings have sensible defaults.
	 *              You might choose (for instance) to add default command line
	 *              arguments, or set an active Spring profile.
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		LOG.info("UHCService Configuration started");
		return application.sources(UHCService.class);
	}

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return void
	 * @param args
	 */
	public static void main(String[] args) {
		LOG.info("UHCService Application started");
		SpringApplication.run(UHCService.class, args);
	}

}