/* 
 * ===========================================================================
 * File Name ValidateAccountRecoveryKey.java
 * 
 * Created on Jan 27, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateAccountRecoveryKey.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

public class ValidateAccountRecoveryKeyReq {

	private String accountRecoveryKey;

	public String getAccountRecoveryKey() {
		return accountRecoveryKey;
	}

	public void setAccountRecoveryKey(String accountRecoveryKey) {
		this.accountRecoveryKey = accountRecoveryKey;
	}

}
