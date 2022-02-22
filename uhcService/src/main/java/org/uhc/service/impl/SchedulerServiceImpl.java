/* 
 * ===========================================================================
 * File Name SchedulerControllerImpl.java
 * 
 * Created on Aug 6, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: SchedulerControllerImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.LoanDto;
import org.uhc.dao.dto.PaymentItemDto;
import org.uhc.dao.dto.ProcessedPaymentDto;
import org.uhc.dao.dto.ScheduledPaymentDto;
import org.uhc.dao.dto.TokenDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.service.SchedulerService;
import org.uhc.util.SendNotificationsViaFCMServerHelper;

/**
 * @author nehas3
 * @date Aug 6, 2018
 * @description Implementing SchedulerService to make the scheduler perform the
 *              task in the certain conditions
 */
@Service
public class SchedulerServiceImpl implements SchedulerService {

	private static final Logger LOGGER = LogManager.getLogger(SchedulerServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private SendNotificationsViaFCMServerHelper fcm;

	@Value("${payment.confirmation.messageTitle}")
	private String confirmationNotificationTitle;

	@Value("${payment.confirmation.messageBody}")
	private String confirmationNotificationBody;

	@Value("${payment.confirmation.messageBody2}")
	private String confirmationNotificationBody2;

	@Value("${payment.reminder.messageTitle}")
	private String paymentReminderNotificationTitle;

	@Value("${payment.reminder.messageBody1}")
	private String paymentReminderNotificationBody1;
	
	@Value("${payment.reminder.messageBody2}")
	private String paymentReminderNotificationBody2;

	
	@Value("${notification.remove.in.days}")
	private int numberOfDaysToRemoveNotifications;

	/**
	 * @author nehas3
	 * @date Aug 6, 2018
	 * @return
	 * @param Description : This is sendPaymentConfirmation method to send payment
	 *                    confirmation message to user on the given time.
	 */
	@Override
	public void sendPaymentConfirmation() {
		LOGGER.info("Entering Into sendPaymentConfirmation method");
		LOGGER.info("getProcessedPaymentList");
		List<ProcessedPaymentDto> scheduledPaymentDtoList = userDao.getProcessedPaymentListOfUsers();
		int messageSentCount = 0;
		LoanDto loanDto = null;
		try {
			if (scheduledPaymentDtoList != null) {
				for (ProcessedPaymentDto scheduledPayment : scheduledPaymentDtoList) {
					StringBuilder confNotificationBody = null;
					confNotificationBody = new StringBuilder(this.confirmationNotificationBody);
					UserDto userDto = userDao.getUserByUserId(scheduledPayment.getUserId());
					if (userDto.isNotificationEnabled()) {
						List<PaymentItemDto> paymentItemList = userDao
								.getPaymentItemByPaymentId(scheduledPayment.getPaymentId());
						BigDecimal totalPayment = new BigDecimal(0);
						if (paymentItemList != null && !paymentItemList.isEmpty()) {
							for (PaymentItemDto paymentItemDto : paymentItemList) {
								totalPayment = totalPayment.add(paymentItemDto.getAmount());
							}
						}

						scheduledPayment.setTotalPayment(totalPayment);

						LOGGER.info("getting token for user : {}", scheduledPayment.getUserId());
						List<TokenDto> fcmTokenList = userDao.getFCMToken(scheduledPayment.getUserId());
						if (fcmTokenList != null && !(fcmTokenList.isEmpty())) {
							loanDto = userDao.getLoanAccountDetailsByLoanNum(scheduledPayment.getLoanNumber());
							confNotificationBody.append(" ").append(loanDto.getEncryptedLoanNumber() + " ")
							.append(confirmationNotificationBody2);
							confNotificationBody.append(System.lineSeparator() + "Total Payment: $")
									.append(totalPayment.toString());
							confNotificationBody.append(System.lineSeparator() + "Processed Date: ")
									.append(scheduledPayment.getProcessedDateOfPayment());
							// condition for single user
							for (TokenDto fcmToken : fcmTokenList) {
								boolean isMessageSent = fcm.sendNotificationForPaymentConfirmation(fcmToken.getToken(),
										confirmationNotificationTitle, confNotificationBody.toString());
								if (isMessageSent) {
									messageSentCount++;
									LOGGER.info("Notification sent successfully to user : {}  fcm token ID : {}",
											fcmToken.getUserId(), fcmToken.getTokenId());
								} else {
									LOGGER.info("Notification could not be sent to user :{}  fcm token ID : {}",
											fcmToken.getUserId(), fcmToken.getTokenId());
								}
							}
						} else {
							LOGGER.info("FCM token does not exist for user : {}", scheduledPayment.getUserId());
						}
						if (messageSentCount > 0) {
							boolean isMessageSaved = userDao.saveNotification(scheduledPayment.getUserId(),
									confirmationNotificationTitle, confNotificationBody.toString());
							if (isMessageSaved) {
								LOGGER.info("Notification saved successfully for user : {}",
										scheduledPayment.getUserId());
							} else {
								LOGGER.info("Notification could not be saved for user :{} ",
										scheduledPayment.getUserId());
							}
						} else {
							LOGGER.info("Notification could not be sent to : {}", scheduledPayment.getUserId());
						}
					} else {
						LOGGER.info("User's push notification flag is disabled");
					}
				}
			} else {
				LOGGER.info("No Payment processing date available for any user");
			}
		} catch (Exception exp) {
			LOGGER.error("Scheduler Service method got excption due to which notification sending interrupted : ", exp);
		}
		LOGGER.info("Exit From sendPaymentConfirmation method");
	}

	/**
	 * @author nehas3
	 * @date Aug 6, 2018
	 * @return
	 * @param Description : This is sendPaymentReminderToUser method to send payment
	 *                    confirmation message to user on the given time.
	 */
	@Override
	public void sendPaymentReminderToUser() {
		LOGGER.info("Entering Into sendPaymentReminder method");
		List<ScheduledPaymentDto> scheduledPaymentDtoList = userDao.getScheduledPaymentInfoListOfProcessedUsers();
		int messageSentCount = 0;
		LoanDto loanDto = null;
		try {
			if (scheduledPaymentDtoList != null) {
				for (ScheduledPaymentDto scheduledPayment : scheduledPaymentDtoList) {
					StringBuilder paymenteminderNotificationBody = null;
					paymenteminderNotificationBody = new StringBuilder(this.paymentReminderNotificationBody1);
					UserDto userDto = userDao.getUserByUserId(scheduledPayment.getUserId());
					if (userDto.isNotificationEnabled()) {
						List<TokenDto> fcmTokenList = userDao.getFCMToken(scheduledPayment.getUserId());
						if (!(fcmTokenList.isEmpty())) {
							loanDto = userDao.getLoanAccountDetailsByLoanNum(scheduledPayment.getLoanNumber());
							paymenteminderNotificationBody.append(" ").append(loanDto.getEncryptedLoanNumber() + " ")
							.append(paymentReminderNotificationBody2);
							scheduledPayment.setTotalPayment(loanDto.getMonthlyPaymentForTotal()
									.add(scheduledPayment.getExtraPrincipal()
											.add(scheduledPayment.getExtraEscrow().add(scheduledPayment
													.getLateFeesPaid().add(scheduledPayment.getNsfFeesPaid())))));

							paymenteminderNotificationBody.append(System.lineSeparator() + "Loan Num: ")
									.append(loanDto.getEncryptedLoanNumber());
							paymenteminderNotificationBody.append(System.lineSeparator() + "Due Date: ")
									.append(loanDto.getNextDue());
							paymenteminderNotificationBody.append(System.lineSeparator() + "Scheduled Date: ")
									.append(scheduledPayment.getNextScheduledDate());
							paymenteminderNotificationBody.append(System.lineSeparator() + "Monthly Payment: $")
									.append(loanDto.getMonthlyPayment());

							String firstDigitOfLoan = String.valueOf(loanDto.getLoanNumber()).substring(0, 1);

							if (firstDigitOfLoan.equals("9")) {
								if (scheduledPayment.getExtraPrincipal().compareTo(BigDecimal.ZERO) > 0) {
									paymenteminderNotificationBody.append(System.lineSeparator() + "Addl Principal: $")
											.append(scheduledPayment.getExtraPrincipal().toString());
								}

								if (scheduledPayment.getLateFeesPaid().compareTo(BigDecimal.ZERO) > 0) {
									paymenteminderNotificationBody.append(System.lineSeparator() + "Late Fees: $")
											.append(scheduledPayment.getLateFeesPaid().toString());
								}

								if (scheduledPayment.getNsfFeesPaid().compareTo(BigDecimal.ZERO) > 0) {
									paymenteminderNotificationBody.append(System.lineSeparator() + "NSF Fees: $")
											.append(scheduledPayment.getNsfFeesPaid().toString());
								}

								paymenteminderNotificationBody.append(System.lineSeparator() + "Total Pmt Amt:  $")
										.append(scheduledPayment.getTotalPayment().toString());
							} else {
								if (scheduledPayment.getExtraPrincipal().compareTo(BigDecimal.ZERO) > 0) {
									paymenteminderNotificationBody.append(System.lineSeparator() + "Addl Principal: $")
											.append(scheduledPayment.getExtraPrincipal().toString());
								}

								if (scheduledPayment.getExtraEscrow().compareTo(BigDecimal.ZERO) > 0) {
									paymenteminderNotificationBody.append(System.lineSeparator() + "Addl Escrow: $")
											.append(scheduledPayment.getExtraEscrow().toString());
								}

								if (scheduledPayment.getLateFeesPaid().compareTo(BigDecimal.ZERO) > 0) {
									paymenteminderNotificationBody.append(System.lineSeparator() + "Late Fees: $")
											.append(scheduledPayment.getLateFeesPaid().toString());
								}

								if (scheduledPayment.getNsfFeesPaid().compareTo(BigDecimal.ZERO) > 0) {
									paymenteminderNotificationBody.append(System.lineSeparator() + "NSF Fees: $")
											.append(scheduledPayment.getNsfFeesPaid().toString());
								}
								Integer month = scheduledPayment.getScheduledDate().toInstant()
										.atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();
								if ("3".equals(month.toString()) || "4".equals(month.toString())
										|| "5".equals(month.toString())) {
									paymenteminderNotificationBody.append(System.lineSeparator() + "Total Pmt Amt:  $")
											.append(scheduledPayment.getTotalPayment().toString()).append("*").append(System.lineSeparator());

									paymenteminderNotificationBody.append(System.lineSeparator()
											+ " *Amount changes automatically to accommodate escrow analysis.");
								} else {
									paymenteminderNotificationBody.append(System.lineSeparator() + "Total Pmt Amt:  $")
											.append(scheduledPayment.getTotalPayment().toString());
								}
							}

							for (TokenDto fcmToken : fcmTokenList) {
								boolean isMessageSent = fcm.sendNotificationForPaymentReminder(fcmToken.getToken(),
										paymentReminderNotificationTitle, paymenteminderNotificationBody.toString());
								if (isMessageSent) {
									messageSentCount++;
									LOGGER.info("Notification sent successfully to user : {}  fcm token ID : {}",
											fcmToken.getUserId(), fcmToken.getTokenId());

								} else {
									LOGGER.info("Notification could not be sent to user : {}  fcm token ID : {}",
											fcmToken.getUserId(), fcmToken.getTokenId());
								}
							}
						} else {
							LOGGER.error("FCM token does not exist for user : {}", scheduledPayment.getUserId());
						}
						if (messageSentCount > 0) {
							boolean isMessageSaved = userDao.saveNotification(scheduledPayment.getUserId(),
									paymentReminderNotificationTitle, paymenteminderNotificationBody.toString());
							if (isMessageSaved) {
								LOGGER.info("Notification saved successfully for user : {}",
										scheduledPayment.getUserId());
							} else {
								LOGGER.info("Notification could not be saved for user : {}",
										scheduledPayment.getUserId());
							}
						} else {
							LOGGER.info("Notification could not be sent to user: {}", scheduledPayment.getUserId());
						}
					} else {
						LOGGER.info("User's push notification flag is disabled");
					}
				}
			} else {
				LOGGER.info("No Payment Is Sceduled For User");
			}
		} catch (Exception exp) {
			LOGGER.error("Scheduler service method got excption due to which notification sending interrupted : ", exp);
		}
		LOGGER.info("Exit From sendPaymentReminder method");
	}

	/**
	 * @author nehas3
	 * @date Aug 27, 2018
	 * @return void Description : This is deletenotificationsByScheduler method to
	 *         delete FCM notifications periodically.
	 */
	@Override
	public void deletenotificationsByScheduler() {
		LOGGER.info("Entering Into deletenotificationbyScheduler method");
		boolean isNotificationDeleted = userDao.deleteNotificationsByScheduler(numberOfDaysToRemoveNotifications);
		if (isNotificationDeleted) {
			LOGGER.info("Notification of 90 days before,has been deleted successfully");
		} else {
			LOGGER.info("No notifications are available to delete");
		}
		LOGGER.info("Exit from deletenotificationbyScheduler method");
	}
}
