/* 
 * ===========================================================================
 * File Name ScheduledPaymentServiceImpl.java
 * 
 * Created on Jun 26, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: ScheduledPaymentServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.uhc.controller.envelop.request.LoanPaymentRequest;
import org.uhc.controller.envelop.request.ScheduledPaymentRequest;
import org.uhc.controller.envelop.response.ScheduledPaymentResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.BankingInfoDto;
import org.uhc.dao.dto.LoanDto;
import org.uhc.dao.dto.ScheduledPaymentDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.ScheduledPaymentService;
import org.uhc.util.ConfirmationNumberHelper;
import org.uhc.util.Constants;
import org.uhc.util.Constants.PaymentSource;
import org.uhc.util.Constants.ScheduledPaymentType;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date Jun 26, 2018
 * @description Implementing ScheduledPaymentService to get the
 *              ScheduledPaymentResponse
 */
@Service
public class ScheduledPaymentServiceImpl implements ScheduledPaymentService {

	private static final Logger LOGGER = LogManager.getLogger(ScheduledPaymentServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	private String paymentSource;

	public static final int MAXIMUM_RE_TRY = 20;

	/**
	 * @author nehas3
	 * @date Jun 26, 2018
	 * @return ScheduledPaymentResponse
	 * @param schedulePaymentReq
	 * @Description : this is schedulePayment method that returns
	 *              ScheduledPaymentResponse on basis of requested parameters.
	 */
	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
	@Override
	public ScheduledPaymentResponse schedulePayment(LoanPaymentRequest schedulePaymentReq) {
		LOGGER.info("Entering into schedulePayment method");

		ScheduledPaymentResponse scheduledPaymentResponse = new ScheduledPaymentResponse();

		try {
			synchronized (this) {
				// checking if there if user and their corresponding loan existing or not.
				ScheduledPaymentRequest loanPaymnet = schedulePaymentReq.getSchedulingPayment();

				if (schedulePaymentReq.getNextDue() == null || loanPaymnet.getUserDefinedDate() == null
						|| loanPaymnet.getScheduledDate() == null) {
					scheduledPaymentResponse.setIsSuccessful(false);
					scheduledPaymentResponse.setMessage("Payment could not be scheduled because of invalid date");
					return scheduledPaymentResponse;
				}
				UserDto user = userDao.getUserByUserId(schedulePaymentReq.getUserId());
				LoanDto loan = userDao.getLoanAccountDetailsByLoanNum(schedulePaymentReq.getLoanNumber());
				boolean isPaymentScheduled;
				if (user != null && loan != null) {
					schedulePaymentReq.setPaymentSource(PaymentSource.MBL.getDesc());
					schedulePaymentReq.setNextAvailableMonthForOneTime(
							getNextAvailableMonthForOneTime(loan.getLoanNumber(), loan.getNextDue()));

					schedulePaymentReq.setNextAvailableMonthForRecurring(
							getNextAvailableMonthForRecurring(loan.getLoanNumber(), loan.getNextDue()));
					// checking if user selected pay field to make payment or not.
					if (loanPaymnet.isPay()) {
						loanPaymnet.setMonthlyPayment(new BigDecimal(loan.getMonthlyPayment().replace(",", "")));
						boolean isLoanAccountInStopWeb = userDao
								.checkIfLoanAccInStopWeb(schedulePaymentReq.getLoanNumber());

						if (isLoanAccountInStopWeb) {
							scheduledPaymentResponse.setIsSuccessful(false);
							scheduledPaymentResponse.setMessage(messageReader.getPropertyFileMesssages()
									.get("scheduledPayment.failure.forStopWeb"));
							return scheduledPaymentResponse;
						}

						if (loanPaymnet.getScheduledType().equals(ScheduledPaymentType.TODAY.getDesc())
								&& isThereAnActiveTodayPayment(schedulePaymentReq)) {
							scheduledPaymentResponse.setIsSuccessful(false);
							scheduledPaymentResponse.setMessage(messageReader.getPropertyFileMesssages()
									.get("scheduledPayment.todayPaymentExistMessage1") + " " + this.getPaymentSource()
									+ " "
									+ messageReader.getPropertyFileMesssages()
											.get("scheduledPayment.todayPaymentExistMessage2")
									+ " " + loanPaymnet.getLoanNumber());

							return scheduledPaymentResponse;
						} else if (loanPaymnet.getScheduledType().equals(ScheduledPaymentType.ONE_TIME.getDesc())
								&& schedulePaymentReq.getNextAvailableMonthForOneTime() == null) {

							scheduledPaymentResponse.setIsSuccessful(false);
							scheduledPaymentResponse.setMessage(messageReader.getPropertyFileMesssages()
									.get("scheduledPayment.nextMonthForOneTimeNotExistMessage"));
							return scheduledPaymentResponse;

						} else if (loanPaymnet.getScheduledType().equals(ScheduledPaymentType.RECURRING.getDesc())
								&& schedulePaymentReq.getNextAvailableMonthForRecurring() == null) {

							scheduledPaymentResponse.setIsSuccessful(false);
							scheduledPaymentResponse.setMessage(messageReader.getPropertyFileMesssages()
									.get("scheduledPayment.nextMonthForRecurringNotExistMessage"));
							return scheduledPaymentResponse;
						}

						BankingInfoDto bankingDetails = userDao.getBankingInfo(schedulePaymentReq.getUserId());
						if (bankingDetails != null) {
							// generating confirmation number for each loan on basis of user id
							String paymentConfirmationNumber = buildPaymentConfirmationNumber(user.getUserId());
							loanPaymnet.setConfirmationNumber(paymentConfirmationNumber);

							// formatting the confirmation number
							String formattedConfirmationNum = ConfirmationNumberHelper
									.formatConfirmationNumber(paymentConfirmationNumber);

							// scheduling One-Time payment
							if (loanPaymnet.getScheduledType().equals(ScheduledPaymentType.ONE_TIME.getDesc())) {
								List<Date> scheduledDates = schedulePaymentReq.getSchedulingPayment()
										.getScheduledDate();
								for (Date scheduledDate : scheduledDates) {
									if (getNextAvailableMonthForOneTime(loan.getLoanNumber(),
											loan.getNextDue()) != null) {
										Integer scheduledDateMonth = scheduledDate.toInstant()
												.atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();

										if (scheduledDateMonth.toString().equals(
												getNextAvailableMonthForOneTime(loan.getLoanNumber(), loan.getNextDue())
														.toString())) {

											isPaymentScheduled = userDao.schedulePayment(user.getUserId(), loanPaymnet,
													scheduledDate, schedulePaymentReq.getPaymentSource());
											if (isPaymentScheduled) {
												scheduledPaymentResponse.setIsSuccessful(true);
												scheduledPaymentResponse.setMessage(messageReader
														.getPropertyFileMesssages().get("scheduledPayment.success")
														+ ", Confirmation Number :" + formattedConfirmationNum);
												scheduledPaymentResponse
														.setConfirmationNumber(formattedConfirmationNum);
											} else {
												scheduledPaymentResponse.setIsSuccessful(false);
												scheduledPaymentResponse.setMessage(messageReader
														.getPropertyFileMesssages().get("scheduledPayment.failure"));
											}
										} else {
											scheduledPaymentResponse.setIsSuccessful(false);
											scheduledPaymentResponse.setMessage(messageReader.getPropertyFileMesssages()
													.get("scheduledPayment.failure.incorrectNextAvailableMonthMessage"));
											return scheduledPaymentResponse;
										}

									} else {
										scheduledPaymentResponse.setIsSuccessful(false);
										scheduledPaymentResponse.setMessage(messageReader.getPropertyFileMesssages()
												.get("scheduledPayment.nextMonthForOneTimeNotExistMessage"));
										return scheduledPaymentResponse;
									}
								}
							} else if (loanPaymnet.getScheduledType().equals(ScheduledPaymentType.TODAY.getDesc())
									|| loanPaymnet.getScheduledType()
											.equals(ScheduledPaymentType.RECURRING.getDesc())) {
								// scheduling Today or Recurring Payment
								isPaymentScheduled = userDao.schedulePayment(user.getUserId(), loanPaymnet,
										schedulePaymentReq.getSchedulingPayment().getScheduledDate().get(0),
										schedulePaymentReq.getPaymentSource());
								if (isPaymentScheduled) {
									scheduledPaymentResponse.setIsSuccessful(true);
									scheduledPaymentResponse.setMessage(
											messageReader.getPropertyFileMesssages().get("scheduledPayment.success")
													+ ", Confirmation Number :" + formattedConfirmationNum);
									scheduledPaymentResponse.setConfirmationNumber(formattedConfirmationNum);

								} else {
									scheduledPaymentResponse.setIsSuccessful(false);
									scheduledPaymentResponse.setMessage(
											messageReader.getPropertyFileMesssages().get("scheduledPayment.failure"));
								}
							} else {
								scheduledPaymentResponse.setIsSuccessful(false);
								scheduledPaymentResponse.setMessage(messageReader.getPropertyFileMesssages()
										.get("scheduledPayment.failure.invalidScheduleTypeMessage"));
							}
						} else {
							scheduledPaymentResponse.setIsSuccessful(false);
							scheduledPaymentResponse.setMessage(messageReader.getPropertyFileMesssages()
									.get("scheduledPayment.failureIfBankDetailsNotAvailable"));
						}
					}

				} else {
					scheduledPaymentResponse.setIsSuccessful(false);
					scheduledPaymentResponse
							.setMessage(messageReader.getPropertyFileMesssages().get("user.not.available.error"));
				}
			}

		} catch (RuntimeException runTimeExp) {
			scheduledPaymentResponse.setIsSuccessful(false);
			scheduledPaymentResponse
					.setMessage(messageReader.getPropertyFileMesssages().get("scheduledPayment.failureExps"));
			LOGGER.error("Runtime Exception occured while Scheduling Payment", runTimeExp);
			throw runTimeExp;
		} catch (Exception exp) {
			LOGGER.error("Exception occured while scheduling payement because of ", exp);
		}
		LOGGER.info("Exit from schedulePayment method");
		return scheduledPaymentResponse;
	}

	/**
	 * @author nehas3
	 * @date Jul 5, 2018
	 * @return boolean
	 * @param loanPayment
	 * @return boolean
	 * @exception Description
	 */
	public boolean isThereAnActiveTodayPayment(LoanPaymentRequest loanPayment) {
		LOGGER.info("Entering into isThereAnActiveTodayPayment method");
		ScheduledPaymentRequest req = new ScheduledPaymentRequest();
		int isCanceled = req.getCancelled();
		List<Integer> result = userDao.getActivePaymentByPaymentType(ScheduledPaymentType.TODAY,
				loanPayment.getLoanNumber(), isCanceled);
		if (CollectionUtils.isEmpty(result)) {
			LOGGER.info("No active today-type payment found.");
			return false;
		}
		String paymentSourceName = userDao.getPaymentSourceOfScheduledPayment(result.get(0));
		if (paymentSourceName.equals(PaymentSource.MBL.getDesc())) {
			this.setPaymentSource("Mobile app");
		} else if (paymentSourceName.equals(PaymentSource.WEB.getDesc())) {
			this.setPaymentSource("Web app");
		}
		LOGGER.info("There is an active today-type payment by:{} ", paymentSourceName);
		LOGGER.info("Exit From isThereAnActiveTodayPayment method");
		return true;
	}

	/**
	 * @author nehas3 @date Jul 5, 2018
	 * @return Integer
	 * @param loanNumber
	 * @param nextDue
	 * @return Integer
	 * @throws Exception @exception
	 */
	public Integer getNextAvailableMonthForOneTime(long loanNumber, String nextDue) {
		LOGGER.info("Entering into getNextAvailableMonthForOneTime method");
		LOGGER.info("Calculate next available month for schedule payment for loan:{}", loanNumber);
		List<ScheduledPaymentDto> scheduledPayments = userDao.getScheduledPayments(loanNumber);
		Map<Integer, ScheduledPaymentDto> futurePayments = new TreeMap<>();
		try {
			DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
			Date sqlDate = sdf.parse(nextDue);
			Date nextDewDate = new Date(sqlDate.getTime());
			int dueMonth = nextDewDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();
			for (ScheduledPaymentDto scheduledPayment : scheduledPayments) {
				if (scheduledPayment.getScheduledType().equalsIgnoreCase("today")) { // removing pending today payment.
					futurePayments.put(scheduledPayment.getScheduledDate().toInstant().atZone(ZoneId.systemDefault())
							.getMonthValue(), scheduledPayment);
					continue;
				}
				futurePayments.put(
						scheduledPayment.getScheduledDate().toInstant().atZone(ZoneId.systemDefault()).getMonthValue(),
						scheduledPayment);
			}

			for (int index = 1; index < 5; index++, dueMonth++) {
				if (dueMonth > 12) {
					dueMonth = 1;
					LOGGER.info("Entered in if, when i > 12 {}", dueMonth);
				}

				if (!futurePayments.containsKey(dueMonth)) {
					LOGGER.info("Next available one-time payment month found: {}", dueMonth);
					return dueMonth;
				} else if (futurePayments.get(dueMonth).getScheduledType().equalsIgnoreCase("recurring")) {
					LOGGER.info(
							"A recurring payment found!  No one-time payment is allowed after recurring payment month");
					return null;
				}
			}
		} catch (Exception exp) {
			LOGGER.error("Exception occured while getting getNextAvailableMonthForOneTime ", exp);
		}

		LOGGER.info("Exit From getNextAvailableMonthForOneTime method");
		return null;
	}

	/**
	 * 
	 * @author nehas3
	 * @date Jul 5, 2018
	 * @return Integer
	 * @param loanNumber
	 * @param nextDue
	 * @return
	 * @throws Exception
	 * @exception Description
	 */
	public Integer getNextAvailableMonthForRecurring(long loanNumber, String nextDue) {
		LOGGER.info("Entering into getNextAvailableMonthForRecurring method");
		Integer maxScheduledMonth = 0;
		LOGGER.info("Calculate next available month for schedule payment for loan: {}.", loanNumber);
		try {
			DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
			Date sqlDate = sdf.parse(nextDue);
			Date nextDewDate = new Date(sqlDate.getTime());

			List<ScheduledPaymentDto> scheduledPayments = userDao.getScheduledPayments(loanNumber);
			if (!scheduledPayments.isEmpty()) {
				for (ScheduledPaymentDto scheduledPayment : scheduledPayments) {
					if (scheduledPayment.getScheduledType().equalsIgnoreCase("recurring")) {
						LOGGER.info("There is already a recurring payment for the loan.  Returning null.");
						return null;
					}
				}

				maxScheduledMonth = userDao.getMaxScheduledMonth(loanNumber);
				if (maxScheduledMonth == null) {
					LOGGER.info("No maxScheduledMonth available Returning nextDue month: {}",
							nextDewDate.toInstant().atZone(ZoneId.systemDefault()).getMonthValue());
					return sqlDate.toInstant().atZone(ZoneId.systemDefault()).getMonthValue();
				}
			} else {
				return sqlDate.toInstant().atZone(ZoneId.systemDefault()).getMonthValue();
			}
		} catch (Exception exp) {
			LOGGER.error("Exception occured while getting availablity for recurring payment", exp);
		}
		LOGGER.info("The maxScheduledMonth is {}", maxScheduledMonth);
		LOGGER.info("Exit From getNextAvailableMonthForRecurring method");
		return maxScheduledMonth + 1;
	}

	/**
	 * @author nehas3
	 * @date Mar 7, 2019
	 * @return String
	 * @param userId
	 * @Description this is buildPaymentConfirmationNumber method to generate unique
	 *              and numeric confirmation number
	 */
	private String buildPaymentConfirmationNumber(int userId) {
		LOGGER.info("Entering into getLoanInfo method");
		String confirmationNumber;
		boolean collided;

		int i = 0;
		do {
			confirmationNumber = ConfirmationNumberHelper.generateConfirmationNumber();
			// checking if generated confirmation already exists in db.
			collided = userDao.doesConfirmationNumberExistForUser(confirmationNumber, userId);
		} while (collided && i++ < MAXIMUM_RE_TRY);

		if (collided) {
			LOGGER.error(
					"Unable to build payment confirmation number for userId: {} Last found confirmation number was: {}",
					userId, confirmationNumber);
			throw new IllegalStateException("Unable to build payment confirmation number.");
		}

		LOGGER.info("Generated confirmation number: {}  for userId: {}", confirmationNumber, userId);
		LOGGER.info("Exit from getLoanInfo method");
		return confirmationNumber;
	}

	public String getPaymentSource() {
		return paymentSource;
	}

	public void setPaymentSource(String paymentSource) {
		this.paymentSource = paymentSource;
	}
}