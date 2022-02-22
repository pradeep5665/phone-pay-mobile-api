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
import org.uhc.controller.envelop.request.UpdateEmailRequest;
import org.uhc.controller.envelop.response.UpdateEmailResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.impl.UpdateEmailServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestUpdateEmailService {

	@Mock
	private UserDao userDao;
	
	@InjectMocks
	private UpdateEmailServiceImpl updateBankingInfoService = new UpdateEmailServiceImpl();
	
	private UserDto userDto;
	private int userId;
	private String newEmail;
	
	@InjectMocks
	private UpdateEmailRequest updateEmailRequest;
	
	@InjectMocks
	private UpdateEmailResponse updateEmailResponse;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		userDto = new UserDto();
	}
	
	@Test
	public void testUpdateEmail() throws Exception{
		newEmail = "nehas3@chetu.com";
		userId = 123;
		updateEmailRequest.setUserId(userId);
		updateEmailRequest.setNewEmail(newEmail);
		
		when(userDao.getUserByUserId(userId)).thenReturn(userDto);
		when(userDao.updateEmail(updateEmailRequest)).thenReturn(true);
		
		updateEmailResponse = updateBankingInfoService.updateEmail(updateEmailRequest);
		assertEquals(true, updateEmailResponse.getIsSuccessful());
		assertEquals("User's email updated successfully", updateEmailResponse.getMessage());
	}
	
	@Test
	public void testUpdateEmailFailureIfUserDoesNotExists() throws Exception{
		newEmail = "nehas3@chetu.com";
		userId = 122;
		updateEmailRequest.setUserId(userId);
		updateEmailRequest.setNewEmail(newEmail);
		when(userDao.getUserByUserId(userId)).thenReturn(null);
		updateEmailResponse = updateBankingInfoService.updateEmail(updateEmailRequest);
		assertEquals(false, updateEmailResponse.getIsSuccessful());
		assertEquals("User does not exist", updateEmailResponse.getMessage());
	}
	
	@Test
	public void testUpdateEmailFailureIfEmailCouldNotBeUpdates() throws Exception{
		newEmail = "nehas3@chetu.com";
		userId = 122;
		updateEmailRequest.setUserId(userId);
		updateEmailRequest.setNewEmail(newEmail);
		
		when(userDao.getUserByUserId(userId)).thenReturn(userDto);
		when(userDao.updateEmail(updateEmailRequest)).thenReturn(false);
		
		updateEmailResponse = updateBankingInfoService.updateEmail(updateEmailRequest);
		assertEquals(false, updateEmailResponse.getIsSuccessful());
		assertEquals("User's email could not be updated, userId or password is wrong", updateEmailResponse.getMessage());
	}
}
