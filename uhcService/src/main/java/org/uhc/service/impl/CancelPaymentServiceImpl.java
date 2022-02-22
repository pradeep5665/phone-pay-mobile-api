/* 
 * ===========================================================================
 * File Name CancelPaymentServiceImpl.java
 * 
 * Created on Jul 11, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: CancelPaymentServiceImpl.java,v $
 * ===========================================================================
 */
 package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.CancelPaymentRequest;
import org.uhc.controller.envelop.response.CancelPaymentResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.ScheduledPaymentDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.CancelPaymentService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date Jul 11, 2018
 * @description Implementing CancelPaymentService to get the CancelPaymentResponse
 */
@Service
public class CancelPaymentServiceImpl implements CancelPaymentService{

	private static final Logger LOGGER = LogManager.getLogger(CancelPaymentServiceImpl.class.getName());
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessageReader messageReader;
	
	String[] spCheckbox;
	
	/**
	 * @author nehas3
	 * @date Jul 11, 2018
	 * @return CancelPaymentResponse
	 * @param cancelPaymentRequest
	 * @return true or false
	 * @Description : this is cancelPayment method that is used to cancel the scheduled payment on basis of requested parameters.
	 */
	@Override
	public CancelPaymentResponse cancelPayment(CancelPaymentRequest cancelPaymentRequest) {
		LOGGER.info("cancelPayment(): userId: {}", cancelPaymentRequest.getUserId());
		CancelPaymentResponse cancelPaymentResponse = new CancelPaymentResponse();
		
		try{
			UserDto user = userDao.getUserByUserId(cancelPaymentRequest.getUserId());
			
			if(user != null){
				
				spCheckbox = cancelPaymentRequest.getPaymentIdOfCheckBox();
				boolean isCancelled = false;
				for(String scheduledPaymentId : spCheckbox){
					ScheduledPaymentDto scheduledPayment = userDao.getScheduledPaymentByPaymentId(scheduledPaymentId);
					
					if(scheduledPayment.getUserId() == user.getUserId()){
						
						/* This checks if the payment has already moved from scheduled payment table to payment table. If so,
		                 * the payment in the payment table is also canceled.
		                 */
						if(userDao.hasPaymentMoved(scheduledPayment.getPaymentId())){
							if(userDao.cancelPaymentInPaymentTableByPaymentId(Integer.parseInt(scheduledPaymentId))){
								LOGGER.info("cancelPayment(): Payment cancelled from payment table");
							}else{
								 cancelPaymentResponse.setIsSuccessful(false);
								 cancelPaymentResponse.setMessage(messageReader.getPropertyFileMesssages().get("cancelpayment.failure.error"));
								 return cancelPaymentResponse;
							}
						}
						if(userDao.cancelPaymentByPaymentId(Integer.parseInt(scheduledPaymentId), user.getUserId())){ 
							isCancelled = true; 
						}else{
							LOGGER.info("cancelPayment(): Error canceling payment by paymentId: {} canceled by: {} UserId: {}",
									scheduledPaymentId, user.getUsername(), user.getUserId());
							cancelPaymentResponse.setIsSuccessful(false);
							cancelPaymentResponse.setMessage(messageReader.getPropertyFileMesssages().get("cancelpayment.failure.error"));
						}
					}else{
						cancelPaymentResponse.setIsSuccessful(false);
						cancelPaymentResponse.setMessage(messageReader.getPropertyFileMesssages().get("canclepayment.failure.incorrectUser"));
					}
				}
				if(isCancelled){
					cancelPaymentResponse.setIsSuccessful(true);
					cancelPaymentResponse.setMessage(messageReader.getPropertyFileMesssages().get("cancelpayment.success.message"));
				}
				
			}else{
				cancelPaymentResponse.setIsSuccessful(false);
				cancelPaymentResponse.setMessage(messageReader.getPropertyFileMesssages().get("user.not.available.error"));
			}
			
		}catch(Exception exp){
			LOGGER.error("Payment Could Not be Cancelled Because Of Exception " , exp);
			cancelPaymentResponse.setIsSuccessful(false);
			cancelPaymentResponse.setMessage(messageReader.getPropertyFileMesssages().get("cancelpayment.failure.exp"));
		}

		LOGGER.info("cancelPayment(): cancelPaymentResponse: {}", cancelPaymentRequest);
		return cancelPaymentResponse;
	}

}
