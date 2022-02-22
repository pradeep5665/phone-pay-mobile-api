/* 
 * ===========================================================================
 * File Name ScheduledPaymentRequest.java
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
 * $Log: ScheduledPaymentRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.uhc.util.Constants;

/**
 * @author nehas3
 * @date Jun 26, 2018
 * @Description : This is ScheduledPaymentRequest request bean to scheduled a
 *              payment on the basis of ScheduledPaymentRequest attributes.
 */
public class ScheduledPaymentRequest {

	private int paymentId;
	private Date userDefinedDate;
	private List<Date> scheduledDate = new ArrayList<>();
	private String scheduledType;
	private int userId;
	private long loanNumber;
	private int cancelled;
	private String confirmationNumber;
	private LocalDateTime createdDate;
	private LocalDateTime canceledDate;
	private BigDecimal monthlyPayment;
	private BigDecimal lateFeesOwed = BigDecimal.ZERO;
	private BigDecimal nsfFeesOwed = BigDecimal.ZERO;
	private BigDecimal lateFeesPaid = BigDecimal.ZERO;
	private BigDecimal nsfFeesPaid = BigDecimal.ZERO;
	private BigDecimal extraPrincipal = BigDecimal.ZERO;
	private BigDecimal extraEscrow = BigDecimal.ZERO;
	private BigDecimal totalPayment;
	private Date processedDate;
	private boolean emailReminder;
	private boolean pay;

	private static final Logger LOGGER = LogManager.getLogger(ScheduledPaymentRequest.class);
	
	public ScheduledPaymentRequest() {
		// default constructor is needed
	}

	public ScheduledPaymentRequest(ScheduledPaymentRequest schedulePayment) {
		super();
		this.paymentId = schedulePayment.paymentId;
		this.userDefinedDate = schedulePayment.userDefinedDate;
		this.scheduledDate = schedulePayment.scheduledDate;
		this.scheduledType = schedulePayment.scheduledType;
		this.userId = schedulePayment.userId;
		this.loanNumber = schedulePayment.loanNumber;
		this.cancelled = schedulePayment.cancelled;
		this.createdDate = schedulePayment.createdDate;
		this.canceledDate = schedulePayment.canceledDate;
		this.monthlyPayment = schedulePayment.monthlyPayment;
		this.lateFeesOwed = schedulePayment.lateFeesOwed;
		this.nsfFeesOwed = schedulePayment.nsfFeesOwed;
		this.lateFeesPaid = schedulePayment.lateFeesPaid;
		this.nsfFeesPaid = schedulePayment.nsfFeesPaid;
		this.extraPrincipal = schedulePayment.extraPrincipal;
		this.extraEscrow = schedulePayment.extraEscrow;
		this.totalPayment = schedulePayment.totalPayment;
		this.processedDate = schedulePayment.processedDate;
		this.emailReminder = schedulePayment.emailReminder;
	}

	/** ============Setters and Getters============= */

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public Date getUserDefinedDate() {
		return userDefinedDate;
	}

	public void setUserDefinedDate(String userDefinedDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
		try {
			this.userDefinedDate = formatter.parse(userDefinedDate);
		} catch (ParseException exp) {
			LOGGER.error("Exception occured because of : {}", exp.getMessage());
			this.userDefinedDate = null;
		}
	}

	public List<Date> getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(List<String> scheduledDates) {
		for (String scheduleDate : scheduledDates) {
			SimpleDateFormat formatter = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
			try {
				if(this.scheduledDate!=null) {
					this.scheduledDate.add(formatter.parse(scheduleDate));
				}
			} catch (ParseException exp) {
				LOGGER.error("Exception occured because of : {}", exp.getMessage());
				this.scheduledDate = null;
			}
		}
	}

	public String getScheduledType() {
		return scheduledType;
	}

	public void setScheduledType(String scheduledType) {
		this.scheduledType = scheduledType;
	}

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

	public int getCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled ? 1 : 0;
	}

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getCanceledDate() {
		return canceledDate;
	}

	public void setCanceledDate(LocalDateTime canceledDate) {
		this.canceledDate = canceledDate;
	}

	public BigDecimal getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(BigDecimal monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public BigDecimal getLateFeesOwed() {
		return lateFeesOwed;
	}

	public void setLateFeesOwed(BigDecimal lateFeesOwed) {
		this.lateFeesOwed = lateFeesOwed;
	}

	public BigDecimal getNsfFeesOwed() {
		return nsfFeesOwed;
	}

	public void setNsfFeesOwed(BigDecimal nsfFeesOwed) {
		this.nsfFeesOwed = nsfFeesOwed;
	}

	public BigDecimal getLateFeesPaid() {
		return lateFeesPaid;
	}

	public void setLateFeesPaid(BigDecimal lateFeesPaid) {
		this.lateFeesPaid = lateFeesPaid;
	}

	public BigDecimal getNsfFeesPaid() {
		return nsfFeesPaid;
	}

	public void setNsfFeesPaid(BigDecimal nsfFeesPaid) {
		this.nsfFeesPaid = nsfFeesPaid;
	}

	public BigDecimal getExtraPrincipal() {
		return extraPrincipal;
	}

	public void setExtraPrincipal(BigDecimal extraPrincipal) {
		this.extraPrincipal = extraPrincipal;
	}

	public BigDecimal getExtraEscrow() {
		return extraEscrow;
	}

	public void setExtraEscrow(BigDecimal extraEscrow) {
		this.extraEscrow = extraEscrow;
	}

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}

	public Date getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}

	public boolean isEmailReminder() {
		return emailReminder;
	}

	public void setEmailReminder(boolean emailReminder) {
		this.emailReminder = emailReminder;
	}

	public boolean isPay() {
		return pay;
	}

	public void setPay(boolean pay) {
		this.pay = pay;
	}
}
