/* 
 * ===========================================================================
 * File Name UpdateIncorrectBankingInfoResponse.java
 * 
 * Created on Sep 23, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateIncorrectBankingInfoResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

public class UpdateIncorrectBankingInfoResponse {

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
