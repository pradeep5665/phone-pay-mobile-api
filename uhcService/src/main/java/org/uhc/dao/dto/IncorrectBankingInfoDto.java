/* 
 * ===========================================================================
 * File Name IncorrectBankingInfoDto.java
 * 
 * Created on Jun 27, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: IncorrectBankingInfoDto.java,v $
 * ===========================================================================
 */

/**
 * @author nehas3
 * @date jun 25, 2018
 */
package org.uhc.dao.dto;

import io.netty.util.internal.StringUtil;

/**
 * @author nehas3
 * @date jun 27, 2018
 */
public class IncorrectBankingInfoDto {

	private int id;
	private int userId;
	private String routingNum;
	private String accountNum;
	private int errorCode;
	private String needsNotification;

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

	public String getRoutingNum() {
		return routingNum;
	}

	public void setRoutingNum(String routingNum) {
		this.routingNum = routingNum;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getNeedsNotification() {
		return needsNotification;
	}

	public void setNeedsNotification(String needsNotification) {
		if (needsNotification != null) {
			this.needsNotification = needsNotification;
		} else {
			this.needsNotification = "";
		}
	}

	private String lastFour(String strToMask) {
		if (StringUtil.isNullOrEmpty(strToMask)) {
			return "????";
		}
		return strToMask.trim().substring(strToMask.length()-3);
	}


	@Override
	public String toString() {
		return "IncorrectBankingInfoDto{" +
				"id=" + id +
				", userId=" + userId +
				", routingNum='" + routingNum + '\'' +
				", accountNum='" + lastFour(accountNum) + '\'' +
				", errorCode=" + errorCode +
				", needsNotification='" + needsNotification + '\'' +
				'}';
	}
}
