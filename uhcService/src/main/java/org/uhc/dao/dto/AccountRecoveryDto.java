/* 
 * ===========================================================================
 * File Name AccountRecoveryDto.java
 * 
 * Created on Jan 25, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: AccountRecoveryDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

import java.util.Date;

/**
 * @author nehas3
 * @date Jan 25, 2020
 */
public class AccountRecoveryDto {
	private int id;
	private int userId;
	private String accRecoveryKey;
	private Date recoveryTime;

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

	public String getAccRecoveryKey() {
		return accRecoveryKey;
	}

	public void setAccRecoveryKey(String accRecoveryKey) {
		this.accRecoveryKey = accRecoveryKey;
	}

	public Date getRecoveryTime() {
		return recoveryTime;
	}

	public void setRecoveryTime(Date recoveryTime) {
		if (recoveryTime == null) {
			this.recoveryTime = null;
		}
		this.recoveryTime = recoveryTime;
	}
}
