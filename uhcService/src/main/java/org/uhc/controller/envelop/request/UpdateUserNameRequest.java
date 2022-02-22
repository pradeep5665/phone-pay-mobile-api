/* 
 * ===========================================================================
 * File Name UpdateUserNameRequest.java
 * 
 * Created on May 29, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateUserNameRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date May 29, 2018 Description : This is UpdateUserNameRequest request bean
 *       to get user updated their user name on basis of password,
 *       oldUserName,newUserName.
 */
public class UpdateUserNameRequest {
	private String password;
	private String oldUserName;
	private String newUserName;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldUserName() {
		return oldUserName;
	}

	public void setOldUserName(String oldUserName) {
		this.oldUserName = oldUserName;
	}

	public String getNewUserName() {
		return newUserName;
	}

	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
	}

	@Override
	public String toString() {
		return "UpdateUserNameRequest{" +
				"password='********'" +
				", oldUserName='" + oldUserName + '\'' +
				", newUserName='" + newUserName + '\'' +
				'}';
	}
}
