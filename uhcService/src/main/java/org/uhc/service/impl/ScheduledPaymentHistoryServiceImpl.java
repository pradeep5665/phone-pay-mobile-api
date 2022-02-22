/* 
 * ===========================================================================
 * File Name ScheduledPaymentHistoryServiceImpl.java
 * 
 * Created on Jul 13, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: ScheduledPaymentHistoryServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.ScheduledPaymentHistoryByUserIdAndLoanNumberReq;
import org.uhc.controller.envelop.request.ScheduledPaymentHistoryReq;
import org.uhc.controller.envelop.response.ScheduledPaymentHistoryRes;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.LoanDto;
import org.uhc.dao.dto.PaymentDto;
import org.uhc.dao.dto.ScheduledPaymentHistoryDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.dao.mapper.ScheduledPaymentToPaymentDtoMapper;
import org.uhc.service.ScheduledPaymentHistoryService;
import org.uhc.util.Constants.PaymentSource;
import org.uhc.util.Constants.ScheduledPaymentType;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date Jul 13, 2018
 * @description Implementing AccountService to get the AccountResponse
 */
@Service
public class ScheduledPaymentHistoryServiceImpl implements ScheduledPaymentHistoryService {

	private static final Logger LOGGER = LogManager.getLogger(ScheduledPaymentHistoryServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	/**
	 * @author nehas3
	 * @date Jul 13, 2018
	 * @return ScheduledPaymentHistoryResponse
	 * @param schedulePaymentReq
	 * @Description : this is getScheduledPaymentHistoryByLoanNumber method that returns the
	 *              scheduled payment history of user on basis of requested
	 *              parameters.
	 */
	@Override
	public ScheduledPaymentHistoryRes getScheduledPaymentHistoryByLoanNumber(ScheduledPaymentHistoryByUserIdAndLoanNumberReq paymentHistoryReq) {
		LOGGER.info("Entering into getScheduledPaymentHistory method");

		ScheduledPaymentHistoryRes paymentHistoryRes = new ScheduledPaymentHistoryRes();
		try {

			UserDto user = userDao.getUserByUserId(paymentHistoryReq.getUserId());
			if (user != null) {
				List<LoanDto> loanList = userDao.getLoanAccountsByUserId(paymentHistoryReq.getUserId());

				if (loanList != null && !loanList.isEmpty()) {

					ScheduledPaymentToPaymentDtoMapper scheduledPaymentToPaymentDtoMapper = new ScheduledPaymentToPaymentDtoMapper();

					// payment history from payment table
					List<PaymentDto> scheduledPaymentList = userDao.getPaymentHistoryByLoanNumber(paymentHistoryReq.getLoanNumber());

					// payment history from Scheduled payment table
					List<ScheduledPaymentHistoryDto> scheduledPayments = userDao.getScheduledPaymentHistoryByLoanNumber(paymentHistoryReq.getLoanNumber());

					// merging both payment history together
					scheduledPaymentList.addAll(scheduledPaymentToPaymentDtoMapper
							.mapListScheduledPaymentsToListPaymentDto(scheduledPayments));

					Collections.sort(scheduledPaymentList);

					for (PaymentDto scPayment : scheduledPaymentList) {
						if (scPayment.getScheduleType() != null) {
							if (scPayment.getPaymentSource() != null) {
								if (scPayment.getPaymentSource().equals(PaymentSource.MBL.getDesc())) {
									if (scPayment.getScheduleType().equals(ScheduledPaymentType.ONE_TIME.getDesc())) {
										scPayment.setScheduleType("Mobile-Future");
									} else {
										scPayment.setScheduleType("Mobile-" + scPayment.getScheduleType());
									}

								} else if (scPayment.getPaymentSource().equals(PaymentSource.WEB.getDesc())) {
									scPayment.setScheduleType("Web-" + scPayment.getScheduleType());
								}
							} else {
								scPayment.setScheduleType("Web-" + scPayment.getScheduleType());
							}
						}
						
						scPayment.setCreatedBy(user.getFirstName()+" "+user.getLastName());
						if(scPayment.isCanceled()) {
							scPayment.setCanceledBy(user.getFirstName()+" "+user.getLastName());
						}
						
					}

					paymentHistoryRes.setIsSuccessful(true);
					paymentHistoryRes.setError("");
					paymentHistoryRes.setScheduledPaymentHistory(scheduledPaymentList);
				} else {
					paymentHistoryRes.setIsSuccessful(false);
					paymentHistoryRes.setError(messageReader.getPropertyFileMesssages().get("loanNotFound.error"));
				}
			} else {
				paymentHistoryRes.setIsSuccessful(false);
				paymentHistoryRes.setError(messageReader.getPropertyFileMesssages().get("user.not.available.error"));
			}
		} catch (Exception exp) {
			LOGGER.error("Could not get scheduled payment history because of exception ", exp);
		}
		LOGGER.info("Exit from getScheduledPaymentHistory method");
		return paymentHistoryRes;
	}

   /**
	 * @author nehas3
	 * @date Jul 13, 2018
	 * @return ScheduledPaymentHistoryResponse
	 * @param schedulePaymentReq
	 * @Description : this is getScheduledPaymentHistory method that returns the
	 *              scheduled payment history of user on basis of requested
	 *              parameters.
	 */
	@Override
	public ScheduledPaymentHistoryRes getScheduledPaymentHistory(ScheduledPaymentHistoryReq PaymentHistoryReq) {
		LOGGER.info("Entering into getScheduledPaymentHistory method");

		ScheduledPaymentHistoryRes paymentHistoryRes = new ScheduledPaymentHistoryRes();
		try {

			UserDto user = userDao.getUserByUserId(PaymentHistoryReq.getUserId());
			if (user != null) {
				List<LoanDto> loanList = userDao.getLoanAccountsByUserId(PaymentHistoryReq.getUserId());

				if (loanList != null) {

					ScheduledPaymentToPaymentDtoMapper scheduledPaymentToPaymentDtoMapper = new ScheduledPaymentToPaymentDtoMapper();

					// payment history from payment table
					List<PaymentDto> scheduledPaymentList = userDao.getPaymentHistory(loanList);

					// payment history from Scheduled payment table
					List<ScheduledPaymentHistoryDto> scheduledPayments = userDao.getScheduledPaymentHistory(loanList);

					// merging both payment history together
					scheduledPaymentList.addAll(scheduledPaymentToPaymentDtoMapper
							.mapListScheduledPaymentsToListPaymentDto(scheduledPayments));

					Collections.sort(scheduledPaymentList);

					for (PaymentDto scPayment : scheduledPaymentList) {
						if (scPayment.getScheduleType() != null) {
							if (scPayment.getPaymentSource() != null) {
								if (scPayment.getPaymentSource().equals(PaymentSource.MBL.getDesc())) {
									if (scPayment.getScheduleType().equals(ScheduledPaymentType.ONE_TIME.getDesc())) {
										scPayment.setScheduleType("Mobile-Future");
									} else {
										scPayment.setScheduleType("Mobile-" + scPayment.getScheduleType());
									}

								} else if (scPayment.getPaymentSource().equals(PaymentSource.WEB.getDesc())) {
									scPayment.setScheduleType("Web-" + scPayment.getScheduleType());
								}
							} else {
								scPayment.setScheduleType("Web-" + scPayment.getScheduleType());
							}
						}
					}

					paymentHistoryRes.setIsSuccessful(true);
					paymentHistoryRes.setError("");
					paymentHistoryRes.setScheduledPaymentHistory(scheduledPaymentList);
				} else {
					paymentHistoryRes.setIsSuccessful(false);
					paymentHistoryRes.setError(messageReader.getPropertyFileMesssages().get("loanNotFound.error"));
				}
			} else {
				paymentHistoryRes.setIsSuccessful(false);
				paymentHistoryRes.setError(messageReader.getPropertyFileMesssages().get("user.not.available.error"));
			}
		} catch (Exception exp) {
			LOGGER.error("Could not get scheduled payment history because of exception ", exp);
		}
		return paymentHistoryRes;
	}
}