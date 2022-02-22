/* 
 * ===========================================================================
 * File Name ScheduledPaymentResponse.java
 * 
 * Created on Jun 26, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: ScheduledPaymentResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

/**
 * @author nehas3
 * @date Jun 26, 2018
 * @description creating Scheduled Payment Response for success or failure
 */
public class ScheduledPaymentResponse {

	private Boolean isSuccessful;
	private String message;
	private String confirmationNumber;

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

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

}
