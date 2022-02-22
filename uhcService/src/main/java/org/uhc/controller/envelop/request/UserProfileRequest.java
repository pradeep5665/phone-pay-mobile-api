/* 
 * ===========================================================================
 * File Name UserProfileRequest.java
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
 * $Log: UserProfileRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date May 25, 2018 Description : This is UserProfileRequest request bean to
 *       get user profile of existing user on basis of userName.
 */
public class UserProfileRequest {

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "{" +
			"\"UserProfileRequest\": {" +
				"\"userName\": \"" + getUserName() + "\"" +
			"}" +
		"}";
	}
}
