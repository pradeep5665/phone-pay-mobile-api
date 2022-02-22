/* 
 * ===========================================================================
 * File Name TaxInformationRequest.java
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
 * $Log: TaxInformationRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date May 25, 2018 Description : This is TaxInformationRequest request bean
 *       to get Tax Info for user to on basis of loanNumber.
 */
public class TaxInformationRequest {

	private Long loanNumber;

	public Long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(Long loanNumber) {
		this.loanNumber = loanNumber;
	}
}
