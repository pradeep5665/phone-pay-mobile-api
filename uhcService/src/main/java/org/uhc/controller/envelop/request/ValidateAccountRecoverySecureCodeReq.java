package org.uhc.controller.envelop.request;

public class ValidateAccountRecoverySecureCodeReq {

	private String loanNumber;
	private String ssn;
	private String zip;
	private String email;
	private String securityCode;
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
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	@Override
	public String toString() {
		return "ValidateAccountRecoverySecureCodeReq [loanNumber=" + loanNumber + ", ssn=" + ssn + ", zip=" + zip
				+ ", email=" + email + ", securityCode=" + securityCode + "]";
	}
	
	
}
