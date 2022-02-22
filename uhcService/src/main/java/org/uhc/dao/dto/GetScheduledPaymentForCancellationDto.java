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

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.uhc.util.Constants;

/**
 * @author nehas3
 * @date Jul 2, 2018
 */
public class GetScheduledPaymentForCancellationDto {

	private int paymentId;
	private int userId;
	private String scheduledType;
	private String processedDate;
	private String scheduledDate;
	private BigDecimal lateFeesPaid;
	private BigDecimal nsfFeesPaid = BigDecimal.ZERO;
	private BigDecimal extraPrincipal = BigDecimal.ZERO;
	private BigDecimal extraEscrow = BigDecimal.ZERO;
	private String paymentSource;
	private String totalPayment;

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

	public String getPaymentSource() {
		return paymentSource;
	}

	public void setPaymentSource(String paymentSource) {
		this.paymentSource = paymentSource;
	}

	public String getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(BigDecimal totalPayment) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.totalPayment = df.format(totalPayment);
	}

}
