package org.uhc.controller.envelop.response;

import java.util.List;

import org.uhc.dao.dto.FAQCategoryDto;

public class FAQCategoryResponse {

	private Boolean isSuccessful;
	private String message;
	private List<FAQCategoryDto> categoryList;
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
	public final List<FAQCategoryDto> getCategoryList() {
		return categoryList;
	}
	public final void setCategoryList(List<FAQCategoryDto> categoryList) {
		this.categoryList = categoryList;
	}
	@Override
	public String toString() {
		return "FAQCategoryResponse [isSuccessful=" + isSuccessful + ", message=" + message + ", categoryList="
				+ categoryList + "]";
	}
	
	
}
