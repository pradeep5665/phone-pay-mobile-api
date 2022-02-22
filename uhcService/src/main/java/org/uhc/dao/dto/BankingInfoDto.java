/* 
 * ===========================================================================
 * File Name BankingInfoDto.java
 * 
 * Created on Jun 5, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: BankingInfoDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

/**
 * @author nehas3
 * @date Jun 5, 2018
 */
import io.netty.util.internal.StringUtil;
import org.uhc.util.Constants.AccountType;

public class BankingInfoDto {

	private int id;
	private int userId;
	private String bankAccountNumber;
	private String nameOnBankAccount;
	private AccountType bankAccountType;
	private String routingNumber;

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

	public String getBankAccountNumber() {
		if(bankAccountNumber.length()>4) {
			return bankAccountNumber.substring((bankAccountNumber.length() - 4), bankAccountNumber.length());
		}else {
			return	bankAccountNumber;
		}
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

	public String getRoutingNumber() {
		if(routingNumber.length() > 4) {
		return routingNumber.substring((routingNumber.trim().length() - 4), routingNumber.length()).trim();
		}else {
			return routingNumber;
		}
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber.trim();
	}

	private String lastFour(String strToMask) {
		if (StringUtil.isNullOrEmpty(strToMask) || strToMask.length() < 4) {
			return "????";
		}
		return strToMask.trim().substring(strToMask.length()-3);
	}

	@Override
	public String toString() {
		return "BankingInfoDto{" +
				"id=" + id +
				", userId=" + userId +
				", bankAccountNumber='" + lastFour(bankAccountNumber) + '\'' +
				", nameOnBankAccount='" + nameOnBankAccount + '\'' +
				", bankAccountType=" + bankAccountType +
				", routingNumber='" + routingNumber + '\'' + "}";
	}
}
