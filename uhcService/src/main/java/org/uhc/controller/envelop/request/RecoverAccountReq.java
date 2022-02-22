/* 
 * ===========================================================================
 * File Name RecoverAccountReq.java
 * 
 * Created on Jan 28, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: RecoverAccountReq.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

public class RecoverAccountReq {

	private String accRecoveryKey;
	private String recoveryEmail;
	private String newPassword;
	private String confirmPassword;

	public String getAccRecoveryKey() {
		return accRecoveryKey;
	}

	public void setAccRecoveryKey(String accRecoveryKey) {
		this.accRecoveryKey = accRecoveryKey;
	}

	public String getRecoveryEmail() {
		return recoveryEmail;
	}

	public void setRecoveryEmail(String recoveryEmail) {
		this.recoveryEmail = recoveryEmail;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "RecoverAccountReq [accRecoveryKey=" + accRecoveryKey + ", recoveryEmail=" + recoveryEmail
				+ ", newPassword=" + newPassword + ", confirmPassword=" + confirmPassword + "]";
	}

}
