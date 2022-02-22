/*
 * ===========================================================================
 * File Name UserDao.java
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
 * $Log: UserDao.java,v $
 * ===========================================================================
 */
package org.uhc.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.uhc.controller.envelop.request.CommunicationViewMessageReq;
import org.uhc.controller.envelop.request.LoanStatementsRequest;
import org.uhc.controller.envelop.request.RecoverAccountReq;
import org.uhc.controller.envelop.request.RecoveryAccountReq;
import org.uhc.controller.envelop.request.RegistrationRequest;
import org.uhc.controller.envelop.request.ScheduledPaymentRequest;
import org.uhc.controller.envelop.request.UpdateBankingInfoRequest;
import org.uhc.controller.envelop.request.UpdateEmailRequest;
import org.uhc.controller.envelop.request.UpdatePasswordRequest;
import org.uhc.controller.envelop.request.UpdatePushNotificationFlagRequest;
import org.uhc.controller.envelop.request.UpdateUserNameRequest;
import org.uhc.dao.dto.ACHPaymentsDto;
import org.uhc.dao.dto.AccountRecoveryDto;
import org.uhc.dao.dto.BankingInfoDto;
import org.uhc.dao.dto.CommunicationDto;
import org.uhc.dao.dto.CommunicationViewDto;
import org.uhc.dao.dto.ErrorCodeForBankingInfoDto;
import org.uhc.dao.dto.EscrowDto;
import org.uhc.dao.dto.FAQCategoryDto;
import org.uhc.dao.dto.FAQDetailDto;
import org.uhc.dao.dto.FAQDto;
import org.uhc.dao.dto.GetNotificationDetailsDto;
import org.uhc.dao.dto.GetScheduledPaymentDto;
import org.uhc.dao.dto.GetScheduledPaymentForCancellationDto;
import org.uhc.dao.dto.IncorrectBankingInfoDto;
import org.uhc.dao.dto.LatestVersionDto;
import org.uhc.dao.dto.LoanDto;
import org.uhc.dao.dto.LoanStatementDto;
import org.uhc.dao.dto.OnlineStatementsPrefDto;
import org.uhc.dao.dto.PaymentDto;
import org.uhc.dao.dto.PaymentItemDto;
import org.uhc.dao.dto.ProcessedPaymentDto;
import org.uhc.dao.dto.PropertyDto;
import org.uhc.dao.dto.PushNotificationFlagDto;
import org.uhc.dao.dto.ScheduledPaymentDto;
import org.uhc.dao.dto.ScheduledPaymentHistoryDto;
import org.uhc.dao.dto.TaxInfoDto;
import org.uhc.dao.dto.TokenDto;
import org.uhc.dao.dto.TransactionDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.util.Constants.ScheduledPaymentType;

/**
 * @author nehas3
 * @date May 25, 2018
 * @Description :
 */
@Repository
public interface UserDao {

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return UserDto
	 * @param userName
	 * @param password
	 * @return UserDto
	 * @Description : This is validateUser method to authenticate user.
	 */
	UserDto validateUser(String userName, String password);

	/**
	 * This method is created to enable and disable notification flag for user
	 * @author nehas3
	 * @since Oct 22, 2020
	 * @param updatePushNotificationFlagRequest
	 * @return boolean
	 */
	boolean updatePushNotificationFlag(UpdatePushNotificationFlagRequest updatePushNotificationFlagRequest);

	/**
	 * getNOtificationFlag is created to update notification falg
	 * @author nehas3
	 * @since Oct 19, 2020
	 * @param userId
	 * @return
	 */
	PushNotificationFlagDto getNOtificationFlag(int userId);

	/**
	 * getPushNOtificationFlag method is created to trace notification value if it
	 * is enabled or not
	 *
	 * @author nehas3
	 * @since Oct 19, 2020
	 * @param userId
	 * @return
	 */
	int getPushNOtificationFlag(int userId);

	/**
	 * @author nehas3
	 * @date Oct 25, 2018
	 * @return UserDto
	 * @param user details
	 * @Description : This is updateUser method to update user details of basis of
	 *              some information
	 */
	void updateUser(UserDto user);

