/* 
 * ===========================================================================
 * File Name ProcessedPaymentDto.java
 * 
 * Created on Apr 10, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: ProcessedPaymentDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.uhc.util.Constants;

public class ProcessedPaymentDto {

	private int schedulePaymentId;
	private int paymentId;
	private int userId;
	private String scheduledType;
	private long loanNumber;
	private boolean canceled;
	private Date processedDate;
	private BigDecimal totalPayment;

	public int getSchedulePaymentId() {
		return schedulePaymentId;
	}

	public void setSchedulePaymentId(int schedulePaymentId) {
		this.schedulePaymentId = schedulePaymentId;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getScheduledType() {
		return scheduledType;
	}

	public void setScheduledType(String scheduledType) {
		this.scheduledType = scheduledType;
	}

	public long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public Date getProcessedDate() {
		return processedDate;
	}

	public String getProcessedDateOfPayment() {
		DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
		return sdf.format(getProcessedDate());
	}
	
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}

}
