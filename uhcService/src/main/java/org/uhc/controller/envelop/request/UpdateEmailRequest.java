/* 
 * ===========================================================================
 * File Name UpdateEmailRequest.java
 * 
 * Created on May 28, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateEmailRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date May 28, 2018 Description : This is UpdateEmailRequest request bean to
 *       get user updated their email on basis of userId, password, newEmail.
 */
public class UpdateEmailRequest {

	private int userId;
	private String password;
	private String newEmail;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	@Override
	public String toString() {
		return "UpdateEmailRequest{" +
				"userId=" + userId +
				", password='********'" +
				", newEmail='" + newEmail + '\'' +
				'}';
	}
}
