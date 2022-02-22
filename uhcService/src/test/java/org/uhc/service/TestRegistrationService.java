/* 
 * ===========================================================================
 * File Name TestRegistrationService.java
 * 
 * Created on Jun 18, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: TestRegistrationService.java,v $
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
import org.uhc.controller.envelop.request.RegistrationRequest;
import org.uhc.controller.envelop.response.RegistrationResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.impl.RegistrationServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestRegistrationService {

	@Mock
	private UserDao userDao;
	
	@InjectMocks
	private RegistrationServiceImpl regService;
	
	@InjectMocks
	private RegistrationRequest regRequest;
	
	@InjectMocks
	private RegistrationResponse regResponse;
	
	private UserDto userDto;
	
	private String loanNumber;
	private String ssn;
	private String zip;
	
	private String userName;
	private String email;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		userDto = new UserDto();
	}
	
	@Test
	public void testRgisterUser() throws Exception{
		loanNumber = "1234567";
		ssn = "111111";
		zip="12121";
		userName = "nehas3";
		email = "nehas3@chetu.com";
		regRequest.setLoanNumber(loanNumber);
		regRequest.setSsn(ssn);
		regRequest.setZip(zip);
		regRequest.setUsername(userName);
		regRequest.setEmail(email);
		
		when(userDao.checkLoanAcExist(regRequest.getLoanNumber(),regRequest.getSsn(),regRequest.getZip())).thenReturn(true);
		when(userDao.getUserByUsername(userName)).thenReturn(null);
		when(userDao.registerUser(regRequest)).thenReturn(true);
		regResponse = regService.registerUser(regRequest);
		assertEquals(true, regResponse.getIsSuccessful());
		assertEquals("Registration done successfully", regResponse.getMessage());
	}
	
	@Test
	public void testFailureOfRgisterUserIfLoanDoesNotExists() throws Exception{
		loanNumber = "12345";
		ssn = "11111";
		zip="1211";
		userName = "nehas3";
		email = "nehas3@chetu.com";
		regRequest.setLoanNumber(loanNumber);
		regRequest.setSsn(ssn);
		regRequest.setZip(zip);
		regRequest.setUsername(userName);
		regRequest.setEmail(email);
		
		when(userDao.checkLoanAcExist(regRequest.getLoanNumber(),regRequest.getSsn(),regRequest.getZip())).thenReturn(false);
		regResponse = regService.registerUser(regRequest);
		assertEquals(false, regResponse.getIsSuccessful());
		assertEquals("No Loan records found on the basis of loan number or ssn or zip", regResponse.getMessage());
	}
	
	@Test
	public void testFailureOfRgisterUserIfUserDoesNotExixts() throws Exception{
		loanNumber = "1234567";
		ssn = "111111";
		zip="12121";
		userName = "nehas";
		email = "nehas3@chetu.com";
		regRequest.setLoanNumber(loanNumber);
		regRequest.setSsn(ssn);
		regRequest.setZip(zip);
		regRequest.setUsername(userName);
		regRequest.setEmail(email);
		
		when(userDao.checkLoanAcExist(regRequest.getLoanNumber(),regRequest.getSsn(),regRequest.getZip())).thenReturn(true);
		when(userDao.getUserByUsername(userName)).thenReturn(userDto);
		regResponse = regService.registerUser(regRequest);
		assertEquals(false, regResponse.getIsSuccessful());
		assertEquals("User already exist", regResponse.getMessage());
	}
	
}
