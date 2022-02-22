/* 
 * ===========================================================================
 * File Name TestLoginService.java
 * 
 * Created on Jun 13, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: TestLoginService.java,v $
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
import org.uhc.controller.envelop.request.LoginRequest;
import org.uhc.controller.envelop.response.LoginResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.impl.LoginServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestLoginService {

	@Mock
	private UserDao userDao;
	
	@InjectMocks
	private LoginServiceImpl loginService = new LoginServiceImpl();
	
	private UserDto user;
	private String userName;
	private String password;
	private List<Long> loanAccountList;
	private int userId;
	
	@InjectMocks
	LoginRequest loginRequest;
	
	@InjectMocks
	LoginResponse loginResponse;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		user = new UserDto();
		userId=123;
		userName = "neha";
		loanAccountList = new ArrayList<>();
	}
	
	@Test
	public void testValidateUser() throws Exception{
		password = "Neha@12345";
		user.setUsername(userName);
		user.setUserId(userId);
		loginRequest.setUserName(userName);
		loginRequest.setPassword(password);
		when(userDao.validateUser(userName,"Neha@12345")).thenReturn(user);

		loginResponse = loginService.login(loginRequest);
		assertEquals(true,loginResponse.getIsSuccessful());
		assertEquals("",loginResponse.getError());
		assertEquals(loanAccountList, loginResponse.getLoanAccountList());
		assertEquals(userId,loginResponse.getUserId());	
	}
	
	@Test
	public void testValidateUserFailed() throws Exception{
		
		password = "WrongPass12";
		user.setUsername(userName);
		loginRequest.setUserName(userName);
		loginRequest.setPassword(password);
		when(userDao.validateUser(userName,password)).thenReturn(null);

		loginResponse = loginService.login(loginRequest);
		assertEquals(false,loginResponse.getIsSuccessful());
		assertEquals("User or password not found", loginResponse.getError());
	}
}