	/**
	 * @author nehas3
	 * @date Aug 24, 2018
	 * @return boolean
	 * @param userId
	 * @return boolean
	 * @Description : when user logs in, the login status gets updated in true
	 */
	boolean updateLoginStatus(int userId);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return List<LoanDto>
	 * @param userId
	 * @return list of LoanDto
	 * @Description : this is getLoanAccountsByUserId that will return list of loan
	 *              accounts on the basis of userId.
	 */
	List<LoanDto> getLoanAccountsByUserId(int userId);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return List<Long>
	 * @param userId
	 * @return list of loanNumbers
	 * @Description : this is checkForRefis to find if their is any refinanced loan available.
	 */
	public List<Long> checkForRefis(List<LoanDto> loans);

	/**
     * @param loanNumber
     * @return boolean based on if the input loan number is a CROWN loan or not
     */
    public boolean isCrownLoanNumber(long loanNumber);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return LoanDto
	 * @param loanNumber
	 * @return LoanDto
	 * @Description : this is getLoanAccountDetailsByLoanNum that will return loan
	 *              account details on basis of loanNumber.
	 */
	LoanDto getLoanAccountDetailsByLoanNum(long loanNumber);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return PropertyDto
	 * @param loanNumber
	 * @return PropertyDto
	 * @Description : this is getPropertyInfoByLoanNum method that will return
	 *              property info of user on basis of loanNumber.
	 */
	PropertyDto getPropertyInfoByLoanNum(long loanNumber);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return List<EscrowDto>
	 * @param loanList
	 * @return list of EscrowDto
	 * @Description : this is getEscrowInformationByLoanNum method that will return
	 *              list of escrow info on the basis of list of loan account of a
	 *              particular user
	 */
	List<EscrowDto> getEscrowInformationByLoanNum(long loanNumber);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return List<TransactionDto>
	 * @param loanNumber
	 * @return list of account history or transaction of user done in past.
	 * @@Description : this is getAccountHistoryByLoanNum method that will return
	 *               list of account history or transaction of user done in past.
	 */
	List<TransactionDto> getAccountHistoryByLoanNum(Long loanNumber);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return List<Long>
	 * @param username
	 * @return list of loan account numbers on basis of username
	 * @Description : this is getLoanAccountsByUserName that will return list of
	 *              loan account numbers on basis of username
	 */
	List<Long> getLoanAccountsByUserName(String username);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return List<LoanStatementDto>
	 * @param loanStatementsRequest
	 * @return list of loan statements
	 * @Description : this is getLoanStatementByLoanNumber method that will return
	 *              list of loan statements on basis of requested parameters.
	 */
	List<LoanStatementDto> getLoanStatementByLoanNumber(LoanStatementsRequest loanStatementsRequest);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return boolean
	 * @param registrationRequest
	 * @return boolean
	 * @Description : this is registerUser method that will return success or
	 *              failure on registering user request.
	 */
	boolean registerUser(RegistrationRequest registrationRequest);

	public void addUserLoanRecord(int user_id, long loan_number, int sequence_number);

	/**
	 * This method has been created to make push notification flag auto enabled while registering
	 * @author nehas3
	 * @since Oct 19, 2020
	 * @param registrationRequest
	 * @return true or false as per the update
	 */
	boolean insertPushNotificationFlag(PushNotificationFlagDto pnfDto);

	/**
	 * @author nehas3
	 * @date jun 25, 2018
	 * @return boolean
	 * @param registrationRequest
	 * @return boolean
	 * @Description : this is insertOnlinePrefStatus method that will return success or
	 *              failure on inserting data to online statement pref table.
	 */
	boolean insertOnlinePrefStatus(String loanNumber, int onlineStatementsStatus);

	/**
	 * @author nehas3
	 * @date jun 25, 2018
	 * @return boolean
	 * @param registrationRequest
	 * @return boolean
	 * @Description : this is updateOnlinePrefStatus method that will return success or
	 *              failure on updating data to online statement pref table.
	 */
	public boolean updateOnlinePrefStatus(String loanNumber);

