/* 
 * ===========================================================================
 * File Name LoanStatementsResponse.java
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
 * $Log: LoanStatementsResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

import java.util.List;

import org.uhc.dao.dto.LoanStatementDto;

/**
 * @author nehas3
 * @date May 25, 2018
 * @description creating Loan Statement Service Response for success or failure
 */
public class LoanStatementsResponse {

	private Boolean isSuccessful;
	private String error;
	private String backPath;

	private List<LoanStatementDto> loanStatementList;

	public Boolean getIsSuccessful() {
		return isSuccessful;
	}

	public void setIsSuccessful(Boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<LoanStatementDto> getLoanStatementList() {
		return loanStatementList;
	}

	public void setLoanStatementList(List<LoanStatementDto> loanStatementList) {
		this.loanStatementList = loanStatementList;
	}

	public String getBackPath() {
		return backPath;
	}

	public void setBackPath(String backPath) {
		this.backPath = backPath;
	}

	@Override
	public String toString() {
		return "LoanStatementsResponse{" +
				"isSuccessful=" + isSuccessful +
				", error='" + error + '\'' +
				", backPath='" + backPath + '\'' +
				", loanStatement count=" + loanStatementList.size() +
				'}';
	}
}
