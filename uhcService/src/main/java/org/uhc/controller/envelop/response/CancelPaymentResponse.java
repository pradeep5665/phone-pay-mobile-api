/* 
 * ===========================================================================
 * File Name CancelPaymentResponse.java
 * 
 * Created on Jul 11, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: CancelPaymentResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

/**
 * @author nehas3
 * @date Jul 11, 2018
 * @description creating Cancel Payment Response for success or failure
 */
public class CancelPaymentResponse {

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

	@Override
	public String toString() {
		return "CancelPaymentResponse{" +
				"isSuccessful=" + isSuccessful +
				", message='" + message + '\'' +
				'}';
	}
}
