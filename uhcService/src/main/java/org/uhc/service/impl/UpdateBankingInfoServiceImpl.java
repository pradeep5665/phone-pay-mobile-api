/* 
 * ===========================================================================
 * File Name UpdateBankingInfoServiceImpl.java
 * 
 * Created on Jun 5, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateBankingInfoServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.UpdateBankingInfoRequest;
import org.uhc.controller.envelop.response.UpdateBankingInfoResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.BankingInfoDto;
import org.uhc.dao.dto.ErrorCodeForBankingInfoDto;
import org.uhc.dao.dto.IncorrectBankingInfoDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.UpdateBankingInfoService;
import org.uhc.util.MessageReader;
import org.uhc.util.Constants.ScheduledPaymentType;

/**
 * @author nehas3
 * @date June 5, 2018
 * @description Implementing UpdateBankingInfoServiceImpl to get the
 *              UpdateBankingInfoResponse
 */
@Service
public class UpdateBankingInfoServiceImpl implements UpdateBankingInfoService {

	private static final Logger LOGGER = LogManager.getLogger(UpdateBankingInfoServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	/**
	 * 
	 * @author nehas3
	 * @date June 5, 2018
	 * @return UpdateBankingInfoResponse
	 * @param updateBankingInfoRequest
	 * @return success or failure on update Banking info for user Description : this
	 *         is updateBankingInfo that returns success or failure on update
	 *         Banking info for user.
	 */
	@Override
	public UpdateBankingInfoResponse updateBankingInfo(UpdateBankingInfoRequest updateBankingInfoRequest) {
		LOGGER.info("Entering into updateBankingInfo method");

		UpdateBankingInfoResponse updateBankingInfoResponse = new UpdateBankingInfoResponse();

		try {
			UserDto user = userDao.getUserByUserId(updateBankingInfoRequest.getUserId());
			if (user != null) {
				BankingInfoDto bankingInfoDto = userDao.getBankingInfo(updateBankingInfoRequest.getUserId());
				if (bankingInfoDto != null && updateBankingInfoRequest.getId() != 0) {
					boolean isRoutingNumWhiteListed = userDao
							.isRoutingNumberWhiteListed(updateBankingInfoRequest.getRoutingNumber());
					if (isRoutingNumWhiteListed) {
						IncorrectBankingInfoDto blackRoutingNumberDetails = userDao.isRoutingNumberBlackListed(
								updateBankingInfoRequest.getRoutingNumber(),
								updateBankingInfoRequest.getBankAccountNumber());
						if (blackRoutingNumberDetails == null) {
							List<Long> loanNumbers = userDao.getLoanAccountsByUserName(user.getUsername());
							if (loanNumbers != null && !loanNumbers.isEmpty()) {
								List<Integer> result = userDao.getActiveTodayPaymentByPaymentType(
										ScheduledPaymentType.TODAY, loanNumbers.get(0));
								if (result.isEmpty()) {
									boolean isBankingInfoUpdated = userDao.updateBankingInfo(
											updateBankingInfoRequest.getId(), updateBankingInfoRequest);
									if (isBankingInfoUpdated) {
										List<IncorrectBankingInfoDto> validateBankingInfoList = userDao
												.validateBankingInfo(updateBankingInfoRequest.getUserId());
										if (validateBankingInfoList != null && !validateBankingInfoList.isEmpty()
												&& (validateBankingInfoList.get(0).getErrorCode() == 2
														|| validateBankingInfoList.get(0).getErrorCode() == 3)
												&& ("1".equals(
														validateBankingInfoList.get(0).getNeedsNotification()))) {
											boolean isIncorrectBankingInfoUpdated = userDao
													.updateIncorrectBankingInfo(validateBankingInfoList.get(0).getId());
											if (isIncorrectBankingInfoUpdated) {
												LOGGER.info(
														"updated needs_notification of highest id in incorrect_banking info tbale to 0");
											} else {
												LOGGER.info("could not update needs_notification value");
											}
										}
										updateBankingInfoResponse.setIsSuccessful(true);
										updateBankingInfoResponse.setMessage(
												messageReader.getPropertyFileMesssages().get("bankingInfo.success"));
									} else {
										updateBankingInfoResponse.setIsSuccessful(false);
										updateBankingInfoResponse.setMessage(
												messageReader.getPropertyFileMesssages().get("bankingInfo.failure"));
									}
								} else {
									updateBankingInfoResponse.setIsSuccessful(false);
									updateBankingInfoResponse.setMessage(messageReader.getPropertyFileMesssages().get(
											"scheduledPayment.todayPaymentExistMessage") + " " + loanNumbers.get(0));
								}
							} else {
								updateBankingInfoResponse.setIsSuccessful(false);
								updateBankingInfoResponse.setMessage(
										messageReader.getPropertyFileMesssages().get("user.not.available.error"));
							}
						} else {
							ErrorCodeForBankingInfoDto errorInfo = null;
							errorInfo = userDao.getErrorMessage(blackRoutingNumberDetails.getErrorCode());
							updateBankingInfoResponse.setIsSuccessful(false);
							updateBankingInfoResponse.setMessage(errorInfo.getValidationMessage());
						}

					} else {
						updateBankingInfoResponse.setIsSuccessful(false);
						updateBankingInfoResponse.setMessage(messageReader.getPropertyFileMesssages()
								.get("bankingInfo.failure.incorrectRoutingNumber"));
					}
				} else {
					boolean isRoutingNumWhiteListed = userDao
							.isRoutingNumberWhiteListed(updateBankingInfoRequest.getRoutingNumber());
					if ((isRoutingNumWhiteListed)) {
						IncorrectBankingInfoDto blackRoutingNumberDetails = userDao.isRoutingNumberBlackListed(
								updateBankingInfoRequest.getRoutingNumber(),
								updateBankingInfoRequest.getBankAccountNumber());
						if (blackRoutingNumberDetails == null) {
							boolean isBankingInfoUpdated = userDao.addBankingInfo(updateBankingInfoRequest);
							if (isBankingInfoUpdated) {
								List<IncorrectBankingInfoDto> validateBankingInfoList = userDao
										.validateBankingInfo(updateBankingInfoRequest.getUserId());
								if (validateBankingInfoList != null && !validateBankingInfoList.isEmpty()
										&& (validateBankingInfoList.get(0).getErrorCode() == 2
												|| validateBankingInfoList.get(0).getErrorCode() == 3)
										&& ("1".equals(validateBankingInfoList.get(0).getNeedsNotification()))) {
									boolean isIncorrectBankingInfoUpdated = userDao
											.updateIncorrectBankingInfo(validateBankingInfoList.get(0).getId());
									if (isIncorrectBankingInfoUpdated) {
										LOGGER.info(
												"updated needs_notification of highest id in incorrect_banking info tbale to 0");
									} else {
										LOGGER.info("could not update needs_notification value");
									}
								}
								updateBankingInfoResponse.setIsSuccessful(true);
								updateBankingInfoResponse.setMessage(
										messageReader.getPropertyFileMesssages().get("bankingInfo.success"));
							} else {
								updateBankingInfoResponse.setIsSuccessful(false);
								updateBankingInfoResponse.setMessage(
										messageReader.getPropertyFileMesssages().get("bankingInfo.failure"));
							}
						} else {
							ErrorCodeForBankingInfoDto errorInfo = null;
							errorInfo = userDao.getErrorMessage(blackRoutingNumberDetails.getErrorCode());
							updateBankingInfoResponse.setIsSuccessful(false);
							updateBankingInfoResponse.setMessage(errorInfo.getValidationMessage());
						}

					} else {
						updateBankingInfoResponse.setIsSuccessful(false);
						updateBankingInfoResponse.setMessage(messageReader.getPropertyFileMesssages()
								.get("bankingInfo.failure.incorrectRoutingNumber"));
					}
				}
			} else {
				updateBankingInfoResponse.setIsSuccessful(false);
				updateBankingInfoResponse
						.setMessage(messageReader.getPropertyFileMesssages().get("user.not.available.error"));
			}
		} catch (Exception exp) {
			updateBankingInfoResponse.setIsSuccessful(false);
			updateBankingInfoResponse.setMessage(messageReader.getPropertyFileMesssages().get("bankingInfo.failure"));
			LOGGER.error("UpdateBankingInfoServiceImpl ::: ", exp);
		}

		LOGGER.info("Exits From updateBankingInfo method");
		return updateBankingInfoResponse;
	}

}
