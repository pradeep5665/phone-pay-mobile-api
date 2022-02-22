package org.uhc.controller.envelop.request;

/**
 *  @author pradeepy
 *  @date May 21, 2021
 *  Description : This is ValidateRegistrationWithSecureCodeReq request bean to get account Info of user on basis of loanNumber.
 */
public class ValidateRegistrationWithSecureCodeReq {
	  private String loanNumber;
			private String ssn;
			private String zip;
			private String securityCode;
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
			public String getSecurityCode() {
				return securityCode;
			}
			public void setSecurityCode(String securityCode) {
				this.securityCode = securityCode;
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
				return "ValidateRegistrationWithSecureCodeReq [loanNumber=" + loanNumber + ", ssn=" + ssn + ", zip="
						+ zip + ", securityCode=" + securityCode + ", username=" + username + ", email=" + email + "]";
			}
			
			

}
