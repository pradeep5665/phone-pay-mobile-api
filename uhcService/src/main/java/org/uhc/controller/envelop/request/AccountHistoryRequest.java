/* 
 * ===========================================================================
 * File Name AccountHistoryRequest.java
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
 * $Log: AccountHistoryRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date May 25, 2018
 * @Description : This is AccountHistoryRequest request bean to get account history of user on basis of loanNumber.
 */
public class AccountHistoryRequest {

	private Long loanNumber;

	public Long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	@Override
	public String toString() {
		return "{{\"AccountHistoryRequest\": {\"loanNumber\" : \"" + getLoanNumber() + "\"}}";
	}
}
