/* 
 * ===========================================================================
 * File Name TestUpdateUserNameService.java
 * 
 * Created on Jun 20, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: TestUpdateUserNameService.java,v $
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
import org.uhc.controller.envelop.request.UpdateUserNameRequest;
import org.uhc.controller.envelop.response.UpdateUserNameResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.impl.UpdateUserNameServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestUpdateUserNameService {

	@Mock
	private UserDao userDao;
	
	@InjectMocks
	private UpdateUserNameServiceImpl updateBankingInfoService = new UpdateUserNameServiceImpl();
	
	private UserDto userDto;
	private String password;
	private String oldUserName;
	private String newUserName;
	
	@InjectMocks
	UpdateUserNameRequest updateUserNameRequest;
	
	@InjectMocks
	UpdateUserNameResponse updateUserNameResponse;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		userDto = new UserDto();
	}
	
	@Test
	public void testUpdateUserName() throws Exception{
		password = "neha@123";
		oldUserName = "nehas3";
		newUserName = "neha123";
		updateUserNameRequest.setPassword(password);
		updateUserNameRequest.setOldUserName(oldUserName);
		updateUserNameRequest.setNewUserName(newUserName);
		
		when(userDao.getUserByUsername(oldUserName)).thenReturn(userDto);
		when(userDao.updateUserName(updateUserNameRequest)).thenReturn(true);
		
		updateUserNameResponse = updateBankingInfoService.updateUserName(updateUserNameRequest);
		assertEquals(true, updateUserNameResponse.getIsSuccessful());
		assertEquals("User Name Updated SuccessFully", updateUserNameResponse.getMessage());
	}
	
	@Test
	public void testUpdateUserNameFailureIfUserDoesNotExists() throws Exception{
		password = "neha@123";
		oldUserName = "nehas3";
		newUserName = "neha123";
		updateUserNameRequest.setPassword(password);
		updateUserNameRequest.setOldUserName(oldUserName);
		updateUserNameRequest.setNewUserName(newUserName);
		
		when(userDao.getUserByUsername(oldUserName)).thenReturn(null);
		updateUserNameResponse = updateBankingInfoService.updateUserName(updateUserNameRequest);
		assertEquals(false, updateUserNameResponse.getIsSuccessful());
		assertEquals("User name does not exist ", updateUserNameResponse.getMessage());
	}
	
	@Test
	public void testUpdateUserNameFailureIfUserNameCouldNotBeUpdates() throws Exception{
		password = "neha@123";
		oldUserName = "nehas3";
		newUserName = "neha123";
		updateUserNameRequest.setPassword(password);
		updateUserNameRequest.setOldUserName(oldUserName);
		updateUserNameRequest.setNewUserName(newUserName);
		userDto.setUsername(oldUserName);
		
		when(userDao.getUserByUsername(oldUserName)).thenReturn(userDto);
		when(userDao.updateUserName(updateUserNameRequest)).thenReturn(false);
		
		updateUserNameResponse = updateBankingInfoService.updateUserName(updateUserNameRequest);
		assertEquals(false, updateUserNameResponse.getIsSuccessful());
		assertEquals("This User Name Can Not Be Updated", updateUserNameResponse.getMessage());
	}
}
