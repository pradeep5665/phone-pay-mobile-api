/* 
 * ===========================================================================
 * File Name ValidateLoanNumberRes.java
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
 * $Log: ValidateLoanNumberRes.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

public class ValidateRoutingNumberRes {

	private Boolean isRoutingNumberValid;
	private String message;

	public Boolean getIsRoutingNumberValid() {
		return isRoutingNumberValid;
	}

	public void setIsRoutingNumberValid(Boolean isRoutingNumberValid) {
		this.isRoutingNumberValid = isRoutingNumberValid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
