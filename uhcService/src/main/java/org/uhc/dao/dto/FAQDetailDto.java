package org.uhc.dao.dto;

import java.util.List;

public class FAQDetailDto {

	private long categoryID;
	private List<FAQSequenceDto> faqSequenceList;
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
