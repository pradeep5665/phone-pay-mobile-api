/* 
 * ===========================================================================
 * File Name GetScheduledPaymentForCancellationRequest.java
 * 
 * Created on July 9, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetScheduledPaymentForCancellationRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date July 9, 2018
 * Description : This is GetScheduledPaymentForCancellationReq request bean to get scheduled payment info on the basis of loanNumber.
 */
public class GetScheduledPaymentForCancellationReq {

	private int loanNumber;

	public int getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(int loanNumber) {
		this.loanNumber = loanNumber;
	}	
}
