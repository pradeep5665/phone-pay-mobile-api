/* 
 * ===========================================================================
 * File Name UpdateReadStatusMessageResponse.java
 * 
 * Created on Aug 22, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateReadStatusMessageResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

/**
 * @author nehas3
 * @date Aug 22, 2018
 * @description creating Update Read Status Message Service Response for success or failure
 */
public class UpdateReadStatusMessageResponse {

	private boolean isSuccessful;
	private String message;
	public boolean isSuccessful() {
		return isSuccessful;
	}
	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
}
