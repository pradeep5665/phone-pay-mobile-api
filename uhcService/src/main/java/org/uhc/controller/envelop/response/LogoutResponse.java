/* 
 * ===========================================================================
 * File Name LogoutResponse.java
 * 
 * Created on Aug 24, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: LogoutResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

/**
 * @author nehas3
 * @date Aug 24, 2018
 * @description creating Logout Service Response for success or failure of user's log out.
 */
public class LogoutResponse {

	private boolean isSuccessful;
	private String message;
	
	public boolean isSuccessful() {
		return isSuccessful;
	}
	public void setIsSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "LogoutResponse{" +
				"isSuccessful=" + isSuccessful +
				", message='" + message + '\'' +
				'}';
	}
}
