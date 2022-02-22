/* 
 * ===========================================================================
 * File Name updateReadStatusMessageReq.java
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
 * $Log: updateReadStatusMessageReq.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date Aug 22, 2018
 * @Description : This is UpdateReadStatusMessageRequest request bean to update
 *              read status of FCM notification on basis of messageIds.
 */
public class UpdateReadStatusMessageRequest {

	private int messageId;
	private boolean readStatus;

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public boolean isReadStatus() {
		return readStatus;
	}

	public void setReadStatus(boolean readStatus) {
		this.readStatus = readStatus;
	}

}
