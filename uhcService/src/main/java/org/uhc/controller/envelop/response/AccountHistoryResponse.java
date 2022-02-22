/* 
 * ===========================================================================
 * File Name AccountHistoryResponse.java
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
 * $Log: AccountHistoryResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

import java.util.List;
import org.uhc.dao.dto.TransactionDto;

/**
 * @author nehas3
 * @date May 25, 2018
 * @description creating Account History Response for success or failure
 */
public class AccountHistoryResponse {

	private Boolean isSuccessful;
	private String error;
	private List<TransactionDto> transactionList;

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

	public List<TransactionDto> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<TransactionDto> transactionList) {
		this.transactionList = transactionList;
	}

	@Override
	public String toString() {
		return "AccountHistoryResponse{" +
				"isSuccessful=" + isSuccessful +
				", error='" + error + '\'' +
				", record count=" + transactionList.size() +
				'}';
	}
}
