/* 
 * ===========================================================================
 * File Name UpdateUserNameServiceImpl.java
 * 
 * Created on May 29, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateUserNameServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.UpdateUserNameRequest;
import org.uhc.controller.envelop.response.UpdateUserNameResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.UserDto;
import org.uhc.exception.InvalidUsernameException;
import org.uhc.service.UpdateUserNameService;
import org.uhc.util.UserNameValidation;

/**
 * @author nehas3
 * @date May 29, 2018
 * @description Implementing UpdateUserNameService to get the
 *              UpdateUserNameResponse
 */
@Service
public class UpdateUserNameServiceImpl implements UpdateUserNameService {

	private static final Logger LOGGER = LogManager.getLogger(UpdateUserNameServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;
	
	/**
	 * 
	 * @author nehas3
	 * @date May 29, 2018
	 * @return UpdateUserNameResponse
	 * @param updateUserNameRequest
	 * @return Description : this is updateUserName method to update user name on
	 *         basis of requested parameters.
	 */
	@Override
	public UpdateUserNameResponse updateUserName(UpdateUserNameRequest updateUserNameRequest) {
		LOGGER.info("Entering into updateUserName method");

		UpdateUserNameResponse updateUserNameResponse = new UpdateUserNameResponse();
		try {
			String lowerCaseUserName = updateUserNameRequest.getNewUserName().toLowerCase();
			if (updateUserNameRequest.getNewUserName() != null && updateUserNameRequest.getNewUserName().equals(lowerCaseUserName)) {
				boolean isUserValidated = UserNameValidation.validateUsername(updateUserNameRequest.getNewUserName());
					if (isUserValidated) {
						UserDto user = userDao.getUserByUsernameAndPWD(updateUserNameRequest.getOldUserName(), updateUserNameRequest.getPassword());
						if (user != null) {
							UserDto userDto = userDao.getUserByUsername(updateUserNameRequest.getNewUserName());

							if (userDto == null) {
								// updating user name only if user is valid or exists
								boolean isUserNameUpdated = userDao.updateUserName(updateUserNameRequest);
								if (isUserNameUpdated) {
									updateUserNameResponse.setIsSuccessful(true);
									updateUserNameResponse.setMessage("200"); // success
								} else {
									updateUserNameResponse.setIsSuccessful(false);
									updateUserNameResponse.setMessage("403"); // Miscellaneous error
								}
							} else {
								updateUserNameResponse.setIsSuccessful(false);
								updateUserNameResponse.setMessage("406"); // user name already exists

							}
						} else {
							updateUserNameResponse.setIsSuccessful(false);
							updateUserNameResponse.setMessage("405"); // user not found
						}
					} else {
						updateUserNameResponse.setIsSuccessful(false);
						updateUserNameResponse.setMessage("402"); // user name validation failure
					}
				}else {
				updateUserNameResponse.setIsSuccessful(false);
				updateUserNameResponse.setMessage("402"); // empty user name
			}

		}catch (InvalidUsernameException iue) {
			updateUserNameResponse.setIsSuccessful(false);
			updateUserNameResponse.setMessage("402"); // Invalid user name
			LOGGER.error("User name is not valid, Try Again With Correct Input", iue);
		} catch (Exception exp) {
			updateUserNameResponse.setIsSuccessful(false);
			updateUserNameResponse.setMessage("403"); // Miscellaneous error
			LOGGER.error("User's user name could not be updated because of ", exp);
		}
		LOGGER.info("Exit From updateUserName method");
		return updateUserNameResponse;
	}
}
