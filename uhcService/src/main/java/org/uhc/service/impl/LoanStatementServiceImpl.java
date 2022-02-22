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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.LoanStatementsRequest;
import org.uhc.controller.envelop.response.LoanStatementsResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.LoanStatementDto;
import org.uhc.service.LoanStatementService;
import org.uhc.util.Constants;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date May 25, 2018
 * @description Implementing LoanStatementService to get the
 *              LoanStatementsResponse
 */
@Service
public class LoanStatementServiceImpl implements LoanStatementService {

	private static final Logger LOGGER = LogManager.getLogger(LoanStatementServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	@Value("${server.download.Path}")
	private String downloadAction;

	@Value("${server.download.statements}")
	private String serverUrl;

	@Value("${uhc.back_path_URL}")
	private String backPath;

	/**
	 * 
	 * @author nehas3
	 * @date June 7, 2018
	 * @return LoanStatementsResponse
	 * @param loanStatementsRequest
	 * @return loan statements for user. Description : this is getLoanStatement
	 *         method to get loan statements for user on basis of requested
	 *         parameters.
	 */
	@Override
	public LoanStatementsResponse getLoanStatement(LoanStatementsRequest loanStatementsRequest) {
		LOGGER.info("getLoanStatement(): request: {}", loanStatementsRequest);

		LoanStatementsResponse loanStatementsResponse = new LoanStatementsResponse();
		List<LoanStatementDto> updatedLoanDto = new ArrayList<>();
		try {
			List<LoanStatementDto> loanStatementDtoList = userDao.getLoanStatementByLoanNumber(loanStatementsRequest);

			if (loanStatementDtoList != null) {
				loanStatementsResponse.setIsSuccessful(true);
				loanStatementsResponse.setError(" ");
				String backendPath = serverUrl.concat(downloadAction).concat(this.backPath);
				loanStatementsResponse.setBackPath(backendPath);

				for (LoanStatementDto loanStatementDto : loanStatementDtoList) {
					LoanStatementDto loanStatement = new LoanStatementDto();
					String frontPath = serverUrl.concat(downloadAction)
							.concat(loanStatementDto.getFrontPath());
					loanStatement.setFrontPath(frontPath);
					String stmtDate = loanStatementDto.getStatementDate();
					DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
					Date statementDate = sdf.parse(stmtDate);
					loanStatement.setStatementDate(statementDate);
					updatedLoanDto.add(loanStatement);
				}
				loanStatementsResponse.setLoanStatementList(updatedLoanDto);

			} else {
				loanStatementsResponse.setIsSuccessful(false);
				loanStatementsResponse.setError(messageReader.getPropertyFileMesssages().get("loanStatement.error"));
			}
		} catch (Exception exp) {
			loanStatementsResponse.setIsSuccessful(false);
			loanStatementsResponse.setError(messageReader.getPropertyFileMesssages().get("loanStatement.error"));
			LOGGER.error("getLoanStatement(): Finding loan statement failed:", exp);
		}
		LOGGER.info("getLoanStatement(): {}", loanStatementsResponse);
		return loanStatementsResponse;
	}
}
