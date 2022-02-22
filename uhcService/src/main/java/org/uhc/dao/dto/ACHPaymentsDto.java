/* 
 * ===========================================================================
 * File Name ACHPaymentsDto.java
 * 
 * Created on Sep 27, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ACHPaymentsDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

/**
 * @author nehas3
 * @date September 27, 2019
 */
public class ACHPaymentsDto {

	private long achLoanNumber;
	private String achType;
	private int achDays;
	private String achDate;

	public long getAchLoanNumber() {
		return achLoanNumber;
	}

	public void setAchLoanNumber(long achLoanNumber) {
		this.achLoanNumber = achLoanNumber;
	}

	public String getAchType() {
		return achType;
	}

	public void setAchType(String achType) {
		this.achType = achType;
	}

	public int getAchDays() {
		return achDays;
	}

	public void setAchDays(int achDays) {
		this.achDays = achDays;
	}

	public String getAchDate() {
		return achDate;
	}

	public void setAchDate(String achDate) {
		this.achDate = achDate;
	}

	@Override
	public String toString() {
		return "ACHPaymentsDto{" +
				"achLoanNumber=" + achLoanNumber +
				", achType='" + achType + '\'' +
				", achDays=" + achDays +
				", achDate='" + achDate + '\'' +
				'}';
	}
}
