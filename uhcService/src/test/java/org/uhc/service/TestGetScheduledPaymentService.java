/* 
 * ===========================================================================
 * File Name TestGetScheduledPaymentService.java
 * 
 * Created on Jul 14, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: TestGetScheduledPaymentService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.uhc.controller.envelop.request.GetScheduledPaymentRequest;
import org.uhc.controller.envelop.response.GetScheduledPaymentResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.GetScheduledPaymentDto;
import org.uhc.dao.dto.LoanDto;
import org.uhc.service.impl.GetScheduledPaymentServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestGetScheduledPaymentService {

	@Mock
	private UserDao userDao;
	
	@InjectMocks
	GetScheduledPaymentServiceImpl scheduledPaymentService = new GetScheduledPaymentServiceImpl();
	
	private long loanNumber;
	private LoanDto loan;
	private List<GetScheduledPaymentDto> scheduledPayments;
	
	@InjectMocks
	GetScheduledPaymentRequest scheduledPaymentRequest;
	
	@InjectMocks
	GetScheduledPaymentResponse scheduledPaymentRes;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		loan = new LoanDto();
		scheduledPayments = new ArrayList<>();
	}
	
	@Test
	public void testgetScheduledPayments() throws Exception{
		loanNumber = 12345;
		scheduledPaymentRequest.setLoanNumber(loanNumber);
		when(userDao.getLoanAccountDetailsByLoanNum(loanNumber)).thenReturn(loan);
		when(userDao.getScheduledPaymentList(loanNumber)).thenReturn(scheduledPayments);

		scheduledPaymentRes = scheduledPaymentService.getScheduledPayments(scheduledPaymentRequest);
		assertEquals(true,scheduledPaymentRes.getIsSuccessful());
		assertEquals("Successfully Done", scheduledPaymentRes.getMessage());
		assertEquals(scheduledPayments, scheduledPaymentRes.getScheduledPayments());
	}
	
	@Test
	public void testgetScheduledPaymentsFailureIfNoPaymentScheduled() throws Exception{
		loanNumber = 12345;
		scheduledPaymentRequest.setLoanNumber(loanNumber);
		when(userDao.getLoanAccountDetailsByLoanNum(loanNumber)).thenReturn(loan);
		when(userDao.getScheduledPaymentList(loanNumber)).thenReturn(null);

		scheduledPaymentRes = scheduledPaymentService.getScheduledPayments(scheduledPaymentRequest);
		assertEquals(false, scheduledPaymentRes.getIsSuccessful());
		assertEquals("Schduled Payments are not available", scheduledPaymentRes.getMessage());
	}
	
	@Test
	public void testgetScheduledPaymentsFailureIfLoanDoesNotExist() throws Exception{
		loanNumber = 123;
		scheduledPaymentRequest.setLoanNumber(loanNumber);
		when(userDao.getLoanAccountDetailsByLoanNum(loanNumber)).thenReturn(null);

		scheduledPaymentRes = scheduledPaymentService.getScheduledPayments(scheduledPaymentRequest);
		assertEquals(false, scheduledPaymentRes.getIsSuccessful());
		assertEquals("Loan Number does not exist", scheduledPaymentRes.getMessage());
	}
	
}
