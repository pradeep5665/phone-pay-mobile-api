/* 
 * ===========================================================================
 * File Name GetScheduledPaymentDto.java
 * 
 * Created on Jul 2, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetScheduledPaymentDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.uhc.util.Constants;

/**
 * @author nehas3
 * @date Jul 2, 2018
 */
public class GetScheduledPaymentDto {

	private int userId;
	private long loanNumber;
	private String scheduledType;
	private String processedDate;
	private String scheduledDate;
	private String status;
	private String paymentSource;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	public String getScheduledType() {
		return scheduledType;
	}

	public void setScheduledType(String scheduledType) {
		this.scheduledType = scheduledType;
	}

	public String getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Date processedDate) {
		if (processedDate != null) {
			DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
			this.processedDate = sdf.format(processedDate);
		} else {
			this.processedDate = null;
		}
	}

	public String getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(Date scheduledDate) {
		if (scheduledDate != null) {
			DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
			this.scheduledDate = sdf.format(scheduledDate);
		} else {
			this.scheduledDate = null;
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentSource() {
		return paymentSource;
	}

	public void setPaymentSource(String paymentSource) {
		this.paymentSource = paymentSource;
	}
}
