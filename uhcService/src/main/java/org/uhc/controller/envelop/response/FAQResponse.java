package org.uhc.controller.envelop.response;

import java.util.List;

import org.uhc.dao.dto.FAQSequenceDto;

public class FAQResponse {

	private Boolean isSuccessful;
	private String message;
	
	private long categoryID;
	private List<FAQSequenceDto> faqSequenceList;
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
	public final long getCategoryID() {
		return categoryID;
	}
	public final void setCategoryID(long categoryID) {
		this.categoryID = categoryID;
	}
	public final List<FAQSequenceDto> getFaqSequenceList() {
		return faqSequenceList;
	}
	public final void setFaqSequenceList(List<FAQSequenceDto> faqSequenceList) {
		this.faqSequenceList = faqSequenceList;
	}
	
}
