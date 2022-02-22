/* 
 * ===========================================================================
 * File Name BankingInfoServiceImpl.java
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
 * $Log: BankingInfoServiceImpl.java,v $
 * ===========================================================================
 */
 package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.BankingInfoRequest;
import org.uhc.controller.envelop.response.BankingInfoResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.BankingInfoDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.BankingInfoService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date May 25, 2018
 * @description Implementing BankingInfoServiceImpl to get the BankingInfoResponse
 */
@Service
public class BankingInfoServiceImpl implements BankingInfoService{
	private static final Logger LOGGER = LogManager.getLogger(BankingInfoServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessageReader messageReader;
	
	/**
	 * 
	 * @author nehas3
	 * @date June 7, 2018
	 * @return BankingInfoResponse 
	 * @param bankingInfoRequest
	 * @return banking info of user
	 * Description : this is getBankinginfo method that returns the banking information of user on basis of requested params.
	 */
	@Override
	public BankingInfoResponse getBankingInfo(BankingInfoRequest bankingInfoRequest) {
		LOGGER.info("getBankingInfo(): userId: {}", bankingInfoRequest.getUserId());
		
		BankingInfoResponse bankingInfoResponse = new BankingInfoResponse();
		
		try{
			UserDto user = userDao.getUserByUserId(bankingInfoRequest.getUserId());
			
			if(user!=null){
			BankingInfoDto bankingInfoDto = userDao.getBankingInfo(bankingInfoRequest.getUserId());
				if(bankingInfoDto!=null){
					bankingInfoResponse.setIsSuccessful(true);
					bankingInfoResponse.setError("");
					bankingInfoResponse.setBankingInfo(bankingInfoDto);	
				}else{
					bankingInfoResponse.setIsSuccessful(false);
					bankingInfoResponse.setError(messageReader.getPropertyFileMesssages().get("bankingInfoNotFound.error"));
				}
			}else{
				bankingInfoResponse.setIsSuccessful(false);
				bankingInfoResponse.setError(messageReader.getPropertyFileMesssages().get("user.not.available.error"));
			}
		}catch(Exception exp){
			bankingInfoResponse.setIsSuccessful(false);
			bankingInfoResponse.setError(messageReader.getPropertyFileMesssages().get("bankingInfo.execption.error"));
			LOGGER.error("getBankingInfo(): " , exp);
		}

		LOGGER.info("getBankingInfo(): bankingInfoResponse: {}", bankingInfoResponse);
		return bankingInfoResponse;
	}

}
