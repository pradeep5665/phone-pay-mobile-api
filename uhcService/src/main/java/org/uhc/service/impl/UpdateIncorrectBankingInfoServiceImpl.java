/* 
 * ===========================================================================
 * File Name UpdateIncorrectBankingInfoServiceImpl.java
 * 
 * Created on Sep 23, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateIncorrectBankingInfoServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.UpdateIncorrectBankingInfoRequest;
import org.uhc.controller.envelop.response.UpdateIncorrectBankingInfoResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.IncorrectBankingInfoDto;
import org.uhc.service.UpdateIncorrectBankingInfoService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date Sep 23, 2019
 * @description Implementing UpdateIncorrectBankingInfoServiceImpl to get the
 *              UpdateIncorrectBankingInfoResponse
 */
@Service
public class UpdateIncorrectBankingInfoServiceImpl implements UpdateIncorrectBankingInfoService {

	private static final Logger LOGGER = LogManager.getLogger(UpdateIncorrectBankingInfoService.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	/**
	 * 
	 * @author nehas3
	 * @date Sep 23, 2019
	 * @return UpdateIncorrectBankingInfoResponse
	 * @param UpdateIncorrectBankingInfoRequest
	 * @return success or failure on update incorrect Banking info for user Description : this
	 *         is updateBankingInfo that returns success or failure on update
	 *         Banking info for user.
	 */
	@Override
	public UpdateIncorrectBankingInfoResponse updateIncorrectBankingInfo(
			UpdateIncorrectBankingInfoRequest updateIncorrectBankingInfoReq) {
		LOGGER.info("Entering into validateBankingInfo method");
		UpdateIncorrectBankingInfoResponse updateIncorrectBankingInfoRes = new UpdateIncorrectBankingInfoResponse();
		try {
			IncorrectBankingInfoDto getIncorrectBankingInfo = userDao.getIncorrectBankingInfo(updateIncorrectBankingInfoReq.getIncorrectBankiInfoId());
			if (getIncorrectBankingInfo != null) {
				boolean isIncorrectBankingInfoUpdated = userDao.updateIncorrectBankingInfo(updateIncorrectBankingInfoReq.getIncorrectBankiInfoId());
				if (isIncorrectBankingInfoUpdated) {
					updateIncorrectBankingInfoRes.setIsSuccessful(true);
					updateIncorrectBankingInfoRes.setMessage(
							messageReader.getPropertyFileMesssages().get("upadteIncorrectBankInfo.success"));
				} else {
					updateIncorrectBankingInfoRes.setIsSuccessful(false);
					updateIncorrectBankingInfoRes.setMessage(
							messageReader.getPropertyFileMesssages().get("upadteIncorrectBankInfo.failure"));
				}
			} else {
				updateIncorrectBankingInfoRes.setIsSuccessful(false);
				updateIncorrectBankingInfoRes.setMessage(
						messageReader.getPropertyFileMesssages().get("upadteIncorrectBankInfo.failureForInvalidId")
								+ updateIncorrectBankingInfoReq.getIncorrectBankiInfoId());
			}
		} catch (Exception exp) {
			updateIncorrectBankingInfoRes.setIsSuccessful(false);
			updateIncorrectBankingInfoRes
					.setMessage(messageReader.getPropertyFileMesssages().get("upadteIncorrectBankInfo.failure"));
			LOGGER.error("Could not update incorrect banking info because of exception");
		}

		return updateIncorrectBankingInfoRes;
	}

}
