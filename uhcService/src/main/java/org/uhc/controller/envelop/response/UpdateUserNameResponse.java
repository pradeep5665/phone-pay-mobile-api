/* 
 * ===========================================================================
 * File Name UpdateUserNameResponse.java
 * 
 * Created on May 29, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateUserNameResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

/**
 * @author nehas3
 * @date May 29, 2018
 * @description creating Update UserName Service Response for success or failure
 */
public class UpdateUserNameResponse {

	 private Boolean isSuccessful;
	 private String message;
	public Boolean getIsSuccessful() {
		return isSuccessful;
	}
	public void setIsSuccessful(Boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	 
	 
}
