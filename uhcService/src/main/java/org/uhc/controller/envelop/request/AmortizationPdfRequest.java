package org.uhc.controller.envelop.request;

import java.util.List;

import org.uhc.dao.dto.AmortizationPmtDto;

public class AmortizationPdfRequest {

	private long loanNumber;
	private String ammountBorrowed;
	private String paybackPeriods;
	private String interestRate;
	private String maturityDate;
	private String currentUnpaiPrincipleBalance;
	private String nextPaymentDue;
	private String principalAndInterest;
	private String escrowPayment;
	private String monthlyPayment;
	private String anticipatedPayOff;
	private String remainingInterestPaid;
	private String email;
	private String principalAndInterestMod;
	private String escrowPaymentMod;
	private String monthlyPaymentMod;
	private String anticipatedPayOffMod;
	private String remainingInterestPaidMod;
	private String interestSaved; 
	private String paymentType;
	private String title;
	private String extraPrincipalAmt;
	
	private List<AmortizationPmtDto> pmtDtos;

	public final long getLoanNumber() {
		return loanNumber;
	}

	public final List<AmortizationPmtDto> getPmtDtos() {
		return pmtDtos;
	}

	public final void setPmtDtos(List<AmortizationPmtDto> pmtDtos) {
		this.pmtDtos = pmtDtos;
	}

	public final void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}


	public final String getAmmountBorrowed() {
		return ammountBorrowed;
	}

	public final void setAmmountBorrowed(String ammountBorrowed) {
		this.ammountBorrowed = ammountBorrowed;
	}

	public final String getPaybackPeriods() {
		return paybackPeriods;
	}

	public final void setPaybackPeriods(String paybackPeriods) {
		this.paybackPeriods = paybackPeriods;
	}

	public final String getInterestRate() {
		return interestRate;
	}

	public final void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public final String getMaturityDate() {
		return maturityDate;
	}

	public final void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	public final String getCurrentUnpaiPrincipleBalance() {
		return currentUnpaiPrincipleBalance;
	}

	public final void setCurrentUnpaiPrincipleBalance(String currentUnpaiPrincipleBalance) {
		this.currentUnpaiPrincipleBalance = currentUnpaiPrincipleBalance;
	}

	public final String getNextPaymentDue() {
		return nextPaymentDue;
	}

	public final void setNextPaymentDue(String nextPaymentDue) {
		this.nextPaymentDue = nextPaymentDue;
	}

	public final String getPrincipalAndInterest() {
		return principalAndInterest;
	}

	public final void setPrincipalAndInterest(String principalAndInterest) {
		this.principalAndInterest = principalAndInterest;
	}

	public final String getEscrowPayment() {
		return escrowPayment;
	}

	public final void setEscrowPayment(String escrowPayment) {
		this.escrowPayment = escrowPayment;
	}

	public final String getMonthlyPayment() {
		return monthlyPayment;
	}

	public final void setMonthlyPayment(String monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public final String getAnticipatedPayOff() {
		return anticipatedPayOff;
	}

	public final void setAnticipatedPayOff(String anticipatedPayOff) {
		this.anticipatedPayOff = anticipatedPayOff;
	}

	public final String getRemainingInterestPaid() {
		return remainingInterestPaid;
	}

	public final void setRemainingInterestPaid(String remainingInterestPaid) {
		this.remainingInterestPaid = remainingInterestPaid;
	}

	public final String getEmail() {
		return email;
	}

	public final void setEmail(String email) {
		this.email = email;
	}

	public final String getPrincipalAndInterestMod() {
		return principalAndInterestMod;
	}

	public final void setPrincipalAndInterestMod(String principalAndInterestMod) {
		this.principalAndInterestMod = principalAndInterestMod;
	}

	public final String getEscrowPaymentMod() {
		return escrowPaymentMod;
	}

	public final void setEscrowPaymentMod(String escrowPaymentMod) {
		this.escrowPaymentMod = escrowPaymentMod;
	}

	public final String getMonthlyPaymentMod() {
		return monthlyPaymentMod;
	}

	public final void setMonthlyPaymentMod(String monthlyPaymentMod) {
		this.monthlyPaymentMod = monthlyPaymentMod;
	}

	public final String getAnticipatedPayOffMod() {
		return anticipatedPayOffMod;
	}

	public final void setAnticipatedPayOffMod(String anticipatedPayOffMod) {
		this.anticipatedPayOffMod = anticipatedPayOffMod;
	}

	public final String getRemainingInterestPaidMod() {
		return remainingInterestPaidMod;
	}

	public final void setRemainingInterestPaidMod(String remainingInterestPaidMod) {
		this.remainingInterestPaidMod = remainingInterestPaidMod;
	}

	public final String getInterestSaved() {
		return interestSaved;
	}

	public final void setInterestSaved(String interestSaved) {
		this.interestSaved = interestSaved;
	}
	public final String getPaymentType() {
		return paymentType;
	}

	public final void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public final String getTitle() {
		return title;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public final String getExtraPrincipalAmt() {
		return extraPrincipalAmt;
	}

	public final void setExtraPrincipalAmt(String extraPrincipalAmt) {
		this.extraPrincipalAmt = extraPrincipalAmt;
	}
}
