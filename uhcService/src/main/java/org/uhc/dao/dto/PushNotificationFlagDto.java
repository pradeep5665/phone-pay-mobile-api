/* 
 * ===========================================================================
 * File Name PushNotificationFlagDto.java
 * 
 * Created on Oct 19, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: PushNotificationFlagDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

public class PushNotificationFlagDto {
	private int userId;
	private int flagNameId;
	private int flagValue;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFlagNameId() {
		return flagNameId;
	}

	public void setFlagNameId(int flagNameId) {
		this.flagNameId = flagNameId;
	}

	public int getFlagValue() {
		return flagValue;
	}

	public void setFlagValue(int flagValue) {
		this.flagValue = flagValue;
	}

}
