package org.uhc.dao.dto;

public class FAQDto {

	private long id;
	private long categoryID;
	private int questionSequence;
	private String questionTitle;
	private String qauestionAnswer;
	private String application;
	private String active;
	
	
	
	public final long getId() {
		return id;
	}
	public final void setId(long id) {
		this.id = id;
	}
	
	public final long getCategoryID() {
		return categoryID;
	}
	public final void setCategoryID(long categoryID) {
		this.categoryID = categoryID;
	}
	
	public final int getQuestionSequence() {
		return questionSequence;
	}
	public final void setQuestionSequence(int questionSequence) {
		this.questionSequence = questionSequence;
	}
	public final String getQuestionTitle() {
		return questionTitle;
	}
	public final void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	
	public final String getQauestionAnswer() {
		return qauestionAnswer;
	}
	public final void setQauestionAnswer(String qauestionAnswer) {
		this.qauestionAnswer = qauestionAnswer;
	}
	public final String getApplication() {
		return application;
	}
	public final void setApplication(String application) {
		this.application = application;
	}
	public final String getActive() {
		return active;
	}
	public final void setActive(String active) {
		this.active = active;
	}
	
}
