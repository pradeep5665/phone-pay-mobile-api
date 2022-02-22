package org.uhc.controller.envelop.request;

public class FAQReq {

	
	private long categoryId;
	private String application;

	public final long getCategoryId() {
		return categoryId;
	}

	public final void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public final String getApplication() {
		return application;
	}

	public final void setApplication(String application) {
		this.application = application;
	}
	
	
}