	/**
	 * @author nehas3
	 * @date Aug 28, 2018
	 * @return boolean
	 * @param userId
	 * @param token
	 * @return boolean
	 * @Description : Once the user logs in, the FCM token gets save with this
	 *              method.
	 */
	boolean saveUserInFCMTokenTable(int userId, String token);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return UserDto
	 * @param username username
	 * @return UserDto
	 * @Description : this is getUserByUsername method to get username on basis of
	 *              entered user name.
	 */
	UserDto getUserBySsn(String ssn);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return UserDto
	 * @param username username
	 * @return UserDto
	 * @Description : this is getUserByUsername method to get username on basis of
	 *              entered user name.
	 */
	UserDto getUserByUsername(String username);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return UserDto
	 * @param username
	 * @param pwd
	 * @return UserDto
	 * @Description : this is getUserByUsername method to get user name on basis of
	 *              entered user name and PWD.
	 */
	UserDto getUserByUsernameAndPWD(String username, String pwd);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return UserDto
	 * @param userId
	 * @return UserDto userId
	 * @Description : this is getUserByUserId method to get user on basis of entered
	 *              userId.
	 */
	UserDto getUserByUserId(int userId);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return UserDto
	 * @param userId
	 * @param pwd
	 * @return UserDto userId
	 * @Description : this is getUserByUserId method to get user on basis of entered
	 *              userId and pwd.
	 */
	public UserDto getUserByUserIdAndPWD(int userId, String pwd);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return boolean
	 * @param loanNumber
	 * @param ssn
	 * @param zip
	 * @return true or false
	 * @Description : this is checkLoanAcExist method to check if loan exists of a
	 *              particular user on basis of loanNumber,ssn, zip.
	 */
	boolean checkLoanAcExist(String loanNumber, String ssn, String zip);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return boolean
	 * @param loanNumber
	 * @param ssn
	 * @param zip
	 * @return true or false
	 * @Description : this is checkLoanAcExist method to check if loan exists of a
	 *              particular user on basis of loanNumber,ssn, zip.
	 */
	 List<String> checkLoanAcExistNew(String loanNumber, String ssn, String zip);

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return List<TaxInfoDto>
	 * @param loanNumber
	 * @return List of tax information for user.
	 * @Description : This is getTaxInfobyLoanNumber to get tax information of user
	 *              on basis of their loanNumber.
	 */
	List<TaxInfoDto> getTaxInfobyLoanNumber(Long loanNumber);

	/**
	 * @author nehas3
	 * @date May 28, 2018
	 * @return OnlineStatementsPrefDto
	 * @param loanNumber
	 * @return current status of Online statement preference.
	 * @Description : this is getStatementPrefStatus to get current status of Online
	 *              statement preference.
	 */
	OnlineStatementsPrefDto getStatementPrefStatus(long loanNumber);

	/**
	 * @author nehas3
	 * @date May 28, 2018
	 * @return boolean
	 * @param UpdateEmailRequest
	 * @return true or false for email update
	 * @Description : this is updateEmail method to update user's email on basis of
	 *              requested parameters.
	 */
	boolean updateEmail(UpdateEmailRequest updateEmailRequest);

	/**
	 * @author nehas3
	 * @date May 29, 2018
	 * @return boolean
	 * @param updateUserNameRequest
	 * @return true or false on update user name
	 * @Description : this is updateUserName method to update user name on basis of
	 *              requested parameter
	 */
	boolean updateUserName(UpdateUserNameRequest updateUserNameRequest);

	/**
	 * @author nehas3
	 * @date September 23, 2018
	 * @return boolean
	 * @param incorrectBankInfoId
	 * @return true or false on update user name
	 * @Description : this is updateIncorrectBankingInfo method to update update Incorrect Banking Info on basis of
	 *              requested parameter
	 */
	boolean updateIncorrectBankingInfo(int incorrectBankInfoId);

	/**
	 * @author nehas3
	 * @date May 29, 2018
	 * @return boolean
	 * @param updatePasswordRequest
	 * @return true or false on updating user email
	 * @Description : this is updatePassword to update user password on basis of
	 *              requested parameters.
	 */
	boolean updatePassword(UpdatePasswordRequest updatePasswordRequest);

