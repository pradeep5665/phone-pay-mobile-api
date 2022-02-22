/* 
 * ===========================================================================
 * File Name BankingInfoResponse.java
 * 
 * Created on June 5, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: EscrowResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

import org.uhc.dao.dto.BankingInfoDto;

/**
 * @author nehas3
 * @date June 5, 2018
 * @description Creating Banking Information Service Response for success or failure
 */
public class BankingInfoResponse {

	private Boolean isSuccessful;
	private String error;
	private BankingInfoDto bankingInfo;

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

	public BankingInfoDto getBankingInfo() {
		return bankingInfo;
	}

	public void setBankingInfo(BankingInfoDto bankingInfo) {
		this.bankingInfo = bankingInfo;
	}

	@Override
	public String toString() {
		return "BankingInfoResponse{" +
				"isSuccessful=" + isSuccessful +
				", error='" + error + '\'' +
				", bankingInfo=" + bankingInfo +
				'}';
	}
}
