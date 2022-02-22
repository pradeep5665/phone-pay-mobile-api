/* 
 * ===========================================================================
 * File Name DeleteBankingInfoResponse.java
 * 
 * Created on Jun 7, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: DeleteBankingInfoResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

/**
 * @author nehas3
 * @date June 7, 2018
 * @description creating Delete Banking Info Service Response for success or failure
 */
public class DeleteBankingInfoResponse {

	private Boolean isSuccessful;
	private String message;
	
	public Boolean getIsSuccessful() {
		return isSuccessful;
	}
	public void setIsSuccessful(Boolean isSuccessful) {
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
		return "DeleteBankingInfoResponse{" +
				"isSuccessful=" + isSuccessful +
				", message='" + message + '\'' +
				'}';
	}
}
