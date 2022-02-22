/* 
 * ===========================================================================
 * File Name TestAccountService.java
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
 * $Log: TestAccountService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.uhc.controller.envelop.request.AccountRequest;
import org.uhc.controller.envelop.response.AccountResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.LoanDto;
import org.uhc.dao.dto.PropertyDto;
import org.uhc.service.impl.AccountServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:messages.properties")
public class TestAccountService {

	@Mock
	private UserDao userDao;

	@InjectMocks
	private AccountServiceImpl accountService = new AccountServiceImpl();

	private LoanDto loanDto;
	private PropertyDto propertyDto;
	private long loanNumber;

	@InjectMocks
	AccountRequest accountRequest;

	@InjectMocks
	AccountResponse accountResponse;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		loanDto = new LoanDto();
		propertyDto = new PropertyDto();

	}

	@Test
	public void testGetBalanceInfo() throws Exception {
		loanNumber = 1234567;
		String maturityDate = "05/05/2023";
		String firstPaymentDate = "05/05/2015";
		accountRequest.setLoanNumber(loanNumber);
		when(userDao.getLoanAccountDetailsByLoanNum(loanNumber)).thenReturn(loanDto);
		when(userDao.getPropertyInfoByLoanNum(loanNumber)).thenReturn(propertyDto);
		when(userDao.getMaturityPaymentDate(loanNumber)).thenReturn(maturityDate);
		when(userDao.getFirstPaymentDate(loanNumber)).thenReturn(firstPaymentDate);

		accountResponse = accountService.getAccountBalInfo(accountRequest);
		assertEquals(true, accountResponse.getIsSuccessful());
		assertEquals("", accountResponse.getError());
		assertEquals(loanDto, accountResponse.getLoanAccountDetails());
		assertEquals(propertyDto, accountResponse.getPropertyInfo());
		assertEquals(maturityDate, accountResponse.getMaturityDate());
		assertEquals(firstPaymentDate, accountResponse.getFirstPaymentDate());
	}

	@Test
	public void testGetBalanceInfoFailure() throws Exception {
		loanNumber = 1234;
		accountRequest.setLoanNumber(loanNumber);
		when(userDao.getLoanAccountDetailsByLoanNum(loanNumber)).thenReturn(null);
		accountResponse = accountService.getAccountBalInfo(accountRequest);
		assertEquals(false, accountResponse.getIsSuccessful());
		assertEquals("No Account Information Found", accountResponse.getError());
	}
}
