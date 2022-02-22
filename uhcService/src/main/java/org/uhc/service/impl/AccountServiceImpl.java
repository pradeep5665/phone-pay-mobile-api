/* 
 * ===========================================================================
 * File Name AccountServiceImpl.java
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
 * $Log: AccountServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.AccountRequest;
import org.uhc.controller.envelop.response.AccountResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.LoanDto;
import org.uhc.dao.dto.PropertyDto;
import org.uhc.service.AccountService;
import org.uhc.util.Constants;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date May 25, 2018
 * @description Implementing AccountService to get the AccountResponse
 */
@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger LOGGER = LogManager.getLogger(AccountServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return AccountResponse
	 * @param accountResquest
	 * @return Account balance info of user
	 * @Description : this is getAccountBalInfo method that returns Account balance
	 *              info of user on basis of requested parameters.
	 */
	@Override
	public AccountResponse getAccountBalInfo(AccountRequest accountResquest) {
		AccountResponse accountResponse = new AccountResponse();
		try {
			LoanDto loanDto = userDao.getLoanAccountDetailsByLoanNum(accountResquest.getLoanNumber());
			if (loanDto != null) {
				/* TODO: Investigate if these three DB calls can be reduced to one */
				PropertyDto propertydto = userDao.getPropertyInfoByLoanNum(accountResquest.getLoanNumber());
				String maturityDate = userDao.getMaturityPaymentDate(accountResquest.getLoanNumber());
				String firstPaymentDate = userDao.getFirstPaymentDate(accountResquest.getLoanNumber());
				
				// to make loan number 10 digit just to make check if loan is in stopfile or not.
				DecimalFormat df = new DecimalFormat("0000000000");
				String formattedLoanNumber = df.format(accountResquest.getLoanNumber());
			    boolean	isStopFile = userDao.isLoanInStopFile(formattedLoanNumber);
			    if(isStopFile) {
			    	accountResponse.setIsLoanInStopFile(true);
			    }else {
			    	accountResponse.setIsLoanInStopFile(false);
			    }
				Date matureDate = new Date();
				DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
				matureDate = sdf.parse(maturityDate);
				accountResponse.setIsSuccessful(true);
				accountResponse.setError("");
				accountResponse.setMaturityDate(matureDate);
				accountResponse.setFirstPaymentDate(firstPaymentDate);
				accountResponse.setLoanAccountDetails(loanDto);
				accountResponse.setPropertyInfo(propertydto);

			} else {
				accountResponse.setIsSuccessful(false);
				accountResponse.setError(messageReader.getPropertyFileMesssages().get("account.error"));
			}
		} catch (ParseException e) {
			LOGGER.error("getAccountBalInfo(): Malformed maturity date", e);
		} catch (Exception exp) {
			accountResponse.setIsSuccessful(false);
			accountResponse.setError("account.error");

			LOGGER.error("getAccountBalInfo(): ", exp);
		}
		LOGGER.info("getAccountBalInfo(): accountResponse: {}", accountResponse);
		return accountResponse;
	}
}
