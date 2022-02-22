/* 
 * ===========================================================================
 * File Name PaymentDto.java
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
 * $Log: PaymentDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.uhc.dao.bean.LoanSubtotal;
import org.uhc.util.Constants.AccountType;
import org.uhc.util.Constants.PaymentStatus;

/**
 * @author nehas3
 * @date Jul 2, 2018
 */
public class PaymentDto implements Comparable<PaymentDto> {
	private int id;
	private List<PaymentItemDto> paymentItems = new ArrayList<>(4);
	private String confirmationNumber;
	private LocalDateTime paymentTime; // Payment date used for sorting.
	private int paymentBatch;
	private boolean canceled;
	private LocalDate scheduledPaymentTime;
	private String scheduledPaymentDate; // payment date used for display payment time in formatted way.
	private String processedDate;
	private String canceledDate;
	private String canceledBy;
	private String createDate;
	private String createdBy;
	private String scheduleType;
	private String paymentSource;
	private int userId;
	private String paymentAccountNumberLastFour;
	private String routingNumberLastFour;
	private String nameOnPaymentAccount;
	private AccountType paymentAccountType;
	private PaymentStatus paymentStatus;
	private Integer scheduledPaymentId;

	// formatter used for all of the paymentDto date.

	public PaymentDto(PaymentDto paymentDto) {
		this.id = paymentDto.getId();
		this.paymentItems = paymentDto.getPaymentItems().stream().map(PaymentItemDto::new).collect(Collectors.toList());
		this.confirmationNumber = paymentDto.getConfirmationNumber();
		this.paymentTime = paymentDto.getPaymentTime();
		this.paymentBatch = paymentDto.getPaymentBatch();
		this.canceled = paymentDto.isCanceled();
		this.scheduledPaymentDate = paymentDto.getScheduledPaymentDate();
		this.scheduleType = paymentDto.getScheduleType();
		this.paymentSource = paymentDto.getPaymentSource();
		this.userId = paymentDto.getUserId();
		this.paymentAccountNumberLastFour = paymentDto.getPaymentAccountNumberLastFour();
		this.routingNumberLastFour = paymentDto.getRoutingNumberLastFour();
		this.nameOnPaymentAccount = paymentDto.getNameOnPaymentAccount();
		this.paymentAccountType = paymentDto.getPaymentAccountType();
		this.paymentStatus = paymentDto.getPaymentStatus();
		this.scheduledPaymentId = paymentDto.getScheduledPaymentId();
	}

	public PaymentDto() {
		// Needed a copy constructor in certain circumstances but the default no
		// arg constructor is used most places
	}

	public List<LoanSubtotal> getLoanSubtotals() {
		Map<Long, BigDecimal> loanToSubtotal = new LinkedHashMap<>();

		for (PaymentItemDto paymentItem : paymentItems) {
			Long loanNumber = paymentItem.getLoanNumber();

			if (loanToSubtotal.get(loanNumber) == null) {
				loanToSubtotal.put(loanNumber, paymentItem.getAmount());
			} else {
				loanToSubtotal.put(loanNumber, loanToSubtotal.get(loanNumber).add(paymentItem.getAmount()));
			}
		}

		List<LoanSubtotal> loanSubtotals = new ArrayList<>();
		loanToSubtotal.forEach((loan, subtotal) -> loanSubtotals.add(new LoanSubtotal(loan, subtotal)));
		return loanSubtotals;
	}

