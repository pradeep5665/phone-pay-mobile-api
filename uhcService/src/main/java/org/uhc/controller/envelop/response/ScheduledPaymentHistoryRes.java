/* 
 * ===========================================================================
 * File Name ScheduledPaymentHistoryRes.java
 * 
 * Created on Jul 13, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: ScheduledPaymentHistoryRes.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

import java.util.List;

import org.uhc.dao.dto.PaymentDto;

/**
 * @author nehas3
 * @date Jul 13, 2018
 * @description creating ScheduledPayment History Response for success or
 *              failure
 */
public class ScheduledPaymentHistoryRes {

	private Boolean isSuccessful;
	private String error;
	private List<PaymentDto> scheduledPaymentHistory;

	public Boolean getIsSuccessful() {
		return isSuccessful;
	}

	public void setIsSuccessful(Boolean isSuccessful) {
		if (isSuccessful != null) {
			this.isSuccessful = isSuccessful;
		} else {
			this.isSuccessful = false;
		}

	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<PaymentDto> getScheduledPaymentHistory() {
		return scheduledPaymentHistory;
	}

	public void setScheduledPaymentHistory(List<PaymentDto> scheduledPaymentHistory) {
		this.scheduledPaymentHistory = scheduledPaymentHistory;
	}
}