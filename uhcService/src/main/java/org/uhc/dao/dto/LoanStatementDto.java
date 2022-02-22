/* 
 * ===========================================================================
 * File Name LoanStatementDto.java
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
 * $Log: LoanStatementDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.uhc.util.Constants;

/**
 * @author nehas3
 * @date May 25, 2018
 */
public class LoanStatementDto {

	private String statementDate;
	private String frontPath;

	public String getStatementDate() {
		return statementDate;
	}

	public void setStatementDate(Date statementDate) {
		if (statementDate != null) {
			DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
			this.statementDate = sdf.format(statementDate);
		} else {
			this.statementDate = null;
		}
	}

	public String getFrontPath() {
		return frontPath;
	}

	public void setFrontPath(String frontPath) {
		this.frontPath = frontPath;
	}

}
