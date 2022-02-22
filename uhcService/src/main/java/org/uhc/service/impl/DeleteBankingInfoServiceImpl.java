/* 
 * ===========================================================================
 * File Name DeleteBankingInfoServiceImpl.java
 * 
 * Created on June 7, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: DeleteBankingInfoServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.DeleteBankingInfoRequest;
import org.uhc.controller.envelop.response.DeleteBankingInfoResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.BankingInfoDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.DeleteBankingInfoService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date June 7, 2018
 * @description Implementing DeleteBankingInfoServiceImpl to delete user's
 *              banking info
 */
@Service
public class DeleteBankingInfoServiceImpl implements DeleteBankingInfoService {

	private static final Logger LOGGER = LogManager.getLogger(DeleteBankingInfoServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessageReader messageReader;

	/**
	 * 
	 * @author nehas3
	 * @date June 7, 2018
	 * @return DeleteBankingInfoResponse
	 * @param deleteBankingInfoRequest
	 * @return success or failure on delete Banking info for user Description :
	 *         this is deleteBankingInfo method that returns success or failure
	 *         on delete Banking info for user.
	 */
	@Override
	public DeleteBankingInfoResponse deleteBankingInfo(DeleteBankingInfoRequest deleteBankingInfoRequest) {
		LOGGER.info("deleteBankingInfo(): userId: {}", deleteBankingInfoRequest.getUserId());

		DeleteBankingInfoResponse deleteBankingInfoResponse = new DeleteBankingInfoResponse();

		try {
			UserDto user = userDao.getUserByUserId(deleteBankingInfoRequest.getUserId());
			if (user != null) {
				BankingInfoDto bankingInfoDto = userDao.getBankingInfo(deleteBankingInfoRequest.getUserId());
				if (bankingInfoDto != null) {
					boolean isBankingInfodeleted = userDao.deleteBankingInfo(deleteBankingInfoRequest.getUserId());

					if (isBankingInfodeleted) {
						deleteBankingInfoResponse.setIsSuccessful(true);
						deleteBankingInfoResponse.setMessage(messageReader.getPropertyFileMesssages().get("deleteBankingInfo.success"));
					} else {
						deleteBankingInfoResponse.setIsSuccessful(false);
						deleteBankingInfoResponse.setMessage(messageReader.getPropertyFileMesssages().get("deleteBankingInfo.failure"));
					}
				} else {
					deleteBankingInfoResponse.setIsSuccessful(false);
					deleteBankingInfoResponse.setMessage(messageReader.getPropertyFileMesssages().get("bankingInfoNotFound.error"));
				}

			} else {
				deleteBankingInfoResponse.setIsSuccessful(false);
				deleteBankingInfoResponse.setMessage(messageReader.getPropertyFileMesssages().get("user.not.available.error"));
			}

		} catch (Exception exp) {
			deleteBankingInfoResponse.setIsSuccessful(false);
			deleteBankingInfoResponse.setMessage(messageReader.getPropertyFileMesssages().get("deleteBankingInfo.failure"));
			LOGGER.error("deleteBankingInfo(): delete failed", exp);
		}
		LOGGER.info("deleteBankingInfo(): deleteBankingInfoResponse: {}", deleteBankingInfoResponse);
		return deleteBankingInfoResponse;
	}

}
