/* 
 * ===========================================================================
 * File Name TestAccountHistoryService.java
 * 
 * Created on Jun 15, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: TestAccountHistoryService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.uhc.controller.envelop.request.AccountHistoryRequest;
import org.uhc.controller.envelop.response.AccountHistoryResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.TransactionDto;
import org.uhc.service.impl.AccountHistoryServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestAccountHistoryService {

	@Mock
	private UserDao userDao;
	
	@InjectMocks
	private AccountHistoryServiceImpl accountHistoryService = new AccountHistoryServiceImpl();
	
	private List<TransactionDto>  transactionDtoList;
	private long loanNumber;
	
	
	@InjectMocks
	AccountHistoryRequest accountHistoryRequest;
	
	@InjectMocks
	AccountHistoryResponse accountHistoryResponse;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);	
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setEscrow(new BigDecimal("1500"));
		transactionDto.setInterest(new BigDecimal("2000"));
		transactionDtoList = new ArrayList<TransactionDto>();
		transactionDtoList.add(transactionDto);
				
	}
	
	@Test
	public void testGetAccountHistoryByLoanNum() throws Exception{
		loanNumber = 1234567;
		accountHistoryRequest.setLoanNumber(loanNumber);
		when(userDao.getAccountHistoryByLoanNum(loanNumber)).thenReturn(transactionDtoList);
		accountHistoryResponse = accountHistoryService.getAccountHistoryByLoanNumber(accountHistoryRequest);
		assertEquals(true, accountHistoryResponse.getIsSuccessful());
		assertEquals("", accountHistoryResponse.getError());
		assertEquals(transactionDtoList, accountHistoryResponse.getTransactionList());
	}
	
	@Test
	public void testFailureOfGetAccountHistoryByLoanNum() throws Exception{
		loanNumber = 1234;
		accountHistoryRequest.setLoanNumber(loanNumber);
		when(userDao.getLoanAccountDetailsByLoanNum(loanNumber)).thenReturn(null);
		accountHistoryResponse = accountHistoryService.getAccountHistoryByLoanNumber(accountHistoryRequest);
		assertEquals(false, accountHistoryResponse.getIsSuccessful());
		assertEquals("No Transaction Found", accountHistoryResponse.getError());
	}
}
