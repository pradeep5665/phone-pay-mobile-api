/* 
 * ===========================================================================
 * File Name UpdateReadStatusServiceImpl.java
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
 * $Log: UpdateReadStatusServiceImpl.java,v $
 * ===========================================================================
 */
 package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.UpdateReadStatusMessageRequest;
import org.uhc.controller.envelop.response.UpdateReadStatusMessageResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.GetNotificationDetailsDto;
import org.uhc.service.UpdateReadStatusService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date Aug 22, 2018
 * @description Implementing UpdateReadStatusService to get the UpdateReadStatusResponse
 */
@Service
 public class UpdateReadStatusServiceImpl implements UpdateReadStatusService {

	private static final Logger LOGGER = LogManager.getLogger(UpdateReadStatusServiceImpl.class.getName());
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessageReader messageReader;
	
	/**
	 * @author nehas3
	 * @date Aug 22, 2018
	 * @return UpdateReadStatusMessageResponse 
	 * @param readStatusResquest
	 * @return true or false accordingly
	 * @Description : this is updateReadStatusOfMessage method that returns boolean values on basis of requested parameters.
	 */
	@Override
	public UpdateReadStatusMessageResponse updateReadStatusOfMessage(UpdateReadStatusMessageRequest readStatusResquest){

		LOGGER.info("Entering into updateReadStatusOfMessage method");
		
		UpdateReadStatusMessageResponse readStatusMessageRes = new UpdateReadStatusMessageResponse();
		
		try{
			if(readStatusResquest.isReadStatus()){
			GetNotificationDetailsDto notificationDetails = userDao.getNotificationDetailsByMessageId(readStatusResquest.getMessageId());
			if(notificationDetails != null){
			boolean isReadStatusUpdated = userDao.updateReadStatusOfMessage(readStatusResquest.getMessageId());
			if(isReadStatusUpdated){
				readStatusMessageRes.setSuccessful(true);
				readStatusMessageRes.setMessage(messageReader.getPropertyFileMesssages().get("updateReadStatus.success"));
			}else{
				readStatusMessageRes.setSuccessful(false);
				readStatusMessageRes.setMessage(messageReader.getPropertyFileMesssages().get("updateReadStatus.failure"));
			}
			}else{
				readStatusMessageRes.setSuccessful(false);
				readStatusMessageRes.setMessage(messageReader.getPropertyFileMesssages().get("messageIdNotFound.error"));
			}
			}else{
				readStatusMessageRes.setSuccessful(false);
				readStatusMessageRes.setMessage(messageReader.getPropertyFileMesssages().get("updateReadStatus.failure.readStatusNotTrue"));
			}
			
		}catch(Exception exp){
			readStatusMessageRes.setSuccessful(false);
			readStatusMessageRes.setMessage(messageReader.getPropertyFileMesssages().get("updateReadStatus.failure"));
			LOGGER.error("Read Status Could Not Be Updated Because Of Exception " , exp);
		}
		LOGGER.info("Exit from updateReadStatusOfMessage method");
		return readStatusMessageRes;
	}
}
