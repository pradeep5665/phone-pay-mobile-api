/* 
 * ===========================================================================
 * File Name EscrowResponse.java
 * 
 * Created on May 25, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: EscrowResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

import java.util.List;

import org.uhc.dao.dto.EscrowDto;

/**
 * @author nehas3
 * @date May 25, 2018
 * @description Creating Escrow Information Service Response for success or failure
 */
public class EscrowResponse {

	private Boolean isSuccessful;
	private String error;
	private List<EscrowDto> escrowInfoDetails;

	public Boolean getIsSuccessful() {
		return isSuccessful;
	}

	public void setIsSuccessful(Boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<EscrowDto> getEscrowInfoDetails() {
		return escrowInfoDetails;
	}

	public void setEscrowInfoDetails(List<EscrowDto> escrowInfoDetails) {
		this.escrowInfoDetails = escrowInfoDetails;
	}

	@Override
	public String toString() {
		return "EscrowResponse{" +
				"isSuccessful=" + isSuccessful +
				", error='" + error + '\'' +
				", escrowInfoDetails=" + escrowInfoDetails +
				'}';
	}
}
