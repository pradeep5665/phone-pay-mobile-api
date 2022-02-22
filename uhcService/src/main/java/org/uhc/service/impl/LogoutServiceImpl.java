/* 
 * ===========================================================================
 * File Name LogoutServiceImpl.java
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
 * $Log: LogoutServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.LogoutRequest;
import org.uhc.controller.envelop.response.LogoutResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.LogoutService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date Aug 24, 2018
 * @description Implementing LogoutService to get the LogoutResponse
 */
@Service
public class LogoutServiceImpl implements LogoutService{

	private static final Logger LOGGER = LogManager.getLogger(LogoutServiceImpl.class.getName());
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessageReader messageReader;

	/**
	 * @author nehas3
	 * @date Aug 24, 2018
	 * @return LogoutResponse 
	 * @param logoutRequest
	 * @return true or false
	 * @Description : this is logout to log out from the application.
	 */
	@Override
	public LogoutResponse logout(LogoutRequest logoutRequest){
		LOGGER.info("logout(): userId: {}", logoutRequest.getUserId());
		LogoutResponse logoutResponse = new LogoutResponse();
		try{
			UserDto userDto = userDao.getUserByUserId(logoutRequest.getUserId());
			if(userDto != null){
				boolean isUserLoggedOut = userDao.logout(logoutRequest.getUserId());
				if(isUserLoggedOut){
					logoutResponse.setIsSuccessful(true);
					logoutResponse.setMessage(messageReader.getPropertyFileMesssages().get("logout.success"));
				}else{
					logoutResponse.setIsSuccessful(false);
					logoutResponse.setMessage(messageReader.getPropertyFileMesssages().get("logout.failure"));
				}
			}else{
				logoutResponse.setIsSuccessful(false);
				logoutResponse.setMessage(messageReader.getPropertyFileMesssages().get("user.not.available.error"));
			}
		}catch(Exception exp){
			logoutResponse.setIsSuccessful(false);
			logoutResponse.setMessage(messageReader.getPropertyFileMesssages().get("logout.failure"));
			LOGGER.error("logout(): logout failed.", exp);
		}
		LOGGER.info("logout(): {}", logoutResponse);
		return logoutResponse;
	}
}
