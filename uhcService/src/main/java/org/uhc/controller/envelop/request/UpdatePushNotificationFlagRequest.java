/* 
 * ===========================================================================
 * File Name UpdatePushNotificationFlagRequest.java
 * 
 * Created on Oct 22, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdatePushNotificationFlagRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

public class UpdatePushNotificationFlagRequest {

	private int userId;
	private int flagValue;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFlagValue() {
		return flagValue;
	}

	public void setFlagValue(boolean flagValue) {
		if (flagValue == true) {
			this.flagValue = 1;
		} else {
			this.flagValue = 0;
		}

	}

}
