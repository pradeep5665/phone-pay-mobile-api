/* 
 * ===========================================================================
 * File Name ValidateAccountRecoveryReq.java
 * 
 * Created on Jan 24, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateAccountRecoveryReq.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

public class ValidateAccountRecoveryReq {

	private String loanNumber;
	private String ssn;
	private String zip;
	private String email;

	public String getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ValidateAccountRecoveryReq [loanNumber=" + loanNumber + ", ssn=" + ssn + ", zip=" + zip
				+ ", email=" + email + "]";
	}

}
