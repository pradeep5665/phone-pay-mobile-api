/* 
 * ===========================================================================
 * File Name LoginResponse.java
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
 * $Log: LoginResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author nehas3
 * @date May 25, 2018
 * @description creating Login Service Response for success or failure
 */
@Component
public class LoginResponse {

	private Boolean isSuccessful;
	private String error;
	private int userId;
	private List<String> loanAccountList;
	
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<String> getLoanAccountList() {
		return loanAccountList;
	}

	public void setLoanAccountList(List<String> loanAccountList) {
		this.loanAccountList = loanAccountList;
	}

	@Override
	public String toString() {
		return "LoginResponse{" +
				"isSuccessful=" + isSuccessful +
				", error='" + error + '\'' +
				", userId=" + userId +
				", user has " + (loanAccountList == null ? "unknown number of" : loanAccountList.size()) +
				" loans}";
	}
}
