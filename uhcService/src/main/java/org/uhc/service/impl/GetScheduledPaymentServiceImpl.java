/* 
 * ===========================================================================
 * File Name GetScheduledPaymentServiceImpl.java
 * 
 * Created on Jul 2, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018 
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetScheduledPaymentServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.GetScheduledPaymentRequest;
import org.uhc.controller.envelop.response.GetScheduledPaymentResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.ACHPaymentsDto;
import org.uhc.dao.dto.GetScheduledPaymentDto;
import org.uhc.dao.dto.LoanDto;
import org.uhc.service.GetScheduledPaymentService;
import org.uhc.util.Constants;
import org.uhc.util.Constants.PaymentSource;
import org.uhc.util.Constants.ScheduledPaymentType;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date Jul 2, 2018
 */
@Service
public class GetScheduledPaymentServiceImpl implements GetScheduledPaymentService {

	private static final Logger LOGGER = LogManager.getLogger(GetScheduledPaymentServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	/**
	 * @author nehas3
	 * @date Jul 2, 2018
	 * @return GetScheduledPaymentResponse
	 * @param scheduledPaymentRequest
	 * @return Scheduled payments
	 * @Description : this is getScheduledPayments method that returns scheduled
	 *              payments info of user on basis of requested parameters.
	 */
	@Override
	public GetScheduledPaymentResponse getScheduledPayments(GetScheduledPaymentRequest scheduledPaymentRequest) {
		String currentDate = null;
		LOGGER.info("getScheduledPayments(): loanNumber: {}", scheduledPaymentRequest.getLoanNumber());
		GetScheduledPaymentResponse scheduledPaymentResponse = new GetScheduledPaymentResponse();
		try {
			LoanDto loan = userDao.getLoanAccountDetailsByLoanNum(scheduledPaymentRequest.getLoanNumber());
			if (loan != null) {
				Date date = new Date();
				DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
				currentDate = sdf.format(date);
				List<GetScheduledPaymentDto> scheduledPayments = userDao.getScheduledPaymentList(scheduledPaymentRequest.getLoanNumber());               
				ACHPaymentsDto achPayment = userDao.getAchPaymentDetails(scheduledPaymentRequest.getLoanNumber());
				if(achPayment != null) {
					// getting ACH payment details and setting it to scheduled payments
					GetScheduledPaymentDto sclPayment = new GetScheduledPaymentDto();
					 StringBuilder monthText = new StringBuilder();
		                	int achDay = achPayment.getAchDays() + 1;
		                	Date mydate = new Date();
		                	monthText = monthText.append('/').append(achDay).append('/');
		                	
		                	String achDate = sdf.format(mydate).replaceAll("/[0-9]*/", monthText.toString());
		                	achPayment.setAchDate(achDate);
		                	sclPayment.setLoanNumber(achPayment.getAchLoanNumber());
		                	sclPayment.setScheduledType(ScheduledPaymentType.AUTO_DRAFT.getDesc());
		                	sclPayment.setScheduledDate(sdf.parse(achPayment.getAchDate()));
		                	sclPayment.setPaymentSource(PaymentSource.ACH.getDesc());
		                	if (sdf.parse(achPayment.getAchDate()).before(sdf.parse(currentDate))) {
		                		sclPayment.setStatus("--");
		                		sclPayment.setProcessedDate(sdf.parse(achPayment.getAchDate()));
		                		Date nextSchDate = DateUtils.addMonths(sdf.parse(achPayment.getAchDate()), 1);
		                		sclPayment.setScheduledDate(nextSchDate);
							}else if(sdf.parse(achPayment.getAchDate()).after(sdf.parse(currentDate))){
								sclPayment.setStatus("--");
							} else if(sdf.parse(achPayment.getAchDate()).equals(sdf.parse(currentDate))){
								sclPayment.setStatus(Constants.GetScheduledPaymentStatus.PENDING.getDesc());
							}
		                	scheduledPayments.add(sclPayment);
				} else {
                	LOGGER.error("getScheduledPayments(): No ACH payment found");
                }
				if (scheduledPayments != null && !scheduledPayments.isEmpty()) {
					for (GetScheduledPaymentDto scheduledPayment : scheduledPayments) {
						if (scheduledPayment.getPaymentSource().equals(PaymentSource.MBL.getDesc())|| 
								scheduledPayment.getPaymentSource().equals(PaymentSource.WEB.getDesc())) {
							if (scheduledPayment.getPaymentSource().equals(PaymentSource.MBL.getDesc())) {
								if(scheduledPayment.getScheduledType().equals(ScheduledPaymentType.ONE_TIME.getDesc())) {
									scheduledPayment.setScheduledType("Mobile-Future");
								}else {
									scheduledPayment.setScheduledType("Mobile-" + scheduledPayment.getScheduledType());
								}
								
							} else if (scheduledPayment.getPaymentSource().equals(PaymentSource.WEB.getDesc())) {
								scheduledPayment.setScheduledType("Web-" + scheduledPayment.getScheduledType());
							}

							if ((scheduledPayment.getProcessedDate() == null) && (sdf.parse(scheduledPayment.getScheduledDate()).after(sdf.parse(currentDate)))) {
								scheduledPayment.setStatus("");
							} else if (scheduledPayment.getProcessedDate() == null
									&& ((sdf.parse(scheduledPayment.getScheduledDate()).before(sdf.parse(currentDate)))
											|| (scheduledPayment.getScheduledDate().equals(currentDate)))) {
								scheduledPayment.setStatus(Constants.GetScheduledPaymentStatus.PENDING.getDesc());

							} else if (scheduledPayment.getProcessedDate().equals(currentDate)) {
								scheduledPayment.setStatus(Constants.GetScheduledPaymentStatus.PROCESSED.getDesc());
							}
						}
					}
					scheduledPaymentResponse.setIsSuccessful(true);
					scheduledPaymentResponse
							.setMessage(messageReader.getPropertyFileMesssages().get("getScheduledPayment.success"));
					scheduledPaymentResponse.setScheduledPayments(scheduledPayments);
				} else {
					scheduledPaymentResponse.setIsSuccessful(false);
					scheduledPaymentResponse
							.setMessage(messageReader.getPropertyFileMesssages().get("getScheduledPayment.failure"));
				}
			} else {
				scheduledPaymentResponse.setIsSuccessful(false);
				scheduledPaymentResponse.setMessage(messageReader.getPropertyFileMesssages().get("loanNotFound.error"));
			}
		} catch (Exception exp) {
			scheduledPaymentResponse.setIsSuccessful(false);
			scheduledPaymentResponse.setMessage(messageReader.getPropertyFileMesssages().get("getScheduledPayment.failureExp"));
			LOGGER.error("getScheduledPayments(): Getting a scheduled payment failed,", exp);
		}

		LOGGER.info("getScheduledPayments(): scheduledPaymentResponse: {}", scheduledPaymentResponse);
		return scheduledPaymentResponse;
	}
}