	/**
	 * @author nehas3
	 * @date May 31, 2018
	 * @return boolean
	 * @param isOnlineStatementEnabled
	 * @param loanList
	 * @return true or false on updating online statement preference.
	 * @Description : this is updateOnlineStatementPref method to update updating
	 *              online statement preference on the basis of requested parameter.
	 */
	boolean updateOnlineStatementPref(Boolean isOnlineStatementEnabled, List<Long> loanList);

	/**
	 * @author nehas3
	 * @date June 5, 2018
	 * @return BankingInfoDto
	 * @param userId
	 * @return banking information of user
	 * @Description : this is getBankingInfo to get banking info of user on basis of
	 *              userId.
	 */
	BankingInfoDto getBankingInfo(int userId);

	/**
	 * @author nehas3
	 * @date June 4, 2018
	 * @return boolean
	 * @param userId
	 * @param updateBankingInfoRequest
	 * @return true or false on updating user's banking info.
	 * @Description : this is updateBankingInfo method to update user's banking info
	 *              on the basis of requested parameters.
	 */
	boolean updateBankingInfo(Integer id, UpdateBankingInfoRequest updateBankingInfoRequest);

	boolean addBankingInfo(UpdateBankingInfoRequest updateBankingInfoRequest);

	/**
	 * @author nehas3
	 * @date June 7, 2018
	 * @return boolean
	 * @param userId
	 * @return success or failure on delete banking info
	 * @Description : this is deleteBankingInfo method to delete banking info of
	 *              user on basis of userId
	 */
	boolean deleteBankingInfo(int userId);

	/**
	 * @author nehas3
	 * @date Jun 25, 2018
	 * @return boolean
	 * @param routingNumber
	 * @return boolean
	 * @Description : this is to confirm if routing number is WhiteListed or not
	 */
	boolean isRoutingNumberWhiteListed(String routingNumber);

	/**
	 * @author nehas3
	 * @date Jun 25, 2018
	 * @return boolean
	 * @param routingNumber
	 * @param accountNumber
	 * @return boolean
	 * @Description : this is to confirm if routing number is Black Listed or not
	 */
	IncorrectBankingInfoDto isRoutingNumberBlackListed(String routingNumber, String accountNumber);

	/**
	 * @author nehas3
	 * @date August 23, 2019
	 * @return IncorrectBankingInfoDto
	 * @param routingNumber
	 * @param accountNumber
	 * @return boolean
	 * @Description : this is to confirm if banking info is valid or not
	 */
	List<IncorrectBankingInfoDto> validateBankingInfo(int userId);

	/**
	 * @author nehas3
	 * @date August 23, 2019
	 * @return IncorrectBankingInfoDto
	 * @param routingNumber
	 * @param accountNumber
	 * @return boolean
	 * @Description : this is to get data from incorrect bank info table.
	 */
	IncorrectBankingInfoDto getIncorrectBankingInfo(int incorrectBankingInfoId);

	/**
	 * @author nehas3
	 * @date August 23, 2019
	 * @return ErrorCodeForBankingInfoDto
	 * @param errorId
	 * @return boolean
	 * @Description : this is getErrorMessage to get message in response to a
	 *              corresponding error code
	 */
	ErrorCodeForBankingInfoDto getErrorMessage(int errorId);

	/**
	 * @author nehas3
	 * @date Jul 02, 2018
	 * @return boolean
	 * @param userId
	 * @param loanPayment
	 * @return boolean
	 * @Description : This is schedulePayment method to scheduled a payment
	 */
	boolean schedulePayment(int userId, ScheduledPaymentRequest loanPayment, Date scheduledDate, String paymentSource);

	/**
	 * @author nehas3
	 * @date May 13, 2019
	 * @return boolean
	 * @param loanNumber
	 * @return boolean
	 * @Description : This is checkIfLoanAccInStopWeb method to check if the loan
	 *              account exist in stop web before scheduling the payment
	 */
	boolean checkIfLoanAccInStopWeb(long loanNumber);

	/**
	 * @author nehas3
	 * @date Jul 02, 2018
	 * @return List<Integer>
	 * @param paymentType
	 * @param loanNumber
	 * @param cancelled
	 * @return List Of Active payments Info Description : This is
	 *         getActivePaymentByPaymentType method to get Active Payments info
	 */
	List<Integer> getActivePaymentByPaymentType(ScheduledPaymentType paymentType, long loanNumber, int cancelled);

