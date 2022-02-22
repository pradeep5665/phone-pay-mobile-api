/* 
 * ===========================================================================
 * File Name PaymentItemDto.java
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
 * $Log: PaymentItemDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;

import org.uhc.util.Constants.PaymentItemType;

/**
 * @author nehas3
 * @date Jul 13, 2018
 */
public class PaymentItemDto {
	private int paymentId;
	private BigDecimal amount;
	private PaymentItemType type;
	private long loanNumber;

	public PaymentItemDto(PaymentItemDto paymentItemDto) {
		super();
		this.paymentId = paymentItemDto.getPaymentId();
		this.amount = paymentItemDto.getAmount();
		this.type = paymentItemDto.getType();
		this.loanNumber = paymentItemDto.getLoanNumber();
	}

	public PaymentItemDto() {
		// Needed a copy constructor in certain circumstances but the default no
		// arg constructor is used most places
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount ;
	}

	public PaymentItemType getType() {
		return type;
	}

	public void setType(PaymentItemType type) {
		this.type = type;
	}

	public long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	public boolean same(PaymentItemDto other) {
			return (this.paymentId == other.getPaymentId() && this.amount.compareTo(other.getAmount()) == 0
					&& this.type == other.getType() && this.loanNumber == other.getLoanNumber());
	}
}
