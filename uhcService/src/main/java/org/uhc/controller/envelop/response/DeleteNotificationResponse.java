/* 
 * ===========================================================================
 * File Name DeleteNotificationResponse.java
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
 * $Log: DeleteNotificationResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

/**
 * @author nehas3
 * @date Aug 24, 2018
 * @description creating Delete Notification Response for success or failure
 */
public class DeleteNotificationResponse {

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

	@Override
	public String toString() {
		return "DeleteNotificationResponse{" +
				"isSuccessful=" + isSuccessful +
				", message='" + message + '\'' +
				'}';
	}
}
