/* 
 * ===========================================================================
 * File Name UserProfileResponse.java
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
 * $Log: UserProfileResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

import org.uhc.dao.dto.OnlineStatementsPrefDto;
import org.uhc.dao.dto.PropertyDto;

/**
 * @author nehas3
 * @date May 25, 2018
 * @description creating User Profile Service Response for success or failure
 */
public class UserProfileResponse {

	private Boolean isSuccessfull;
	private String message;
	private String userName;
	private String oldUserName;
	private String email;
	private int userId;
	private boolean isPushNotificationFlagEnabled;
	private PropertyDto propertyDto;
	private OnlineStatementsPrefDto onlineStatementsPrefDto;

	public Boolean getIsSuccessfull() {
		return isSuccessfull;
	}

	public void setIsSuccessfull(Boolean isSuccessfull) {
		this.isSuccessfull = isSuccessfull;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOldUserName() {
		return oldUserName;
	}

	public void setOldUserName(String oldUserName) {
		this.oldUserName = oldUserName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isPushNotificationFlagEnabled() {
		return isPushNotificationFlagEnabled;
	}

	public void setPushNotificationFlagEnabled(boolean isPushNotificationFlagEnabled) {
		this.isPushNotificationFlagEnabled = isPushNotificationFlagEnabled;
	}

	public PropertyDto getPropertyDto() {
		return propertyDto;
	}

	public void setPropertyDto(PropertyDto propertyDto) {
		this.propertyDto = propertyDto;
	}

	public OnlineStatementsPrefDto getOnlineStatementsPrefDto() {
		return onlineStatementsPrefDto;
	}

	public void setOnlineStatementsPrefDto(OnlineStatementsPrefDto onlineStatementsPrefDto) {
		this.onlineStatementsPrefDto = onlineStatementsPrefDto;
	}

	@Override
	public String toString() {
		return "UserProfileResponse [isSuccessfull=" + isSuccessfull + ", message=" + message + ", userName=" + userName
				+ ", oldUserName=" + oldUserName + ", email=" + email + ", userId=" + userId + ", propertyDto="
				+ propertyDto + ", onlineStatementsPrefDto=" + onlineStatementsPrefDto + "]";
	}
}
