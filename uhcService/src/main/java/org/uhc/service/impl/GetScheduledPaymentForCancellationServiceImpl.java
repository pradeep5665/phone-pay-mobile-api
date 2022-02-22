/* 
 * ===========================================================================
 * File Name GetScheduledPaymentForCancellationServiceImpl.java
 * 
 * Created on Jul 9, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetScheduledPaymentForCancellationServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.GetScheduledPaymentForCancellationReq;
import org.uhc.controller.envelop.response.GetScheduledPaymentForCancellationRes;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.GetScheduledPaymentForCancellationDto;
import org.uhc.dao.dto.LoanDto;
import org.uhc.dao.dto.ScheduledPaymentDto;
import org.uhc.service.GetScheduledPaymentForCancellationService;
import org.uhc.util.MessageReader;
import org.uhc.util.Constants.PaymentSource;
import org.uhc.util.Constants.ScheduledPaymentType;

/**
 * @author nehas3
 * @date Jul 9, 2018
 */
@Service
public class GetScheduledPaymentForCancellationServiceImpl implements GetScheduledPaymentForCancellationService {

	private static final Logger LOGGER = LogManager
			.getLogger(GetScheduledPaymentForCancellationServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	/**
	 * @author nehas3
	 * @date Jul 9, 2018
	 * @return GetScheduledPaymentForCancellationRes
	 * @param scheduledPaymentReq
	 * @return Scheduled payments for cancellation
	 * @Description : this is getScheduledPayments method that returns scheduled
	 *              payments info of user for cancellation on basis of requested
	 *              parameters.
	 */
	@Override
	public GetScheduledPaymentForCancellationRes getScheduledPayments(
			GetScheduledPaymentForCancellationReq scheduledPaymentReq) {

		LOGGER.info("getScheduledPayments(): loanNumber: {}", scheduledPaymentReq.getLoanNumber());
		GetScheduledPaymentForCancellationRes scheduledPaymentRes = new GetScheduledPaymentForCancellationRes();
		List<GetScheduledPaymentForCancellationDto> scheduledPayments = new ArrayList<>();

		try {
			LoanDto loan = userDao.getLoanAccountDetailsByLoanNum(scheduledPaymentReq.getLoanNumber());
			if (loan != null) {
				scheduledPayments = userDao.getScheduledPaymentListForCancellation(scheduledPaymentReq.getLoanNumber());
				if (!scheduledPayments.isEmpty()) {
					scheduledPaymentRes.setIsSuccessful(true);
					scheduledPaymentRes
							.setMessage(messageReader.getPropertyFileMesssages().get("getScheduledPayment.success"));

					for (GetScheduledPaymentForCancellationDto scheduledPayment : scheduledPayments) {

						ScheduledPaymentDto schedulePayment = new ScheduledPaymentDto();

						schedulePayment
								.setMonthlyPayment(userDao.getMonthlyPayment(scheduledPaymentReq.getLoanNumber()));

						scheduledPayment.setTotalPayment(schedulePayment.getMonthlyPayment()
								.add(scheduledPayment.getExtraEscrow().add(scheduledPayment.getExtraPrincipal().add(
										scheduledPayment.getLateFeesPaid().add(scheduledPayment.getNsfFeesPaid())))));

						if (scheduledPayment.getPaymentSource().equals(PaymentSource.MBL.getDesc())
								|| scheduledPayment.getPaymentSource().equals(PaymentSource.WEB.getDesc())) {
							if (scheduledPayment.getPaymentSource().equals(PaymentSource.MBL.getDesc())) {
								if (scheduledPayment.getScheduledType()
										.equals(ScheduledPaymentType.ONE_TIME.getDesc())) {
									scheduledPayment.setScheduledType("Mobile-Future");
								} else {
									scheduledPayment.setScheduledType("Mobile-" + scheduledPayment.getScheduledType());
								}

							} else if (scheduledPayment.getPaymentSource().equals(PaymentSource.WEB.getDesc())) {
								scheduledPayment.setScheduledType("Web-" + scheduledPayment.getScheduledType());
							}
						}
						scheduledPaymentRes.setSchduledPayments(scheduledPayments);
					}

				} else {
					scheduledPaymentRes.setIsSuccessful(false);
					scheduledPaymentRes
							.setMessage(messageReader.getPropertyFileMesssages().get("getScheduledPayment.failure"));
				}

			} else {
				scheduledPaymentRes.setIsSuccessful(false);
				scheduledPaymentRes.setMessage(messageReader.getPropertyFileMesssages().get("loanNotFound.error"));
			}

		} catch (Exception exp) {
			LOGGER.error("getScheduledPayments(): Getting scheduled payments failed.", exp);
			scheduledPaymentRes.setIsSuccessful(false);
			scheduledPaymentRes
					.setMessage(messageReader.getPropertyFileMesssages().get("getScheduledPayment.failureExp"));
		}
		LOGGER.info("getScheduledPayments(): {}", scheduledPaymentRes);
		return scheduledPaymentRes;
	}

}
