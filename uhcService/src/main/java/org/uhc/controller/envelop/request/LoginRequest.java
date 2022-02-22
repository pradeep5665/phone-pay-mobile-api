/* 
 * ===========================================================================
 * File Name LoginRequest.java
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
 * $Log: LoginRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

import org.springframework.stereotype.Component;

/**
 * @author nehas3
 * @date May 25, 2018 Description : This is LoginRequest request bean to get
 *       authenticated user to log in.
 */
@Component
public class LoginRequest {

	private String userName;
	private String password;
	private String token;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMaskedPassword() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.password.length(); i++)
			sb.append('*');
		return sb.toString();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "{\"LoginRequest\": {" +
				"\"userName\": \"" + userName + "\"" +
				",\"password\": \"<omitted>\"" +
				",\"token\": \"" + token + "\"" +
				"}}";
	}
}
