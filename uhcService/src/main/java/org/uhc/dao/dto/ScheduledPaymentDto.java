/* 
 * ===========================================================================
 * File Name ScheduledPaymnetDto.java
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
 * $Log: ScheduledPaymnetDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.uhc.util.Constants;

/**
 * @author nehas3
 * @date Jul 2, 2018
 */
public class ScheduledPaymentDto {

	private int paymentId;
	private Date userDefinedDate;
	private Date scheduledDate;
	private String scheduledType;
	private int userId;
	private long loanNumber;
	private boolean canceled;
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

	public ScheduledPaymentDto() {
		// One default constructor is needed
	}

	public ScheduledPaymentDto(ScheduledPaymentDto schedulePayment) {
		super();
		this.paymentId = schedulePayment.paymentId;
		this.userDefinedDate = schedulePayment.userDefinedDate;
		this.scheduledDate = schedulePayment.scheduledDate;
		this.scheduledType = schedulePayment.scheduledType;
		this.userId = schedulePayment.userId;
		this.loanNumber = schedulePayment.loanNumber;
		this.canceled = schedulePayment.canceled;
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

	public void setUserDefinedDate(Date userDefinedDate) {
		if(userDefinedDate!=null) {
			this.userDefinedDate = userDefinedDate;
		}else {
			this.userDefinedDate = null;
		}
		
	}

	public Date getScheduledDate() {
		return scheduledDate;
	}

	public String getNextScheduledDate() {
		DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
		return sdf.format(getScheduledDate());
	}

	public void setScheduledDate(Date scheduledDate) {
		if(scheduledDate!=null) {
			this.scheduledDate = scheduledDate;
		}else {
			this.scheduledDate = null;
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

	public Boolean getCanceled() {
		return canceled;
	}

	public void setCanceled(Boolean canceled) {
		this.canceled = canceled;
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

	public String getProcessedDateOfPayment() {
		DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
		return sdf.format(getProcessedDate());
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

	@Override
	public String toString() {
		return "ScheduledPaymentDto{" +
				"paymentId=" + paymentId +
				", userDefinedDate=" + userDefinedDate +
				", scheduledDate=" + scheduledDate +
				", scheduledType='" + scheduledType + '\'' +
				", userId=" + userId +
				", loanNumber=" + loanNumber +
				", canceled=" + canceled +
				", createdDate=" + createdDate +
				", canceledDate=" + canceledDate +
				", monthlyPayment=" + monthlyPayment +
				", lateFeesOwed=" + lateFeesOwed +
				", nsfFeesOwed=" + nsfFeesOwed +
				", lateFeesPaid=" + lateFeesPaid +
				", nsfFeesPaid=" + nsfFeesPaid +
				", extraPrincipal=" + extraPrincipal +
				", extraEscrow=" + extraEscrow +
				", totalPayment=" + totalPayment +
				", processedDate=" + processedDate +
				", emailReminder=" + emailReminder +
				", pay=" + pay +
				'}';
	}
}
