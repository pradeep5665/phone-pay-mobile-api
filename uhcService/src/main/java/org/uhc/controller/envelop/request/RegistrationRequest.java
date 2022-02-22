/* 
 * ===========================================================================
 * File Name RegistrationRequest.java
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
 * $Log: RegistrationRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date May 25, 2018 Description : This is RegistrationRequest request bean to
 *       register user who already applied for mortgage but not registered.
 */
public class RegistrationRequest {

	private String loanNumber;
	private String ssn;
	private String zip;

	private String username;
	private String email;
	private String password;
	private boolean onlineStatementPrefStatus;
	private boolean agreed;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isOnlineStatementPrefStatus() {
		return onlineStatementPrefStatus;
	}

	public void setOnlineStatementPrefStatus(boolean onlineStatementPrefStatus) {
		this.onlineStatementPrefStatus = onlineStatementPrefStatus;
	}

	public boolean isAgreed() {
		return agreed;
	}

	public void setAgreed(boolean agreed) {
		this.agreed = agreed;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("{\"RegistrationRequest\": {");
		builder.append("\"loanNumber\": \"").append(getLoanNumber()).append("\",");
		builder.append("\"ssn\": \"").append(getSsn().substring(getSsn().length()-4)).append("\",");
		builder.append("\"zip\": \"").append(getZip()).append("\",");
		builder.append("\"username\": \"").append(getUsername()).append("\",");
		builder.append("\"email\": \"").append(getEmail()).append("\",");
		builder.append("\"password\": \"<omitted>\",");
		builder.append("\"onlineStatementPrefStatus\": \"").append(isOnlineStatementPrefStatus()).append("\",");
		builder.append("\"agreed\": \"").append(isAgreed()).append("\"}");
		return builder.toString();
	}
}
