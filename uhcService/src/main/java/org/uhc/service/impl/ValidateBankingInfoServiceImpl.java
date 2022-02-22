/* 
 * ===========================================================================
 * File Name ValidateBankingInfoServiceImpl.java
 * 
 * Created on Aug 23, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateBankingInfoServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.ValidateBankingInfoRequest;
import org.uhc.controller.envelop.response.ValidateBankingInfoResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.BankingInfoDto;
import org.uhc.dao.dto.ErrorCodeForBankingInfoDto;
import org.uhc.dao.dto.IncorrectBankingInfoDto;
import org.uhc.service.ValidateBankingInfoService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date Aug 23, 2019
 * @description Implementing ValidateBankingInfoService to get the
 *              ValidateBankingInfoResponse
 */
@Service
public class ValidateBankingInfoServiceImpl implements ValidateBankingInfoService {

	private static final Logger LOGGER = LogManager.getLogger(ValidateBankingInfoService.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	/**
	 * 
	 * @author nehas3
	 * @date Aug 23, 2019
	 * @param ValidateBankingInfoRequest
	 * @return ValidateBankingInfoResponse
	 * @Description if banking info is correct or not Description : this is getEscrowInfo
	 *         to get escrow information for a user.
	 */
	@Override
	public ValidateBankingInfoResponse validateBankingInfo(ValidateBankingInfoRequest validateBankingInfoReq) {
		LOGGER.info("Entering into validateBankingInfo method");
		ValidateBankingInfoResponse validateBankingInfoRes = new ValidateBankingInfoResponse();
		int count = 0;
		try {
			BankingInfoDto bankingInfoDto = userDao.getBankingInfo(validateBankingInfoReq.getUserId());
			List<IncorrectBankingInfoDto> validateBankingInfoList = userDao
					.validateBankingInfo(validateBankingInfoReq.getUserId());
			ErrorCodeForBankingInfoDto errorInfo = null;
			if (bankingInfoDto != null && validateBankingInfoList != null && !validateBankingInfoList.isEmpty()) {
				IncorrectBankingInfoDto validateBankInfo = new IncorrectBankingInfoDto();
				for (IncorrectBankingInfoDto validateBankingInfo : validateBankingInfoList) {
					if ("1".equals(validateBankingInfo.getNeedsNotification())) {
						validateBankInfo.setId(validateBankingInfo.getId());
						validateBankInfo.setNeedsNotification(validateBankingInfo.getNeedsNotification());
						validateBankInfo.setErrorCode(validateBankingInfo.getErrorCode());
						count++;
					}
				}
				if (count == 0) {
					validateBankingInfoRes.setMessage("Banking info verfied successfully");
				} else if (count > 0 && validateBankInfo.getErrorCode() == 1) {
					errorInfo = userDao.getErrorMessage(validateBankInfo.getErrorCode());
					if (errorInfo != null) {
						validateBankingInfoRes.setIncorrectBankiInfoId(validateBankInfo.getId());
						validateBankingInfoRes.setNeedNotificationValue(validateBankInfo.getNeedsNotification());
						validateBankingInfoRes.setErrorCode(validateBankInfo.getErrorCode());
						validateBankingInfoRes.setMessage(errorInfo.getNotificationMessage());

						boolean isIncorrectBankingInfoUpdated = userDao
								.updateIncorrectBankingInfo(validateBankInfo.getId());
						if (isIncorrectBankingInfoUpdated) {
							LOGGER.info(
									"updated needs_notification of highest id in incorrect_banking info tbale to 0");
						} else {
							LOGGER.info("could not update needs_notification value");
						}
					}
				} else if (count > 0 && (validateBankInfo.getErrorCode() == 2 || validateBankInfo.getErrorCode() == 3)) {
					errorInfo = userDao.getErrorMessage(validateBankInfo.getErrorCode());
					validateBankingInfoRes.setIncorrectBankiInfoId(validateBankInfo.getId());
					validateBankingInfoRes.setNeedNotificationValue(validateBankInfo.getNeedsNotification());
					validateBankingInfoRes.setErrorCode(validateBankInfo.getErrorCode());
					validateBankingInfoRes.setMessage(errorInfo.getNotificationMessage());
				}
			} else if (bankingInfoDto != null && (validateBankingInfoList == null)
					|| validateBankingInfoList.isEmpty()) {
				validateBankingInfoRes.setMessage("Banking info verfied successfully");
			} else {
				LOGGER.error("Banking info is not available for user: {}", validateBankingInfoReq.getUserId());
				validateBankingInfoRes
						.setMessage(messageReader.getPropertyFileMesssages().get("bankingInfo.failure.notSaved"));
			}

		} catch (Exception exp) {
			LOGGER.error("Could not validate banking info because of exception", exp);
		}

		LOGGER.info("Exit From validateBankingInfo method");
		return validateBankingInfoRes;
	}

}
