/* 
 * ===========================================================================
 * File Name ValidateUserNameRes.java
 * 
 * Created on Aug 13, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateUserNameRes.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

public class ValidateUserNameRes {

	private Boolean isUserNameValid;
	private String message;

	public Boolean getIsUserNameValid() {
		return isUserNameValid;
	}

	public void setIsUserNameValid(Boolean isUserNameValid) {
		this.isUserNameValid = isUserNameValid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
