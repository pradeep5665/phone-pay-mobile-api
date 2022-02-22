/* 
 * ===========================================================================
 * File Name DeleteNotificationRequest.java
 * 
 * Created on Aug 24, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: DeleteNotificationRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date Aug 24, 2018
 * Description : This is DeleteNotificationRequest request bean to delete notifications of user on basis of messageId.
 */
public class DeleteNotificationRequest {

	private int messageId;

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}	
}
