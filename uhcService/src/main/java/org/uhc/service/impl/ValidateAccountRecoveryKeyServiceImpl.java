/* 
 * ===========================================================================
 * File Name ValidateAccountRecoveryKeyServiceImpl.java
 * 
 * Created on Jan 27, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateAccountRecoveryKeyServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.ValidateAccountRecoveryKeyReq;
import org.uhc.controller.envelop.response.ValidateAccountRecoveryKeyRes;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.AccountRecoveryDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.ValidateAccountRecoveryKeyService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date Oct 13, 2020
 */
@Service
public class ValidateAccountRecoveryKeyServiceImpl implements ValidateAccountRecoveryKeyService {

	private static final Logger LOGGER = LogManager.getLogger(ValidateAccountRecoveryKeyServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	int addMinuteTime;

	/**
	 * validateAccountRecoveryKey have been created to validate the key being
	 * used by user, while recovering their account, if it is not expired or invalid
	 * @author nehas3
	 * @since Oct 13, 2020
	 * @param aacountRecKeyReq
	 * @return response of validate account recovery key api
	 */
	@Override
	public ValidateAccountRecoveryKeyRes validateAccountRecoveryKey(ValidateAccountRecoveryKeyReq acountRecKeyReq) {
		LOGGER.info("Entering into validateAccountRecoveryKey method");
		ValidateAccountRecoveryKeyRes validateAccountRecoveryKeyRes = new ValidateAccountRecoveryKeyRes();
		UserDto userDto;
		try {
			AccountRecoveryDto accRecoveryDto = userDao.getAccountRecoveryKeyDetails(acountRecKeyReq.getAccountRecoveryKey());
			if (accRecoveryDto != null) {
				if (accRecoveryDto.getAccRecoveryKey() != null) {
					userDto = userDao.getUserByUserId(accRecoveryDto.getUserId());
					Date currentTime = new Date();
					addMinuteTime = 30;
					Date accRecoveryTimeExpiryTime = accRecoveryDto.getRecoveryTime();
					accRecoveryTimeExpiryTime = DateUtils.addMinutes(accRecoveryTimeExpiryTime, addMinuteTime); // add	// minute

					int timeComparision = currentTime.compareTo(accRecoveryTimeExpiryTime);
					if (timeComparision == 0 || timeComparision == -1) {
						validateAccountRecoveryKeyRes.setIsSuccessFull(true);
						validateAccountRecoveryKeyRes.setStatusCode(messageReader.getStatusCode().get("Key validated successfully"));
						validateAccountRecoveryKeyRes.setMessage("Key validated successfully");
						validateAccountRecoveryKeyRes.setUserName(userDto.getUsername());
					} else {
						boolean isKeyReset = userDao.resetRecoveryKeyForFailure(accRecoveryDto.getId());
						if(isKeyReset) {
							LOGGER.info("updated account rec key after getting expired");
						}
						validateAccountRecoveryKeyRes.setIsSuccessFull(false);
						validateAccountRecoveryKeyRes.setStatusCode(messageReader.getStatusCode().get("Invalid key provided"));
						validateAccountRecoveryKeyRes.setMessage("The entered key is expired");
					}
				} else {
					validateAccountRecoveryKeyRes.setIsSuccessFull(false);
					validateAccountRecoveryKeyRes.setStatusCode(messageReader.getStatusCode().get("Key does not exist"));// not available in
																									// database
					validateAccountRecoveryKeyRes.setMessage("Key does not exist");
				}
			} else {
				validateAccountRecoveryKeyRes.setIsSuccessFull(false);
				validateAccountRecoveryKeyRes.setStatusCode(messageReader.getStatusCode().get("Key does not exist"));// not available in database
				validateAccountRecoveryKeyRes.setMessage("Key does not exist");
			}
		} catch (Exception exp) {
			LOGGER.error("Could not validate AccountRecoveryKey because of exception", exp);
			validateAccountRecoveryKeyRes.setIsSuccessFull(false);
			validateAccountRecoveryKeyRes.setStatusCode(
			messageReader.getStatusCode().get("Could not validate recovery key because of exception"));// not available in database
			validateAccountRecoveryKeyRes.setMessage("Could not validate recovery key because of exception");
		}

		LOGGER.info("Exit from validateAccountRecoveryKey method");
		return validateAccountRecoveryKeyRes;
	}

}
