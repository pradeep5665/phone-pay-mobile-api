package org.uhc.dao.dto;

public class AmortizationPmtDto {

	private String dueDate;
	private String paymentAmt;
	private String interest;
	private String principal;
	private String balance;
	public final String getDueDate() {
		return dueDate;
	}
	public final void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public final String getPaymentAmt() {
		return paymentAmt;
	}
	public final void setPaymentAmt(String paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public final String getInterest() {
		return interest;
	}
	public final void setInterest(String interest) {
		this.interest = interest;
	}
	public final String getPrincipal() {
		return principal;
	}
	public final void setPrincipal(String principal) {
		this.principal = principal;
	}
	public final String getBalance() {
		return balance;
	}
	public final void setBalance(String balance) {
		this.balance = balance;
	}
	
	
	
}
