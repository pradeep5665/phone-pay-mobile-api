/* 
 * ===========================================================================
 * File Name UserProfileServiceImpl.java
 * 
 * Created on May 25, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UserProfileServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.UserProfileRequest;
import org.uhc.controller.envelop.response.UserProfileResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.OnlineStatementsPrefDto;
import org.uhc.dao.dto.PropertyDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.UserProfileService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date May 25, 2018
 * @description Implementing UserProfileService to get the UserProfileResponse
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {

	private static final Logger LOGGER = LogManager.getLogger(UserProfileServiceImpl.class.getName());
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessageReader messageReader;

	/**
	 * 
	 * @author nehas3
	 * @date May 25, 2018
	 * @return UserProfileResponse 
	 * @param UserProfileRequest
	 * @return user profile
	 * Description : this is getUserProfile method to get user profile info on basis of requested parameters.
	 */
	@Override
	public UserProfileResponse getUserProfile(UserProfileRequest userProfileRequest) {
		UserProfileResponse userProfileResponse = new UserProfileResponse();
		try {
			UserDto userDto = userDao.getUserByUsername(userProfileRequest.getUserName());

			 /*finding if requested user exists */
			if (userDto.getUsername() != null) { 
				userProfileResponse.setUserId(userDto.getUserId());
				userProfileResponse.setUserName(userDto.getUsername());
				userProfileResponse.setOldUserName(userDto.getUsername());
				userProfileResponse.setEmail(userDto.getEmail());
				
				int notificationFlag = userDao.getPushNOtificationFlag(userDto.getUserId());
				userProfileResponse.setPushNotificationFlagEnabled( notificationFlag==1 ? true : false);
				// fetching loan account details
				List<Long> loanAccountList = userDao.getLoanAccountsByUserName(userProfileRequest.getUserName());

				if (loanAccountList != null) {

					// fetching property info
					PropertyDto propertyDto = userDao.getPropertyInfoByLoanNum(loanAccountList.get(0));

					// fetching online preference status
					OnlineStatementsPrefDto onlineStatementsPrefDto = userDao
							.getStatementPrefStatus(loanAccountList.get(0));
					userProfileResponse.setPropertyDto(propertyDto);
					userProfileResponse.setOnlineStatementsPrefDto(onlineStatementsPrefDto);
					userProfileResponse.setIsSuccessfull(true);
					userProfileResponse.setMessage(messageReader.getPropertyFileMesssages().get("upadteUserProfile.success"));

				} else {
					userProfileResponse.setIsSuccessfull(false);
					userProfileResponse.setMessage(messageReader.getPropertyFileMesssages().get("upadteUserProfile.failure") + " " + userProfileRequest.getUserName());
				}
			} else {
				userProfileResponse.setIsSuccessfull(false);
				userProfileResponse.setMessage(messageReader.getPropertyFileMesssages().get("user.not.available.error"));
			}
		} catch (Exception exp) {
			userProfileResponse.setIsSuccessfull(false);
			userProfileResponse.setMessage(messageReader.getPropertyFileMesssages().get("upadteUserProfile.failureExp") + " " + userProfileRequest.getUserName());
			LOGGER.error("UserProfileServiceImpl :: " , exp);
		}
		LOGGER.info("Exit From getUserProfile method");
		return userProfileResponse;
	}

}
