/* 
 * ===========================================================================
 * File Name ScheduledPaymentHistoryDto.java
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
 * $Log: ScheduledPaymentHistoryDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.uhc.util.Constants;

/**
 * @author nehas3
 * @date Jul 13, 2018
 */
public class ScheduledPaymentHistoryDto implements Comparable<ScheduledPaymentHistoryDto> {

	private int id;
	private int scheduledPaymentId;
	private String confirmationNumber;
	private int userId;
	private LocalDateTime createDate;
	private String scheduledType;
	private String paymentSource;
	private Date userDefinedDate;
	private Date nextScheduledDate;
	private LocalDateTime processDate;
	private LocalDateTime batchTime;
	private boolean emailReminder;
	private boolean canceled;
	private LocalDateTime canceledDate;
	private String canceledBy;
	private long loanNumber;
	private BigDecimal monthlyPayment;
	private BigDecimal lateFees;
	private BigDecimal nsfFees;
	private BigDecimal extraPrincipal;
	private BigDecimal extraEscrow;
	private LocalDateTime paymentTime;
	private String paymentAccountNumberLastFour;
	private Constants.AccountType paymentAccountType;

	/**
	 * 
	 * Provide Constructor Description here
	 * @param scheduledPaymentDto
	 */
	public ScheduledPaymentHistoryDto(ScheduledPaymentHistoryDto scheduledPaymentDto) {
		this.id = scheduledPaymentDto.id;
		this.scheduledPaymentId = scheduledPaymentDto.getScheduledPaymentId();
		this.confirmationNumber = scheduledPaymentDto.confirmationNumber;
		this.userId = scheduledPaymentDto.userId;
		this.createDate = scheduledPaymentDto.createDate;
		this.scheduledType = scheduledPaymentDto.scheduledType;
		this.userDefinedDate = scheduledPaymentDto.userDefinedDate;
		this.nextScheduledDate = scheduledPaymentDto.nextScheduledDate;
		this.processDate = scheduledPaymentDto.processDate;
		this.emailReminder = scheduledPaymentDto.emailReminder;
		this.canceled = scheduledPaymentDto.canceled;
		this.canceledDate = scheduledPaymentDto.canceledDate;
		this.canceledBy = scheduledPaymentDto.canceledBy;
		this.loanNumber = scheduledPaymentDto.loanNumber;
		this.monthlyPayment = scheduledPaymentDto.monthlyPayment;
		this.lateFees = scheduledPaymentDto.lateFees;
		this.nsfFees = scheduledPaymentDto.nsfFees;
		this.extraEscrow = scheduledPaymentDto.extraEscrow;
		this.extraPrincipal = scheduledPaymentDto.extraPrincipal;
		this.paymentTime = scheduledPaymentDto.paymentTime;
		this.paymentAccountNumberLastFour = scheduledPaymentDto.getPaymentAccountNumberLastFour();
		this.paymentAccountType = scheduledPaymentDto.getPaymentAccountType();
	}

	public ScheduledPaymentHistoryDto() {
		// Needed a copy constructor in certain circumstances but the default no arg
		// constructor is used most places
	}

