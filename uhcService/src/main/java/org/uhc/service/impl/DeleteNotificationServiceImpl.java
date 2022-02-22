/* 
 * ===========================================================================
 * File Name DeleteNotificationServiceImpl.java
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
 * $Log: DeleteNotificationServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.DeleteNotificationRequest;
import org.uhc.controller.envelop.response.DeleteNotificationResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.GetNotificationDetailsDto;
import org.uhc.service.DeleteNotificationService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date Aug 24, 2018
 * @description Implementing DeleteNotificationService to get the DeleteNotification
 */
@Service
public class DeleteNotificationServiceImpl implements DeleteNotificationService{

	private static final Logger LOGGER = LogManager.getLogger(DeleteNotificationServiceImpl.class.getName());
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessageReader messageReader;
	
	/**
	 * @author nehas3
	 * @date Aug 24, 2018
	 * @return DeleteNotificationResponse 
	 * @param deleteNotificationRequest
	 * @return true or false accordingly
	 * @Description : this is deleteNotification method that returns true or false when the notification is deleted by user.
	 */
	@Override
	public DeleteNotificationResponse deleteNotification(DeleteNotificationRequest deleteNotificationRequest){
		LOGGER.info("deleteNotification(): messageId: {}", deleteNotificationRequest.getMessageId());
		DeleteNotificationResponse deleteNotificationResponse = new DeleteNotificationResponse();
		try{
			GetNotificationDetailsDto notificationDetails = userDao.getNotificationDetailsByMessageId(deleteNotificationRequest.getMessageId());
			
			if(notificationDetails != null){
				boolean isNotificationDeleted = userDao.deleteNotification(deleteNotificationRequest.getMessageId());
				if(isNotificationDeleted){
					deleteNotificationResponse.setSuccessful(true);
					deleteNotificationResponse.setMessage(messageReader.getPropertyFileMesssages().get("deletenotification.success"));
				}else{
					deleteNotificationResponse.setSuccessful(false);
					deleteNotificationResponse.setMessage(messageReader.getPropertyFileMesssages().get("deletenotification.failure"));
				}
			}else{
				deleteNotificationResponse.setSuccessful(false);
				deleteNotificationResponse.setMessage(messageReader.getPropertyFileMesssages().get("messageIdNotFound.error"));
			}
		}catch(Exception exp){
			deleteNotificationResponse.setSuccessful(false);
			deleteNotificationResponse.setMessage(messageReader.getPropertyFileMesssages().get("deletenotification.failure"));
			LOGGER.error("deleteNotification(): delete failed" , exp);
		}
		LOGGER.info("deleteNotification(): deleteNotificationResponse: {}", deleteNotificationResponse);
		return deleteNotificationResponse;
	}
}
