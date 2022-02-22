/* 
 * ===========================================================================
 * File Name BasicAuthEntryPoint.java
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
 * $Log: BasicAuthEntryPoint.java,v $
 * ===========================================================================
 */
package org.uhc.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author nehas3
 * @date May 25, 2018
 * Description : This is BasicAuthEntryPoint class to check if requested user is authorized and authenticated or not.
 */
@Component
public class BasicAuthEntryPoint extends BasicAuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		     AuthenticationException authException) throws IOException, ServletException {
		// Authentication failed, send error response.
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName("UHC_SERVICE_REALM");
	}
}
