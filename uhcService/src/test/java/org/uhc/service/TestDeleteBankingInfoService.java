/* 
 * ===========================================================================
 * File Name TestBankingInfoService.java
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
 * $Log: TestBankingInfoService.java,v $
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
import org.uhc.controller.envelop.request.DeleteBankingInfoRequest;
import org.uhc.controller.envelop.response.DeleteBankingInfoResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.BankingInfoDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.impl.DeleteBankingInfoServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestDeleteBankingInfoService {

	@Mock
	private UserDao userDao;
	
	@InjectMocks
	private DeleteBankingInfoServiceImpl deleteBankingInfoService = new DeleteBankingInfoServiceImpl();
	
	private UserDto userDto;
	private BankingInfoDto bankingInfoDto;
	private int userId;
	
	@InjectMocks
	DeleteBankingInfoRequest deleteBankingInfoRequest;
	
	@InjectMocks
	DeleteBankingInfoResponse deletebankingInfoResponse;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		userDto = new UserDto();
		bankingInfoDto = new BankingInfoDto();
	}
	
	@Test
	public void testUpdateBankingInfo() throws Exception{
		userId = 123;
		deleteBankingInfoRequest.setUserId(userId);
		
		when(userDao.getUserByUserId(userId)).thenReturn(userDto);
		when(userDao.getBankingInfo(userId)).thenReturn(bankingInfoDto);
		when(userDao.deleteBankingInfo(userId)).thenReturn(true);
		
		deletebankingInfoResponse = deleteBankingInfoService.deleteBankingInfo(deleteBankingInfoRequest);
		assertEquals(true, deletebankingInfoResponse.getIsSuccessful());
		assertEquals("Banking Information deleted SuccessFully", deletebankingInfoResponse.getMessage());
	}
	
	@Test
	public void testUpdateBankingInfoFailureIfUserDoesNotExists() throws Exception{
		userId = 123;
		deleteBankingInfoRequest.setUserId(userId);
		when(userDao.getUserByUserId(userId)).thenReturn(null);
		deletebankingInfoResponse = deleteBankingInfoService.deleteBankingInfo(deleteBankingInfoRequest);
		assertEquals(false, deletebankingInfoResponse.getIsSuccessful());
		assertEquals("User Does Not Exists", deletebankingInfoResponse.getMessage());
	}
	
	@Test
	public void testUpdateBankingInfoFailureIfBankingInfoDoesNotExists() throws Exception{
		userId = 131;
		deleteBankingInfoRequest.setUserId(userId);
		when(userDao.getUserByUserId(userId)).thenReturn(userDto);
		when(userDao.getBankingInfo(userId)).thenReturn(null);
		deletebankingInfoResponse = deleteBankingInfoService.deleteBankingInfo(deleteBankingInfoRequest);
		assertEquals(false, deletebankingInfoResponse.getIsSuccessful());
		assertEquals("Banking Info Does Not Exists", deletebankingInfoResponse.getMessage());
	}
	
	@Test
	public void testUpdateBankingInfoFailureIfBankingInfoCouldNotBeUpdates() throws Exception{
		userId = 131;
		deleteBankingInfoRequest.setUserId(userId);
		when(userDao.getUserByUserId(userId)).thenReturn(userDto);
		when(userDao.getBankingInfo(userId)).thenReturn(bankingInfoDto);
		when(userDao.deleteBankingInfo(userId)).thenReturn(false);
		
		deletebankingInfoResponse = deleteBankingInfoService.deleteBankingInfo(deleteBankingInfoRequest);
		assertEquals(false, deletebankingInfoResponse.getIsSuccessful());
		assertEquals("Banking Information Could Not Be deleted", deletebankingInfoResponse.getMessage());
	}
}
