/* 
 * ===========================================================================
 * File Name EscrowServiceImpl.java
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
 * $Log: EscrowServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.EscrowRequest;
import org.uhc.controller.envelop.request.EscrowRequestByLoanNumber;
import org.uhc.controller.envelop.response.EscrowResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.EscrowDto;
import org.uhc.dao.dto.LoanDto;
import org.uhc.service.EscrowService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date May 25, 2018
 * @description Implementing EscrowService to get the EscrowResponse
 */
@Service
public class EscrowServiceImpl implements EscrowService {

	private static final Logger LOGGER = LogManager.getLogger(EscrowServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessageReader messageReader;

	/**
	 * 
	 * @author nehas3
	 * @date May 25, 2018
	 * @return EscrowResponse 
	 * @param escrowRequest
	 * @return escrow info for user
	 * Description : this is getEscrowInfoByLoanNumber to get escrow information by loan number.
	 */

	@Override
	public EscrowResponse getEscrowInfoByLoanNumber(EscrowRequestByLoanNumber escrowRequest) {
		
			LOGGER.info("getEscrowInfo(): loanNumber: {}", escrowRequest.getLoanNumber());

			EscrowResponse escrowResponse = new EscrowResponse();

			try{
				LoanDto loanInfo = userDao.getLoanAccountDetailsByLoanNum(escrowRequest.getLoanNumber());
				if(loanInfo!=null && !("0.00").equals(loanInfo.getUnpaidPrincipalBalance())) {
					List<EscrowDto> escrowDtoList = userDao.getEscrowInformationByLoanNum(escrowRequest.getLoanNumber());
					if (!escrowDtoList.isEmpty()) {
						escrowResponse.setIsSuccessful(true);
						escrowResponse.setError("");
						escrowResponse.setEscrowInfoDetails(escrowDtoList);
					} else {
						escrowResponse.setIsSuccessful(false);
						escrowResponse.setError(messageReader.getPropertyFileMesssages().get("escrowNotFound.error"));
						LOGGER.info("getEscrowInfo(): No active loan found");
					}
				} else {
					escrowResponse.setIsSuccessful(false);
					escrowResponse.setError(messageReader.getPropertyFileMesssages().get("loanNotFound.error"));
					LOGGER.info("getEscrowInfo(): No loan records available");
				}
			} catch(Exception exp) {
				escrowResponse.setIsSuccessful(false);
				escrowResponse.setError(exp.getMessage());
				LOGGER.error("getEscrowInfo():" , exp);
			}
			LOGGER.info("getEscrowInfo(): escrowResponse: {}", escrowResponse);
			return escrowResponse;
		}

   /**
	 * Phase-1
	 * @author nehas3
	 * @date May 25, 2018
	 * @return EscrowResponse 
	 * @param escrowRequest
	 * @return escrow info for user
	 * Description : this is getEscrowInfo to get escrow information for user.
	 */
	@Override
	public EscrowResponse getEscrowInfo(EscrowRequest escrowRequest) {
		LOGGER.info("getEscrowInfo(): userName: {}", escrowRequest.getUserName());

		EscrowResponse escrowResponse = new EscrowResponse();

		try{
			List<Long> loanAccountList = userDao.getLoanAccountsByUserName(escrowRequest.getUserName());
			List<Long> activeLoans = new ArrayList<Long>();
			if ( loanAccountList!= null && !loanAccountList.isEmpty() ) {
				for(Long loanNum : loanAccountList) {
					LoanDto loanInfo = userDao.getLoanAccountDetailsByLoanNum(loanNum);
					if (loanInfo != null && !("0.00").equals(loanInfo.getUnpaidPrincipalBalance())) {
						activeLoans.add(loanInfo.getLoanNumber());
					}
				}
			}
			if( activeLoans!=null && !activeLoans.isEmpty()) {
				List<EscrowDto> escrowDtoList = userDao.getEscrowInformationByLoanNum(activeLoans);
				if (!escrowDtoList.isEmpty()) {
					escrowResponse.setIsSuccessful(true);
					escrowResponse.setError("");
					escrowResponse.setEscrowInfoDetails(escrowDtoList);
				} else {
					escrowResponse.setIsSuccessful(false);
					escrowResponse.setError(messageReader.getPropertyFileMesssages().get("escrowNotFound.error"));
					LOGGER.info("getEscrowInfo(): No active loan found");
				}


			} else {
				escrowResponse.setIsSuccessful(false);
				escrowResponse.setError(messageReader.getPropertyFileMesssages().get("loanNotFound.error"));
				LOGGER.info("getEscrowInfo(): No loan records available");
			}
		} catch(Exception exp) {
			escrowResponse.setIsSuccessful(false);
			escrowResponse.setError(exp.getMessage());
			LOGGER.error("getEscrowInfo():" , exp);
		}
		LOGGER.info("getEscrowInfo(): escrowResponse: {}", escrowResponse);
		return escrowResponse;
	}
}
