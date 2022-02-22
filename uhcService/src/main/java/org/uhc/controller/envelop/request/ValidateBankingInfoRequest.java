/* 
 * ===========================================================================
 * File Name ValidateBankingInfoRequest.java
 * 
 * Created on Aug 23, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateBankingInfoRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 *  @author nehas3
 *  @date Aug 23, 2019
 *  Description : This is ValidateBankingInfoRequest request bean to get account Info of user on basis of loanNumber.
 */
public class ValidateBankingInfoRequest {

	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
