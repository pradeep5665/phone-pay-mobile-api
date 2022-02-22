/* 
 * ===========================================================================
 * File Name TestUserProfileService.java
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
 * $Log: TestUserProfileService.java,v $
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
import org.uhc.controller.envelop.request.UserProfileRequest;
import org.uhc.controller.envelop.response.UserProfileResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.OnlineStatementsPrefDto;
import org.uhc.dao.dto.PropertyDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.impl.UserProfileServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestUserProfileService {

	@Mock
	private UserDao userDao;
	
	@InjectMocks
	private UserProfileServiceImpl userProfileService;
	
	@InjectMocks
	UserProfileRequest userProfileReq;
	
	@InjectMocks
	UserProfileResponse userProfileRes;
	
	private UserDto userDto;
	private List<Long> loanAccountList;
	private PropertyDto propertyDto;
	private OnlineStatementsPrefDto onlineStatementsPrefDto;
	
	private String userName;
	private String email;
	private int userId;
	private String firstName;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		userDto = new UserDto();
		loanAccountList = new ArrayList<>();
		propertyDto = new PropertyDto();
		onlineStatementsPrefDto = new OnlineStatementsPrefDto();
	}
	
	@Test
	public void testUserProfile() throws Exception{
		userName = "nehas3";
		email = "nehas3@chetu.com";
		firstName = "neha";
		userId = 201;
		@SuppressWarnings("deprecation")
		Long loanNumber = new Long("12345");
		loanAccountList.add(loanNumber);
		userProfileReq.setUserName(userName);
		
		userDto.setUsername(userName);
		userDto.setEmail(email);
		userDto.setFirstName(firstName);
		userDto.setUserId(userId);
		
		when(userDao.getUserByUsername(userName)).thenReturn(userDto);
		when(userDao.getLoanAccountsByUserName(userName)).thenReturn(loanAccountList);
		when(userDao.getPropertyInfoByLoanNum(loanAccountList.get(0))).thenReturn(propertyDto);
		when(userDao.getStatementPrefStatus(loanAccountList.get(0))).thenReturn(onlineStatementsPrefDto);
		userProfileRes = userProfileService.getUserProfile(userProfileReq);
		assertEquals(true, userProfileRes.getIsSuccessfull());
		assertEquals("Current Status of User Profile", userProfileRes.getMessage());
	}
	
	@Test
	public void testFailureOfRgisterUserIfUserDoesNotExixts() throws Exception{
		
		userName = "nehas";
		email = "nehas4@chetu.com";
		firstName = "neha";
		userId = 202;
		userProfileReq.setUserName(userName);
		
		userDto.setEmail(email);
		userDto.setFirstName(firstName);
		userDto.setUserId(userId);
		
		when(userDao.getUserByUsername(userName)).thenReturn(userDto);
		userProfileRes = userProfileService.getUserProfile(userProfileReq);
		assertEquals(false, userProfileRes.getIsSuccessfull());
		assertEquals("user does not exist", userProfileRes.getMessage());
	}
	
	@Test
	public void testFailureOfRgisterUserIfLoanDoesNotExists() throws Exception{
		userName = "nehas";
		email = "nehas4@chetu.com";
		firstName = "nehaS3";
		userId = 203;
		userProfileReq.setUserName(userName);
		
		userDto.setUsername(userName);
		userDto.setEmail(email);
		userDto.setFirstName(firstName);
		userDto.setUserId(userId);
		
		@SuppressWarnings("deprecation")
		Long loanNumber = new Long("12345");
		loanAccountList.add(loanNumber);
		
		when(userDao.getUserByUsername(userName)).thenReturn(userDto); 
		when(userDao.getLoanAccountsByUserName(userName)).thenReturn(null);
		userProfileRes = userProfileService.getUserProfile(userProfileReq);
		assertEquals(false, userProfileRes.getIsSuccessfull());
		assertEquals("No Status available for nehas", userProfileRes.getMessage());
	}
}
