/* 
 * ===========================================================================
 * File Name UpdatePasswordRequest.java
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
 * $Log: UpdatePasswordRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date May 29, 2018
 * @Description : This is UpdatePasswordRequest request bean to get user updated
 *              their Password on basis of userId, password,
 *              oldPassword,newPassword.
 */
public class UpdatePasswordRequest {
	private int userId;
	private String oldPassword;
	private String newPassword;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "UpdatePasswordRequest{" +
				"userId=" + userId +
				", oldPassword='^^^^^^^^'" +
				", newPassword='********'" +
				'}';
	}
}
