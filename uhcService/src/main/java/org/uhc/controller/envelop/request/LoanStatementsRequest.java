/* 
 * ===========================================================================
 * File Name LoanStatementsRequest.java
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
 * $Log: LoanStatementsRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date May 25, 2018 Description : This is LoanStatementsRequest request bean
 *       to get Loan Statements of user on basis of loanNumber, dates between
 *       they require statements for.
 */
public class LoanStatementsRequest {

	private Long loanNumber;
	private String fromMonth;
	private String fromYear;
	private String toMonth;
	private String toYear;

	public Long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(Long loanNumber) {
		this.loanNumber = loanNumber;
	}

	public String getFromMonth() {
		return fromMonth;
	}

	public void setFromMonth(String fromMonth) {
		this.fromMonth = fromMonth;
	}

	public String getFromYear() {
		return fromYear;
	}

	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}

	public String getToMonth() {
		return toMonth;
	}

	public void setToMonth(String toMonth) {
		this.toMonth = toMonth;
	}

	public String getToYear() {
		return toYear;
	}

	public void setToYear(String toYear) {
		this.toYear = toYear;
	}

	@Override
	public String toString() {
		return "LoanStatementsRequest{" +
				"loanNumber=" + loanNumber +
				", fromMonth='" + fromMonth + '\'' +
				", fromYear='" + fromYear + '\'' +
				", toMonth='" + toMonth + '\'' +
				", toYear='" + toYear + '\'' +
				'}';
	}
}
