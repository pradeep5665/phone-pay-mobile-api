/* 
 * ===========================================================================
 * File Name LoanPaymentRquest.java
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
 * $Log: LoanPaymentRquest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.uhc.util.Constants;

/**
 * @author nehas3
 * @date Jun 26, 2018 Description : This is LoanPaymentRequest request bean to
 *       get Loan Payment Info of user.
 */
public class LoanPaymentRequest {

	private static final Logger LOGGER = LogManager.getLogger(LoanPaymentRequest.class);

	private int userId;
	private long loanNumber;
	private Date nextDue;
	private boolean webPaymentStopped;
	private List<ScheduledPaymentRequest> scheduledPayments;
	private ScheduledPaymentRequest schedulingPayment;
	private Integer nextAvailableMonthForOneTime;
	private Integer nextAvailableMonthForRecurring;
	private String paymentSource;

	LoanPaymentRequest() {
		scheduledPayments = new ArrayList<>();
		schedulingPayment = new ScheduledPaymentRequest();
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

	public Date getNextDue() {
		return nextDue;
	}

	public void setNextDue(String nextDue) {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
		try {
			this.nextDue = sdf.parse(nextDue);
		} catch (ParseException exp) {
			LOGGER.error("Parse exception oclcured : {}", exp.getMessage());
			this.nextDue = null;
		}
	}

	public boolean isWebPaymentStopped() {
		return webPaymentStopped;
	}

	public void setWebPaymentStopped(boolean webPaymentStopped) {
		this.webPaymentStopped = webPaymentStopped;
	}

	public List<ScheduledPaymentRequest> getScheduledPayments() {
		return scheduledPayments;
	}

	public void setScheduledPayments(List<ScheduledPaymentRequest> scheduledPayments) {
		this.scheduledPayments = scheduledPayments;
	}

	public ScheduledPaymentRequest getSchedulingPayment() {
		return schedulingPayment;
	}

	public void setSchedulingPayment(ScheduledPaymentRequest schedulingPayment) {
		this.schedulingPayment = schedulingPayment;
	}

	public Integer getNextAvailableMonthForOneTime() {
		return nextAvailableMonthForOneTime;
	}

	public void setNextAvailableMonthForOneTime(Integer nextAvailableMonthForOneTime) {
		this.nextAvailableMonthForOneTime = nextAvailableMonthForOneTime;
	}

	public Integer getNextAvailableMonthForRecurring() {
		return nextAvailableMonthForRecurring;
	}

	public void setNextAvailableMonthForRecurring(Integer nextAvailableMonthForRecurring) {
		this.nextAvailableMonthForRecurring = nextAvailableMonthForRecurring;
	}

	public String getPaymentSource() {
		return paymentSource;
	}

	public void setPaymentSource(String paymentSource) {
		this.paymentSource = paymentSource;
	}

}
