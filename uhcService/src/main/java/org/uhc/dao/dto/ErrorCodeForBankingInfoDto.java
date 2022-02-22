/* 
 * ===========================================================================
 * File Name ErrorCodeForBankingInfoDto.java
 * 
 * Created on Aug 23, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ErrorCodeForBankingInfoDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

public class ErrorCodeForBankingInfoDto {

	private int id;
	private String notificationMessage;
	private String validationMessage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}
	

	@Override
	public String toString() {
		return "ErrorCodeForBankingInfoDto{" + "id=" + id + ", notificationMessage='" + notificationMessage + '\''
				+ '}';
	}
}
