/* 
 * ===========================================================================
 * File Name GetNotificationDetailsDto.java
 * 
 * Created on Aug 22, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetNotificationDetailsDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.uhc.util.Constants;

/**
 * @author nehas3
 * @date Aug 22, 2018
 */
public class GetNotificationDetailsDto {

	private int messageId;
	private int userId;
	private String messageTitle;
	private String messageBody;
	private boolean readStatus;
	private String messageTime;
	
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	public boolean isReadStatus() {
		return readStatus;
	}
	public void setReadStatus(boolean readStatus) {
		this.readStatus = readStatus;
	}
	public String getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(LocalDateTime messageTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATETIME_FORMAT.getValue());
		ZonedDateTime ztd =  messageTime.atZone(ZoneId.systemDefault());
		Date messageDate = Date.from(ztd.toInstant());
		this.messageTime = sdf.format(messageDate);
	}

	@Override
	public String toString() {
		return "GetNotificationDetailsDto{" +
				"messageId=" + messageId +
				", userId=" + userId +
				", messageTitle='" + messageTitle + '\'' +
				", messageBody='" + messageBody + '\'' +
				", readStatus=" + readStatus +
				", messageTime='" + messageTime + '\'' +
				'}';
	}
}
