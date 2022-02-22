/* 
 * ===========================================================================
 * File Name AccountHistoryServiceImpl.java
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
 * $Log: AccountHistoryServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.AccountHistoryRequest;
import org.uhc.controller.envelop.response.AccountHistoryResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.TransactionDto;
import org.uhc.service.AccountHistoryService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date May 25, 2018
 * @Description Implementing AccountHistoryService to get the
 *              AccountHistoryResponse
 */
@Service
public class AccountHistoryServiceImpl implements AccountHistoryService {

	private static final Logger LOGGER = LogManager.getLogger(AccountHistoryServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;
	
	/**
	 * 
	 * @author nehas3
	 * @date May 25, 2018
	 * @return AccountHistoryResponse
	 * @param accountHistoryRequest
	 * @return account history of user
	 * @Description : this is getAccountHistoryByLoanNumber method that returns
	 *              account history of user on basis of requested parameter
	 */
	@Override
	public AccountHistoryResponse getAccountHistoryByLoanNumber(AccountHistoryRequest accountHistoryRequest) {
		LOGGER.info("getAccountHistoryByLoanNumber(): loanNumber: {}", accountHistoryRequest.getLoanNumber());
		AccountHistoryResponse accountHistoryResponse = new AccountHistoryResponse();

		try {
			/*
			 * Fetching account history of user on the basis of loan number
			 */
			List<TransactionDto> transactionDtoList = userDao.getAccountHistoryByLoanNum(accountHistoryRequest.getLoanNumber());
			for(TransactionDto transactionDto : transactionDtoList) {
				if(("Payment").equals(transactionDto.getDesc()) && transactionDto.getTotalAmount().equals(transactionDto.getEscrow())) {
					transactionDto.setDesc("Escrow Payment");
				}
			}

			if (!transactionDtoList.isEmpty()) {
				accountHistoryResponse.setIsSuccessful(true);
				accountHistoryResponse.setError("");
				accountHistoryResponse.setTransactionList(transactionDtoList);
				
			} else {
				accountHistoryResponse.setIsSuccessful(false);
				accountHistoryResponse.setError(messageReader.getPropertyFileMesssages().get("accountHistory.error"));
			}
		} catch (Exception exp) {
			accountHistoryResponse.setIsSuccessful(false);
			accountHistoryResponse.setError(exp.getMessage());

			LOGGER.error("getAccountHistoryByLoanNumber(): ", exp);
		}
		LOGGER.info("getAccountHistoryByLoanNumber(): {}", accountHistoryResponse);
		return accountHistoryResponse;
	}
}
