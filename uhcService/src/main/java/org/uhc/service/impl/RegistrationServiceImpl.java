/* 
 * ===========================================================================
 * File Name RegistrationServiceImpl.java
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
 * $Log: RegistrationServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.uhc.controller.envelop.request.RegistrationRequest;
import org.uhc.controller.envelop.response.RegistrationResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.LoanDto;
import org.uhc.dao.dto.OnlineStatementsPrefDto;
import org.uhc.dao.dto.PushNotificationFlagDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.RegistrationService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date May 25, 2018
 * @description Implementing RegistrationService to get the RegistrationResponse
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

	private static final Logger LOGGER = LogManager.getLogger(RegistrationServiceImpl.class.getName());
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return RegistrationResponse
	 * @param regRequest
	 * @return success or failure for registering a valid user. Description : this
	 *         is registerUser method that returns success or failure for
	 *         registering a valid user.
	 */
	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
	@Override
	public RegistrationResponse registerUser(RegistrationRequest regReq) {
		UserDto userDto;
		
		RegistrationResponse regResponse = new RegistrationResponse();
		try {
			if (regReq.isAgreed()) {
				List<String> ssnList = userDao.checkLoanAcExistNew(regReq.getLoanNumber(), regReq.getSsn(),
						regReq.getZip());

				if (ssnList != null && ssnList.size() == 1) {
					userDto = userDao.getUserBySsn(ssnList.get(0));
					synchronized (this) {
						if (userDto == null) {
							regReq.setSsn(ssnList.get(0));
							boolean isRegistered = userDao.registerUser(regReq);
							if (isRegistered) {
								UserDto user  = userDao.getUserByUsername(regReq.getUsername());
								if(user!=null) {
									PushNotificationFlagDto pnfDto = new PushNotificationFlagDto();
									pnfDto.setUserId(user.getUserId());
									pnfDto.setFlagNameId(1);
									pnfDto.setFlagValue(1);
									boolean isPushNotificationFlagUpdated = userDao.insertPushNotificationFlag(pnfDto);
									if(isPushNotificationFlagUpdated) {
										LOGGER.info("registerUser(): PushNotification flag inserted successfully");
									}else {
										LOGGER.info("registerUser(): PushNotification flag could not be inserted");
									}
								}
								boolean isInstertOnlinePref = false;
								OnlineStatementsPrefDto onlineStatementsPrefDto = userDao.getStatementPrefStatus(Integer.parseInt(regReq.getLoanNumber()));
								if(onlineStatementsPrefDto != null) {
									isInstertOnlinePref = userDao.updateOnlinePrefStatus(regReq.getLoanNumber());
								}else {
									isInstertOnlinePref = userDao.insertOnlinePrefStatus(regReq.getLoanNumber(),0);
								}
								if (isInstertOnlinePref) {
									LOGGER.info("registerUser(): OnlineStatementPref inserted successfully");
								} else {
									LOGGER.info("registerUser(): OnlineStatementPref could not be inserted");
								}
								regResponse.setIsSuccessful(true);
								regResponse.setMessage(messageReader.getPropertyFileMesssages().get("registration.success"));
								
							} else {
								regResponse.setIsSuccessful(false);
								regResponse.setMessage(messageReader.getPropertyFileMesssages().get("registration.failure"));
								LOGGER.error("registerUser(): registration failed. userName: {}, loanNumber: {}", regReq.getUsername(), regReq.getLoanNumber());
							}
						} else {
							regResponse.setIsSuccessful(false);
							regResponse.setMessage(messageReader.getPropertyFileMesssages().get("user.alreadyExist"));
							LOGGER.info("registerUser(): userName: {} already exists.", regReq.getUsername());
						}
					}
				}else if(ssnList != null && ssnList.size() > 1) {
					regResponse.setIsSuccessful(false);
					regResponse.setMessage("The Profile can not be created at this time, please contact UHC");
					LOGGER.error("registerUser(): Data integrity error. userName: {} has more than one SSN", regReq.getUsername());
				}else {
					regResponse.setIsSuccessful(false);
					regResponse.setMessage(messageReader.getPropertyFileMesssages().get("registration.recordsNotFound"));
					LOGGER.info("registerUser(): No SSN found for loanNumber: {}, userName: {}", regReq.getLoanNumber(), regReq.getUsername());
				}
			} else {
				regResponse.setIsSuccessful(false);
				regResponse.setMessage(messageReader.getPropertyFileMesssages().get("registration.click.agree"));
			}
		} catch (RuntimeException runTimeExp) {
			regResponse.setIsSuccessful(false);
			regResponse.setMessage(messageReader.getPropertyFileMesssages().get("scheduledPayment.failureExps"));
			LOGGER.error("registerUser(): Runtime Exception occurred while registering userName: {}", regReq.getUsername(), runTimeExp);
			throw runTimeExp;
		} catch (Exception exp) {
			regResponse.setIsSuccessful(false);
			regResponse.setMessage(messageReader.getPropertyFileMesssages().get("registration.failure"));
			LOGGER.error("registerUser(): Unknown exception. userName: {}, loanNumber: {}. ", regReq.getUsername(), regReq.getLoanNumber(), exp);
		}
		return regResponse;
	}

	/**
	 * @author pradeepy
	 * @date May 21, 2021
	 * @return RegistrationResponse
	 * @param regRequest
	 * @return success or failure for registering a valid user. Description : this
	 *         is registerUser method that returns success or failure for
	 *         registering a valid user.
	 */
	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
	@Override
	public RegistrationResponse registerUserWithSecureCode(RegistrationRequest regRequest) {
		UserDto userDto;
		
		RegistrationResponse regResponse = new RegistrationResponse();
		try {
			if (regRequest.isAgreed()) {
				List<String> ssnList = userDao.checkLoanAcExistNew(regRequest.getLoanNumber(), regRequest.getSsn(),
						regRequest.getZip());

				if (ssnList != null && ssnList.size() == 1) {
					userDto = userDao.getUserBySsn(ssnList.get(0));
					synchronized (this) {
						if (userDto == null) {
							regRequest.setSsn(ssnList.get(0));
							boolean isRegistered = userDao.registerUser(regRequest);
							if (isRegistered) {
								UserDto user  = userDao.getUserByUsername(regRequest.getUsername());
								if(user!=null) {
									List<LoanDto> loans = userDao.getLoanAccountsByUserId(user.getUserId());
									for (LoanDto loan : loans) {
										userDao.addUserLoanRecord(user.getUserId(), loan.getLoanNumber(), loan.getSequenceNumber());
									}
									PushNotificationFlagDto pnfDto = new PushNotificationFlagDto();
									pnfDto.setUserId(user.getUserId());
									pnfDto.setFlagNameId(1);
									pnfDto.setFlagValue(1);
									boolean isPushNotificationFlagUpdated = userDao.insertPushNotificationFlag(pnfDto);
									if(isPushNotificationFlagUpdated) {
										LOGGER.info("registerUser(): PushNotification flag inserted successfully");
									}else {
										LOGGER.info("registerUser(): PushNotification flag could not be inserted");
									}
								}
								boolean isInstertOnlinePref = false;
								OnlineStatementsPrefDto onlineStatementsPrefDto = userDao.getStatementPrefStatus(Integer.parseInt(regRequest.getLoanNumber()));
								if(onlineStatementsPrefDto != null) {
									isInstertOnlinePref = userDao.updateOnlinePrefStatus(regRequest.getLoanNumber());
								}else {
									isInstertOnlinePref = userDao.insertOnlinePrefStatus(regRequest.getLoanNumber(),0);
								}
								if (isInstertOnlinePref) {
									LOGGER.info("registerUser(): OnlineStatementPref inserted successfully");
								} else {
									LOGGER.info("registerUser(): OnlineStatementPref could not be inserted");
								}
								regResponse.setIsSuccessful(true);
								regResponse.setMessage(messageReader.getPropertyFileMesssages().get("registration.success"));
								
							} else {
								regResponse.setIsSuccessful(false);
								regResponse.setMessage(messageReader.getPropertyFileMesssages().get("registration.failure"));
								LOGGER.error("registerUser(): registration failed. userName: {}, loanNumber: {}", regRequest.getUsername(), regRequest.getLoanNumber());
							}
						} else {
							regResponse.setIsSuccessful(false);
							regResponse.setMessage(messageReader.getPropertyFileMesssages().get("user.alreadyExist"));
							LOGGER.info("registerUser(): userName: {} already exists.", regRequest.getUsername());
						}
					}
				}else if(ssnList != null && ssnList.size() > 1) {
					regResponse.setIsSuccessful(false);
					regResponse.setMessage("The Profile can not be created at this time, please contact UHC");
					LOGGER.error("registerUser(): Data integrity error. userName: {} has more than one SSN", regRequest.getUsername());
				}else {
					regResponse.setIsSuccessful(false);
					regResponse.setMessage(messageReader.getPropertyFileMesssages().get("registration.recordsNotFound"));
					LOGGER.info("registerUser(): No SSN found for loanNumber: {}, userName: {}", regRequest.getLoanNumber(), regRequest.getUsername());
				}
			} else {
				regResponse.setIsSuccessful(false);
				regResponse.setMessage(messageReader.getPropertyFileMesssages().get("registration.click.agree"));
			}
		} catch (RuntimeException runTimeExp) {
			regResponse.setIsSuccessful(false);
			regResponse.setMessage(messageReader.getPropertyFileMesssages().get("scheduledPayment.failureExps"));
			LOGGER.error("registerUser(): Runtime Exception occurred while registering userName: {}", regRequest.getUsername(), runTimeExp);
			throw runTimeExp;
		} catch (Exception exp) {
			regResponse.setIsSuccessful(false);
			regResponse.setMessage(messageReader.getPropertyFileMesssages().get("registration.failure"));
			LOGGER.error("registerUser(): Unknown exception. userName: {}, loanNumber: {}. ", regRequest.getUsername(), regRequest.getLoanNumber(), exp);
		}
		return regResponse;
}
}
