/* 
 * ===========================================================================
 * File Name GetScheduledPaymentRequest.java
 * 
 * Created on Jul 2, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetScheduledPaymentRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date Jul 2, 2018
 * @Description : This is GetScheduledPayment request bean to get already scheduled payment info on the basis of loanNumber.
 */
public class GetScheduledPaymentRequest {

	private long loanNumber;

	public long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

}
