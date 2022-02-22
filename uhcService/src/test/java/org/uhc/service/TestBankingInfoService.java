/* 
 * ===========================================================================
 * File Name TestDeleteBankingInfoService.java
 * 
 * Created on Jun 19, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: TestDeleteBankingInfoService.java,v $
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
import org.uhc.controller.envelop.request.BankingInfoRequest;
import org.uhc.controller.envelop.response.BankingInfoResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.BankingInfoDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.impl.BankingInfoServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestBankingInfoService {

	@Mock
	private UserDao userDao;
	
	@InjectMocks
	private BankingInfoServiceImpl bankingInfoService = new BankingInfoServiceImpl();
	
	private UserDto userDto;
	private BankingInfoDto bankingInfoDto;
	private int userId;
	
	@InjectMocks
	BankingInfoRequest bankingInfoRequest;
	
	@InjectMocks
	BankingInfoResponse bankingInfoResponse;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		userDto = new UserDto();
		bankingInfoDto = new BankingInfoDto();
	}
	
	@Test
	public void testBankingInfo() throws Exception{
		userId = 123;
		bankingInfoRequest.setUserId(userId);
		
		when(userDao.getUserByUserId(userId)).thenReturn(userDto);
		when(userDao.getBankingInfo(userId)).thenReturn(bankingInfoDto);
		
		bankingInfoResponse = bankingInfoService.getBankingInfo(bankingInfoRequest);
		
		assertEquals(bankingInfoDto, bankingInfoResponse.getBankingInfo());
		assertEquals(true, bankingInfoResponse.getIsSuccessful());
		assertEquals("", bankingInfoResponse.getError());
	}
	
	@Test
	public void testGetBankingInfoFailureIfUserDoesNotExists() throws Exception{
		userId = 1234;
		bankingInfoRequest.setUserId(userId);
		when(userDao.getUserByUserId(userId)).thenReturn(null);
		bankingInfoResponse = bankingInfoService.getBankingInfo(bankingInfoRequest);
		assertEquals(false, bankingInfoResponse.getIsSuccessful());
		assertEquals("User Does Not Exist", bankingInfoResponse.getError());
	}
	
	@Test
	public void testGetBankingInfoFailureIfBankingInfoDoesNotExists() throws Exception{
		userId = 131;
		bankingInfoRequest.setUserId(userId);
		when(userDao.getUserByUserId(userId)).thenReturn(userDto);
		bankingInfoResponse = bankingInfoService.getBankingInfo(bankingInfoRequest);
		when(userDao.getBankingInfo(userId)).thenReturn(bankingInfoDto);
		assertEquals(false, bankingInfoResponse.getIsSuccessful());
		assertEquals("Banking Information Does Not Exist", bankingInfoResponse.getError());
	}
}
