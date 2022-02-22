/* 
 * ===========================================================================
 * File Name MessageReader.java
 * 
 * Created on Aug 29, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: MessageReader.java,v $
 * ===========================================================================
 */
package org.uhc.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author nehas3
 * @date Aug 29, 2018
 * @Description : This is MessageReader class to load all the messages from
 *              messages.properties file once and use it at required places
 */
@PropertySource("classpath:messages.properties")
@Scope("singleton")
@Component
public class MessageReader {

	@Autowired
	private Environment env;

	/**
	 * @author nehas3
	 * @date Sep 5, 2018
	 * @return Map<String,String> Description : Loading all the messages stored in
	 *         messages.property as soon as the application starts up and using
	 *         respective response for each request
	 */
	public Map<String, String> getPropertyFileMesssages() {
		Map<String, String> message = new HashMap<>();
		
		/* Login Service Message */
		message.put("login.error", env.getProperty("login.error"));

		/* Account Service Message */
		message.put("account.error", env.getProperty("account.error"));

		/* Account History Service Message */
		message.put("accountHistory.error", env.getProperty("accountHistory.error"));

		/* User Not Available Message */
		message.put("user.not.available.error", env.getProperty("user.not.available.error"));

		/* Banking Info Service Messages */
		message.put("bankingInfoNotFound.error", env.getProperty("bankingInfoNotFound.error"));
		message.put("bankingInfo.execption.error", env.getProperty("bankingInfo.execption.error"));

		/* Cancel Payment Service Messages */
		message.put("cancelpayment.success.message", env.getProperty("cancelpayment.success.message"));
		message.put("cancelpayment.failure.error", env.getProperty("cancelpayment.failure.error"));
		message.put("canclepayment.failure.incorrectUser", env.getProperty("canclepayment.failure.incorrectUser"));
		message.put("cancelpayment.failure.exp", env.getProperty("cancelpayment.failure.exp"));

		/* Delete Banking Info Service Messages */
		message.put("deleteBankingInfo.success", env.getProperty("deleteBankingInfo.success"));
		message.put("deleteBankingInfo.failure", env.getProperty("deleteBankingInfo.failure"));

		/* Delete Notification Service Messages */
		message.put("deletenotification.success", env.getProperty("deletenotification.success"));
		message.put("deletenotification.failure", env.getProperty("deletenotification.failure"));

		/* MessageId Not Available Message */
		message.put("messageIdNotFound.error", env.getProperty("messageIdNotFound.error"));

		/* Escrow Service Message */
		message.put("escrowNotFound.error", env.getProperty("escrowNotFound.error"));

		/* Loan Not Available Message */
		message.put("loanNotFound.error", env.getProperty("loanNotFound.error"));

		/* Get Notification Details Service Message */
		message.put("getNotificationDetails.success", env.getProperty("getNotificationDetails.success"));
		message.put("getNotificationDetails.failure.messageNotFound",
				env.getProperty("getNotificationDetails.failure.messageNotFound"));
		message.put("getNotificationDetails.failure.tokenNotFound",
				env.getProperty("getNotificationDetails.failure.tokenNotFound"));
		message.put("getNotificationDetails.failureExp", env.getProperty("getNotificationDetails.failureExp"));

		/* Get Scheduled Payment Service in cancellation Message */
		message.put("getScheduledPayment.success", env.getProperty("getScheduledPayment.success"));
		message.put("getScheduledPayment.failure", env.getProperty("getScheduledPayment.failure"));
		message.put("getScheduledPayment.failureExp", env.getProperty("getScheduledPayment.failureExp"));

		/* Loan Statement Service Message */
		message.put("loanStatement.error", env.getProperty("loanStatement.error"));

		/* Logout Service Message */
		message.put("logout.success", env.getProperty("logout.success"));
		message.put("logout.failure", env.getProperty("logout.failure"));

		/* Registration service Message */
		message.put("registration.success", env.getProperty("registration.success"));
		message.put("registration.failure", env.getProperty("registration.failure"));
		message.put("registration.loanNotFound", env.getProperty("registration.loanNotFound"));
		message.put("registration.recordsNotFound", env.getProperty("registration.recordsNotFound"));
		message.put("registration.click.agree", env.getProperty("registration.click.agree"));
		message.put("user.alreadyExist", env.getProperty("user.alreadyExist"));

		/* Scheduled Payment Service Message */
		message.put("scheduledPayment.success", env.getProperty("scheduledPayment.success"));
		message.put("scheduledPayment.failure", env.getProperty("scheduledPayment.failure"));
		message.put("scheduledPayment.failureExp", env.getProperty("scheduledPayment.failureExp"));
		message.put("scheduledPayment.nextMonthForOneTimeNotExistMessage",
				env.getProperty("scheduledPayment.nextMonthForOneTimeNotExistMessage"));
		message.put("scheduledPayment.nextMonthForRecurringNotExistMessage",
				env.getProperty("scheduledPayment.nextMonthForRecurringNotExistMessage"));
		message.put("scheduledPayment.failure.incorrectNextAvailableMonthMessage",
				env.getProperty("scheduledPayment.failure.incorrectNextAvailableMonthMessage"));
		message.put("scheduledPayment.failure.invalidScheduleTypeMessage",
				env.getProperty("scheduledPayment.failure.invalidScheduleTypeMessage"));
		message.put("scheduledPayment.todayPaymentExistMessage",
				env.getProperty("scheduledPayment.todayPaymentExistMessage"));
		message.put("scheduledPayment.todayPaymentExistMessage1",
				env.getProperty("scheduledPayment.todayPaymentExistMessage1"));
		message.put("scheduledPayment.todayPaymentExistMessage2",
				env.getProperty("scheduledPayment.todayPaymentExistMessage2"));
		message.put("scheduledPayment.failure.forStopWeb", env.getProperty("scheduledPayment.failure.forStopWeb"));
		message.put("scheduledPayment.failureIfBankDetailsNotAvailable",
				env.getProperty("scheduledPayment.failureIfBankDetailsNotAvailable"));

		/* Tax Info Service Message */
		message.put("taxinfo.error", env.getProperty("taxinfo.error"));

		/* Update Banking Info Service Message */
		message.put("bankingInfo.success", env.getProperty("bankingInfo.success"));
		message.put("bankingInfo.failure", env.getProperty("bankingInfo.failure"));
		message.put("bankingInfo.failure.incorrectRoutingNumber",
				env.getProperty("bankingInfo.failure.incorrectRoutingNumber"));
		message.put("bankingInfo.failure.notSaved", env.getProperty("bankingInfo.failure.notSaved"));

		/* Update Email Service Message */
		message.put("upadateEmail.success", env.getProperty("upadateEmail.success"));
		message.put("upadateEmail.incorectUserDetails", env.getProperty("upadateEmail.incorectUserDetails"));
		message.put("upadateEmail.emptyEmail", env.getProperty("upadateEmail.emptyEmail"));
		message.put("upadateEmail.failureExp", env.getProperty("upadateEmail.failureExp"));

		/* Update Online Statement Preference Service Message */
		message.put("updateOnlineStatementPref.success", env.getProperty("updateOnlineStatementPref.success"));
		message.put("updateOnlineStatementPref.failure.statementNotFound",
				env.getProperty("updateOnlineStatementPref.failure.statementNotFound"));
		message.put("updateOnlineStatementPref.failureExp", env.getProperty("updateOnlineStatementPref.failureExp"));

		/* Update Password Service Message */
		message.put("updatePassword.success", env.getProperty("updatePassword.success"));
		message.put("updatePassword.failure", env.getProperty("updatePassword.failure"));
		message.put("updatePassword.incorrectPassword", env.getProperty("updatePassword.incorrectPassword"));
		message.put("updatePassword.invalidPassword", env.getProperty("updatePassword.invalidPassword"));

		/* Update Read Status Service Message */
		message.put("updateReadStatus.success", env.getProperty("updateReadStatus.success"));
		message.put("updateReadStatus.failure", env.getProperty("updateReadStatus.failure"));
		message.put("updateReadStatus.failure.readStatusNotTrue",
				env.getProperty("updateReadStatus.failure.readStatusNotTrue"));

		/* Update User Name Service Message */
		message.put("upadteUserName.success", env.getProperty("upadteUserName.success"));
		message.put("upadteUserName.failure", env.getProperty("upadteUserName.failure"));
		message.put("upadteUserName.incorrectUserName", env.getProperty("upadteUserName.incorrectUserName"));
		message.put("upadteUserName.failureForNullUser", env.getProperty("upadteUserName.failureForNullUser"));
		message.put("upadteUserName.failureIfUserAlreadyExist",
				env.getProperty("upadteUserName.failureIfUserAlreadyExist"));

		/* Update User Profile Service Message */
		message.put("upadteUserProfile.success", env.getProperty("upadteUserProfile.success"));
		message.put("upadteUserProfile.failure", env.getProperty("upadteUserProfile.failure"));
		message.put("upadteUserProfile.failureExp", env.getProperty("upadteUserProfile.failureExp"));

		/* Update Incorrect Bank Info Service Message */
		message.put("upadteIncorrectBankInfo.success", env.getProperty("upadteIncorrectBankInfo.success"));
		message.put("upadteIncorrectBankInfo.failure", env.getProperty("upadteIncorrectBankInfo.failure"));
		message.put("upadteIncorrectBankInfo.failureForInvalidId",
				env.getProperty("upadteIncorrectBankInfo.failureForInvalidId"));
		
		/* Update Incorrect Bank Info Service Message */
		message.put("upadtePushNotification.success", env.getProperty("upadtePushNotification.success"));
		message.put("upadtePushNotification.failure", env.getProperty("upadtePushNotification.failure"));
		message.put("upadtePushNotification.failureForInvalidId",
				env.getProperty("upadtePushNotification.failureForInvalidId"));
		message.put("communication.success",env.getProperty("communication.success"));
		message.put("communication.error",env.getProperty("communication.error"));
		message.put("communicationMessageList.error",env.getProperty("communicationMessageList.error"));
		message.put("communicationMessageList.success",env.getProperty("communicationMessageList.success"));
		message.put("osLatestVersion.error",env.getProperty("osLatestVersion.error"));
		message.put("osLatestVersion.success",env.getProperty("osLatestVersion.success"));
		
		message.put("message.not.viewed",env.getProperty("message.not.viewed"));
		message.put("message.non.displayonce",env.getProperty("message.non.displayonce"));
		message.put("message.viewed",env.getProperty("message.viewed"));
		message.put("msg.info",env.getProperty("msg.info"));
		message.put("faqCategoryList.success",env.getProperty("faqCategoryList.success"));
		message.put("faqCategoryList.error",env.getProperty("faqCategoryList.error"));
		message.put("faqList.success",env.getProperty("faqList.success"));
		message.put("faqList.error",env.getProperty("faqList.error"));
		
		return message;
	}

