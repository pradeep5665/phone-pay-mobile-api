/* 
 * ===========================================================================
 * File Name TransactionDto.java
 * 
 * Created on May 25, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: TransactionDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.uhc.util.Constants;

/**
 * @author nehas3
 * @date May 25, 2018
 */
public class TransactionDto {

	private static final NumberFormat NUMBERFORMATTER = NumberFormat.getInstance(new Locale("en_US"));
	private static final DateFormat DATEFORMATTER;
	static {
		DATEFORMATTER = new SimpleDateFormat("MM/dd/yyyy");
		DATEFORMATTER.setTimeZone(TimeZone.getTimeZone("America/Denver"));
	}

	private long loan;
	private String posted;
	private String nextDue;
	private String desc;
	private String totalAmount;
	private String principal;
	private String interest;
	private String escrow;

	public long getLoan() {
		return loan;
	}

	public void setLoan(long loan) {
		this.loan = loan;
	}

	public String getPosted() {
		return posted;
	}

	public void setPosted(Date posted) {
		if (posted != null) {
			DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
			this.posted = sdf.format(posted);
		} else {
			this.posted = null;
		}
	}

	public String getNextDue() {
		return nextDue;
	}

	public void setNextDue(Date nextDue) {
		if (nextDue != null) {
			DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
			this.nextDue = sdf.format(nextDue);
		} else {
			this.nextDue = null;
		}
	}

	public String getDesc() {
		return desc.trim();
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.totalAmount = df.format(totalAmount);
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.principal = df.format(principal);
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.interest = df.format(interest);
	}

	public String getEscrow() {
		return escrow;
	}

	public void setEscrow(BigDecimal escrow) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.escrow = df.format(escrow);
	}

	public static NumberFormat getNumberformatter() {
		return NUMBERFORMATTER;
	}

	public static DateFormat getDateformatter() {
		return DATEFORMATTER;
	}
}
