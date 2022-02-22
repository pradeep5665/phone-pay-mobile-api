/* 
 * ===========================================================================
 * File Name GetNotificationDetailsRequest.java
 * 
 * Created on Aug 22, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetNotificationDetailsRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 *  @author nehas3
 *  @date Aug 22, 2018
 *  @Description : This is GetNotificationDetailsRequest request bean to get Notification Details of user on basis of userId.
 */
public class GetNotificationDetailsRequest {

	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "{" +
			"\"GetNotificationDetailsRequest\": {" +
				"\"userId\": \"" + getUserId() + "\"" +
			"}" +
		"}";
	}
}
