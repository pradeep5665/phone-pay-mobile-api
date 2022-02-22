/* 
 * ===========================================================================
 * File Name UpdateBankingInfoRequest.java
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
 * $Log: UpdateBankingInfoRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

import io.netty.util.internal.StringUtil;
import org.uhc.util.Constants.AccountType;

/**
 * @author nehas3
 * @date June 5, 2018 Description : This is UpdateBankingInfoRequest request
 *       bean to get user updated their Banking info on basis of userId.
 */
public class UpdateBankingInfoRequest {

	private int id;
	private int userId;
	private String routingNumber;
	private String bankAccountNumber;
	private String nameOnBankAccount;
	private AccountType bankAccountType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getRoutingNumber() {
		return routingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getNameOnBankAccount() {
		return nameOnBankAccount;
	}

	public void setNameOnBankAccount(String nameOnBankAccount) {
		this.nameOnBankAccount = nameOnBankAccount;
	}

	public AccountType getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(AccountType bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	private String lastFour(String strToMask) {
		if (StringUtil.isNullOrEmpty(strToMask) || strToMask.length() < 4) {
			return "????";
		}
		return strToMask.trim().substring(strToMask.length()-3);
	}


	@Override
	public String toString() {
		return "UpdateBankingInfoRequest{" +
				"id=" + id +
				", userId=" + userId +
				", routingNumber='" + routingNumber + '\'' +
				", bankAccountNumber='" + lastFour(bankAccountNumber) + '\'' +
				", nameOnBankAccount='" + nameOnBankAccount + '\'' +
				", bankAccountType=" + bankAccountType +
				'}';
	}
}