	/**
	 * @author nehas3
	 * @date Jul 02, 2018
	 * @return List<Integer> : list of ids of active payments
	 * @param paymentType
	 * @param loanNumber
	 * @return List Of Active payments Info Description : This is
	 *         getActivePaymentByPaymentType method to get Active Payments info on
	 *         basis of paymentType and loanNumber
	 */
	List<Integer> getActiveTodayPaymentByPaymentType(ScheduledPaymentType paymentType, long loanNumber);

	/**
	 * @author nehas3
	 * @date Jul 02, 2018
	 * @return List<ScheduledPaymentDto>
	 * @param loanNumber
	 * @return List of scheduled payments
	 * @Description : This is getScheduledPayments to get list of already scheduled
	 *              payments.
	 */
	List<ScheduledPaymentDto> getScheduledPayments(long loanNumber);

	/**
	 * @author nehas3
	 * @date Jul 02, 2018
	 * @return List<GetScheduledPaymentDto>
	 * @param loanNumber
	 * @return List of selected scheduled payments.
	 * @Description : This is getScheduledPaymentList to get selected scheduled
	 *              Payment by loanNumber.
	 */
	List<GetScheduledPaymentDto> getScheduledPaymentList(long loanNumber);

	/**
	 * @author nehas3
	 * @date Jul 02, 2018
	 * @return List<GetScheduledPaymentForCancellationDto>
	 * @param loanNumber
	 * @return List of GetScheduled Payments For Cancellation
	 * @Description : This is getScheduledPaymentListForCancellation to get
	 *              Scheduled Payments For Cancellation.
	 */
	List<GetScheduledPaymentForCancellationDto> getScheduledPaymentListForCancellation(long loanNumber);

	/**
	 * @author nehas3
	 * @date Jul 06, 2018
	 * @return Integer
	 * @param loanNumber
	 * @Description : This is getMaxScheduledMonth method to get max scheduled
	 *              month.
	 */
	Integer getMaxScheduledMonth(long loanNumber);

	/**
	 * @author nehas3
	 * @date Aug 28, 2018
	 * @return String
	 * @param loanNumber
	 * @Description : getting maturity date of a loan application
	 */
	String getMaturityPaymentDate(long loanNumber);

	/**
	 * @author nehas3
	 * @date Aug 28, 2018
	 * @return String
	 * @param loanNumber
	 * @Description : getting first month payment date of a loan application
	 */
	String getFirstPaymentDate(long loanNumber);

	/**
	 * @author nehas3
	 * @date Jul 06, 2018
	 * @return BigDecimal
	 * @param loanNumber
	 * @Description : This is getMonthlyPayment method to get monthly payments on
	 *              basis of loanNumber
	 */
	BigDecimal getMonthlyPayment(long loanNumber);

	/**
	 * @author nehas3
	 * @date Jul 18, 2018
	 * @return ScheduledPaymentDto
	 * @param paymentId
	 * @return ScheduledPayment payments
	 * @Description : This is getScheduledPaymentByPaymentId method to get scheduled
	 *              payments by payment id
	 */
	ScheduledPaymentDto getScheduledPaymentByPaymentId(String paymentId);

	/**
	 *
	 * @author nehas3
	 * @date Jul 18, 2018
	 * @return boolean
	 * @param paymentId
	 * @return true or false
	 * @Description : Checking if scheduled payments have been moved to payment
	 *              table.
	 */
	boolean hasPaymentMoved(int paymentId);

	/**
	 * @author nehas3
	 * @date Jul 18, 2018
	 * @return boolean
	 * @param paymentId
	 * @return true or false
	 * @Description : This is cancelPaymentInPaymentTableByPaymentId method to
	 *              cancel payment in payment table.
	 */
	boolean cancelPaymentInPaymentTableByPaymentId(int paymentId);

	/**
	 * @author nehas3
	 * @date Jul 18, 2018
	 * @return boolean
	 * @param paymentId
	 * @param userId
	 * @return true or false
	 * @Description : This is cancelPaymentByPaymentId method to cancel payments in
	 *              scheduled_payment table.
	 */
	boolean cancelPaymentByPaymentId(int paymentId, int userId);

