/* 
 * ===========================================================================
 * File Name GetNotificationDetailsResponse.java
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
 * $Log: GetNotificationDetailsResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

import java.util.List;

import org.uhc.dao.dto.GetNotificationDetailsDto;

/**
 * @author nehas3
 * @date Aug 22, 2018
 * @description creating Get Notification Details Service Response for success or failure
 */
public class GetNotificationDetailsResponse {

	private Boolean isSuccessful;
	private String message;
	private List<GetNotificationDetailsDto> notificationList;
	public Boolean getIsSuccessful() {
		return isSuccessful;
	}
	public void setIsSuccessful(Boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<GetNotificationDetailsDto> getNotificationList() {
		return notificationList;
	}
	public void setNotificationList(List<GetNotificationDetailsDto> notificationList) {
		this.notificationList = notificationList;
	}

	@Override
	public String toString() {
		String result = "GetNotificationDetailsResponse{" +
				"isSuccessful: " + isSuccessful +
				", message: '" + message + '\'' +
				", notificationList: <not shown>";
		result += '}';
		return result;
	}
}
