/* 
 * ===========================================================================
 * File Name LogoutRequest.java
 * 
 * Created on Aug 24, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: LogoutRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 *  @author nehas3
 *  @date Aug 24, 2018
 *  @Description : This is LogoutRequest request bean to logout user on basis of userId.
 */
public class LogoutRequest {

	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}	
}