	/**
	 * @author nehas3
	 * @date Jul 18, 2018
	 * @return List<PaymentDto>
	 * @param loans
	 * @return List of payments history
	 * @Description : This is getPaymentHistory method to get payment history
	 */
	List<PaymentDto> getPaymentHistoryByLoanNumber(long loanNumber);

	/**
	 * @author nehas3
	 * @date Jul 18, 2018
	 * @return List<ScheduledPaymentHistoryDto>
	 * @param loans
	 * @return List of Scheduled Payments history
	 * @Description : This is getScheduledPaymentsHistory method to get scheduled
	 *              payments history
	 */
	List<ScheduledPaymentHistoryDto> getScheduledPaymentHistoryByLoanNumber(long loanNumber);

	/**
	 * @author nehas3
	 * @date Aug 6, 2018
	 * @return List<ScheduledPaymentDto>
	 * @return List of scheduled payment that are processed
	 * @Description : Getting processed payment list of users whose payment has been
	 *              processed
	 */
	List<ProcessedPaymentDto> getProcessedPaymentListOfUsers();

	/**
	 * @author nehas3
	 * @date Aug 6, 2018
	 * @return boolean
	 * @param userId
	 * @param token
	 * @return boolean
	 * @Description : Checking if FCM token is already existing in database on basis
	 *              of userId and token.
	 */
	boolean isTokenRegistered(int userId, String token);

	/**
	 * @author nehas3
	 * @date Aug 24, 2018
	 * @return boolean
	 * @param userId
	 * @param notificationTitle
	 * @param notificationBody
	 * @return boolean
	 * @Description : Once the FCM push notification gets sent, the notification
	 *              gets stored in database as same time with the help of
	 *              saveNotification method.
	 */
	boolean saveNotification(int userId, String notificationTitle, String notificationBody);

	/**
	 * @author nehas3
	 * @date Aug 20, 2018
	 * @return List<TokenDto>
	 * @param userId
	 * @return List of tokenDto
	 * @Description : Getting FCM token from database on basis of userId.
	 */
	List<TokenDto> getFCMToken(Integer userId);

	/**
	 * @author nehas3
	 * @date Aug 8, 2018
	 * @return List<ScheduledPaymentDto>
	 * @Description : Getting scheduled payment info list to send the payment
	 *              reminder.
	 */
	List<ScheduledPaymentDto> getScheduledPaymentInfoListOfProcessedUsers();

	/**
	 * @author nehas3
	 * @date Aug 24, 2018
	 * @return List<GetNotificationDetailsDto>
	 * @param userId
	 * @return List of notifications
	 * @Description : Getting list of notifications for a particular user on basis
	 *              of userId.
	 */
	List<GetNotificationDetailsDto> getNotificationDetails(int userId);

	/**
	 * @author nehas3
	 * @date Aug 28, 2018
	 * @return GetNotificationDetailsDto
	 * @param messageId
	 * @return GetNotificationDetailsDto
	 * @Description : Getting all the notifications available in database on basis
	 *              of messageId.
	 */
	GetNotificationDetailsDto getNotificationDetailsByMessageId(int messageId);

	/**
	 * @author nehas3
	 * @date Aug 24, 2019
	 * @return ACHPaymentsDto
	 * @param loanNumber
	 * @Description : getAchPaymentDetails method is created to get ACH payment
	 *              details set by user while scheduling payment
	 */
	ACHPaymentsDto getAchPaymentDetails(long loanNumber);

	/**
	 * @author nehas3
	 * @date Aug 24, 2018
	 * @return boolean
	 * @param messageId
	 * @Description : Updating the read status of a message on the basis of its
	 *              messageId.
	 */
	boolean updateReadStatusOfMessage(int messageId);

	/**
	 * @author nehas3
	 * @date Aug 27, 2018
	 * @return boolean
	 * @param messageId
	 * @Description : Soft deleting notifications on basis of messageId
	 */
	boolean deleteNotification(int messageId);

	/**
	 * @author nehas3
	 * @date Aug 27, 2018
	 * @return boolean
	 * @param userId
	 * @Description : user can log out b using logout API.
	 */
	boolean logout(int userId);