	public Map<String, Integer> getStatusCode() {
		Map<String, Integer> messageCode = new HashMap<>();
		messageCode.put("User is validated successfully", 200);
		messageCode.put("Key validated successfully", 200);
		messageCode.put("Account recovery done successfully", 200);
		messageCode.put("The information provided doesn’t match our records.", 201);
		messageCode.put("User does not exists", 202);
		messageCode.put("User already exists", 202);
		messageCode.put("Invalid key provided", 203);
		messageCode.put("Key does not exist", 205);
		messageCode.put("Could not validate user because of exception", 204);
		messageCode.put("Could not validate recovery key because of exception", 204);
		messageCode.put("Could not complete account recovery process because of exception", 204);
		messageCode.put("Invalid password", 402);
		messageCode.put("Email address does not exist", 206);
		messageCode.put("Username already exist", 207);
		messageCode.put("The Profile can not be created at this time, please contact UHC", 209);
		messageCode.put("Communication Message Return successfully", 210);
		messageCode.put("this message already viewed by user", 211);
		messageCode.put("Viewed record not saved", 212);
		messageCode.put("No message Information found", 213);
		messageCode.put("Successfully got the communication message Records", 214);
		messageCode.put("Account recovery could not be because of exception", 500);

		return messageCode;
	}
}