	public LocalDateTime getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(LocalDateTime paymentTime) {
		this.paymentTime = paymentTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScheduledPaymentId() {
		return scheduledPaymentId;
	}

	public void setScheduledPaymentId(int scheduledPaymentId) {
		this.scheduledPaymentId = scheduledPaymentId;
	}

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		if (confirmationNumber != null) {
			this.confirmationNumber = confirmationNumber;
		} else {
			this.confirmationNumber = "N/A";
		}
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	/*******
	 * Need to change this to TJ's Enum impl
	 * 
	 * @return scheduledType
	 */
	public String getScheduledType() {
		if ("oneTime".equals(scheduledType)) {
			return "One-Time";
		}
		return StringUtils.capitalize(scheduledType);
	}

	public void setScheduledType(String scheduledType) {
		this.scheduledType = scheduledType;
	}

	public String getPaymentSource() {
		return paymentSource;
	}

	/**
	 * To set payment source value
	 * @author nehas3
	 * @since Oct 13, 2020
	 * @param paymentSource
	 */
	public void setPaymentSource(String paymentSource) {
		if (paymentSource != null) {
			this.paymentSource = paymentSource;
		} else {
			this.paymentSource = "N/A";
		}

	}

	public Date getUserDefinedDate() {
		return userDefinedDate;
	}

	public void setUserDefinedDate(Date userDefinedDate) {
		this.userDefinedDate = userDefinedDate;
	}

	public Date getNextScheduledDate() {
		return nextScheduledDate;
	}

	public void setNextScheduledDate(Date nextScheduledDate) {
		this.nextScheduledDate = nextScheduledDate;
	}

	public LocalDateTime getProcessDate() {
		return processDate;
	}

	public void setProcessDate(LocalDateTime processDate) {
		this.processDate = processDate;
	}

	public LocalDateTime getBatchTime() {
		return batchTime;
	}

	public void setBatchTime(LocalDateTime batchTime) {
		this.batchTime = batchTime;
	}

	public boolean isEmailReminder() {
		return emailReminder;
	}

	public void setEmailReminder(boolean emailReminder) {
		this.emailReminder = emailReminder;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public LocalDateTime getCanceledDate() {
		return canceledDate;
	}

	public void setCanceledDate(LocalDateTime canceledDate) {
		this.canceledDate = canceledDate;
	}

	public String getCanceledBy() {
		return canceledBy;
	}

	public void setCanceledBy(String canceledBy) {
		this.canceledBy = canceledBy;
	}

	public long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	public BigDecimal getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(BigDecimal monthlyPayment) {
		if (monthlyPayment != null) {
			this.monthlyPayment = monthlyPayment;
		} else {
			this.monthlyPayment = BigDecimal.ZERO;
		}

	}

	public BigDecimal getLateFees() {
		return lateFees;
	}

	public void setLateFees(BigDecimal lateFees) {
		if (lateFees != null) {
			this.lateFees = lateFees;
		} else {
			this.lateFees = BigDecimal.ZERO;
		}
	}

	public BigDecimal getNsfFees() {
		return nsfFees;
	}

	public void setNsfFees(BigDecimal nsfFees) {
		if (nsfFees != null) {
			this.nsfFees = nsfFees;
		} else {
			this.nsfFees = BigDecimal.ZERO;
		}
	}

	public BigDecimal getExtraPrincipal() {
		return extraPrincipal;
	}

	public void setExtraPrincipal(BigDecimal extraPrincipal) {
		if (extraPrincipal != null) {
			this.extraPrincipal = extraPrincipal;
		} else {
			this.extraPrincipal = BigDecimal.ZERO;
		}
	}

	public BigDecimal getExtraEscrow() {
		return extraEscrow;
	}

	public void setExtraEscrow(BigDecimal extraEscrow) {
		if (extraEscrow != null) {
			this.extraEscrow = extraEscrow;
		} else {
			this.extraEscrow = BigDecimal.ZERO;
		}
	}

	public BigDecimal getPaymentTotal() {
		return monthlyPayment.add(lateFees.add(nsfFees.add(extraEscrow.add(extraPrincipal))));
	}

	public String getPaymentAccountNumberLastFour() {
		return paymentAccountNumberLastFour;
	}

	public void setPaymentAccountNumberLastFour(String paymentAccountNumberLastFour) {
		if (paymentAccountNumberLastFour != null) {
			this.paymentAccountNumberLastFour = paymentAccountNumberLastFour;
		} else {
			this.paymentAccountNumberLastFour = "N/A";
		}

	}

	public Constants.AccountType getPaymentAccountType() {
		return paymentAccountType;
	}

	public void setPaymentAccountType(Constants.AccountType paymentAccountType) {
		this.paymentAccountType = paymentAccountType;
	}

	@Override
	public int compareTo(ScheduledPaymentHistoryDto scheduledPaymentHistoryDtoObj) {
		if (scheduledPaymentHistoryDtoObj != null) {
			return scheduledPaymentHistoryDtoObj.getPaymentTime().compareTo(this.paymentTime); // descending order
		}
		return 0;
	}

}
