/* 
 * ===========================================================================
 * File Name UpdateOnlineStatementPrefServiceImpl.java
 * 
 * Created on Jun 1, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateOnlineStatementPrefServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.UpdateOnlineStatementPrefRequest;
import org.uhc.controller.envelop.response.UpdateOnlineStatementPrefResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.UpdateOnlineStatementPrefService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date May 28, 2018
 * @description Implementing UpdateOnlineStatementPrefService to get the
 *              UpdateOnlineStatementPrefResponse
 */
@Service
public class UpdateOnlineStatementPrefServiceImpl implements UpdateOnlineStatementPrefService {

	private static final Logger LOGGER = LogManager.getLogger(UpdateOnlineStatementPrefServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	/**
	 * 
	 * @author nehas3
	 * @date May 30, 2018
	 * @return UpdateOnlineStatementPrefResponse
	 * @param statementPrefRequest
	 * @return success or failure on update Online Statement Preference for user
	 *         Description : this is updateOnlineStatementPref that returns success
	 *         or failure on update Online Statement Preference for user
	 */
	@Override
	public UpdateOnlineStatementPrefResponse updateOnlineStatementPref(
			UpdateOnlineStatementPrefRequest statementPrefRequest) {
		LOGGER.info("Entering into updateOnlineStatementPref method");

		UpdateOnlineStatementPrefResponse statementPrefResponse = new UpdateOnlineStatementPrefResponse();

		try {

			UserDto user = userDao.getUserByUserId(statementPrefRequest.getUserId());

			if (user != null) {
				List<Long> loanNumberList = userDao.getLoanAccountsByUserName(user.getUsername());

				if (loanNumberList != null) {
					boolean isOnlineStatementPrefUpdated = userDao.updateOnlineStatementPref(
							statementPrefRequest.getIsOnlineStatementEnabled(), loanNumberList);

					if (isOnlineStatementPrefUpdated) {
						statementPrefResponse.setIsSuccessful(true);
						statementPrefResponse.setMessage(
								messageReader.getPropertyFileMesssages().get("updateOnlineStatementPref.success"));
						LOGGER.info("Online Statement Pref Updated successfully");
					} else {
						statementPrefResponse.setIsSuccessful(false);
						statementPrefResponse.setMessage(messageReader.getPropertyFileMesssages()
								.get("updateOnlineStatementPref.failure.statementNotFound"));
						LOGGER.info("Online Statement does not exists for user:{}", statementPrefRequest.getUserId());
					}

				} else {
					statementPrefResponse.setIsSuccessful(false);
					statementPrefResponse
							.setMessage(messageReader.getPropertyFileMesssages().get("loanNotFound.error"));
					LOGGER.info("No Loan Record found to update online statement preference for user: {}",
							statementPrefRequest.getUserId());
				}

			} else {
				statementPrefResponse.setIsSuccessful(false);
				statementPrefResponse
						.setMessage(messageReader.getPropertyFileMesssages().get("user.not.available.error"));
				LOGGER.info("User Does Not Exists, userId :{} ", statementPrefRequest.getUserId());
			}

		} catch (Exception exp) {
			statementPrefResponse.setIsSuccessful(false);
			statementPrefResponse
					.setMessage(messageReader.getPropertyFileMesssages().get("updateOnlineStatementPref.failureExp"));
			LOGGER.error("User's Online Statement Pref could not be updated because of ", exp);
		}
		LOGGER.info("Exit From updateOnlineStatementPref method");
		return statementPrefResponse;
	}

}
