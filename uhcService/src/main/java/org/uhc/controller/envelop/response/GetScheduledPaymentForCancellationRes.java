/* 
 * ===========================================================================
 * File Name GetScheduledPaymentForCancellationRes.java
 * 
 * Created on July 9, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetScheduledPaymentForCancellationRes.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

import java.util.List;

import org.uhc.dao.dto.GetScheduledPaymentForCancellationDto;

/**
 * @author nehas3
 * @date July 9, 2018
 * @description Creating GetScheduledPaymentForCancellationResponse Information
 *              Service Response for success or failure
 */
public class GetScheduledPaymentForCancellationRes {

	private Boolean isSuccessful;
	private String message;
	private List<GetScheduledPaymentForCancellationDto> schduledPayments;

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

	public List<GetScheduledPaymentForCancellationDto> getSchduledPayments() {
		return schduledPayments;
	}

	public void setSchduledPayments(List<GetScheduledPaymentForCancellationDto> schduledPayments) {
		this.schduledPayments = schduledPayments;
	}

	@Override
	public String toString() {
		return "GetScheduledPaymentForCancellationRes{" +
				"isSuccessful=" + isSuccessful +
				", message='" + message + '\'' +
				", schduledPayments count=" + schduledPayments.size() +
				'}';
	}
}
