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
import org.uhc.controller.envelop.request.ValidateRoutingNumberReq;
import org.uhc.controller.envelop.response.ValidateRoutingNumberRes;
import org.uhc.dao.UserDao;
import org.uhc.service.ValidateRoutingNumberService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date Aug 13, 2019
 * @description Implementing ValidateLoanNumberService to get the ValidateLoanNumberRes
 */
@Service
public class ValidateRoutingNumberServiceImpl implements ValidateRoutingNumberService{

	private static final Logger LOGGER = LogManager.getLogger(ValidateRoutingNumberServiceImpl.class.getName());
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	/**
	 * @author nehas3
	 * @date Aug 13, 2019
	 * @return ValidateLoanNumberRes
	 * @param ValidateRoutingNumberReq
	 * @return if loan number entered by user is valid or not
	 * @Description : this is validateLoanNumber method that returns if the entered loan number by user is valid or not
	 */
	@Override
	public ValidateRoutingNumberRes validateRoutingNumber(ValidateRoutingNumberReq validateRoutingNumberReq) {
		LOGGER.info("Entering into validateLoanNumber method");
		
		ValidateRoutingNumberRes validateRoutingNumberRes = new ValidateRoutingNumberRes();
		try {
			boolean isRoutingNumWhiteListed = userDao.isRoutingNumberWhiteListed(validateRoutingNumberReq.getRoutingNumber());
			if(isRoutingNumWhiteListed) {
				validateRoutingNumberRes.setIsRoutingNumberValid(true);
				validateRoutingNumberRes.setMessage("Routing number is valid");
			}else {
				validateRoutingNumberRes.setIsRoutingNumberValid(false);
				validateRoutingNumberRes.setMessage(messageReader.getPropertyFileMesssages()
						.get("bankingInfo.failure.incorrectRoutingNumber"));
			}
		}catch(Exception exp) {
			validateRoutingNumberRes.setIsRoutingNumberValid(false);
			validateRoutingNumberRes.setMessage("routing number could not be vaidated");
			LOGGER.error("Exception occured while validating routing number", exp);
		}
		LOGGER.info("Exit from validateRoutingNumber method");
		return validateRoutingNumberRes;
	}

}
