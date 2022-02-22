/* 
 * ===========================================================================
 * File Name LoginServiceImpl.java
 * 
 * Created on May 25, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: LoginServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.LoginRequest;
import org.uhc.controller.envelop.response.LoginResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.LoanDto;
import org.uhc.dao.dto.PushNotificationFlagDto;
import org.uhc.dao.dto.TokenDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.exception.LockedUserLoginException;
import org.uhc.service.LoginService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date May 25, 2018
 * @description Implementing LoginService to get the LoginResponse
 */
@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger LOGGER = LogManager.getLogger(LoginServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	/**
	 * 
	 * @author nehas3
	 * @date May 25, 2018
	 * @return LoginResponse
	 * @param loginRequest
	 * @return success or failure on login for user.
	 * @Description : this is login method to validate the user and authenticate.
	 */
	@Override
	public LoginResponse login(LoginRequest loginRequest) throws LockedUserLoginException {
		LoginResponse loginResponse = new LoginResponse();
		boolean isTokenSaved = false;
		try {
			// validating user on basis of user name and password.
			UserDto userDto = userDao.validateUser(loginRequest.getUserName(), loginRequest.getPassword());
			List<LoanDto> loanDtoList;

			if (userDto != null) {
				PushNotificationFlagDto notificationFlag = userDao.getNOtificationFlag(userDto.getUserId());
				if(notificationFlag == null){
					PushNotificationFlagDto pnfDto = new PushNotificationFlagDto();
					pnfDto.setUserId(userDto.getUserId());
					pnfDto.setFlagNameId(1);
					pnfDto.setFlagValue(1);
					boolean isPushNotificationFlagUpdated = userDao.insertPushNotificationFlag(pnfDto);
					if(isPushNotificationFlagUpdated) {
						LOGGER.info("PushNotification flag inserted successfully");
					}else {
						LOGGER.info("PushNotification flag could not be inserted");
					}
				}
				
				LOGGER.info("login(): Found user: {}", userDto);
				// throwing exception, when user entered the correct login credential, but
				// already been locked by application.
				if (userDto.isLocked()) {
					LOGGER.info("login(): userName {} is locked.", loginRequest.getUserName());
					throw new LockedUserLoginException();
				}

				// if user enters right credential within 5 times attempts.
				if (userDto.getLoginFails() > 0) {
					userDto.setLoginFails(0);
					userDao.updateUser(userDto);
				}

				// getting FCM token to send the push notifications to specific users
				List<TokenDto> tokenList = userDao.getFCMToken(userDto.getUserId());
				if (!tokenList.isEmpty()) {
					// checking if token is registered
					boolean isTokenAvailable = userDao.isTokenRegistered(userDto.getUserId(), loginRequest.getToken());
					if (isTokenAvailable) {
						LOGGER.info("login(): Token is already registered for userId: {}", userDto.getUserId());
					} else {
						// saving FCM token to the database for sending push notifications
						isTokenSaved = userDao.saveUserInFCMTokenTable(userDto.getUserId(), loginRequest.getToken());
					}
				} else {
					isTokenSaved = userDao.saveUserInFCMTokenTable(userDto.getUserId(), loginRequest.getToken());
				}
				if (isTokenSaved) {
					LOGGER.info("login(): Token saved for userId: {}", userDto.getUserId());
				}

				boolean isLoginStatusUpdated = userDao.updateLoginStatus(userDto.getUserId());
				if (isLoginStatusUpdated) {
					LOGGER.info("login(): Login status updated for userId: {}", userDto.getUserId());
				}

				// TODO: refactor this into one pass through the loan list
				loanDtoList = getLoansForUser(userDto.getUserId());
				List<String> loanAccountList = new ArrayList<>();
				for (LoanDto loanDto : loanDtoList) {
					if (Float.parseFloat(loanDto.getUnpaidPrincipalBalance()) > 0.0) {
						loanAccountList.add(String.valueOf(loanDto.getLoanNumber()));
					}
				}
				// To add deactivated loan
				for (LoanDto loanDto : loanDtoList) {
					if (Float.parseFloat(loanDto.getUnpaidPrincipalBalance()) == 0.0) {
						loanAccountList.add(String.valueOf(loanDto.getLoanNumber()+ " D"));
					}
				}
				loginResponse.setLoanAccountList(loanAccountList);
				loginResponse.setError("");
				loginResponse.setUserId(userDto.getUserId());
				loginResponse.setIsSuccessful(true);
			} else {
				failLoginAttempt(loginRequest.getUserName().toLowerCase());
				loginResponse.setError(messageReader.getPropertyFileMesssages().get("login.error"));
				loginResponse.setIsSuccessful(false);
			}
		} catch (LockedUserLoginException exp) {
			loginResponse.setIsSuccessful(false);
			loginResponse.setError("Locked");
		} catch (Exception exp) {
			loginResponse.setIsSuccessful(false);
			loginResponse.setError(exp.getMessage());
			LOGGER.error("login(): login failed: ", exp);
		}
		LOGGER.info("login(): {}", loginResponse);
		return loginResponse;
	}

	/**
	 * @author nehas3
	 * @date Jan 31, 2019
	 * @return void
	 * @param username
	 * @throws LockedUserLoginException
	 * @exception Description
	 */
	private void failLoginAttempt(String username) throws LockedUserLoginException {
		UserDto user = userDao.getUserByUsername(username);
		if (user == null) {
			LOGGER.info("failLoginAttempt(): username: {} doesn't exist", username);
			return;
		}

		if (user.isLocked()) {
			LOGGER.info("failLoginAttempt(): username: {} is locked", username);
			throw new LockedUserLoginException();
		}
		// the validateUser failed but getUserByUsername succeeded, so the password must
		// be incorrect
		user.setLoginFails(user.getLoginFails() + 1);
		LOGGER.info("failLoginAttempt(): {} failed login attempts for username: {}", user.getLoginFails(), username);
		if (user.getLoginFails() > 4) {
			LOGGER.info("failLoginAttempt(): Too many login attempts. Locking user with username: {}", username);
			user.setLocked(true);
		}
		userDao.updateUser(user);
	}

	private List<LoanDto> getLoansForUser(int userId) {
		List<LoanDto> allLoans = userDao.getLoanAccountsByUserId(userId);
		List<Long> refiNumbers = userDao.checkForRefis(allLoans);
		List<LoanDto> refis = new ArrayList<>();
		List<LoanDto> seconds = new ArrayList<>();

		/*
		 * This loop sorts loans into three buckets: firsts, seconds, and refis. The
		 * firsts are what's left in allLoans when the loop terminates.
		 */
		for (Iterator<LoanDto> iterator = allLoans.iterator(); iterator.hasNext();) {
			LoanDto loanDto = iterator.next();
			if (refiNumbers.contains(loanDto.getLoanNumber())) {
				if (!String.valueOf(loanDto.getLoanNumber()).startsWith("9")) {
					// refis of 2nd loans may "exist", but they shouldn't, so we don't add them
					refis.add(loanDto);
				}
				iterator.remove();
			} else if (String.valueOf(loanDto.getLoanNumber()).startsWith("9")) {
				seconds.add(loanDto);
				iterator.remove();
			}
		}

		// if there's a refi, we put the refi in the first position, the second in the
		// second position, and the old first in the third position
		if (!refis.isEmpty()) {
			LOGGER.info("Refi FOUND userId= : {} refi= : {}" , userId , refis.get(0));

			List<LoanDto> loans = new ArrayList<>(3);
			loans.add(findAndRemoveNewest(refis));
			if (!seconds.isEmpty()) {
				loans.add(findAndRemoveNewest(seconds));
			}
			loans.add(findAndRemoveNewest(allLoans));
			return loans;
		}

		// if there is no refi, we put the first in the first position and the second in
		// the second position
		List<LoanDto> loans = new ArrayList<>(2);
		if (!allLoans.isEmpty()) {
			loans.add(findAndRemoveNewest(allLoans));
		}
		if (!seconds.isEmpty()) {
			loans.add(findAndRemoveNewest(seconds));
		}

		// Add all the rest of the loans to the list
		loans.addAll(allLoans);
		loans.addAll(seconds);
		loans.addAll(refis);

		// sort CROWN loans
		for (LoanDto loan : loans) {
			if (userDao.isCrownLoanNumber(loan.getLoanNumber())) {
				loans.sort((loan1, loan2) -> (int) (loan1.getLoanNumber() - loan2.getLoanNumber()));
				LOGGER.info("CROWN loan found; userId= : {}" ,userId);
				break;
			}
		}

		return loans;
	}

	/**
	 * finds the newest loan by loan number, removes it from the list, and returns
	 * it
	 * 
	 * @param loans the loans to search
	 * @return the loan with the biggest loan number in the list
	 */
	private static LoanDto findAndRemoveNewest(List<LoanDto> loans) {
		LoanDto newest = loans.get(0);
		for (int i = 1; i < loans.size(); i++) {
			if (loans.get(i).getLoanNumber() > newest.getLoanNumber()) {
				newest = loans.get(i);
			}
		}
		loans.remove(newest);
		return newest;
	}
}