	/**
	 * @author nehas3
	 * @date Aug 27, 2018
	 * @return boolean
	 * @param numberOfDays
	 * @Description : Notification will be deleted periodically on basis of given
	 *              number of days.
	 */
	boolean deleteNotificationsByScheduler(int numberOfDays);

	/**
	 * @author nehas3
	 * @date April 29, 2019
	 * @return boolean
	 * @Description : this is isLoanInStopFile to check if loan exist in stop file
	 *              list
	 */
	public boolean isLoanInStopFile(String loanNumber);

	/**
	 * @author nehas3
	 * @date Aug 13, 2019
	 * @return boolean
	 * @Description : this is validateLoanNumber to check if entered loan number is
	 *              valid or not.
	 */
	public boolean validateLoanNumber(long loanNumber);

	/**
	 * @author nehas3
	 * @date Aug 27, 2018
	 * @return String
	 * @param schedulePaymentId
	 * @Description : this is getPaymentSourceOfScheduledPayment to get payment
	 *              source of already scheduled payment
	 */
	String getPaymentSourceOfScheduledPayment(int schedulePaymentId);

	/**
	 * @author nehas3
	 * @date March 7, 2019
	 * @param confirmationNumber, userId
	 * @return boolean
	 * @Description : this is doesConfirmationNumberExistForUser method to check if
	 *              confirmation number already exists
	 */
	boolean doesConfirmationNumberExistForUser(String confirmationNumber, int userId);

	/**
	 * @author nehas3
	 * @date JAN 25, 2020
	 * @return boolean
	 * @param validateAccountRecoveryReq
	 * @return true or false for inserting key
	 * @Description : this will insert the requested recovery key in db for 30 mins.
	 */
	boolean insertAccountRecoveryKey(int userId, String accRecoveryKey);

	/**
	 * @author nehas3
	 * @date JAN 27, 2020
	 * @return boolean
	 * @param userId
	 * @param accRecoveryKey
	 * @return true or false if key is valid or not
	 * @Description : this will help to validate is key is fresh and not existing.
	 */
	boolean isAccountRecoveryKeyExisting(int userId, String accRecoveryKey);

	/**
	 * @author nehas3
	 * @date JAN 27, 2020
	 * @return AccountRecoveryDto
	 * @param accRecoveryKey
	 * @return account recovery data from database
	 * @Description : this will help to get account recovery data on basis of
	 *              account recovery key
	 */
	AccountRecoveryDto getAccountRecoveryKeyDetails(String accRecoveryKey);

	/**
	 * @author nehas3
	 * @date September 23, 2018
	 * @return boolean
	 * @param recoveruId
	 * @return true or false on update user name
	 * @Description : this is resetRecoveryKeyForFailure method to update update
	 *              Incorrect Banking Info on basis of requested parameter
	 */
	boolean resetRecoveryKeyForFailure(int recoveruId);

	/**
	 * @author nehas3
	 * @date September 25, 2019
	 * @return boolean
	 * @param recoverAccountReq
	 * @return true or false on reset password
	 * @Description : this is resetPassword method to update to reset password from
	 *              user profile screen
	 */
	boolean resetPassword(int id, RecoverAccountReq recoverAccountReq);

	/**
	 * @author nehas3
	 * @date September 25, 2019
	 * @return boolean
	 * @param recoveruId
	 * @return true or false on reset recovery key
	 * @Description : this is resetRecoveryKeyForSuccess method gets called to reset
	 *              some auto generated key, in the process of account recovery
	 */
	boolean resetRecoveryKeyForSuccess(int recoveruId);

	/**
	 * @author nehas3
	 * @date September 25, 2019
	 * @return boolean
	 * @param id
	 * @param recoverAccountReq
	 * @return true or false on reset account
	 * @Description : this is resetAccount method for user to reset their account.
	 */
	boolean resetAccount(int id, RecoverAccountReq recoverAccountReq);

	/**
	 * @author nehas3
	 * @date September 25, 2019
	 * @return boolean
	 * @param userId
	 * @return true or false on reset recovery key
	 * @Description : this is getOldPassword method to get old password from user
	 *              table.
	 */
	public String getOldPassword(int userId);

