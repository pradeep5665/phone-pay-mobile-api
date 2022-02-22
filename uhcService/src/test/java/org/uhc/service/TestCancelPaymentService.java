/* 
 * ===========================================================================
 * File Name TestCancelPaymentService.java
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
 * $Log: TestCancelPaymentService.java,v $
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.uhc.controller.envelop.request.CancelPaymentRequest;
import org.uhc.controller.envelop.response.CancelPaymentResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.ScheduledPaymentDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.impl.CancelPaymentServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestCancelPaymentService {

	@Mock
	private UserDao userDao;
	
	@InjectMocks
	CancelPaymentServiceImpl cancelPaymentService = new CancelPaymentServiceImpl();
	
	@InjectMocks
	CancelPaymentRequest cancelPaymentReq;
	
	@InjectMocks
	CancelPaymentResponse cancelPaymentRes;
	
	private ScheduledPaymentDto scheduledPayment;
	private boolean isSuccessful = false;
	private UserDto user;
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		user = new UserDto();
		scheduledPayment = new ScheduledPaymentDto();
	}
	
	@Test
	public void testgetScheduledPayments() throws Exception{
		int userId = 123;
		int paymentIds = 121;
		user.setUserId(userId);
		scheduledPayment.setUserId(123);
		scheduledPayment.setPaymentId(paymentIds);
		cancelPaymentReq.setUserId(userId);
		final String[] paymentIdOfCheckBox =  {"110","111"};
		cancelPaymentReq.setPaymentIdOfCheckBox(paymentIdOfCheckBox);
		when(userDao.getUserByUserId(userId)).thenReturn(user);
		for(String paymentId : paymentIdOfCheckBox)
		{
		when(userDao.getScheduledPaymentByPaymentId(paymentId)).thenReturn(scheduledPayment);
		when(userDao.hasPaymentMoved(scheduledPayment.getPaymentId())).thenReturn(true);
		when(userDao.cancelPaymentInPaymentTableByPaymentId(Integer.parseInt(paymentId))).thenReturn(true);
		when(userDao.cancelPaymentByPaymentId(Integer.parseInt(paymentId),user.getUserId())).thenReturn(true);
		cancelPaymentRes = cancelPaymentService.cancelPayment(cancelPaymentReq);
		isSuccessful=true;
		}
		if(isSuccessful){
		assertEquals(true,cancelPaymentRes.getIsSuccessful());
		assertEquals("Payment Cancelled Successfully", cancelPaymentRes.getMessage());
		}
	}
}
