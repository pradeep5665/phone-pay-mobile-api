/* 
 * ===========================================================================
 * File Name ValidateLoanNumberServiceImpl.java
 * 
 * Created on Aug 13, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateLoanNumberServiceImpl.java,v $
 * ===========================================================================
 */
 package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.ValidateUserNameReq;
import org.uhc.controller.envelop.response.ValidateUserNameRes;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.ValidateUserNameService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date Aug 13, 2019
 * @description Implementing ValidateLoanNumberService to get the ValidateLoanNumberRes
 */
@Service
public class ValidateUserNameServiceImpl implements ValidateUserNameService{

	private static final Logger LOGGER = LogManager.getLogger(ValidateUserNameServiceImpl.class.getName());
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	/**
	 * @author nehas3
	 * @date Aug 13, 2019
	 * @return ValidateLoanNumberRes
	 * @param ValidateLoanNumberReq
	 * @return if loan number entered by user is valid or not
	 * @Description : this is validateLoanNumber method that returns if the entered loan number by user is valid or not
	 */
	@Override
	public ValidateUserNameRes validateUserName(ValidateUserNameReq validateUserNameReq) {
		UserDto userDto;
		ValidateUserNameRes validateUserNameRes = new ValidateUserNameRes();
		try {
			userDto = userDao.getUserByUsername(validateUserNameReq.getUserName());
			if(userDto == null) {
				validateUserNameRes.setIsUserNameValid(true);
				validateUserNameRes.setMessage("");
				LOGGER.info("validateUserName(): username: {} is available.", validateUserNameReq.getUserName());
			}else {
				validateUserNameRes.setIsUserNameValid(false);
				validateUserNameRes.setMessage(messageReader.getPropertyFileMesssages().get("user.alreadyExist"));
				LOGGER.info("validateUserName(): username: {} already exists.", validateUserNameReq.getUserName());
			}
		}catch(Exception exp) {
			validateUserNameRes.setIsUserNameValid(false);
			validateUserNameRes.setMessage("user name could not be validated");
			LOGGER.error("Unknown exception occurred while validating userName: {}", validateUserNameReq.getUserName(), exp);
		}
		return validateUserNameRes;
	}

}
