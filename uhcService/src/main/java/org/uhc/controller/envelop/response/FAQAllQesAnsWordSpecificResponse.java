package org.uhc.controller.envelop.response;

import java.util.List;

import org.uhc.dao.dto.FAQWordSpecificDto;

public class FAQAllQesAnsWordSpecificResponse {
	
	private Boolean isSuccessful;
	private String message;
	private List<FAQWordSpecificDto> faqWordSpecificList;
	public final Boolean getIsSuccessful() {
		return isSuccessful;
	}
	public final void setIsSuccessful(Boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	public final String getMessage() {
		return message;
	}
	public final void setMessage(String message) {
		this.message = message;
	}
	public final List<FAQWordSpecificDto> getFaqWordSpecificList() {
		return faqWordSpecificList;
	}
	public final void setFaqWordSpecificList(List<FAQWordSpecificDto> faqWordSpecificList) {
		this.faqWordSpecificList = faqWordSpecificList;
	}
	
	
	

}
