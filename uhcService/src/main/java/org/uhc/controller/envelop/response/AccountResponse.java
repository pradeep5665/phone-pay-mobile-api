/* 
 * ===========================================================================
 * File Name AccountResponse.java
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
 * $Log: AccountResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.uhc.dao.dto.LoanDto;
import org.uhc.dao.dto.PropertyDto;
import org.uhc.util.Constants;

/**
 * @author nehas3
 * @date May 25, 2018
 * @description creating Account Service Response for success or failure
 */
public class AccountResponse {

	private Boolean isSuccessful;
	private Boolean isLoanInStopFile;
	private String error;
	private PropertyDto propertyInfo;
	private String maturityDate;
	private String firstPaymentDate;
	private LoanDto loanAccountDetails;

	public Boolean getIsSuccessful() {
		return isSuccessful;
	}

	public void setIsSuccessful(Boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public Boolean getIsLoanInStopFile() {
		return isLoanInStopFile;
	}

	public void setIsLoanInStopFile(Boolean isLoanInStopFile) {
		this.isLoanInStopFile = isLoanInStopFile;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public PropertyDto getPropertyInfo() {
		return propertyInfo;
	}

	public void setPropertyInfo(PropertyDto propertyInfo) {
		this.propertyInfo = propertyInfo;
	}

	public String getFirstPaymentDate() {
		return firstPaymentDate;
	}

	public void setFirstPaymentDate(String firstPaymentDate) {
		this.firstPaymentDate = firstPaymentDate;
	}

	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
		if (maturityDate != null) {
			this.maturityDate = sdf.format(maturityDate);
		} else {
			this.maturityDate = null;
		}
	}

	public LoanDto getLoanAccountDetails() {
		return loanAccountDetails;
	}

	public void setLoanAccountDetails(LoanDto loanAccountDetails) {
		this.loanAccountDetails = loanAccountDetails;
	}

	@Override
	public String toString() {
		return "AccountResponse{" +
				"isSuccessful=" + isSuccessful +
				", isLoanInStopFile=" + isLoanInStopFile +
				", error='" + error + '\'' +
				", propertyInfo=" + propertyInfo +
				", maturityDate='" + maturityDate + '\'' +
				", firstPaymentDate='" + firstPaymentDate + '\'' +
				", loanAccountDetails=" + loanAccountDetails +
				'}';
	}
}
