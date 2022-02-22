package org.uhc.dao.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class CommunicationViewDto {
	private long id;
	private long userId;
	private long messageId;
	private LocalDateTime dateViewed;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public LocalDateTime getDateViewed() {
		return dateViewed;
	}
	public void setDateViewed(LocalDateTime dateViewed) {
		this.dateViewed = dateViewed;
	}
	
}
