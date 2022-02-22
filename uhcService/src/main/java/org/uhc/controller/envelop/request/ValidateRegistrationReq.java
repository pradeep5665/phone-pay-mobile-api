/* 
 * ===========================================================================
 * File Name ValidateRegistrationReq.java
 * 
 * Created on Jun 12, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateRegistrationReq.java,v $
 * ===========================================================================
 */
 package org.uhc.controller.envelop.request;

 /**
  *  @author nehas3
  *  @date Jun 12, 2019
  *  Description : This is ValidateRegistrationReq request bean to get account Info of user on basis of loanNumber.
  */
 public class ValidateRegistrationReq {

	    private String loanNumber;
		private String ssn;
		private String zip;
		private String registrationKey;
		private String username;
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
		public String getRegistrationKey() {
			return registrationKey;
		}
		public void setRegistrationKey(String registrationKey) {
			this.registrationKey = registrationKey;
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

	 @Override
	 public String toString() {
		 StringBuilder builder = new StringBuilder("{\"ValidateRegistrationReq\": {");
		 builder.append("\"loanNumber\": \"").append(getLoanNumber()).append("\",");
		 builder.append("\"ssn\": \"").append(getSsn().substring(getSsn().length()-4)).append("\",");
		 builder.append("\"zip\": \"").append(getZip()).append("\",");
		 builder.append("\"username\": \"").append(getUsername()).append("\",");
		 builder.append("\"email\": \"").append(getEmail()).append("\"}");
		 return builder.toString();
	 }
 }
