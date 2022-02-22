package org.uhc.controller.envelop.response;

import java.time.LocalDateTime;

public class CommunicationViewResponse {
	
	private Boolean isSuccessful;
	private String message;
	private long userId;
	private long messageId;
	private String dateViewed;
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
	public final long getUserId() {
		return userId;
	}
	public final void setUserId(long userId) {
		this.userId = userId;
	}
	public final long getMessageId() {
		return messageId;
	}
	public final void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	
	public final String getDateViewed() {
		return dateViewed;
	}
	public final void setDateViewed(String dateViewed) {
		this.dateViewed = dateViewed;
	}
	@Override
	public String toString() {
		return "CommunicationViewResponse [isSuccessful=" + isSuccessful + ", message=" + message + ", userId=" + userId
				+ ", messageId=" + messageId + ", dateViewed=" + dateViewed + "]";
	}
	
	

}
