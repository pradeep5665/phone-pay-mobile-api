/* 
 * ===========================================================================
 * File Name UpdateOnlineStatementPrefRequest.java
 * 
 * Created on May 30, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateOnlineStatementPrefRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date May 30, 2018 Description : This is UpdateOnlineStatementPrefRequest
 *       request bean to get user updated their Online Statement preference on
 *       basis of userId, password, isOnlineStatementEnabled.
 */
public class UpdateOnlineStatementPrefRequest {

	private int userId;
	private Boolean isOnlineStatementEnabled;
	private Boolean isAgreeToDiscloser;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Boolean getIsOnlineStatementEnabled() {
		return isOnlineStatementEnabled;
	}

	public void setIsOnlineStatementEnabled(Boolean isOnlineStatementEnabled) {
		this.isOnlineStatementEnabled = isOnlineStatementEnabled;
	}

	public Boolean getIsAgreeToDiscloser() {
		return isAgreeToDiscloser;
	}

	public void setIsAgreeToDiscloser(Boolean isAgreeToDiscloser) {
		this.isAgreeToDiscloser = isAgreeToDiscloser;
	}
}
