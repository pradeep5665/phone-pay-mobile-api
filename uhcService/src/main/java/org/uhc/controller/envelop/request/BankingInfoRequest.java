/* 
 * ===========================================================================
 * File Name BankingInfoRequest.java
 * 
 * Created on June 05, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: BankingInfoRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date June 5, 2018
 * Description : This is BankingInfoRequest request bean to get Current Banking Info of user on basis of userId.
 */
public class BankingInfoRequest {

	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
