/* 
 * ===========================================================================
 * File Name GetScheduledPaymentResponse.java
 * 
 * Created on July 2, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetScheduledPaymentResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

import java.util.List;

import org.uhc.dao.dto.GetScheduledPaymentDto;

/**
 * @author nehas3
 * @date July 2, 2018
 * @description Creating GetScheduledPaymentResponse Information Service
 *              Response for success or failure
 */
public class GetScheduledPaymentResponse {

	private Boolean isSuccessful;
	private String message;
	private List<GetScheduledPaymentDto> scheduledPayments;

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

	public List<GetScheduledPaymentDto> getScheduledPayments() {
		return scheduledPayments;
	}

	public void setScheduledPayments(List<GetScheduledPaymentDto> scheduledPayments) {
		this.scheduledPayments = scheduledPayments;
	}

	@Override
	public String toString() {
		return "GetScheduledPaymentResponse{" +
				"isSuccessful=" + isSuccessful +
				", message='" + message + '\'' +
				", scheduledPayments=" + scheduledPayments +
				'}';
	}
}
