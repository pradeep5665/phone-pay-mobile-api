/* 
 * ===========================================================================
 * File Name TestUpdateOnlineStatementPrefService.java
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
 * $Log: TestUpdateOnlineStatementPrefService.java,v $
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
import org.uhc.controller.envelop.request.UpdateOnlineStatementPrefRequest;
import org.uhc.controller.envelop.response.UpdateOnlineStatementPrefResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.impl.UpdateOnlineStatementPrefServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestUpdateOnlineStatementPrefService {

	@Mock
	private UserDao userDao;
	
	@InjectMocks
	private UpdateOnlineStatementPrefServiceImpl updateBankingInfoService = new UpdateOnlineStatementPrefServiceImpl();
	
	private UserDto userDto;
	private int userId;
	private Boolean isOnlineStatementEnabled;
	private List<Long> loanNumberList;
	private String userName;
	
	@InjectMocks
	private UpdateOnlineStatementPrefRequest stmtPrefReq;
	
	@InjectMocks
	private UpdateOnlineStatementPrefResponse stmtPrefRes;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		userDto = new UserDto();
		loanNumberList = new ArrayList<>();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateStatementPref() throws Exception{
		isOnlineStatementEnabled = true;
		userId = 123;
		userName = "nehas3";
		loanNumberList.add(new Long("12345"));
		loanNumberList.add(new Long("1234"));
		userDto.setUsername(userName);
		stmtPrefReq.setUserId(userId);
		stmtPrefReq.setIsOnlineStatementEnabled(isOnlineStatementEnabled);
		
		when(userDao.getUserByUserId(userId)).thenReturn(userDto);
		when(userDao.getLoanAccountsByUserName(userDto.getUsername())).thenReturn(loanNumberList);
		when(userDao.updateOnlineStatementPref(stmtPrefReq.getIsOnlineStatementEnabled(),loanNumberList)).thenReturn(true);
		
		stmtPrefRes = updateBankingInfoService.updateOnlineStatementPref(stmtPrefReq);
		assertEquals(true, stmtPrefRes.getIsSuccessful());
		assertEquals("Online Statement Pref Updated successfully", stmtPrefRes.getMessage());
	}
	
	@Test
	public void testUpdateOnlineStatementFailureIfUserDoesNotExists() throws Exception{
		isOnlineStatementEnabled = true;
		userId = 123;
		stmtPrefReq.setUserId(userId);
		stmtPrefReq.setIsOnlineStatementEnabled(isOnlineStatementEnabled);
		
		when(userDao.getUserByUserId(userId)).thenReturn(null);

		stmtPrefRes = updateBankingInfoService.updateOnlineStatementPref(stmtPrefReq);
		assertEquals(false, stmtPrefRes.getIsSuccessful());
		assertEquals("User Does Not Exists, userId", stmtPrefRes.getMessage());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateUpdateOnlineStatementFailureIfOnlineStatementDoesNotExists() throws Exception{
		isOnlineStatementEnabled = true;
		userId = 123;
		userName = "nehas3";
		loanNumberList.add(new Long("12345"));
		loanNumberList.add(new Long("1234"));
		userDto.setUsername(userName);
		stmtPrefReq.setUserId(userId);
		stmtPrefReq.setIsOnlineStatementEnabled(isOnlineStatementEnabled);
		
		when(userDao.getUserByUserId(userId)).thenReturn(userDto);
		when(userDao.getLoanAccountsByUserName(userDto.getUsername())).thenReturn(loanNumberList);
		when(userDao.updateOnlineStatementPref(stmtPrefReq.getIsOnlineStatementEnabled(),loanNumberList)).thenReturn(false);
		
		stmtPrefRes = updateBankingInfoService.updateOnlineStatementPref(stmtPrefReq);
		assertEquals(false, stmtPrefRes.getIsSuccessful());
		assertEquals("Online Statement does not exists for this user", stmtPrefRes.getMessage());
	}
}