	/**
	 * @author nehas3
	 * @date September 25, 2019
	 * @return boolean
	 * @param paymentID
	 * @return list of Payment Item
	 * @Description : this is getPaymentItemByPaymentId to get list of payment items
	 *              while making payment
	 */
	List<PaymentItemDto> getPaymentItemByPaymentId(int paymentID);

	List<EscrowDto> getEscrowInformationByLoanNum(List<Long> loanList);

	List<PaymentDto> getPaymentHistory(List<LoanDto> loans);

	List<ScheduledPaymentHistoryDto> getScheduledPaymentHistory(List<LoanDto> loans);

	/**
	 * @author pradeepy
	 * @date Jan 29, 2021
	 * @return CommunicationDto
	 * @param messageId
	 * @Description : this is getCommunicationMessageByMessageId that will return message
	 *              details on basis of messageId.
	 */
	CommunicationDto getCommunicationMessageDetailsByMessageId(long messageId);

	/**
	 * @author pradeepy
	 * @date Feb 01, 2021
	 * @return CommunicationDto
	 * @param messageId
	 * @Description : this is getCommunicationMessageList that will return list of message
	 *              details.
	 */
	List<CommunicationDto> getCommunicationMessageList();

	/**
	 * @author pradeepy
	 * @date feb 5, 2021
	 * @return CommunicationDto
	 * @param messageId
	 * @Description : this is getMessageViewedByMessageIdAndUserId that will return message view
	 *              details on basis of messageId and userId.
	 */
	CommunicationViewDto getMessageViewedByMessageIdAndUserId(CommunicationViewMessageReq messageReq );

	/**
	 * @author pradeepy
	 * @date feb 6, 2021
	 * @return CommunicationViewDto
	 * @param messageReq
	 * @Description : this is saveMessageViewedRecord that will save message view
	 *              details on basis of messageId and userId.
	 */
	boolean saveMessageViewedRecord(CommunicationViewDto viewDto );
	//getOSLatestVersionInfo

	/**
	 * @author pradeepy
	 * @date feb 25, 2021
	 * @return LatstVersionDto
	 * @param OSName
	 * @Description : this is getOSLatestVersionInfoByOSName that will return latest version
	 *              details on basis of OSName.
	 */
	LatestVersionDto getOSLatestVersionInfoByOSName(String osName);


	/**
	 * @author pradeepy
	 * @date May 24, 2021
	 * @return boolean
	 * @param recoveryAccountReq
	 * @return true or false on reset password
	 * @Description : this is resetPassword method to update to reset password from
	 *              user profile screen
	 */
	boolean updatePassword(int id, RecoveryAccountReq recoverAccountReq);

	/**
	 * @author pradeepy
	 * @date May 25, 2021
	 * @return boolean
	 * @param id
	 * @param recoveryAccountReq
	 * @return true or false on reset account
	 * @Description : this is resetAccount method for user to reset their account.
	 */
	boolean updateAccount(int id, RecoveryAccountReq recoverAccountReq);



	/**
	 * @author pradeepy
	 * @date Oct 11, 2021
	 * @return FAQCategoryDto
	 * @Description : this is getFAQCategoryList that will return list of FAQ category.
	 */
	List<FAQCategoryDto> getFAQCategoryList();

	/**
	 * @author pradeepy
	 * @date Oct 11, 2021
	 * @param categoryId
	 * @param application
	 * @return List<FAQDto>
	 * @Description : this is getFAQByCategoryIdAndApp method to get faq list on basis of entered
	 *              categoryId and application i.e : ios, andriod.
	 */
	List<FAQDto> getFAQByCategoryIdAndApp(long categoryId, String app);

	/**
	 * @author pradeepy
	 * @date Oct 11, 2021
	 * @param categoryId
	 * @return List<FAQDto>
	 * @Description : this is getFAQByCategoryIdA method to get faq list on basis of entered
	 *              categoryId.
	 */
	List<FAQDto> getFAQByCategoryId(long categoryId);

	/**
	 * @author pradeepy
	 * @date Oct 11, 2021
	 * @param categoryId
	 * @return List<FAQDto>
	 * @Description : this is getFAQByWordSpecific method to get question answer on word specific.
	 *
	 */
	List<FAQDto> getFAQByWordSpecific(String searchString);
}
