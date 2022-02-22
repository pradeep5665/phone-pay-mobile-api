/* 
 * ===========================================================================
 * File Name AccountResquest.java
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
 * $Log: AccountResquest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 *  @author nehas3
 *  @date May 25, 2018
 *  Description : This is AccountResquest request bean to get account Info of user on basis of loanNumber.
 */
public class AccountRequest {

	private long loanNumber;

	public long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

}