	@Override
	public int compareTo(PaymentDto paymentDtoObj) {
		if (paymentDtoObj.getScheduledPaymentTime() == null) {
			return 1;
		} else if (this.scheduledPaymentTime == null) {
			return -1;
		}
		return paymentDtoObj.getScheduledPaymentTime().compareTo(this.scheduledPaymentTime);// descending
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<PaymentItemDto> getPaymentItems() {
		return paymentItems;
	}

	public void setPaymentItems(List<PaymentItemDto> paymentItemDtoList) {
		paymentItems = paymentItemDtoList;
	}

	public void addPaymentItem(PaymentItemDto paymentItem) {
		this.paymentItems.add(paymentItem);
	}

	public String getConfirmationNumber() {
		return confirmationNumber.trim();
	}

	public void setConfirmationNumber(String confirmationNumber) {
		if (confirmationNumber != null) {
			this.confirmationNumber = confirmationNumber;
		} else {
			this.confirmationNumber = "N/A";
		}
	}

	public LocalDateTime getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(LocalDateTime paymentTime) {
		if (paymentTime == null) {
			this.paymentTime = null;
		}
		this.paymentTime = paymentTime;
	}

	public String getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(LocalDateTime processedDate) {
		if (processedDate == null) {
			this.processedDate = null;
		} else {
			DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
			this.processedDate = sdf.format(processedDate);
		}
	}

	public String getCanceledDate() {
		return canceledDate;
	}

	public void setCanceledDate(LocalDateTime canceledDate) {
		if (canceledDate == null) {
			this.canceledDate = null;
		} else {
			DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
			this.canceledDate = sdf.format(canceledDate);
		}
	}

	public String getCanceledBy() {
		return canceledBy;
	}

	public void setCanceledBy(String canceledBy) {
		if(canceledBy == null) {
			this.canceledBy = "N/A";
		}else {
			this.canceledBy = canceledBy;
		}
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		if (createDate == null) {
			this.createDate = "N/A";
		} else {
			DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
			this.createDate = sdf.format(createDate);
		}
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		if(createdBy==null) {
			this.createdBy = "N/A";
		}else {
			this.createdBy = createdBy;
		}
	}

	public int getPaymentBatch() {
		return paymentBatch;
	}

	public void setPaymentBatch(int paymentBatch) {
		this.paymentBatch = paymentBatch;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public String getScheduledPaymentDate() {
		if (this.scheduledPaymentTime != null) {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			return dateTimeFormatter.format(scheduledPaymentTime);
		}
		return null;
	}

	public LocalDate getScheduledPaymentTime() {
		return scheduledPaymentTime;
	}

	public void setScheduledPaymentTime(LocalDate scheduledPaymentTime) {
		if (scheduledPaymentTime != null) {
			this.scheduledPaymentTime = scheduledPaymentTime;
		} else {
			this.scheduledPaymentTime = null;
		}
	}

	public String getScheduleType() {
		if ("oneTime".equals(scheduleType)) {
			return "One-Time";
		}
		return StringUtils.capitalize(scheduleType);
	}

	public void setScheduleType(String scheduleType) {
		if (scheduleType != null) {
			this.scheduleType = scheduleType;
		} else {
			this.scheduleType = "N/A";
		}
	}

	public String getPaymentSource() {
		return paymentSource;
	}

	public void setPaymentSource(String paymentSource) {
		if (paymentSource != null) {
			this.paymentSource = paymentSource;
		} else {
			this.paymentSource = "N/A";
		}

	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPaymentAccountNumberLastFour() {
		return "***" + paymentAccountNumberLastFour;
	}

	public void setPaymentAccountNumberLastFour(String paymentAccountNumberLastFour) {
		if (paymentAccountNumberLastFour != null) {
			this.paymentAccountNumberLastFour = paymentAccountNumberLastFour;
		} else {
			this.paymentAccountNumberLastFour = "N/A";
		}
	}

	public String getRoutingNumberLastFour() {
		return "***" + routingNumberLastFour;
	}

	public void setRoutingNumberLastFour(String routingNumberLastFour) {
		if (routingNumberLastFour != null) {
			this.routingNumberLastFour = routingNumberLastFour;
		} else {
			this.routingNumberLastFour = "N/A";
		}

	}

	public String getNameOnPaymentAccount() {
		return nameOnPaymentAccount;
	}

	public void setNameOnPaymentAccount(String nameOnPaymentAccount) {
		if (nameOnPaymentAccount != null) {
			this.nameOnPaymentAccount = nameOnPaymentAccount;
		} else {
			this.nameOnPaymentAccount = "N/A";
		}

	}

	public AccountType getPaymentAccountType() {
		return paymentAccountType;
	}

	public void setPaymentAccountType(AccountType paymentAccountType) {
		this.paymentAccountType = paymentAccountType;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Integer getScheduledPaymentId() {
		return scheduledPaymentId;
	}

	public void setScheduledPaymentId(Integer scheduledPaymentId) {
		this.scheduledPaymentId = scheduledPaymentId;
	}

	public boolean contains(PaymentItemDto paymentItemDto) {
		for (PaymentItemDto paymentItem : paymentItems) {
			if (paymentItem.same(paymentItemDto)) {
				return true;
			}
		}
		return false;
	}

}
