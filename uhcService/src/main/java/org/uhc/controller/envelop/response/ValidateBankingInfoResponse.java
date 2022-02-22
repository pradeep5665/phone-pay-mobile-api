/* 
 * ===========================================================================
 * File Name ValidateBankingInfoResponse.java
 * 
 * Created on Aug 23, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateBankingInfoResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

public class ValidateBankingInfoResponse {

	private int incorrectBankiInfoId;
	private String needNotificationValue;
	private int errorCode;
	private String message;

	public int getIncorrectBankiInfoId() {
		return incorrectBankiInfoId;
	}

	public void setIncorrectBankiInfoId(int incorrectBankiInfoId) {
		this.incorrectBankiInfoId = incorrectBankiInfoId;
	}

	public String getNeedNotificationValue() {
		return needNotificationValue;
	}

	public void setNeedNotificationValue(String needNotificationValue) {
		this.needNotificationValue = needNotificationValue;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
