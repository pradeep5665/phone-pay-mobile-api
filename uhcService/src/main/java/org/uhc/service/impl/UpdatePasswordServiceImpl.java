/* 
 * ===========================================================================
 * File Name UpdatePasswordServiceImpl.java
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
 * $Log: UpdatePasswordServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.UpdatePasswordRequest;
import org.uhc.controller.envelop.response.UpdatePasswordResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.UserDto;
import org.uhc.exception.InvalidPasswordException;
import org.uhc.service.UpdatePasswordService;
import org.uhc.util.PasswordValidation;

/**
 * @author nehas3
 * @date May 28, 2018
 * @description Implementing UpdatePasswordService to get the
 *              UpdatePasswordResponse
 */
@Service
public class UpdatePasswordServiceImpl implements UpdatePasswordService {

	private static final Logger LOGGER = LogManager.getLogger(UpdatePasswordServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	/**
	 * 
	 * @author nehas3
	 * @date May 29, 2018
	 * @return UpdatePasswordResponse
	 * @param updatePasswordRequest
	 * @return success or failure on update Password for user Description : this is
	 *         updatePassword that returns success or failure on update Password for
	 *         user.
	 */
	@Override
	public UpdatePasswordResponse updatePassword(UpdatePasswordRequest updatePasswordRequest) {
		LOGGER.info("Entering into updatePassword method");

		UpdatePasswordResponse updatePasswordResponse = new UpdatePasswordResponse();
		try {
			if (updatePasswordRequest.getNewPassword() != null) {

				boolean isPasswordValid = PasswordValidation.validatePassword(updatePasswordRequest.getNewPassword());

				if (isPasswordValid) {
					/* Checking if user exist on the basis of entered userId */
					UserDto user = userDao.getUserByUserIdAndPWD(updatePasswordRequest.getUserId(),
							updatePasswordRequest.getOldPassword());
					if (user != null) {
						/*
						 * updating password only if user is valid or exists
						 */
						boolean isPasswordUpdated = userDao.updatePassword(updatePasswordRequest);

						if (isPasswordUpdated) {
							updatePasswordResponse.setIsSuccessful(true);
							updatePasswordResponse.setMessage("200"); // success
						} else {
							updatePasswordResponse.setIsSuccessful(false);
							updatePasswordResponse.setMessage("401"); // Miscellaneous error
						}
					} else {
						updatePasswordResponse.setIsSuccessful(false);
						updatePasswordResponse.setMessage("405"); // user not found
					}
				} else {
					updatePasswordResponse.setIsSuccessful(false);
					updatePasswordResponse.setMessage("402"); // validate password error
				}
			} else {
				updatePasswordResponse.setIsSuccessful(false);
				updatePasswordResponse.setMessage("402"); // empty password
			}

		} catch (InvalidPasswordException ipe) {
			updatePasswordResponse.setIsSuccessful(false);
			updatePasswordResponse.setMessage("402");
			LOGGER.error("Password does not meet requirements. Please check the requirements and enter a new password.",
					ipe);
		} catch (Exception exp) {
			updatePasswordResponse.setIsSuccessful(false);
			updatePasswordResponse.setMessage("403"); // Exception
			LOGGER.error("User's password could not be updated because of ", exp);
		}
		LOGGER.info("Exit From updatePassword method");
		return updatePasswordResponse;
	}

}
