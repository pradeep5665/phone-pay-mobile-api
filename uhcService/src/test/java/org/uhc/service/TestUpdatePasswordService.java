/* 
 * ===========================================================================
 * File Name TestUpdateEmailService.java
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
 * $Log: TestUpdateEmailService.java,v $
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
import org.uhc.controller.envelop.request.UpdatePasswordRequest;
import org.uhc.controller.envelop.response.UpdatePasswordResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.impl.UpdatePasswordServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestUpdatePasswordService {

	@Mock
	private UserDao userDao;
	
	@InjectMocks
	private UpdatePasswordServiceImpl updateBankingInfoService = new UpdatePasswordServiceImpl();
	
	private UserDto userDto;
	private int userId;
	private String oldPassword;
	private String newPassword;
	
	@InjectMocks
	UpdatePasswordRequest updatePasswordRequest;
	
	@InjectMocks
	UpdatePasswordResponse updatePasswordResponse;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		userDto = new UserDto();
	}
	
	@Test
	public void testUpdatePassword() throws Exception{
		oldPassword = "neha@123";
		newPassword = "neha@12345";
		userId = 123;
		updatePasswordRequest.setUserId(userId);
		updatePasswordRequest.setOldPassword(oldPassword);
		updatePasswordRequest.setNewPassword(newPassword);
		
		when(userDao.getUserByUserId(userId)).thenReturn(userDto);
		when(userDao.updatePassword(updatePasswordRequest)).thenReturn(true);
		
		updatePasswordResponse = updateBankingInfoService.updatePassword(updatePasswordRequest);
		assertEquals(true, updatePasswordResponse.getIsSuccessful());
		assertEquals("Password updated successfully", updatePasswordResponse.getMessage());
	}
	
	@Test
	public void testUpdatePasswordFailureIfUserDoesNotExists() throws Exception{
		oldPassword = "neha@123";
		newPassword = "neha@12345";
		userId = 122;
		updatePasswordRequest.setUserId(userId);
		updatePasswordRequest.setOldPassword(oldPassword);
		updatePasswordRequest.setNewPassword(newPassword);
		
		when(userDao.getUserByUserId(userId)).thenReturn(null);
		updatePasswordResponse = updateBankingInfoService.updatePassword(updatePasswordRequest);
		assertEquals(false, updatePasswordResponse.getIsSuccessful());
		assertEquals("User Does Not Exists", updatePasswordResponse.getMessage());
	}
	
	@Test
	public void testUpdatePasswordFailureIfPasswordCouldNotBeUpdates() throws Exception{
		oldPassword = "neha@12";
		newPassword = "neha@12345";
		userId = 123;
		
		updatePasswordRequest.setUserId(userId);
		updatePasswordRequest.setOldPassword(oldPassword);
		updatePasswordRequest.setNewPassword(newPassword);
		
		when(userDao.getUserByUserId(userId)).thenReturn(userDto);
		when(userDao.updatePassword(updatePasswordRequest)).thenReturn(false);
		
		updatePasswordResponse = updateBankingInfoService.updatePassword(updatePasswordRequest);
		assertEquals(false, updatePasswordResponse.getIsSuccessful());
		assertEquals("Password could not be updated", updatePasswordResponse.getMessage());
	}
	
	@Test
	public void testUpdatePasswordFailureIfNewPasswordIsNullOrBlank() throws Exception{
		oldPassword = "neha@123";
		userId = 123;
		
		updatePasswordRequest.setUserId(userId);
		updatePasswordRequest.setOldPassword(oldPassword);
		
		when(userDao.getUserByUserId(userId)).thenReturn(userDto);
		when(userDao.updatePassword(updatePasswordRequest)).thenReturn(true);
		
		updatePasswordResponse = updateBankingInfoService.updatePassword(updatePasswordRequest);
		assertEquals(false, updatePasswordResponse.getIsSuccessful());
		assertEquals("Enter a valid password", updatePasswordResponse.getMessage());
	}
}
