/* 
 * ===========================================================================
 * File Name GetNotificationDetailsServiceImpl.java
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
 * $Log: GetNotificationDetailsServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.GetNotificationDetailsRequest;
import org.uhc.controller.envelop.response.GetNotificationDetailsResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.GetNotificationDetailsDto;
import org.uhc.dao.dto.TokenDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.GetNotificationDetailsService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date Aug 22, 2018
 * @description Implementing GetNotificationDetailsService to get the GetNotificationDetailsResponse
 */
@Service
public class GetNotificationDetailsServiceImpl implements GetNotificationDetailsService {
	private static final Logger LOGGER = LogManager.getLogger(GetNotificationDetailsServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessageReader messageReader;

	/**
	 * @author nehas3
	 * @date Aug 22, 2018
	 * @return GetNotificationDetailsResponse 
	 * @param notificationRequest
	 * @return Notification details
	 * @Description : this is getNotificationDetails method that returns notification details of user on basis of requested parameters.
	 */
	@Override
	public GetNotificationDetailsResponse getNotificationDetails(GetNotificationDetailsRequest notificationRequest) {
		GetNotificationDetailsResponse notificationResponse = new GetNotificationDetailsResponse();
		try {
			UserDto user = userDao.getUserByUserId(notificationRequest.getUserId());
			if(user != null){
				List<TokenDto> tokenList = userDao.getFCMToken(notificationRequest.getUserId());
				if (!tokenList.isEmpty()) {
					List<GetNotificationDetailsDto> notificationDetailsList = userDao.getNotificationDetails(notificationRequest.getUserId());
					if(!notificationDetailsList.isEmpty()){
						notificationResponse.setIsSuccessful(true);
						notificationResponse.setMessage(messageReader.getPropertyFileMesssages().get("getNotificationDetails.success"));
						notificationResponse.setNotificationList(notificationDetailsList);
					}else{
						notificationResponse.setIsSuccessful(false);
						notificationResponse.setMessage(messageReader.getPropertyFileMesssages().get("getNotificationDetails.failure.messageNotFound"));
					}
				}else{
					notificationResponse.setIsSuccessful(false);
					notificationResponse.setMessage(messageReader.getPropertyFileMesssages().get("getNotificationDetails.failure.tokenNotFound"));
				}
			}else{
				notificationResponse.setIsSuccessful(false);
				notificationResponse.setMessage(messageReader.getPropertyFileMesssages().get("user.not.available.error"));
			}
		} catch (Exception exp) {
			notificationResponse.setIsSuccessful(false);
			notificationResponse.setMessage(messageReader.getPropertyFileMesssages().get("getNotificationDetails.failureExp"));
			LOGGER.error("getNotificationDetails(): getting messages failed." , exp);
		}
		LOGGER.info("getNotificationDetails(): notificationResponse: {}", notificationResponse);
		return notificationResponse;
	}
}
