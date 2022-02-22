package org.uhc.controller.envelop.response;

import java.util.List;

import org.uhc.dao.dto.FAQDetailDto;

public class FAQAllQesAnsResponse {

	private Boolean isSuccessful;
	private String message;
	private List<FAQDetailDto> categoryList;
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
	public final List<FAQDetailDto> getCategoryList() {
		return categoryList;
	}
	public final void setCategoryList(List<FAQDetailDto> categoryList) {
		this.categoryList = categoryList;
	}
}
