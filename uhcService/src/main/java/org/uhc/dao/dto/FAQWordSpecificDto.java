package org.uhc.dao.dto;

public class FAQWordSpecificDto {

	
	private long categoryId;
	private String question;
	private String answer;
	private String application;
	
	
	public final long getCategoryId() {
		return categoryId;
	}
	public final void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public final String getQuestion() {
		return question;
	}
	public final void setQuestion(String question) {
		this.question = question;
	}
	public final String getAnswer() {
		return answer;
	}
	public final void setAnswer(String answer) {
		this.answer = answer;
	}
	public final String getApplication() {
		return application;
	}
	public final void setApplication(String application) {
		this.application = application;
	}
	
	
}
