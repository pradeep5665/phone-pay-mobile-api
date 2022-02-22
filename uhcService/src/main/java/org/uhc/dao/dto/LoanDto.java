/* 
 * ===========================================================================
 * File Name LoanDto.java
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
 * $Log: LoanDto.java,v $
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
 * @date May 25, 2018
 */
public class LoanDto{
	private long loanNumber;
	private String unpaidPrincipalBalance;
	private BigDecimal interestRate;
	private String monthlyPayment;
	private String escrow;
	private String principalAndInterest;
	private String nextDue;
	private long totalPrincipalAmount;
	private String lateFees;
	private String nsfFees;
	private String escrowBalance;
	private String escrowAdvance;
	private boolean stopPayment;
	private int sequenceNumber;


	public long getLoanNumber() {
		return loanNumber;
	}

	public String getEncryptedLoanNumber() {
		StringBuilder encryptedLoanNumber = new StringBuilder();

		// extracting last 4 digit of loan number.
		String loanNum = String.valueOf(this.loanNumber);
		encryptedLoanNumber.append(loanNum.substring(0, 4));
		for (int index = 4; index < loanNum.length(); index++) {
			encryptedLoanNumber.append("*");
		}
		return encryptedLoanNumber.toString();
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	public String getUnpaidPrincipalBalance() {
		return unpaidPrincipalBalance;
	}

	public void setUnpaidPrincipalBalance(BigDecimal unpaidPrincipalBalance) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.unpaidPrincipalBalance = df.format(unpaidPrincipalBalance);
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public BigDecimal getInterestRatePercent() {
		return interestRate.multiply(new BigDecimal(100)).stripTrailingZeros();
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	
	public String getMonthlyPayment() {
		return monthlyPayment;
	}

	public BigDecimal getMonthlyPaymentForTotal() {
		return new BigDecimal(monthlyPayment);
	}
	
	public void setMonthlyPayment(BigDecimal monthlyPayment) {
		if(monthlyPayment != null) {
			DecimalFormat df = new DecimalFormat("0.00");
			this.monthlyPayment = df.format(monthlyPayment);
		}else {
			this.monthlyPayment = null;
		}
	}

	public String getEscrow() {
		return escrow;
	}

	public void setEscrow(BigDecimal escrow) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.escrow = df.format(escrow);
	}

	public String getPrincipalAndInterest() {
		return principalAndInterest;
	}

	public void setPrincipalAndInterest(BigDecimal principalAndInterest) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.principalAndInterest = df.format(principalAndInterest);
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

	public long getTotalPrincipalAmount() {
		return totalPrincipalAmount;
	}

	public void setTotalPrincipalAmount(long totalPrincipalAmount) {
		this.totalPrincipalAmount = totalPrincipalAmount;
	}

	public String getLateFees() {
		return lateFees;
	}

	public void setLateFees(BigDecimal lateFees) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.lateFees = df.format(lateFees);
	}

	public String getNsfFees() {
		return nsfFees;
	}

	public void setNsfFees(BigDecimal nsfFees) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.nsfFees = df.format(nsfFees);
	}

	public String getEscrowBalance() {
		return escrowBalance;
	}

	public void setEscrowBalance(BigDecimal escrowBalance) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.escrowBalance = df.format(escrowBalance);
	}

	public String getEscrowAdvance() {
		return escrowAdvance;
	}

	public void setEscrowAdvance(BigDecimal escrowAdvance) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.escrowAdvance = df.format(escrowAdvance);
	}

	public boolean isStopPayment() {
		return stopPayment;
	}

	public void setStopPayment(boolean stopPayment) {
		this.stopPayment = stopPayment;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int a1_seq) {
		this.sequenceNumber = a1_seq;
	}

	@Override
	public String toString() {
		return "LoanDto{" +
				"loanNumber=" + loanNumber +
				", unpaidPrincipalBalance='" + unpaidPrincipalBalance + '\'' +
				", interestRate=" + interestRate +
				", monthlyPayment='" + monthlyPayment + '\'' +
				", escrow='" + escrow + '\'' +
				", principalAndInterest='" + principalAndInterest + '\'' +
				", nextDue='" + nextDue + '\'' +
				", totalPrincipalAmount=" + totalPrincipalAmount +
				", lateFees='" + lateFees + '\'' +
				", nsfFees='" + nsfFees + '\'' +
				", escrowBalance='" + escrowBalance + '\'' +
				", escrowAdvance='" + escrowAdvance + '\'' +
				", stopPayment=" + stopPayment +
				'}';
	}

}
