/* 
 * ===========================================================================
 * File Name TaxInformationResponse.java
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
 * $Log: TaxInformationResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

import java.util.List;

import org.uhc.dao.dto.TaxInfoDto;

/**
 * @author nehas3
 * @date May 25, 2018
 * @description creating Tax Information Service Response for success or failure
 */
public class TaxInformationResponse {

	private Boolean isSuccessful;
	private String error;
	private List<TaxInfoDto> taxInformation;

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

	public List<TaxInfoDto> getTaxInformation() {
		return taxInformation;
	}

	public void setTaxInformation(List<TaxInfoDto> taxInformation) {
		this.taxInformation = taxInformation;
	}
}
