/* 
 * ===========================================================================
 * File Name ValidateAccountRecoveryKeyRes.java
 * 
 * Created on Jan 27, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateAccountRecoveryKeyRes.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

public class ValidateAccountRecoveryKeyRes {
	private Boolean isSuccessFull;
	private int statusCode;
	private String message;
	private String userName;

	public Boolean getIsSuccessFull() {
		return isSuccessFull;
	}

	public void setIsSuccessFull(Boolean isSuccessFull) {
		this.isSuccessFull = isSuccessFull;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
