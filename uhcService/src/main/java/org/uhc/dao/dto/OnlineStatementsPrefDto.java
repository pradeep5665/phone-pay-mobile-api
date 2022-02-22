/* 
 * ===========================================================================
 * File Name OnlineStatementsPrefDto.java
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
 * $Log: OnlineStatementsPrefDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

/**
 * @author nehas3
 * @date May 25, 2018
 */
public class OnlineStatementsPrefDto {

	private long loanNumber;
	private Boolean onlineStatements;

	public long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	public Boolean getOnlineStatements() {
		return onlineStatements;
	}

	public void setOnlineStatements(Boolean onlineStatements) {
		this.onlineStatements = onlineStatements;
	}

	@Override
	public String toString() {
		return "OnlineStatementsPrefDto{" +
				"loanNumber=" + loanNumber +
				", onlineStatements=" + onlineStatements +
				'}';
	}
}
