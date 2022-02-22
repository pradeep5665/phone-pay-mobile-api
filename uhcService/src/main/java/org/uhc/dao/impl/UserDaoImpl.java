/*
 * ===========================================================================
 * File Name UserDaoImpl.java
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
 * $Log: UserDaoImpl.java,v $
 * ===========================================================================
 */
package org.uhc.dao.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
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
import org.uhc.dao.UserDao;
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
import org.uhc.dao.dto.RoutingNumberDto;
import org.uhc.dao.dto.ScheduledPaymentDto;
import org.uhc.dao.dto.ScheduledPaymentHistoryDto;
import org.uhc.dao.dto.TaxInfoDto;
import org.uhc.dao.dto.TokenDto;
import org.uhc.dao.dto.TransactionDto;
import org.uhc.dao.dto.UserDto;
import org.uhc.dao.mapper.ACHPaymentsMapper;
import org.uhc.dao.mapper.AccountRecoveryMapper;
import org.uhc.dao.mapper.BankingInfoMapper;
import org.uhc.dao.mapper.CommunicationMapper;
import org.uhc.dao.mapper.CommunicationViewMapper;
import org.uhc.dao.mapper.ErrorCodeForBankingInfoMapper;
import org.uhc.dao.mapper.EsrowMapper;
import org.uhc.dao.mapper.FAQCategoryMapper;
import org.uhc.dao.mapper.FAQMapper;
import org.uhc.dao.mapper.GetNotificationDetailsMapper;
import org.uhc.dao.mapper.GetScheduledForCancellationMapper;
import org.uhc.dao.mapper.GetScheduledMapper;
import org.uhc.dao.mapper.IncorrectBankInfoMapper;
import org.uhc.dao.mapper.LatestOSVersionMapper;
import org.uhc.dao.mapper.LoanMapper;
import org.uhc.dao.mapper.LoanStatementMapper;
import org.uhc.dao.mapper.OnlineStatementPrefMapper;
import org.uhc.dao.mapper.PaymentExtractor;
import org.uhc.dao.mapper.PaymentItemMapper;
import org.uhc.dao.mapper.ProcessedPaymentMapper;
import org.uhc.dao.mapper.PropertyInfoMapper;
import org.uhc.dao.mapper.PushNotificationFlagMapper;
import org.uhc.dao.mapper.RoutingNumberMapper;
import org.uhc.dao.mapper.ScheduledPaymentHistoryMapper;
import org.uhc.dao.mapper.ScheduledPaymentMapper;
import org.uhc.dao.mapper.TaxInfoMapper;
import org.uhc.dao.mapper.TokenMapper;
import org.uhc.dao.mapper.TransactionMapper;
import org.uhc.dao.mapper.UserMapper;
import org.uhc.util.Constants;
import org.uhc.util.Constants.ScheduledPaymentType;

import io.netty.util.internal.StringUtil;

/**
 * @author nehas3
 * @date May 25, 2018
 * @Description : This is DAO layer to perform access, modify, and update
 *              actions on data sources from database
 */
@Repository
public class UserDaoImpl implements UserDao {

	private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

	@Autowired
	private JdbcTemplate webJdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${uhc.homeowner}")
	private String homeowner;

	@Value("${uhc.ldataser}")
	private String ldataser;

	@Value("${uhc.combined}")
	private String combined;

	@Value("${uhc.as400cgi}")
	private String as400cgi;

	@Value("${uhc.uhfalib}")
	private String uhfalib;

	@Value("${uhc.utelib}")
	private String utelib;

	/**
	 * @author nehas3
	 * @return user
	 * @param userName,password
	 * @exception null pointer or IndexOutOfBond
	 * @description validating user with user name and password
	 */
	@Override
	public UserDto validateUser(String userName, String password) {
		LOGGER.debug("validateUser(): userName: {}, password: ********", userName);
		UserDto user = null;
		String lowerCaseUsername = userName.toLowerCase();
		final String SQL = "SELECT us.ID, us.USERNAME, us.FIRST_NAME, us.LAST_NAME, us.EMAIL, us.ROLE, us.LOGIN_FAILS, us.LOCKED, us.LOGIN_STATUS, "
				+ "uf.FLAG_VALUE FROM "
				+ homeowner + ".USER us LEFT JOIN " + homeowner + ".USER_FLAGS uf ON us.ID = uf.USER_ID"
				+ " WHERE us.USERNAME= ? AND us.PWD = ?";
		List<UserDto> list = webJdbcTemplate.query(SQL, new Object[] { lowerCaseUsername, password }, new UserMapper());
		if (list != null && !list.isEmpty()) {
			user = list.get(0);
		}
		LOGGER.debug("validateUser(): userName: {}, isValid: {}", userName,
				(user != null ? user.toString() : "failed!"));
		return user;
	}

	/**
	 * This method is created to enable and disable notification flag for user
	 * @author nehas3
	 * @since Oct 22, 2020
	 * @param updatePushNotificationFlagRequest
	 * @return boolean
	 */
	@Override
	public boolean updatePushNotificationFlag(UpdatePushNotificationFlagRequest updatePushNotificationFlagRequest) {
		LOGGER.debug("updatePushNotificationFlag(): with user: {}", updatePushNotificationFlagRequest.getUserId());
		int rowsAffected = 0;
		boolean isNotificationFlagUpdated = false;
		final String SQL = "UPDATE " + homeowner + ".USER_FLAGS SET FLAG_VALUE = ? WHERE USER_ID = ?";
		rowsAffected = webJdbcTemplate.update(SQL, updatePushNotificationFlagRequest.getFlagValue(), updatePushNotificationFlagRequest.getUserId());
		if (rowsAffected == 1) {
			isNotificationFlagUpdated = true;
		}
		return isNotificationFlagUpdated;
	}

	/**
	 *
	 */
	public PushNotificationFlagDto getNOtificationFlag(int userId) {
		LOGGER.debug("getNOtificationFlag(): userId: {} ", userId);
		PushNotificationFlagDto userFlag = null;
		final String SQL = "SELECT USER_ID, FLAG_NAME_ID, FLAG_VALUE from "
				+ homeowner + ".USER_FLAGS WHERE USER_ID = ?";
		List<PushNotificationFlagDto> list = webJdbcTemplate.query(SQL, new Object[] { userId }, new PushNotificationFlagMapper());
		if (list != null && !list.isEmpty()) {
			userFlag = list.get(0);
		}
		LOGGER.debug("getNOtificationFlag(): exit {}",userId);
		return userFlag;
	}

	public int getPushNOtificationFlag(int userId) {
		LOGGER.debug("getPushNOtificationFlag(): userId: {} ", userId);
		int userFlag = 0;
		final String SQL = "SELECT FLAG_VALUE from "
				+ homeowner + ".USER_FLAGS WHERE USER_ID = ?";
		userFlag = webJdbcTemplate.queryForObject(SQL, new Object[] {userId} , Integer.class);

		LOGGER.debug("getNOtificationFlag(): exit {}",userId);
		return userFlag;
	}

	/**
	 * @author nehas3
	 * @return void
	 * @param user details
	 * @exception null pointer
	 * @description updating user's login status when user logs In.
	 */
	@Override
	public void updateUser(UserDto user) {
		LOGGER.debug("updateUser(): with user: {}", user);
		// Never update password in this method. If you need to update a password, use
		final String SQL = "UPDATE " + homeowner + ".USER SET EMAIL = ?, LOCKED = ?, LOGIN_FAILS = ?  WHERE ID = ?";
		int rows = webJdbcTemplate.update(SQL, user.getEmail(), user.isLocked() ? "Y" : "", user.getLoginFails(),
				user.getUserId());
		if (rows != 1) {
			LOGGER.warn("updateUser(): Updated more than 1 row for user: {}", user);
		}
	}

	/**
	 * @author nehas3
	 * @return user
	 * @param userId
	 * @exception null pointer or IndexOutOfBond
	 * @description updating user's login status when user logs In.
	 */
	@Override
	public boolean updateLoginStatus(int userId) {
		LOGGER.debug("updateLoginStatus(): userId: {}", userId);
		int rowsAffected = 0;
		boolean isLoginStatusUpdated = false;
		final String SQL = "UPDATE " + homeowner + ".USER " + "SET LOGIN_STATUS = 1 WHERE ID = ?";
		rowsAffected = webJdbcTemplate.update(SQL, userId);
		if (rowsAffected == 1) {
			isLoginStatusUpdated = true;
		}
		LOGGER.debug("updateLoginStatus(): isLoginStatusUpdated: {}", isLoginStatusUpdated);
		return isLoginStatusUpdated;
	}

	/**
	 * @param userId
	 * @return loanDtoList
	 * @exception null pointer or IndexOutOfBond
	 * @description getting list of loan account list by using user id
	 */
	@Override
	public List<LoanDto> getLoanAccountsByUserId(int userId) {
		LOGGER.debug("getLoanAccountsByUserId(): userId: {}", userId);
		List<LoanDto> loanDtoList = null;
		final String SQL = "SELECT coalesce(swloan#,0) as stopPayment, " + "blloan as loan,"
				+ "sma240 as unpaidPrincipalBalance," + "sml060 as interestRate," + "sma070 as principalAndInterest,"
				+ "sme010 as escrow," + "sma070+sme010-sms010 as monthlyPayment,"
				+ "date(sma133 concat '-' concat sma132 concat '-' concat sma131) as nextDue,"
				+ "ml040_loan_amt as totalPrincipleAmount, sma200 as lateFees,"
				+ "sma220 as nsfFees,sme040 as escrowBalance," + "sme050 as escrowAdvance " + "a1_seq "
				+ "FROM " + ldataser + ".srvbal, " + ldataser + ".srvalt1, " + homeowner + ".user, " + ldataser + ".srvdsr "
				+ "left outer join "
				+ uhfalib + ".stopweb on smr010 = swloan# "
				+ "WHERE smr010 * 10 = blloan and blloan=a1_loan and id = ? and ssn = a1_soc_sec order by nextdue";
		loanDtoList = webJdbcTemplate.query(SQL, new Object[] { userId }, new LoanMapper());
		LOGGER.debug("getLoanAccountsByUserId(): found {} LoanDto records for userId: {}",
				(loanDtoList != null ? loanDtoList.size() : 0), userId);
		return loanDtoList;
	}

	/**
	 * @param loans
	 * @return loanDtoList of refis
	 * @exception null pointer or IndexOutOfBond
	 * @description getting list of loan account for refis
	 */
	@Override
	public List<Long> checkForRefis(List<LoanDto> loans) {
		if (loans.isEmpty()) {
			return Collections.emptyList();
		}
		String sql = "SELECT M1_LN_NUM FROM " + ldataser + ".SRVMLD WHERE TRIM(M1UD01)='FSR' AND (M1_LN_NUM = ?";
		Long[] loanNumbers = new Long[loans.size()];
		loanNumbers[0] = loans.get(0).getLoanNumber();
		StringBuilder str = new StringBuilder();
		for (int i = 1; i < loans.size(); i++) {
			loanNumbers[i] = loans.get(i).getLoanNumber();
			str.append(" OR M1_LN_NUM = ?");
		}
		sql += str.toString();
		sql += ")";
		return webJdbcTemplate.queryForList(sql, loanNumbers, Long.class);
	}

	@Override
	public boolean isCrownLoanNumber(long loanNumber) {
		return (loanNumber > 5070000000L && loanNumber < 5999999999L);
	}

	/**
	 * @param loanNumber
	 * @return loanDto
	 * @exception null pointer or IndexOutOfBond Getting Loan Account Details on
	 *            basis of loan number
	 */
	@Override
	public LoanDto getLoanAccountDetailsByLoanNum(long loanNumber) {
		LOGGER.debug("getLoanAccountDetailsByLoanNum(): loanNumber: {}", loanNumber);
		LoanDto loanDto = null;
		final String SQL = "SELECT coalesce(swloan#,0) as stopPayment, blloan as loan,sma240 as unpaidPrincipalBalance,sml060 as interestRate,sma070 as principalAndInterest,sme010 as escrow, sma070+sme010-sms010 as monthlyPayment, date(sma133 concat '-' concat sma131 concat '-' concat sma132) as nextDue, ml040_loan_amt as totalPrincipleAmount, sma200 as lateFees, sma220 as nsfFees,sme040 as escrowBalance, sme050 as escrowAdvance "
				+ "FROM " + ldataser + ".srvbal, " + ldataser + ".srvalt1, " + homeowner + ".user, " + ldataser
				+ ".srvdsr left outer join " + uhfalib + ".stopweb on smr010 = swloan# "
				+ "WHERE smr010 * 10 = blloan and blloan=a1_loan and blloan = ? and ssn = a1_soc_sec";
		List<LoanDto> list = webJdbcTemplate.query(SQL, new Object[] { loanNumber }, new LoanMapper());

		if (list != null && !list.isEmpty()) {
			loanDto = list.get(0);
		} else {
			LOGGER.debug("invalid loan number");
		}
		LOGGER.debug("getLoanAccountDetailsByLoanNum(): loanNumber: {}, {}", loanNumber,
				(loanDto != null ? loanDto.toString() : null));
		return loanDto;
	}

	/**
	 * @param loanList
	 * @return escrowDto
	 * @exception null pointer or IndexOutOfBond
	 * @description getting escrow information on the basis of list of loan numbers
	 */
	@Override
	public List<EscrowDto> getEscrowInformationByLoanNum(long loanNumber) {
		LOGGER.debug("getEscrowInformationByLoanNum(): loanNumber has {} entries", loanNumber);
		List<EscrowDto> escrowDto = null;
		final String SQL = "SELECT CASE ESCTYP WHEN '31' THEN 'Property Tax' WHEN '40' THEN 'FHA Insurance' WHEN '41' THEN 'Private Insurance' WHEN '50' THEN 'Hazard Insurance' WHEN '51' THEN 'Flood Insurance' WHEN '66' THEN 'Condo Insurance' END AS \"type\" ,V010 AS \"vendor\" ,VENREF AS \"vendorID\" ,DATE ( CONCAT ( CONCAT ( CONCAT ( CONCAT ( EMATMO , '/' ) , EMATDA ) , '/' ) , EMATYR ) ) AS \"date\" ,ANLAMT AS \"amount\" FROM "
				+ ldataser + ".SRVESCE , " + ldataser + ".SRVVNM WHERE ESCTYP = V001 AND VENDOR = V002 AND LOAN = ?";
		escrowDto = webJdbcTemplate.query(SQL, new Object[] { loanNumber }, new EsrowMapper());
		LOGGER.debug("getEscrowInformationByLoanNum(): found {} escrowDto records", escrowDto.size());
		return escrowDto;
	}

	/**
	 * @param loanNumber
	 * @return propertyDto
	 * @exception null pointer or IndexOutOfBond getting Property details of user on
	 *            the basis of loan number
	 */
	@Override
	public PropertyDto getPropertyInfoByLoanNum(long loanNumber) {
		LOGGER.debug("getPropertyInfoByLoanNum(): loanNumber: {}", loanNumber);
		PropertyDto propertyDto = null;
		final String SQL = "SELECT MAADD1, MACTY, MAST, MAZIP FROM " + combined + ".MPF001 WHERE MALOAN# * 10 = ?";
		List<PropertyDto> list = webJdbcTemplate.query(SQL, new Object[] { loanNumber }, new PropertyInfoMapper());
		if (list != null && !list.isEmpty()) {
			propertyDto = list.get(0);
		} else {
			LOGGER.error("no property details is available for user");
		}
		LOGGER.debug("getPropertyInfoByLoanNum(): loanNumber: {}, {}", loanNumber, propertyDto);
		return propertyDto;
	}

	/**
	 * @param loanNumber
	 * @return transactions
	 * @exception null pointer or IndexOutOfBond
	 * @Description : getting account history for user on basis of their loan number
	 */
	@Override
	public List<TransactionDto> getAccountHistoryByLoanNum(Long loanNumber) {
		LOGGER.debug("getAccountHistoryByLoanNum(): loanNumber: {}", loanNumber);
		List<TransactionDto> transactions = null;
		String sql = "SELECT YSLN as \"loan\", date(substring(right(repeat('0', 8) concat trim(char(ydate)),8),5,4) concat '-' concat substring(right(repeat('0', 8) concat trim(char(ydate)),8),1,2) concat '-' concat substring(right(repeat('0', 8) concat trim(char(ydate)),8),3,2)) as \"posted\", date(substring(right(repeat('0', 8) concat trim(char(ydudt)),8),5,4) concat '-' concat substring(right(repeat('0', 8) concat trim(char(ydudt)),8),1,2) concat '-' concat substring(right(repeat('0', 8) concat trim(char(ydudt)),8),3,2)) as \"nextDue\", coalesce(coalesce((SELECT cddesc FROM "
				+ uhfalib
				+ ".codefile WHERE upper(cdprogram) = 'TRANSCODES' AND cdcode = concat(YTYPE,YOVER)), (SELECT cddesc FROM "
				+ uhfalib
				+ ".codefile WHERE upper(cdprogram) = 'TRANSCODES' AND cdcode = YTYPE)), 'adjustment') as \"desc\", CASE WHEN YSUCD='R4' AND YTYPE IN ('92','93','60','61','62') THEN -(YPRAM + YINAM - YSUAM + YESAM) WHEN YSUCD='R4' AND YTYPE NOT IN ('92','93','60','61','62') THEN (YPRAM + YINAM - YSUAM + YESAM) WHEN YSUCD<>'R4' AND YTYPE IN ('92','93','60','61','62') THEN -(YPRAM + YINAM + YESAM) WHEN YSUCD<>'R4' AND YTYPE NOT IN ('92','93','60','61','62') THEN (YPRAM + YINAM + YESAM) END as \"totalAmount\", CASE WHEN YTYPE IN ('92','93','60','61','62') THEN -(YPRAM) ELSE YPRAM END as \"principal\", CASE WHEN YSUCD ='R4' AND YTYPE IN ('92','93','60','61','62') THEN -(YINAM - YSUAM) WHEN YSUCD ='R4' AND YTYPE NOT IN ('92','93','60','61','62') THEN (YINAM - YSUAM) WHEN YSUCD<>'R4' AND YTYPE IN ('92','93','60','61','62') THEN -(YINAM) WHEN YSUCD<>'R4' AND YTYPE NOT IN ('92','93','60','61','62') THEN (YINAM) END as \"interest\", CASE WHEN YTYPE IN ('92','93','60','61','62') THEN -(YESAM) ELSE YESAM END as \"escrow\" FROM "
				+ ldataser
				+ ".srvytrn WHERE YSLN =? AND (YPRAM + YINAM + YESAM <> 0) and not (YTYPE='11' and YOVER='51' and YOD1='11') ORDER BY \"posted\" desc FETCH FIRST 80 ROWS ONLY";
		transactions = webJdbcTemplate.query(sql, new Object[] { loanNumber }, new TransactionMapper());
		LOGGER.debug("getAccountHistoryByLoanNum(): loanNumber: {} found {} TransactionDto records", loanNumber,
				transactions.size());
		return transactions;
	}

	/**
	 * @param userName
	 * @return loanList
	 * @exception null pointer or IndexOutOfBond
	 * @description getting loan account number by using user name
	 */
	@Override
	public List<Long> getLoanAccountsByUserName(String userName) {
		LOGGER.debug("getLoanAccountsByUserName(): userName: {}", userName);
		List<Long> loanList = null;
		final String SQL = "SELECT blloan as loan " + "FROM " + ldataser + ".srvbal, " + ldataser + ".srvalt1, "
				+ homeowner + ".user " + "WHERE blloan=a1_loan and USERNAME = ? and ssn = a1_soc_sec";

		loanList = webJdbcTemplate.queryForList(SQL, new Object[] { userName }, Long.class);
		LOGGER.debug("getLoanAccountsByUserName(): userName: {} found {} loan numbers", userName, loanList.size());
		return loanList;
	}

	/**
	 * @param loanStmtRq
	 * @return loanStatementDtoList getting list of Loan Statements
	 * @exception null pointer or IndexOutOfBond
	 * @description getting list of LoanStatementDto by using loanStmtRq
	 */
	@Override
	public List<LoanStatementDto> getLoanStatementByLoanNumber(LoanStatementsRequest loanStmtRq) {
		LOGGER.debug("getLoanStatementByLoanNumber(): {}", loanStmtRq);
		final String SQL = "SELECT statement_date, statement_path AS front_path FROM " + homeowner
				+ ".loan_statement WHERE loan_number = ? ORDER BY statement_date desc";

		List<LoanStatementDto> loanStatementDtoList = webJdbcTemplate.query(SQL,
				new Object[] { loanStmtRq.getLoanNumber()}, new LoanStatementMapper());
		LOGGER.debug("getLoanStatementByLoanNumber(): along with loan statements: {}",
				(loanStatementDtoList.isEmpty() ? "No loan statements exist"
						: " found " + loanStatementDtoList.size() + " LoanStatementDtoList records"));
		return loanStatementDtoList;
	}

	/**
	 * @param userName
	 * @return userDto
	 * @exception null pointer or IndexOutOfBond
	 * @description getting user by using username
	 */
	@Override
	public UserDto getUserByUsername(String username) {
		LOGGER.debug("getUserByUsername(): username: {}", username);
		UserDto userDto = null;
		final String SQL = "SELECT u.ID, u.USERNAME, u.FIRST_NAME, u.LAST_NAME, u.EMAIL, u.ROLE, u.LOGIN_FAILS, u.LOCKED, u.LOGIN_STATUS, uf.FLAG_VALUE FROM "
				+ homeowner + ".USER u LEFT JOIN " + homeowner + ".USER_FLAGS uf ON u.ID = uf.USER_ID "
				+ "WHERE u.USERNAME= ?";
		List<UserDto> list = webJdbcTemplate.query(SQL, new Object[] { username }, new UserMapper());
		if (list != null && !list.isEmpty()) {
			userDto = list.get(0);
		}
		LOGGER.debug("getUserByUsername(): username: {}: {}", username,
				(userDto != null ? userDto.toString() : " not found."));
		return userDto;
	}

	/**
	 * @param username
	 * @param pwd
	 * @return userDto
	 * @exception null pointer or IndexOutOfBond
	 * @description getting user by using username and password
	 */
	@Override
	public UserDto getUserByUsernameAndPWD(String username, String pwd) {
		LOGGER.debug("getUserByUsernameAndPWD(): username: {}", username);
		UserDto userDto = null;
		final String SQL ="SELECT u.ID, u.USERNAME, u.FIRST_NAME, u.LAST_NAME, u.EMAIL, u.ROLE, u.LOGIN_FAILS, u.LOCKED, u.LOGIN_STATUS, uf.FLAG_VALUE FROM "
				+ homeowner + ".USER u LEFT JOIN " + homeowner + ".USER_FLAGS uf ON u.ID = uf.USER_ID "
				+ "WHERE u.USERNAME= ? AND u.PWD = ?";
		List<UserDto> list = webJdbcTemplate.query(SQL, new Object[] { username, pwd }, new UserMapper());
		if (list != null && !list.isEmpty()) {
			userDto = list.get(0);
		} else {
			LOGGER.error("getUserByUsernameAndPWD(): Could not find username: {} in USER table.", username);
		}
		LOGGER.debug("getUserByUsernameAndPWD(): username: {}: {}", username,
				(userDto != null ? userDto.toString() : " not found!"));
		return userDto;
	}

	/**
	 * @param ssn
	 * @return userDto
	 * @exception null pointer or IndexOutOfBond
	 * @description getting user by using username
	 */
	@Override
	public UserDto getUserBySsn(String ssn) {
		LOGGER.debug("getUserBySsn(): ssn: {}", ssn != null ? lastFour(ssn) : "");
		if (StringUtil.isNullOrEmpty(ssn)) {
			throw new IllegalArgumentException("getUserBySsn(): SSN cannot be null.");
		}
		UserDto userDto = null;
		final String SQL = "SELECT u.ID, u.USERNAME, u.FIRST_NAME, u.LAST_NAME, u.EMAIL, u.ROLE, u.LOGIN_FAILS, u.LOCKED, u.LOGIN_STATUS, uf.FLAG_VALUE FROM "
				+ homeowner + ".USER u LEFT JOIN " + homeowner + ".USER_FLAGS uf ON u.ID = uf.USER_ID "
				+ "WHERE u.SSN = ?";
		List<UserDto> list = webJdbcTemplate.query(SQL, new Object[] { ssn }, new UserMapper());
		if (list != null && !list.isEmpty()) {
			userDto = list.get(0);
		}

		LOGGER.debug("getUserBySsn(): ssn: {} {}", (ssn != null ? lastFour(ssn) : ""),
				(userDto != null ? userDto.toString() : "null"));
		return userDto;
	}

	private String lastFour(String ssn) {
		if (StringUtil.isNullOrEmpty(ssn) || ssn.length() < 4) {
			return "????";
		}
		return ssn.substring(ssn.length() - 4);
	}

	/**
	 * @param userId
	 * @return userDto
	 * @exception null pointer or IndexOutOfBond
	 * @description getting user by using userId
	 */
	@Override
	public UserDto getUserByUserId(int userId) {
		LOGGER.debug("getUserByUserId(): userId: {}", userId);
		UserDto userDto = null;
		final String SQL = "SELECT u.ID, u.USERNAME, u.FIRST_NAME, u.LAST_NAME, u.EMAIL, u.ROLE, u.LOGIN_FAILS, u.LOCKED, u.LOGIN_STATUS, uf.FLAG_VALUE FROM "
				+ homeowner + ".USER u LEFT JOIN " + homeowner + ".USER_FLAGS uf ON u.ID = uf.USER_ID "
						+ "WHERE u.ID = ?";
		List<UserDto> list = webJdbcTemplate.query(SQL, new Object[] { userId }, new UserMapper());
		if (list != null && !list.isEmpty()) {
			userDto = list.get(0);
		} else {
			LOGGER.error("getUserByUserId(): Could not find userId: {} in the USER table.", userId);
		}
		LOGGER.debug("getUserByUserId(): userId: {} {}", userId, (userDto != null ? userDto.toString() : "null"));
		return userDto;
	}

	@Override
	public UserDto getUserByUserIdAndPWD(int userId, String pwd) {
		LOGGER.debug("getUserByUserIdAndPWD(): userId: {}", userId);
		UserDto userDto = null;
		final String SQL = "SELECT u.ID, u.USERNAME, u.FIRST_NAME, u.LAST_NAME, u.EMAIL, u.ROLE, u.LOGIN_FAILS, u.LOCKED, u.LOGIN_STATUS, uf.FLAG_VALUE FROM "
				+ homeowner + ".USER u LEFT JOIN " + homeowner + ".USER_FLAGS uf ON u.ID = uf.USER_ID "
				+ "WHERE u.ID = ? AND u.PWD = ?";
		List<UserDto> list = webJdbcTemplate.query(SQL, new Object[] { userId, pwd }, new UserMapper());
		if (list != null && !list.isEmpty()) {
			userDto = list.get(0);
		} else {
			LOGGER.error("getUserByUserIdAndPWD(): Could not find userId: {} in the USER table.", userId);
		}
		LOGGER.debug("getUserByUserIdAndPWD(): userId: {} {}", userId, (userDto != null ? userDto.toString() : "null"));
		return userDto;
	}

	/**
	 * @param loanNumber,ssn,zip
	 * @return boolean isDataAvailable
	 * @exception null pointer or IndexOutOfBond
	 * @description Checking if loan exists for the particular user on basis of
	 *              loanNumber,ssn,zip
	 */
	@Override
	public boolean checkLoanAcExist(String loanNumber, String ssn, String zip) {
		LOGGER.debug("checkLoanAcExist(): loanNumber: {}, ssn: {} zip: {}", loanNumber,
				(ssn != null ? lastFour(ssn) : ""), zip);
		SqlRowSet rowSet = null;
		boolean isDataAvailable = false;
		if (zip.length() != 5) {
			return false;
		}
		if (!(StringUtils.isNumeric(loanNumber) && StringUtils.isNumeric(ssn) && StringUtils.isNumeric(zip))) {
			return false;
		}
		final String SQL = "SELECT A1_LOAN, A1_CITY, A1_STATE, A1_ZIP, A1_SOC_SEC FROM " + ldataser
				+ ".srvalt1 WHERE A1_LOAN = ? and A1_SOC_SEC = ? and A1_ZIP like '" + zip + "%'";
		rowSet = webJdbcTemplate.queryForRowSet(SQL, loanNumber, ssn);
		isDataAvailable = rowSet.next();
		LOGGER.debug("checkLoanAcExist(): loanNumber: {}, isDataAvailable: {}", loanNumber, isDataAvailable);
		return isDataAvailable;
	}

	@Override
	public List<String> checkLoanAcExistNew(String loanNumber, String ssn, String zip) {
		LOGGER.debug("checkLoanAcExistNew(): loanNumber: {}, ssn: {} zip: {}", loanNumber,
				(ssn != null ? lastFour(ssn) : ""), zip);
		List<String> ssnList = null;

		final String SQL = "SELECT A1_SOC_SEC FROM " + ldataser
				+ ".srvalt1 WHERE A1_LOAN = ? and RIGHT(A1_SOC_SEC, 4) = ? and A1_ZIP like '" + zip + "%'";
		ssnList = webJdbcTemplate.queryForList(SQL, String.class, loanNumber, ssn);
		LOGGER.debug("checkLoanAcExistNew(): loanNumber: {}, isDataAvailable: {}", loanNumber,
				(ssnList != null && !ssnList.isEmpty()));
		return ssnList;
	}

	/**
	 * @author nehas3
	 * @date Aug 13, 2019
	 * @return boolean
	 * @Description : this is validateLoanNumber to check if entered loan number is
	 *              valid or not.
	 */
	@Override
	public boolean validateLoanNumber(long loanNumber) {
		LOGGER.debug("validateLoanNumber(): loanNumber: {}", loanNumber);
		SqlRowSet rowSet = null;
		boolean isDataAvailable = false;
		final String SQL = "SELECT A1_LOAN, A1_CITY, A1_STATE, A1_ZIP, A1_SOC_SEC FROM " + ldataser
				+ ".srvalt1 WHERE A1_LOAN = ?";
		rowSet = webJdbcTemplate.queryForRowSet(SQL, loanNumber);
		isDataAvailable = rowSet.next();
		LOGGER.debug("validateLoanNumber(): loanNumber: {} isDataAvailable: {}", loanNumber, isDataAvailable);
		return isDataAvailable;
	}

	/**
	 * @param registrationRequest
	 * @return boolean updatedRowCount
	 * @exception null pointer or IndexOutOfBond Registering
	 * @description user on the basis of registrationRequest
	 */
	@Override
	public boolean registerUser(RegistrationRequest registrationRequest) {
		LOGGER.debug("registerUser(): {}", registrationRequest);
		int updatedRowCount = 0;
		final String SQL = "INSERT into " + homeowner
				+ ".user (username, first_name, last_name, pwd, role, last_pwd_chg, create_time, modify_time, ssn,email) "
				+ "SELECT cast(? AS varchar(20)), " + ldataser + ".srvalt1.a1_first_name, " + ldataser
				+ ".srvalt1.a1_last_name, cast(? as varchar(20)), 400, current timestamp, current timestamp, current timestamp, cast(? as NUMERIC(9,0)), cast(? as varchar(50)) FROM "
				+ ldataser + ".srvalt1 WHERE " + ldataser + ".srvalt1.a1_soc_sec = ? fetch first 1 rows only";
		updatedRowCount = webJdbcTemplate.update(SQL, registrationRequest.getUsername(),
				registrationRequest.getPassword(), registrationRequest.getSsn(), registrationRequest.getEmail(),
				registrationRequest.getSsn());
		LOGGER.debug("registerUser(): Updated {} records", updatedRowCount);
		return ((updatedRowCount > 0) ? true : false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addUserLoanRecord(int user_id, long loan_number, int sequence_number) {
		LOGGER.debug("addUserLoanRecord; userId: {}, loan_number: {}, sequence_number: {}", user_id, loan_number, sequence_number);
		final String SQL = "insert into " + homeowner + ".user_loan (user_id, loan_number, sequence_number) values(?,?,?)";
		webJdbcTemplate.update(SQL, user_id, loan_number, sequence_number);
	}

	/**
	 * This method has been created to make push notification flag auto enabled while registering
	 * @author nehas3
	 * @since Oct 19, 2020
	 * @param registrationRequest
	 * @return true or false as per the update
	 */
	@Override
	public boolean insertPushNotificationFlag(PushNotificationFlagDto pnfDto) {
		LOGGER.debug("insertPushNotificationFlag(): pnfDto: {}", pnfDto);
		int updatedRowCount = 0;
		final String SQL = "INSERT INTO " + homeowner + ".user_flags (USER_ID, FLAG_NAME_ID, FLAG_VALUE)"
				+ " VALUES (:userId, :flagNameId, :flagValue)";
		SqlParameterSource params = new MapSqlParameterSource().addValue("userId", pnfDto.getUserId())
				.addValue("flagNameId", pnfDto.getFlagNameId())
				.addValue("flagValue", pnfDto.getFlagValue());
		updatedRowCount = namedParameterJdbcTemplate.update(SQL, params);
		LOGGER.debug("insertPushNotificationFlag(): Updated {} records", updatedRowCount);
		return ((updatedRowCount > 0) ? true : false);
	}

	@Override
	public boolean insertOnlinePrefStatus(String loanNumber, int onlineStatementsStatus) {
		LOGGER.debug("insertOnlinePrefStatus(): loanNumber: {}, onlineStatementsStatus", loanNumber, onlineStatementsStatus);
		int updatedRowCount = 0;
		final String SQL = "INSERT INTO " + homeowner + ".ONLINE_STATEMENTS_PREF (LOAN, ONLINE_STATEMENTS)"
				+ " VALUES (:loan, :online_statements)";
		SqlParameterSource params = new MapSqlParameterSource().addValue("loan", loanNumber)
				.addValue("online_statements", onlineStatementsStatus);
		updatedRowCount = namedParameterJdbcTemplate.update(SQL, params);
		LOGGER.debug("insertOnlinePrefStatus(): Updated {} records", updatedRowCount);
		return ((updatedRowCount > 0) ? true : false);
	}

	@Override
	public boolean updateOnlinePrefStatus(String loanNumber) {
		LOGGER.debug("updateOnlinePrefStatus(): loanNumber: {}", loanNumber);
		int updatedRowCount = 0;
		final String SQL = "UPDATE " + homeowner + ".ONLINE_STATEMENTS_PREF SET LOAN = ? WHERE LOAN = ?";
		updatedRowCount = webJdbcTemplate.update(SQL, loanNumber, loanNumber);
		LOGGER.debug("updateOnlinePrefStatus(): Updated {} records.", updatedRowCount);
		return updatedRowCount > 0;
	}

	/**
	 * @param loanNumber
	 * @return taxInfoDtoList
	 * @exception null pointer or IndexOutOfBond
	 * @description getting tax information on the basis of loan number
	 */
	@Override
	public List<TaxInfoDto> getTaxInfobyLoanNumber(Long loanNumber) {
		LOGGER.debug("getTaxInfobyLoanNumber(): {}", loanNumber);
		List<TaxInfoDto> taxInfoDtoList = null;
		final String SQL = "SELECT LOAN_NUMBER, PATH, YEAR, TYPE FROM " + homeowner
				+ ".yearend_tax_statements WHERE LOAN_NUMBER = ? ORDER BY YEAR DESC";
		taxInfoDtoList = webJdbcTemplate.query(SQL, new Object[] { loanNumber }, new TaxInfoMapper());
		LOGGER.debug("getTaxInfobyLoanNumber(): loanNumber: {}, found {} TaxInfoDto records", loanNumber,
				(taxInfoDtoList != null ? taxInfoDtoList.size() : 0));
		return taxInfoDtoList;
	}

	/**
	 * @param loanNumber
	 * @return OnlineStatementsPrefDto
	 * @exception null pointer or IndexOutOfBond
	 * @description getting Status online Statement Preference
	 */
	@Override
	public OnlineStatementsPrefDto getStatementPrefStatus(long loanNumber) {
		LOGGER.debug("getStatementPrefStatus(): loanNumber: {}", loanNumber);
		OnlineStatementsPrefDto prefDto = null;
		final String SQL = "SELECT LOAN, ONLINE_STATEMENTS FROM " + homeowner
				+ ".ONLINE_STATEMENTS_PREF WHERE LOAN = ?";
		List<OnlineStatementsPrefDto> list = webJdbcTemplate.query(SQL, new Object[] { loanNumber },
				new OnlineStatementPrefMapper());
		if (list != null && !list.isEmpty()) {
			prefDto = list.get(0);
		} else {
			LOGGER.error("OnlineStatementsPrefDto could not be found");
		}
		LOGGER.debug("getStatementPrefStatus(): loanNumber: {}, {}", loanNumber,
				(prefDto != null ? prefDto.toString() : "null"));
		return prefDto;
	}

	/**
	 * @param updateEmailRequest
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @description Updating user email by using user id and password
	 */
	@Override
	public boolean updateEmail(UpdateEmailRequest updateEmailRequest) {
		LOGGER.debug("updateEmail(): updateEmailRequest: {}", updateEmailRequest);
		int updatedRowCount = 0;
		final String SQL = "UPDATE " + homeowner + ".USER SET EMAIL = ? WHERE ID = ? AND PWD = ?";
		updatedRowCount = webJdbcTemplate.update(SQL, updateEmailRequest.getNewEmail(), updateEmailRequest.getUserId(),
				updateEmailRequest.getPassword());
		LOGGER.debug("updateEmail(): updated {} USER.email fields for {}", updatedRowCount, updateEmailRequest);
		return (updatedRowCount > 0);
	}

	/**
	 * @param updateUserNameRequest
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @description Updating user user name by using old user name and password
	 */
	@Override
	public boolean updateUserName(UpdateUserNameRequest updateUserNameRequest) {
		LOGGER.debug("updateUserName(): updateUserNameRequest: {}", updateUserNameRequest);
		int updatedRowCount = 0;
		final String SQL = "UPDATE " + homeowner + ".USER SET USERNAME = ? WHERE USERNAME = ? AND PWD = ?";
		updatedRowCount = webJdbcTemplate.update(SQL, updateUserNameRequest.getNewUserName(),
				updateUserNameRequest.getOldUserName(), updateUserNameRequest.getPassword());
		LOGGER.debug("updateUserName(): updated {} USER.username fields for {}", updatedRowCount,
				updateUserNameRequest);
		return (updatedRowCount > 0);
	}

	/**
	 * @param updatePasswordRequest
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @description Updating user's password name by using userId and password
	 */
	@Override
	public boolean updatePassword(UpdatePasswordRequest updatePasswordRequest) {
		LOGGER.debug("updatePassword(): updatePasswordRequest: {}", updatePasswordRequest);
		int updatedRowCount = 0;
		final String SQL = "UPDATE " + homeowner + ".USER SET PWD = ? WHERE ID = ? AND PWD = ?";
		updatedRowCount = webJdbcTemplate.update(SQL, updatePasswordRequest.getNewPassword(),
				updatePasswordRequest.getUserId(), updatePasswordRequest.getOldPassword());
		LOGGER.debug("updatePassword(): update {} USER.pwd fields for {}", updatedRowCount, updatePasswordRequest);
		return (updatedRowCount > 0);
	}

	/**
	 * @author nehas3
	 * @date September 25, 2019
	 * @return boolean
	 * @param incorrectBankInfoId
	 * @return true or false on update user name
	 * @Description : this is updateIncorrectBankingInfo method to update update Incorrect Banking Info on basis of
	 *              requested parameter
	 */
	@Override
	public boolean resetPassword(int id, RecoverAccountReq recoverAccountReq) {
		LOGGER.debug("resetPassword(): id {}, RecoverAccountReq: {}", id, recoverAccountReq);
		int updatedRowCount = 0;
		final String SQL = "UPDATE " + homeowner + ".USER SET PWD = ?, LOCKED = ?, LOGIN_FAILS = '0' WHERE ID = ?";
		updatedRowCount = webJdbcTemplate.update(SQL, recoverAccountReq.getNewPassword(), "", id);
		LOGGER.debug("resetPassword(): update {} USER.pwd fields for {}", updatedRowCount, recoverAccountReq);
		return (updatedRowCount > 0);
	}

	/**
	 * @author nehas3
	 * @date September 25, 2019
	 * @return boolean
	 * @param userId
	 * @return true or false on reset recovery key
	 * @Description : this is getOldPassword method to get old password from user table.
	 */
	@Override
	public String getOldPassword(int userId) {
		LOGGER.debug("getOldPassword(): userId {}", userId);
		String pwd = null;
		final String SQL = "SELECT PWD FROM " + homeowner + ".USER WHERE ID = ?";
		List<String> list = webJdbcTemplate.queryForList(SQL, String.class, userId);
		if (list != null && !list.isEmpty()) {
			pwd = list.get(0);
		} else {
			LOGGER.error("getOldPassword(): id {} could not get password for", userId);
		}
		return pwd;
	}

	/**
	 * @author nehas3
	 * @date September 25, 2019
	 * @return boolean
	 * @param id
	 * @param recoverAccountReq
	 * @return true or false on reset account
	 * @Description : this is resetAccount method for user to reset their account.
	 */
	@Override
	public boolean resetAccount(int id, RecoverAccountReq recoverAccountReq) {
		LOGGER.debug("resetAccount(): id {}, RecoverAccountReq: {}", id, recoverAccountReq);
		int updatedRowCount = 0;
		final String SQL = "UPDATE " + homeowner
				+ ".USER SET PWD = ?, EMAIL = ?, LOCKED = ?, LOGIN_FAILS = '0' WHERE ID = ?";
		updatedRowCount = webJdbcTemplate.update(SQL, recoverAccountReq.getNewPassword(),
				recoverAccountReq.getRecoveryEmail(), "", id);
		LOGGER.debug("resetAccount(): update {} USER.pwd fields for {}", updatedRowCount, recoverAccountReq);
		return (updatedRowCount > 0);
	}

	/**
	 * @param isOnlineStatementEnabled , loanList
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @description Updating user Online Statement Preference by list of loan
	 *              numbers and setting online_statements accordingly.
	 */
	@Override
	public boolean updateOnlineStatementPref(Boolean isOnlineStatementEnabled, List<Long> loanList) {
		LOGGER.debug("updateOnlineStatementPref(): isOnlineStatementEnabled: {} for {} loans", isOnlineStatementEnabled,
				loanList.size());
		int updatedRowCount = 0;
		for (Long loanNumber : loanList) {
			OnlineStatementsPrefDto prefDto = getStatementPrefStatus(loanNumber);

			if (prefDto.getOnlineStatements() != null) {
				final String SQL = "UPDATE " + homeowner
						+ ".ONLINE_STATEMENTS_PREF SET ONLINE_STATEMENTS = ? WHERE LOAN = ?";
				updatedRowCount = webJdbcTemplate.update(SQL, isOnlineStatementEnabled, loanNumber);
				updatedRowCount++;
			} else {
				LOGGER.debug("updateOnlineStatementPref(): Already agreed to online statement preference");
			}
		}
		LOGGER.debug(
				"updateOnlineStatementPref(): updated {} ONLINE_STATEMENT_PREF.online_statement fields for {} loans",
				updatedRowCount, loanList.size());
		return (updatedRowCount > 0);
	}

	/**
	 * @param userId
	 * @return BankingInfoDto
	 * @exception null pointer or IndexOutOfBond Getting Banking Info Of User On The
	 *            Basis Of userId
	 */
	@Override
	public BankingInfoDto getBankingInfo(int userId) {
		LOGGER.debug("getBankingInfo(): userId: {}", userId);
		BankingInfoDto paymentMethods = null;
		final String SQL = "SELECT ID, PAYMENT_ACCOUNT_NUMBER, ROUTING_NUMBER, NAME_ON_PAYMENT_ACCOUNT, USER_ID, PAYMENT_ACCOUNT_TYPE"
				+ " FROM " + homeowner + ".PAYMENT_METHOD WHERE USER_ID = ?";
		List<BankingInfoDto> list = webJdbcTemplate.query(SQL, new Object[] { userId }, new BankingInfoMapper());
		if (list != null && !list.isEmpty()) {
			paymentMethods = list.get(0);
		} else {
			LOGGER.error("getBankingInfo(): No payment_method record found for userId: {}", userId);
		}
		LOGGER.debug("getBankingInfo(): userId: {} {}", userId,
				paymentMethods != null ? paymentMethods.toString() : "Bank details are not available");
		return paymentMethods;
	}

	/**
	 * @param userId, updateBankingInfoRequest
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond Updating Banking Info Of User On
	 *            The Basis Of userId and requested fields that user wants to
	 *            update.
	 */
	@Override
	public boolean updateBankingInfo(Integer id, UpdateBankingInfoRequest updateBankingInfoRequest) {
		LOGGER.debug("updateBankingInfo(): id: {} {}", id, updateBankingInfoRequest);
		int updatedRowCount = 0;
		final String SQL = "UPDATE " + homeowner
				+ ".PAYMENT_METHOD SET PAYMENT_ACCOUNT_NUMBER=?,NAME_ON_PAYMENT_ACCOUNT=?,PAYMENT_ACCOUNT_TYPE=?,ROUTING_NUMBER=? WHERE USER_ID = ? AND ID = ?";
		Object[] params = new Object[] { updateBankingInfoRequest.getBankAccountNumber(),
				updateBankingInfoRequest.getNameOnBankAccount(), updateBankingInfoRequest.getBankAccountType().getKey(),
				updateBankingInfoRequest.getRoutingNumber(), updateBankingInfoRequest.getUserId(), id };
		updatedRowCount = webJdbcTemplate.update(SQL, params);
		LOGGER.debug("updateBankingInfo(): updated {} payment_method records for {}", updatedRowCount,
				updateBankingInfoRequest);
		return (updatedRowCount > 0);
	}

	@Override
	public boolean addBankingInfo(UpdateBankingInfoRequest updateBankingInfoRequest) {
		LOGGER.debug("addBankingInfo(): {}", updateBankingInfoRequest!=null ? updateBankingInfoRequest.toString(): "");
		if (updateBankingInfoRequest == null) {
			return false;
		}
		int insertedRowCount = 0;
		final String SQL = "INSERT INTO " + homeowner
				+ ".PAYMENT_METHOD (USER_ID, PAYMENT_ACCOUNT_NUMBER, NAME_ON_PAYMENT_ACCOUNT, PAYMENT_ACCOUNT_TYPE, ROUTING_NUMBER)"
				+ " VALUES (:user_id, :payment_account_number, :name_on_payment_account, :payment_account_type, :routing_number)";

		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("user_id", updateBankingInfoRequest.getUserId())
				.addValue("payment_account_number", updateBankingInfoRequest.getBankAccountNumber())
				.addValue("name_on_payment_account", updateBankingInfoRequest.getNameOnBankAccount())
				.addValue("payment_account_type", updateBankingInfoRequest.getBankAccountType().getKey())
				.addValue("routing_number", updateBankingInfoRequest.getRoutingNumber());
		insertedRowCount = namedParameterJdbcTemplate.update(SQL, params);
		LOGGER.debug("addBankingInfo(): inserted {} payment_method", insertedRowCount);
		return (insertedRowCount > 0);
	}

	/**
	 * @param userId
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Deleting Banking Info Of User On The Basis Of userId.
	 */
	@Override
	public boolean deleteBankingInfo(int userId) {
		LOGGER.debug("deleteBankingInfo(): userId: {}", userId);
		int deletedRowCount = 0;
		try {
			final String SQL = "DELETE FROM " + homeowner + ".PAYMENT_METHOD WHERE USER_ID = ?";
			deletedRowCount = webJdbcTemplate.update(SQL, userId);

			if (deletedRowCount > 1) {
				throw new IllegalStateException("deleteBankingInfo(): deleted " + deletedRowCount + " rows for userId: "
						+ userId + "! It should have only deleted one!");
			}
			if (deletedRowCount == 0) {
				boolean hasBankingInfo = getBankingInfo(userId) == null;
				if (hasBankingInfo) {
					LOGGER.debug("deleteBankingInfo(): delete failed for userId: {}", userId);
				} else {
					LOGGER.warn("deleteBankingInfo(): delete failed for userId: {}. No banking info found!?!", userId);
				}
				return !hasBankingInfo;
			}
		} catch (Exception exp) {
			LOGGER.error("deleteBankingInfo(): exception occurred while deleting Banking info: ", exp);
			return false;
		}
		LOGGER.debug("deleteBankingInfo(): deleted {} records for userId: {}", deletedRowCount, userId);
		return true;
	}

	/**
	 * @param routingNumber
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Checking if entered routing number is white listed or not
	 *              while updating user profile.
	 */
	@Override
	public boolean isRoutingNumberWhiteListed(String routingNumber) {
		LOGGER.debug("isRoutingNumberWhiteListed(): routingNumber: {}", routingNumber);
		RoutingNumberDto routNumb = null;
		final String SQL = "SELECT RNUMB FROM " + as400cgi + ".ROUTNUMB WHERE RNUMB = ?";
		List<RoutingNumberDto> list = webJdbcTemplate.query(SQL, new Object[] { routingNumber },
				new RoutingNumberMapper());
		if (list != null && !list.isEmpty()) {
			routNumb = list.get(0);
		} else {
			LOGGER.error("isRoutingNumberWhiteListed(): Routing number: {} not found in ROUTNUMB table.", routingNumber);
		}
		LOGGER.debug("isRoutingNumberWhiteListed(): routingNumber: {} {}", routingNumber, routNumb);
		return (routNumb != null);
	}

	/**
	 * @param routingNumber, accountNumber
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Checking if entered routing number is black listed or not
	 *              while updating user profile.
	 */
	@Override
	public IncorrectBankingInfoDto isRoutingNumberBlackListed(String routingNumber, String accountNumber) {
		LOGGER.debug("isRoutingNumberBlackListed(): routingNumber: {}, accountNumber: {}", routingNumber,
				accountNumber != null ? lastFour(accountNumber) : "");
		IncorrectBankingInfoDto incorrectBankingInfo = null;
		final String SQL = "SELECT ID ,USER_ID, ROUTING_NUMBER, ACCOUNT_NUMBER, ERROR_ID, NEEDS_NOTIFICATION FROM "
				+ homeowner + ".INCORRECT_BANK_INFO " + "WHERE ROUTING_NUMBER = ? AND ACCOUNT_NUMBER = ?";

		List<IncorrectBankingInfoDto> list = webJdbcTemplate.query(SQL, new Object[] { routingNumber, accountNumber },
				new IncorrectBankInfoMapper());
		if (list != null && !list.isEmpty()) {
			incorrectBankingInfo = list.get(0);
		} else {
			LOGGER.error("isRoutingNumberBlackListed(): Could not find incorrect_bank_info record for r: {} and a: {}", routingNumber,
					accountNumber != null ? lastFour(accountNumber) : "");
		}
		LOGGER.debug("isRoutingNumberBlackListed(): routingNumber: {}, accountNumber: {} for {}", routingNumber,
				accountNumber != null ? lastFour(accountNumber) : "",
				(incorrectBankingInfo != null ? incorrectBankingInfo.toString() : "null"));
		return incorrectBankingInfo;
	}

	/**
	 * @param userID
	 * @return List of IncorrectBankingInfoDto
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Checking if banking details are valid or not
	 */
	@Override
	public List<IncorrectBankingInfoDto> validateBankingInfo(int userID) {
		LOGGER.debug("validateBankingInfo(): userID: {}", userID);
		List<IncorrectBankingInfoDto> incorrectBankingInfo = null;
		final String SQL = "SELECT ID, USER_ID, ROUTING_NUMBER, ACCOUNT_NUMBER, ERROR_ID, NEEDS_NOTIFICATION FROM "
				+ homeowner + ".INCORRECT_BANK_INFO " + "WHERE USER_ID = ? AND ID = (SELECT MAX(ID) FROM " + homeowner
				+ ".INCORRECT_BANK_INFO WHERE USER_ID = ?)";
		incorrectBankingInfo = webJdbcTemplate.query(SQL, new Object[] { userID, userID },
				new IncorrectBankInfoMapper());
		LOGGER.debug("validateBankingInfo(): found {} IncorrectBankingInfoDto records for userID: {}", userID,
				(incorrectBankingInfo != null ? incorrectBankingInfo.size() : 0));
		return incorrectBankingInfo;

	}

	@Override
	public IncorrectBankingInfoDto getIncorrectBankingInfo(int incorrectBankingInfoId) {
		LOGGER.debug("getIncorrectBankingInfo(): incorrectBankingInfoId: {}", incorrectBankingInfoId);
		IncorrectBankingInfoDto incorrectBankingInfo = null;
		final String SQL = "SELECT ID, USER_ID, ROUTING_NUMBER, ACCOUNT_NUMBER, ERROR_ID, NEEDS_NOTIFICATION FROM "
				+ homeowner + ".INCORRECT_BANK_INFO " + "WHERE ID = ?";
		List<IncorrectBankingInfoDto> result = webJdbcTemplate.query(SQL, new Object[] { incorrectBankingInfoId },
				new IncorrectBankInfoMapper());

		if (result != null && !result.isEmpty()) {
			incorrectBankingInfo = result.get(0);
		} else {
			LOGGER.error("getIncorrectBankingInfo(): invalid routing number or account number");
		}
		LOGGER.debug("getIncorrectBankingInfo(): incorrectBankingInfoId: {} {}", incorrectBankingInfo,
				(incorrectBankingInfo != null ? incorrectBankingInfo.toString() : "null"));
		return incorrectBankingInfo;

	}

	@Override
	public ErrorCodeForBankingInfoDto getErrorMessage(int errorId) {
		LOGGER.debug("getErrorMessage(): errorId: {}", errorId);
		ErrorCodeForBankingInfoDto errorInfo = null;
		final String SQL = "SELECT ID, NOTIFICATION_MESSAGE, VALIDATION_MESSAGE FROM " + homeowner + ".BANK_INFO_ERROR "
				+ "WHERE ID = ?";

		List<ErrorCodeForBankingInfoDto> list = webJdbcTemplate.query(SQL, new Object[] { errorId },
				new ErrorCodeForBankingInfoMapper());
		if (list != null && !list.isEmpty()) {
			errorInfo = list.get(0);
		} else {
			LOGGER.error("getErrorMessage(): Could not find notification message for errorId: {}", errorId);
		}
		LOGGER.debug("getErrorMessage(): errorId: {} {}", errorId, (errorInfo != null ? errorInfo.toString() : "null"));
		return errorInfo;
	}

	/**
	 * @param userId, loanPayment
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Scheduling Payment on the basis of userId and
	 *              ScheduledPaymentRequest attributes.
	 */
	@Override
	public boolean schedulePayment(int userId, ScheduledPaymentRequest loanPayment, Date scheduledDate,
			String paymentSource) {
		LOGGER.debug("schedulePayment(): userId: {} loanPayment: {} scheduledDate: {} paymentSource: {}", userId,
				loanPayment, scheduledDate, paymentSource);
		int insertedRowCount = 0;
		DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
		final String SQL = "INSERT INTO " + homeowner + ".SCHEDULED_PAYMENT (USER_ID, SCHEDULED_TYPE, USER_DEFINED_DATE"
				+ ", NEXT_SCHEDULED_DATE, EMAIL_REMINDER, LOAN_NUMBER, LATE_FEE, NSF_FEE, EXTRA_PRINCIPAL, EXTRA_ESCROW, PAYMENT_SOURCE, CONFIRMATION_NUMBER, MONTHLY_PAYMENT) "
				+ " VALUES (:user_id, :SCHEDULED_TYPE, :user_defined_date"
				+ ", :next_scheduled_date, :email_reminder, :loan_number, :late_fee, :nsf_fee, :extra_principal, :extra_escrow, :PAYMENT_SOURCE, :CONFIRMATION_NUMBER, :MONTHLY_PAYMENT)";

		SqlParameterSource params = new MapSqlParameterSource().addValue("user_id", userId)
				.addValue("SCHEDULED_TYPE", loanPayment.getScheduledType())
				.addValue("user_defined_date", sdf.format(loanPayment.getUserDefinedDate()))
				.addValue("next_scheduled_date", sdf.format(scheduledDate))
				.addValue("email_reminder", loanPayment.isEmailReminder() ? 1 : 0)
				.addValue("loan_number", loanPayment.getLoanNumber())
				.addValue("late_fee", loanPayment.getLateFeesPaid()).addValue("nsf_fee", loanPayment.getNsfFeesPaid())
				.addValue("extra_principal", loanPayment.getExtraPrincipal())
				.addValue("extra_escrow", loanPayment.getExtraEscrow()).addValue("PAYMENT_SOURCE", paymentSource)
				.addValue("CONFIRMATION_NUMBER", loanPayment.getConfirmationNumber())
				.addValue("MONTHLY_PAYMENT", loanPayment.getMonthlyPayment());
		insertedRowCount = namedParameterJdbcTemplate.update(SQL, params);
		LOGGER.debug("schedulePayment(): found {} for userId: {}", insertedRowCount, userId);
		return (insertedRowCount > 0);
	}

	/**
	 * @param paymentType, loanNumber, canceled
	 * @return List<Integer>
	 * @exception null pointer or IndexOutOfBond
	 * @Description : getting active payments info on basis of paymentType,
	 *              loanNumber and canceled.
	 */
	@Override
	public List<Integer> getActiveTodayPaymentByPaymentType(ScheduledPaymentType paymentType, long loanNumber) {
		LOGGER.debug("getActiveTodayPaymentByPaymentType(): paymentType: {}, loanNumber: {}", paymentType.getDesc(),
				loanNumber);
		List<Integer> activatedPaymentId = null;
		final String SQL = "SELECT ID FROM " + homeowner + ".SCHEDULED_PAYMENT " + " WHERE LOAN_NUMBER = ? "
				+ " AND SCHEDULED_TYPE = ? " + " AND CANCELED = '0' "
				+ " AND (PAYMENT_SOURCE='MBL' OR PAYMENT_SOURCE='WEB') AND PROCESSED_DATE IS NULL";
		activatedPaymentId = webJdbcTemplate.queryForList(SQL, Integer.class, loanNumber, paymentType.getDesc());
		LOGGER.debug("getActiveTodayPaymentByPaymentType(): found {} payment ids for loanNumber: {}", activatedPaymentId,
				loanNumber);
		return activatedPaymentId;
	}

	@Override
	public List<Integer> getActivePaymentByPaymentType(ScheduledPaymentType paymentType, long loanNumber,
			int canceled) {
		LOGGER.debug("getActivePaymentByPaymentType: paymentType: {} loanNumber: {} canceled: {}", paymentType,
				loanNumber, canceled);
		List<Integer> activatedPaymentId = null;
		final String SQL = "SELECT ID FROM " + homeowner + ".SCHEDULED_PAYMENT " + " WHERE LOAN_NUMBER = ? "
				+ " AND SCHEDULED_TYPE = ? " + " AND CANCELED = ? "
				+ " AND PROCESSED_DATE IS NULL AND (PAYMENT_SOURCE='MBL' OR PAYMENT_SOURCE='WEB')";
		activatedPaymentId = webJdbcTemplate.queryForList(SQL, Integer.class, loanNumber, paymentType.getDesc(),
				canceled);
		LOGGER.debug("getActivePaymentByPaymentType(): found {} payment ids for loanNumber: {}", activatedPaymentId,
				loanNumber);
		return activatedPaymentId;
	}

	/**
	 * @param loanNumber
	 * @return List<ScheduledPaymentDto>
	 * @exception null pointer or IndexOutOfBond
	 * @Description : getting all list of already scheduled payments info on basis
	 *              of loanNumber.
	 */
	@Override
	public List<ScheduledPaymentDto> getScheduledPayments(long loanNumber) {
		LOGGER.debug("getScheduledPayments(): loanNumber: {}", loanNumber);
		List<ScheduledPaymentDto> scheduledPayments = null;
		final String SQL = "SELECT ID, USER_ID, LOAN_NUMBER, CREATE_DATE, USER_DEFINED_DATE, NEXT_SCHEDULED_DATE, PROCESSED_DATE, SCHEDULED_TYPE, CANCELED, CANCELED_BY, CANCELED_DATE,	LATE_FEE, NSF_FEE, EXTRA_PRINCIPAL, EXTRA_ESCROW, EMAIL_REMINDER, PAYMENT_SOURCE, CONFIRMATION_NUMBER FROM "
				+ homeowner + ".SCHEDULED_PAYMENT "
				+ " WHERE LOAN_NUMBER = ? AND CANCELED = '0' AND (PAYMENT_SOURCE='MBL' OR PAYMENT_SOURCE='WEB') AND PROCESSED_DATE IS NULL "
				+ "ORDER BY LOAN_NUMBER, NEXT_SCHEDULED_DATE";

		scheduledPayments = webJdbcTemplate.query(SQL, new ScheduledPaymentMapper(), loanNumber);
		LOGGER.debug("getScheduledPayments(): found {} ScheduledPaymentDto records for loanNumber: {} ",
				(scheduledPayments != null ? scheduledPayments.size() : 0), loanNumber);
		return scheduledPayments;
	}

	/**
	 * @param loanNumber
	 * @return List<GetScheduledPaymentDto>
	 * @exception null pointer or IndexOutOfBond
	 * @Description : getting selected list of already scheduled payments info on
	 *              basis of loanNumber.
	 */
	@Override
	public List<GetScheduledPaymentDto> getScheduledPaymentList(long loanNumber) {
		LOGGER.debug("getScheduledPaymentList(): loanNumber: {}", loanNumber);
		List<GetScheduledPaymentDto> scheduledPayments = null;
		final String SQL = "SELECT USER_ID, LOAN_NUMBER, SCHEDULED_TYPE, PROCESSED_DATE, NEXT_SCHEDULED_DATE, PAYMENT_SOURCE FROM "
				+ homeowner + ".SCHEDULED_PAYMENT "
				+ " WHERE LOAN_NUMBER = ? AND CANCELED = '0' AND (PAYMENT_SOURCE='MBL' OR PAYMENT_SOURCE='WEB') AND (PROCESSED_DATE IS NULL OR PROCESSED_DATE  =  CURRENT_DATE)"
				+ "ORDER BY NEXT_SCHEDULED_DATE DESC";

		scheduledPayments = webJdbcTemplate.query(SQL, new GetScheduledMapper(), loanNumber);
		LOGGER.debug("getScheduledPaymentList(): found {} GetScheduledPaymentDto records for loanNumber: {}",
				(scheduledPayments != null ? scheduledPayments.size() : 0), loanNumber);
		return scheduledPayments;
	}

	/**
	 * @param loanNumber
	 * @return Integer
	 * @exception null pointer or IndexOutOfBond
	 * @Description : getting max next date of scheduled payments info on basis of
	 *              loanNumber.
	 */
	@Override
	public Integer getMaxScheduledMonth(long loanNumber) {
		LOGGER.debug("getMaxScheduledMonth(): loanNumber: {}", loanNumber);
		Date maxScheduledDate = null;
		final String SQL = "SELECT MAX(NEXT_SCHEDULED_DATE) FROM " + homeowner + ".SCHEDULED_PAYMENT "
				+ " WHERE LOAN_NUMBER = ? AND CANCELED = '0' AND PROCESSED_DATE IS NULL";

		List<Date> list = webJdbcTemplate.queryForList(SQL, Date.class, loanNumber);
		if (list != null && !list.isEmpty()) {
			maxScheduledDate = list.get(0);
		} else {
			LOGGER.error("getMaxScheduledMonth(): could not get max scheduled date");
		}
		if (maxScheduledDate == null) {
			LOGGER.debug("getMaxScheduledMonth(): No scheduledPayment found for loanNumber: {}. Returning null.",
					loanNumber);
			return null;
		}
		LOGGER.debug("getMaxScheduledMonth(): loanNumber: {}, max scheduled month: {}", loanNumber,
				maxScheduledDate.toInstant().atZone(ZoneId.systemDefault()).getMonthValue());
		return maxScheduledDate.toInstant().atZone(ZoneId.systemDefault()).getMonthValue();
	}

	/**
	 * @param loanNumber
	 * @return BigDecimal
	 * @exception null pointer or IndexOutOfBond
	 * @Description : getting monthly payment on basis of loanNumber.
	 */
	@Override
	public BigDecimal getMonthlyPayment(long loanNumber) {
		LOGGER.debug("getMonthlyPayment(): loanNumber: {}", loanNumber);
		BigDecimal monthlyPayment = null;
		final String SQL = "SELECT sma070+sme010-sms010 as monthlyPayment " + "FROM " + ldataser
				+ ".SRVDSR WHERE smr010*10 = ?";
		List<BigDecimal> list = webJdbcTemplate.queryForList(SQL, BigDecimal.class, loanNumber);
		if (list != null && !list.isEmpty()) {
			monthlyPayment = list.get(0);
		} else {
			LOGGER.error("getMonthlyPayment(): could not get monthly payment for loanNumber: {}", loanNumber);
		}
		LOGGER.debug("getMonthlyPayment): monthlyPayment: {} loanNUmber: {}",
				(monthlyPayment != null ? monthlyPayment : "null"), loanNumber);
		return monthlyPayment;
	}

	/**
	 * @param loanNumber
	 * @return List<GetScheduledPaymentForCancellationDto>
	 * @exception null pointer or IndexOutOfBond
	 * @Description : getting Scheduled Payment List For Cancellation on basis of
	 *              loanNumber.
	 */
	@Override
	public List<GetScheduledPaymentForCancellationDto> getScheduledPaymentListForCancellation(long loanNumber) {
		LOGGER.debug("getScheduledPaymentListForCancellation(): loanNumber: {}", loanNumber);
		List<GetScheduledPaymentForCancellationDto> scheduledPayments = null;
		final String SQL = "SELECT ID, USER_ID, SCHEDULED_TYPE, PROCESSED_DATE, NEXT_SCHEDULED_DATE, LATE_FEE, NSF_FEE, EXTRA_PRINCIPAL, EXTRA_ESCROW, PAYMENT_SOURCE FROM "
				+ homeowner + ".SCHEDULED_PAYMENT "
				+ " WHERE LOAN_NUMBER = ? AND CANCELED = '0' AND PROCESSED_DATE IS NULL AND (PAYMENT_SOURCE='MBL' OR PAYMENT_SOURCE='WEB')"
				+ "ORDER BY LOAN_NUMBER, NEXT_SCHEDULED_DATE DESC";

		scheduledPayments = webJdbcTemplate.query(SQL, new GetScheduledForCancellationMapper(), loanNumber);
		LOGGER.debug(
				"getScheduledPaymentListForCancellation(): found {} GetScheduledPaymentForCancellationDto records for loanNumber: {}",
				(scheduledPayments != null ? scheduledPayments.size() : 0), loanNumber);
		return scheduledPayments;
	}

	/**
	 * @param paymentId
	 * @return ScheduledPaymentDto
	 * @exception null pointer or IndexOutOfBond
	 * @Description : getting Scheduled Payment By Payment Id.
	 */
	@Override
	public ScheduledPaymentDto getScheduledPaymentByPaymentId(String paymentId) {
		LOGGER.debug("getScheduledPaymentByPaymentId(): paymentId: {}", paymentId);
		ScheduledPaymentDto scheduledPayment = null;
		final String SQL = "SELECT ID, USER_ID, LOAN_NUMBER, CREATE_DATE, USER_DEFINED_DATE, NEXT_SCHEDULED_DATE, PROCESSED_DATE, SCHEDULED_TYPE, CANCELED, CANCELED_BY, CANCELED_DATE,	LATE_FEE, NSF_FEE, EXTRA_PRINCIPAL, EXTRA_ESCROW, EMAIL_REMINDER, PAYMENT_SOURCE, CONFIRMATION_NUMBER FROM "
				+ homeowner + ".SCHEDULED_PAYMENT WHERE ID = ?";
		List<ScheduledPaymentDto> list = webJdbcTemplate.query(SQL, new ScheduledPaymentMapper(), paymentId);
		if (list != null && !list.isEmpty()) {
			scheduledPayment = list.get(0);
		} else {
			LOGGER.error("getScheduledPaymentByPaymentId(): Could not find schedule payment for paymentId: {}",
					paymentId);
		}
		LOGGER.debug("getScheduledPaymentByPaymentId(): paymentId: {} {}", paymentId, scheduledPayment);
		return scheduledPayment;
	}

	/**
	 * @param paymentId
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Checking if Payment info has been moved to payment table by
	 *              paymentId.
	 */
	@Override
	public boolean hasPaymentMoved(int paymentId) {
		LOGGER.debug("hasPaymentMoved(): paymentId: {}", paymentId);
		final String SQL = "SELECT COUNT(*) FROM " + homeowner + ".PAYMENT WHERE SCHEDULED_PAYMENT_ID = ?";
		boolean result = webJdbcTemplate.queryForObject(SQL, Integer.class, paymentId) == 1;
		LOGGER.debug("hasPaymentMoved(): has payments for paymentId: {} moved: {}", paymentId, result);
		return result;
	}

	/**
	 * @param paymentId
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Canceling Payment In Payment Table ByPaymentId.
	 */
	@Override
	public boolean cancelPaymentInPaymentTableByPaymentId(int paymentId) {
		LOGGER.debug("cancelPaymentInPaymentTableByPaymentId(): paymentId: {}", paymentId);
		int rowsAffected = 0;
		boolean isPymentCanceled = false;
		final String SQL = "UPDATE " + homeowner + ".PAYMENT SET CANCELED = 1 WHERE SCHEDULED_PAYMENT_ID = ?";
		rowsAffected = webJdbcTemplate.update(SQL, paymentId);
		if (rowsAffected == 1) {
			isPymentCanceled = true;
		}
		LOGGER.debug("cancelPaymentInPaymentTableByPaymentId(): is PaymentId: {} canceled: {}", paymentId,
				isPymentCanceled);
		return isPymentCanceled;
	}

	/**
	 * @param paymentId, userId
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Canceling Payment ByPaymentId In Scheduled Payment Table.
	 */
	@Override
	public boolean cancelPaymentByPaymentId(int paymentId, int userId) {
		LOGGER.debug("cancelPaymentByPaymentId(): paymentId: {} userId: {}", paymentId, userId);
		int rowsAffected = 0;
		final String SQL = "UPDATE " + homeowner
				+ ".SCHEDULED_PAYMENT SET CANCELED = 1, CANCELED_DATE = CURRENT_TIMESTAMP, CANCELED_BY = ? WHERE ID = ?";
		rowsAffected = webJdbcTemplate.update(SQL, userId, paymentId);
		boolean result = (rowsAffected == 1);
		LOGGER.debug("cancelPaymentByPaymentId(): is paymentId: {} for userId: {} canceled: {}", paymentId, userId,
				result);
		return result;
	}

	/**
	 * @param loans
	 * @return List<PaymentDto>
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Getting List Of Payment History on the basis of loan Details.
	 */
	@Override
	public List<PaymentDto> getPaymentHistoryByLoanNumber(long loanNumber) {
		LOGGER.debug("getPaymentHistory(): for {} loan", loanNumber);
		List<PaymentDto> paymentList = null;
		final String SQL = "SELECT p.*, item.PAYMENT_ID, item.LOAN_NUMBER, item.AMOUNT, item.ITEM_TYPE, pb.ID as batch_id, "
				+ "pb.BATCH_TIME, pb.STATUS as batch_status, sp.SCHEDULED_TYPE, sp.PAYMENT_SOURCE, sp.NEXT_SCHEDULED_DATE, sp.CREATE_DATE, sp.CANCELED_DATE "
				+ "FROM " + homeowner + ".PAYMENT p " + " join " + homeowner
				+ ".PAYMENT_ITEM item ON p.ID = item.PAYMENT_ID " + "  LEFT JOIN " + homeowner
				+ ".SCHEDULED_PAYMENT sp ON sp.ID = p.SCHEDULED_PAYMENT_ID" + " LEFT JOIN " + homeowner
				+ ".PAYMENT_PAYMENT_BATCH ppb ON p.ID = ppb.PAYMENT_ID " + "  LEFT JOIN " + homeowner
				+ ".PAYMENT_BATCH pb on ppb.PAYMENT_BATCH_ID = pb.ID " + "WHERE "
				+ "((ppb.PAYMENT_BATCH_ID IS NOT NULL AND pb.STATUS NOT IN ('FAILED', 'RUNNING')) OR  p.CANCELED = 1)"
				+ " AND (sp.PAYMENT_SOURCE = 'MBL' OR sp.PAYMENT_SOURCE = 'WEB' OR sp.PAYMENT_SOURCE IS NULL) "
				+ "AND sp.CREATE_DATE >= CURRENT_DATE - 24 MONTH " + "AND item.loan_number =?";

		paymentList = webJdbcTemplate.query(SQL, new PaymentExtractor(), loanNumber);

		LOGGER.debug("getPaymentHistory(): found {} payment history records for loans",
				(paymentList != null ? paymentList.size() : 0));
		return paymentList;
	}

	/**
	 * @param loans
	 * @return List<ScheduledPaymentHistoryDto>
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Getting List Of Scheduled Payment History on the basis of loan
	 *              Details.
	 */
	@Override
	public List<ScheduledPaymentHistoryDto> getScheduledPaymentHistoryByLoanNumber(long loanNumber) {
		LOGGER.debug("getScheduledPaymentHistory(): for {} loans", loanNumber);
		List<ScheduledPaymentHistoryDto> scheduledPaymentHistoryList = null;
		final String SQL = "SELECT p.ID as PAYMENT_ID, sp.ID as SCHEDULED_PAYMENT_ID"
				+ ", sp.CONFIRMATION_NUMBER, sp.PAYMENT_SOURCE, sp.CREATE_DATE "
				+ ", sp.PROCESSED_DATE, sp.CANCELED, sp.CANCELED_DATE, sp.NEXT_SCHEDULED_DATE, sp.SCHEDULED_TYPE,"
				+ " sp.LOAN_NUMBER, sp.LATE_FEE, sp.NSF_FEE, sp.EXTRA_PRINCIPAL, sp.EXTRA_ESCROW "
				+ ", sp.MONTHLY_PAYMENT " + "FROM " + homeowner + ".SCHEDULED_PAYMENT sp LEFT JOIN " + homeowner
				+ ".PAYMENT p ON p.SCHEDULED_PAYMENT_ID = sp.ID " + "WHERE sp.CANCELED = 1 AND p.ID IS NULL AND"
				+ " sp.PAYMENT_SOURCE<>'TEL'" + " AND sp.LOAN_NUMBER = ?";

		scheduledPaymentHistoryList = webJdbcTemplate.query(SQL, new ScheduledPaymentHistoryMapper(), loanNumber);
		LOGGER.debug("getScheduledPaymentHistory(): found history records for {} loans",
				(scheduledPaymentHistoryList != null ? scheduledPaymentHistoryList.size() : 0));
		return scheduledPaymentHistoryList;
	}

	/**
	 * @param loanNumber
	 * @return String
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Getting Maturity date of Payment on the basis of loanNumber
	 */
	@Override
	public String getMaturityPaymentDate(long loanNumber) {
		LOGGER.debug("getMaturityPaymentDate(): loanNumber: {}", loanNumber);
		String maturityDate = null;
		final String SQL = "SELECT sml291 CONCAT '/' CONCAT sml292 CONCAT '/' CONCAT sml293 as maturityDate FROM "
				+ ldataser + ".SRVDSR " + "WHERE (smr010*10) = ?";
		List<String> list = webJdbcTemplate.queryForList(SQL, String.class, loanNumber);
		if (list != null && !list.isEmpty()) {
			maturityDate = list.get(0);
		} else {
			LOGGER.error("getMaturityPaymentDate(): loanNumber: {} could not get Maturity date", loanNumber);
		}
		LOGGER.debug("getMaturityPaymentDate(): loanNumber: {} has maturityDate: {}", loanNumber, maturityDate);
		return maturityDate;
	}

	/**
	 * @author nehas3
	 * @date April 29, 2019
	 * @return boolean
	 * @Description : this is isLoanInStopFile to check if loan exist in stop file
	 *              list
	 */
	@Override
	public boolean isLoanInStopFile(String loanNumber) {
		LOGGER.debug("isLoanInStopFile(): loanNumber: {}", loanNumber);
		String stopFileLoanNum = null;
		final String SQL = "SELECT * FROM " + utelib + ".STOPKEY WHERE CKEY = ?";
		List<String> result = webJdbcTemplate.queryForList(SQL, String.class, loanNumber);
		if (result != null && !result.isEmpty()) {
			stopFileLoanNum = result.get(0);
		}
		boolean isInStopFile = (stopFileLoanNum != null);
		LOGGER.debug("isLoanInStopFile(): loanNumber: {} isLoanInStopFile: {}", loanNumber, isInStopFile);
		return isInStopFile;
	}

	/**
	 * @param loanNumber
	 * @return String
	 * @exception null pointer or IndexOutOfBond
	 * @Description Getting First Payment date on the basis of loanNumber
	 */
	@Override
	public String getFirstPaymentDate(long loanNumber) {
		LOGGER.debug("getFirstPaymentDate(): loanNumber: {}", loanNumber);
		String firstPaymentDate = null;
		final String SQL = "SELECT sml111 CONCAT '/' CONCAT sml112 CONCAT '/' CONCAT sml113 as firstPaymentDate FROM "
				+ ldataser + ".SRVDSR " + "WHERE (smr010*10) = ?";
		List<String> list = webJdbcTemplate.queryForList(SQL, String.class, loanNumber);
		if (!list.isEmpty()) {
			firstPaymentDate = list.get(0);
		} else {
			LOGGER.error("getFirstPaymentDate(): could not get First Payment date");
		}
		LOGGER.debug("getFirstPaymentDate(): loanNumber: {}, firstPaymentDate: {}", loanNumber, firstPaymentDate);
		return firstPaymentDate;
	}

	/**
	 * @param
	 * @return List<ScheduledPaymentDto>
	 * @exception null pointer or IndexOutOfBond
	 * @Description Getting processed payment list of users whose payment has been
	 *              processed
	 */
	@Override
	public List<ProcessedPaymentDto> getProcessedPaymentListOfUsers() {
		LOGGER.debug("getProcessedPaymentListOfUsers(): called.");
		List<ProcessedPaymentDto> scheduledPayment = null;
		final String SQL = "SELECT sp.ID, sp.USER_ID, sp.LOAN_NUMBER, sp.PROCESSED_DATE, sp.SCHEDULED_TYPE, sp.CANCELED, p.ID AS PAYMENT_ID FROM "
				+ homeowner + ".SCHEDULED_PAYMENT AS sp LEFT JOIN " + homeowner
				+ ".PAYMENT as p ON sp.ID = p.SCHEDULED_PAYMENT_ID " + "WHERE sp.CANCELED='0' AND"
				+ " sp.PROCESSED_DATE = CURRENT_DATE";
		scheduledPayment = webJdbcTemplate.query(SQL, new ProcessedPaymentMapper());
		LOGGER.debug("getProcessedPaymentListOfUsers(): found {} ScheduledPaymentDto records",
				(scheduledPayment != null ? scheduledPayment.size() : 0));
		return scheduledPayment;
	}

	/**
	 * @author nehas3
	 * @date September 25, 2019
	 * @return boolean
	 * @param paymentID
	 * @return list of Payment Item
	 * @Description : this is getPaymentItemByPaymentId to get list of payment items while making payment
	 */
	@Override
	public List<PaymentItemDto> getPaymentItemByPaymentId(int paymentID) {
		LOGGER.debug("getPaymentItemByPaymentId(): paymentId: {}", paymentID);
		List<PaymentItemDto> paymentItem = null;
		final String SQL = "SELECT PAYMENT_ID, LOAN_NUMBER, AMOUNT, ITEM_TYPE FROM " + homeowner + ".PAYMENT_ITEM "
				+ "WHERE PAYMENT_ID = ?";
		paymentItem = webJdbcTemplate.query(SQL, new PaymentItemMapper(), paymentID);
		LOGGER.debug("getPaymentItemByPaymentId(): found {} PaymentItemDto records",
				(paymentItem != null ? paymentItem.size() : 0));
		return paymentItem;
	}

	/**
	 * @param userId, token
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description Once the user logs in, the FCM token gets save with this method.
	 */
	@Override
	public boolean saveUserInFCMTokenTable(int userId, String token) {
		LOGGER.debug("saveUserInFCMTokenTable(): userId: {}, token: {}", userId, token);
		Integer rowcount = 0;
		final String SQL = "INSERT INTO " + homeowner + ".FCM_TOKEN (USER_ID, TOKEN)  values(?,?)";
		rowcount = webJdbcTemplate.update(SQL, userId, token);
		LOGGER.debug("saveUserInFCMTokenTable(): userId: {}, token: {}, rowCount: {}", userId, token, rowcount);
		return (rowcount > 0);
	}

	/**
	 * @param userId
	 * @return List<TokenDto>
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Getting FCM token from database on basis of userId.
	 */
	@Override
	public List<TokenDto> getFCMToken(Integer userId) {
		LOGGER.debug("getFCMToken(): userId: {}", userId);
		List<TokenDto> tokenList = null;
		final String SQL = "SELECT ID,USER_ID , TOKEN FROM " + homeowner + ".FCM_TOKEN WHERE USER_ID = ?";
		tokenList = webJdbcTemplate.query(SQL, new TokenMapper(), userId);
		LOGGER.debug("getFCMToken(): userId: {}, found {} tokens", userId, (tokenList != null ? tokenList.size() : 0));
		return tokenList;
	}

	/**
	 * @param
	 * @return List<ScheduledPaymentDto>
	 * @exception null pointer or IndexOutOfBond
	 * @Description Getting scheduled payment info list to send the payment
	 *              reminder.
	 */
	@Override
	public List<ScheduledPaymentDto> getScheduledPaymentInfoListOfProcessedUsers() {
		LOGGER.debug("getScheduledPaymentInfoListOfProcessedUsers(): called.");
		List<ScheduledPaymentDto> scheduledPaymentList = null;
		final String SQL = "SELECT ID, USER_ID, LOAN_NUMBER, CREATE_DATE, USER_DEFINED_DATE, NEXT_SCHEDULED_DATE, PROCESSED_DATE, SCHEDULED_TYPE, CANCELED, CANCELED_BY, CANCELED_DATE,	LATE_FEE, NSF_FEE, EXTRA_PRINCIPAL, EXTRA_ESCROW, EMAIL_REMINDER, PAYMENT_SOURCE, CONFIRMATION_NUMBER FROM "
				+ homeowner + ".SCHEDULED_PAYMENT WHERE NEXT_SCHEDULED_DATE = CURRENT_DATE + 5 DAYS"
				+ " AND CANCELED='0' AND PROCESSED_DATE IS NULL";
		scheduledPaymentList = webJdbcTemplate.query(SQL, new ScheduledPaymentMapper());
		LOGGER.debug("getScheduledPaymentInfoListOfProcessedUsers(): found {} records.",
				(scheduledPaymentList != null ? scheduledPaymentList.size() : 0));
		return scheduledPaymentList;
	}

	/**
	 * @param userId, token
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description Checking if FCM token is already existing in database on basis
	 *              of userId and token.
	 */
	@Override
	public boolean isTokenRegistered(int userId, String token) {
		LOGGER.debug("isTokenRegistered(): userId: {}, token: {}", userId, token);
		boolean isTokenAvailable = false;
		List<TokenDto> tokenData = null;
		final String SQL = "SELECT ID, USER_ID, TOKEN FROM " + homeowner + ".FCM_TOKEN WHERE user_Id = ?"
				+ " AND token = ?";
		tokenData = webJdbcTemplate.query(SQL, new TokenMapper(), userId, token);
		if (!tokenData.isEmpty()) {
			isTokenAvailable = true;
		}
		LOGGER.debug("isTokenRegistered(): userId: {}, token: {}, isTokenRegistered: {}", userId, token,
				isTokenAvailable);
		return isTokenAvailable;
	}

	/**
	 * @param userId, notificationTitle, notificationBody
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description Once the FCM push notification gets sent, the notification gets
	 *              stored in database as same time with the help of
	 *              saveNotification method.
	 */
	@Override
	public boolean saveNotification(int userId, String notificationTitle, String notificationBody) {
		LOGGER.debug("saveNotification(): userId: {}, notificationTitle: {}, notificationBody: {}", userId,
				notificationTitle, notificationBody);
		Integer rowcount = 0;
		Date currentDate = new Date();
		final String SQL = "INSERT INTO " + homeowner
				+ ".FCM_NOTIFICATIONS (USER_ID, MESSAGE_TITLE, MESSAGE_BODY, READ_STATUS, SENT_MESSAGE_TIME)  VALUES(?,?,?,'0',?)";
		rowcount = webJdbcTemplate.update(SQL, userId, notificationTitle, notificationBody, currentDate);
		boolean result = (rowcount > 0);
		LOGGER.debug("saveNotification(): userId: {}, notificationTitle: {}, notificationBody: {}, isSaved: {}", userId,
				notificationTitle, notificationBody, result);
		return result;
	}

	/**
	 * @param userId
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Getting list of notifications for a particular user on basis
	 *              of userId.
	 */
	@Override
	public List<GetNotificationDetailsDto> getNotificationDetails(int userId) {
		LOGGER.debug("getNotificationDetails(): userId: {}", userId);
		List<GetNotificationDetailsDto> notificationDetailsList = null;
		final String SQL = "SELECT ID, USER_ID, MESSAGE_TITLE, MESSAGE_BODY, READ_STATUS, SENT_MESSAGE_TIME, SOFT_DELETE FROM "
				+ homeowner + ".FCM_NOTIFICATIONS WHERE USER_ID = ?"
				+ "AND SENT_MESSAGE_TIME IS NOT NULL AND SOFT_DELETE='0'";
		notificationDetailsList = webJdbcTemplate.query(SQL, new GetNotificationDetailsMapper(), userId);
		LOGGER.debug("getNotificationDetails(): userId: {}, {} records found", userId,
				(notificationDetailsList != null ? notificationDetailsList.size() : 0));
		return notificationDetailsList;
	}

	/**
	 * @param messageId
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Updating the read status of a message on the basis of its
	 *              messageId.
	 */
	@Override
	public boolean updateReadStatusOfMessage(int messageId) {
		LOGGER.debug("updateReadStatusOfMessage(): messageId: {}", messageId);
		int rowsAffected = 0;
		boolean isReadStatusUpdated = false;
		final String SQL = "UPDATE " + homeowner + ".FCM_NOTIFICATIONS " + "SET READ_STATUS = 1 WHERE ID = ?";
		rowsAffected = webJdbcTemplate.update(SQL, messageId);
		if (rowsAffected == 1) {
			isReadStatusUpdated = true;
		}
		LOGGER.debug("updateReadStatusOfMessage(): messageId: {}, isReadStatusUpdated: {}", messageId,
				isReadStatusUpdated);
		return isReadStatusUpdated;
	}

	/**
	 * @param messageId
	 * @return GetNotificationDetailsDto
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Getting all the notifications available in database on basis
	 *              of messageId.
	 */
	@Override
	public GetNotificationDetailsDto getNotificationDetailsByMessageId(int messageId) {
		LOGGER.debug("getNotificationDetailsByMessageId(): messageId: {}", messageId);
		GetNotificationDetailsDto notificationDetails = null;
		final String SQL = "SELECT ID, USER_ID, MESSAGE_TITLE, MESSAGE_BODY, READ_STATUS, SENT_MESSAGE_TIME, SOFT_DELETE FROM "
				+ homeowner + ".FCM_NOTIFICATIONS WHERE ID = ?"
				+ "AND SENT_MESSAGE_TIME IS NOT NULL AND SOFT_DELETE = '0'";
		List<GetNotificationDetailsDto> list = webJdbcTemplate.query(SQL, new GetNotificationDetailsMapper(),
				messageId);
		if (list != null && !list.isEmpty()) {
			notificationDetails = list.get(0);
		} else {
			LOGGER.error("Scheduled payment list could not be found");
		}
		LOGGER.debug("getNotificationDetailsByMessageId(): messageId: {}, {}", messageId, notificationDetails);
		return notificationDetails;
	}

	@Override
	public ACHPaymentsDto getAchPaymentDetails(long loanNumber) {
		LOGGER.debug("getAchPaymentDetails(): loanNumber: {}", loanNumber);
		ACHPaymentsDto aCHPaymentsDto = null;
		final String SQL = "SELECT ACHLOAN#, ACHTYPE, ACHDDAYS FROM " + homeowner + ".ACH_WORK WHERE ACHLOAN# = ?";
		List<ACHPaymentsDto> list = webJdbcTemplate.query(SQL, new ACHPaymentsMapper(), loanNumber);
		if (list != null && !list.isEmpty()) {
			aCHPaymentsDto = list.get(0);
		} else {
			LOGGER.error("ACH payment could not be found");
		}
		LOGGER.debug("getAchPaymentDetails(): loanNumber: {}, {}", loanNumber, aCHPaymentsDto);
		return aCHPaymentsDto;
	}

	/**
	 * @param messageId
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Soft deleting notifications on basis of messageId
	 */
	@Override
	public boolean deleteNotification(int messageId) {
		LOGGER.debug("deleteNotification(): messageId: {}", messageId);
		int rowsAffected = 0;
		boolean isNotificationDeleted = false;
		final String SQL = "UPDATE " + homeowner + ".FCM_NOTIFICATIONS " + "SET SOFT_DELETE = 1 WHERE ID = ?";
		rowsAffected = webJdbcTemplate.update(SQL, messageId);
		if (rowsAffected == 1) {
			isNotificationDeleted = true;
		}
		LOGGER.debug("deleteNotification(): messageId: {}, isNotificationDeleted: {}", messageId,
				isNotificationDeleted);
		return isNotificationDeleted;
	}

	/**
	 * @param userId
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description : user can log out b using logout API.
	 */
	@Override
	public boolean logout(int userId) {
		LOGGER.debug("logout(): userId: {}", userId);
		int rowsAffected = 0;
		boolean isLoggedOut = false;
		final String SQL = "UPDATE " + homeowner + ".user " + "SET LOGIN_STATUS = '0' WHERE ID = ?";
		rowsAffected = webJdbcTemplate.update(SQL, userId);
		if (rowsAffected == 1) {
			isLoggedOut = true;
		}
		LOGGER.debug("logout(): userId: {}, isLoggedOut: {}", userId, isLoggedOut);
		return isLoggedOut;
	}

	/**
	 * @param numberOfDays
	 * @return boolean
	 * @exception null pointer or IndexOutOfBond
	 * @Description : Notification will be deleted periodically on basis of given
	 *              number of days.
	 */
	@Override
	public boolean deleteNotificationsByScheduler(int numberOfDays) {
		LOGGER.debug("deleteNotificationsByScheduler(): numberOfDays: {}", numberOfDays);
		int rowsAffected = 0;
		boolean isNotificationDeleted = false;
		final String SQL = "DELETE FROM " + homeowner + ".FCM_NOTIFICATIONS "
				+ "WHERE SENT_MESSAGE_TIME <= CURRENT_DATE - " + numberOfDays + " DAYS";
		rowsAffected = webJdbcTemplate.update(SQL);
		if (rowsAffected > 0) {
			isNotificationDeleted = true;
		}
		LOGGER.debug("deleteNotificationsByScheduler(): numberOfDays: {}, isNotificationDeleted: {}", numberOfDays,
				isNotificationDeleted);
		return isNotificationDeleted;
	}

	/**
	 * @author nehas3
	 * @date Aug 27, 2018
	 * @return String
	 * @param schedulePaymentId
	 * @Description : this is getPaymentSourceOfScheduledPayment to get payment
	 *              source of already scheduled payment
	 */
	@Override
	public String getPaymentSourceOfScheduledPayment(int schedulePaymentId) {
		LOGGER.debug("getPaymentSourceOfScheduledPayment(): schedulePaymentId: {}", schedulePaymentId);
		String paymentSource = null;
		final String SQL = "SELECT PAYMENT_SOURCE FROM " + homeowner + ".SCHEDULED_PAYMENT WHERE ID = ?";
		List<String> list = webJdbcTemplate.queryForList(SQL, String.class, schedulePaymentId);
		if (list != null && !list.isEmpty()) {
			paymentSource = list.get(0);
		}
		LOGGER.debug("getPaymentSourceOfScheduledPayment(): schedulePaymentId: {}, paymentSource: {}",
				schedulePaymentId, paymentSource);
		return paymentSource;
	}

	/**
	 * @author nehas3
	 * @date March 7, 2019
	 * @param confirmationNumber, userId
	 * @return boolean
	 * @Description : this is doesConfirmationNumberExistForUser method to check if
	 *              confirmation number already exists
	 */
	@Override
	public boolean doesConfirmationNumberExistForUser(String confirmationNumber, int userId) {
		LOGGER.debug("doesConfirmationNumberExistForUser(): confirmationNumber: {}, userId: {}", confirmationNumber,
				userId);
		int numCollisions = 0;
		final String SQL = "SELECT count(*) FROM " + homeowner
				+ ".SCHEDULED_PAYMENT WHERE CONFIRMATION_NUMBER = ? AND USER_ID = ?";
		List<Integer> list = webJdbcTemplate.queryForList(SQL, Integer.class, confirmationNumber, userId);
		if (list != null && !list.isEmpty()) {
			numCollisions = list.get(0);
		} else {
			LOGGER.error(
					"doesConfirmationNumberExistForUser(): webJdbcTemplate.queryForList() failed for confirmationNumber: {} and userId: {}",
					confirmationNumber, userId);
		}
		boolean collided = numCollisions > 0;
		LOGGER.debug("doesConfirmationNumberExistForUser(): confirmationNumber: {}, userId: {}, didCollide: {}",
				confirmationNumber, userId, collided);
		return collided;
	}

	/**
	 * @author nehas3
	 * @date May 13, 2019
	 * @return boolean
	 * @param loanNumber
	 * @return boolean
	 * @Description : This is checkIfLoanAccInStopWeb method to check if the loan
	 *              account exist in stop web before scheduling the payment
	 */
	@Override
	public boolean checkIfLoanAccInStopWeb(long loanNumber) {
		LOGGER.debug("checkIfLoanAccInStopWeb(): loanNumber: {}", loanNumber);
		boolean isLoanInStopWeb = false;
		final String SQL = "SELECT SWLOAN# FROM " + uhfalib + ".STOPWEB WHERE SWLOAN# * 10 = ?";

		List<Long> stopWebLoanNumber = webJdbcTemplate.queryForList(SQL, Long.class, loanNumber);
		if (!stopWebLoanNumber.isEmpty()) {
			isLoanInStopWeb = true;
		} else {
			LOGGER.debug("payment is not in stopweb");
		}
		LOGGER.debug("checkIfLoanAccInStopWeb(): loanNumber isLoanInStopWeb: {}", isLoanInStopWeb);
		return isLoanInStopWeb;
	}

	/**
	 * @author nehas3
	 * @date September 23, 2019
	 * @return boolean
	 * @param incorrectBankInfoId
	 * @return boolean
	 * @Description : This is updateIncorrectBankingInfo method to update
	 *              notification read status in incorrect bank info table
	 */
	@Override
	public boolean updateIncorrectBankingInfo(int incorrectBankInfoId) {
		LOGGER.debug("updateIncorrectBankingInfo(): incorrectBankInfoId: {}", incorrectBankInfoId);
		int rowsAffected = 0;
		boolean isIncorrectBankingInfoUpdated = false;
		final String SQL = "UPDATE " + homeowner + ".INCORRECT_BANK_INFO "
				+ "SET NEEDS_NOTIFICATION = '0' WHERE ID = ?";
		rowsAffected = webJdbcTemplate.update(SQL, incorrectBankInfoId);
		if (rowsAffected == 1) {
			isIncorrectBankingInfoUpdated = true;
		}
		LOGGER.debug("updateIncorrectBankingInfo(): incorrectBankInfoId: {}, isIncorrectBankingInfoUpdated: {}",
				incorrectBankInfoId, isIncorrectBankingInfoUpdated);
		return isIncorrectBankingInfoUpdated;
	}

	/**
	 * @author nehas3
	 * @date JAN 25, 2020
	 * @return boolean
	 * @param userId         : integer value,
	 * @param accRecoveryKey : String value
	 * @return boolean
	 * @Description : This is insertAccountRecoveryKey to insert requested key for
	 *              account recovery process
	 */
	@Override
	public boolean insertAccountRecoveryKey(int userId, String accRecoveryKey) {
		LOGGER.debug("insertAccountRecoveryKey(): userID: {}, key: {}", userId, accRecoveryKey);
		LOGGER.debug("Entering into insertOnlinePrefStatus method");
		int updatedRowCount = 0;
		final String SQL = "INSERT INTO " + homeowner + ".ACC_RECOVERY(USER_ID, ACC_RECOVERY_KEY)"
				+ " VALUES (:userID, :accRecoveryKey)";
		SqlParameterSource params = new MapSqlParameterSource().addValue("userID", userId).addValue("accRecoveryKey",
				accRecoveryKey);

		updatedRowCount = namedParameterJdbcTemplate.update(SQL, params);
		LOGGER.debug("Exit from insertAccountRecoveryKey method");
		return ((updatedRowCount > 0) ? true : false);
	}

	/**
	 * @author nehas3
	 * @date JAN 27, 2020
	 * @return boolean
	 * @param userId
	 * @param accRecoveryKey
	 * @return true or false if key is valid or not
	 * @Description : this will help to validate is key is fresh and not existing.
	 */
	@Override
	public boolean isAccountRecoveryKeyExisting(int userId, String accRecoveryKey) {
		LOGGER.debug("Entering into isAccountRecoveryKeyExisting(): userID: {}, key: {}", userId, accRecoveryKey);
		boolean doesKeyExist = false;
		final String SQL = "SELECT USER_ID, ACC_RECOVERY_KEY FROM " + homeowner
				+ ".ACC_RECOVERY WHERE USER_ID = ? AND ACC_RECOVERY_KEY = ?";

		List<AccountRecoveryDto> list = webJdbcTemplate.query(SQL, new AccountRecoveryMapper(), userId, accRecoveryKey);
		if (list != null && !list.isEmpty()) {
			doesKeyExist = true;
		} else {
			LOGGER.error("isAccountRecoveryKeyExisting(): Key does not exist");
		}
		return doesKeyExist;
	}

	/**
	 * @author nehas3
	 * @date JAN 27, 2020
	 * @return AccountRecoveryDto
	 * @param accRecoveryKey
	 * @return account recovery data from database
	 * @Description : this will help to get account recovery data on basis of
	 *              account recovery key
	 */
	@Override
	public AccountRecoveryDto getAccountRecoveryKeyDetails(String accRecoveryKey) {
		LOGGER.debug("getAccountRecoveryKeyDetails(): accRecoveryKey: {}", accRecoveryKey);
		AccountRecoveryDto accRecoveryDto = null;
		final String SQL = "SELECT ID, USER_ID, ACC_RECOVERY_KEY, RECOVERY_TIME FROM " + homeowner
				+ ".ACC_RECOVERY WHERE ACC_RECOVERY_KEY = ?";

		List<AccountRecoveryDto> list = webJdbcTemplate.query(SQL, new AccountRecoveryMapper(), accRecoveryKey);
		if (list != null && !list.isEmpty()) {
			accRecoveryDto = list.get(0);
		} else {
			LOGGER.error("getAccountRecoveryKeyDetails(): could not find account recovery details");
		}
		return accRecoveryDto;
	}

	/**
	 * @author nehas3
	 * @date September 25, 2019
	 * @return boolean
	 * @param recoveruId
	 * @return true or false on reset recovery key
	 * @Description : this is resetRecoveryKeyForSuccess method gets called to reset some auto generated key,
	 * in the process of account recovery
	 */
	@Override
	public boolean resetRecoveryKeyForSuccess(int recoveryId) {
		LOGGER.debug("resetRecoveryKeyForSuccess(): recoveryId: {}", recoveryId);
		int rowsAffected = 0;
		boolean isKeyReset = false;
		final String SQL = "UPDATE " + homeowner + ".ACC_RECOVERY "
				+ "SET ACC_RECOVERY_KEY = NULL, SUCCESSFUL = '1' WHERE ID = ?";
		rowsAffected = webJdbcTemplate.update(SQL, recoveryId);
		if (rowsAffected == 1) {
			isKeyReset = true;
		}
		LOGGER.debug("resetRecoveryKeyForSuccess(): recoveryId: {} ", recoveryId);
		return isKeyReset;
	}

	/**
	 * @author nehas3
	 * @date September 23, 2018
	 * @return boolean
	 * @param recoveruId
	 * @return true or false on update user name
	 * @Description : this is resetRecoveryKeyForFailure method to update update Incorrect Banking Info on basis of
	 *              requested parameter
	 */
	@Override
	public boolean resetRecoveryKeyForFailure(int recoveryId) {
		LOGGER.debug("resetRecoveryKeyForFailure(): recoveryId: {}", recoveryId);
		int rowsAffected = 0;
		boolean isKeyReset = false;
		final String SQL = "UPDATE " + homeowner + ".ACC_RECOVERY "
				+ "SET ACC_RECOVERY_KEY = NULL, SUCCESSFUL = '0' WHERE ID = ?";
		rowsAffected = webJdbcTemplate.update(SQL, recoveryId);
		if (rowsAffected == 1) {
			isKeyReset = true;
		}
		LOGGER.debug("resetRecoveryKeyForFailure(): recoveryId: {} ", recoveryId);
		return isKeyReset;
	}

	@Override
	public List<EscrowDto> getEscrowInformationByLoanNum(List<Long> loanList) {
		LOGGER.debug("getEscrowInformationByLoanNum(): loanList has {} entries", loanList.size());
		List<EscrowDto> escrowDto = null;
		final String SQL = "SELECT CASE ESCTYP WHEN '31' THEN 'Property Tax' WHEN '40' THEN 'FHA Insurance' WHEN '41' THEN 'Private Insurance' WHEN '50' THEN 'Hazard Insurance' WHEN '51' THEN 'Flood Insurance' WHEN '66' THEN 'Condo Insurance' END AS \"type\" ,V010 AS \"vendor\" ,VENREF AS \"vendorID\" ,DATE ( CONCAT ( CONCAT ( CONCAT ( CONCAT ( EMATMO , '/' ) , EMATDA ) , '/' ) , EMATYR ) ) AS \"date\" ,ANLAMT AS \"amount\" FROM "
				+ ldataser + ".SRVESCE , " + ldataser + ".SRVVNM WHERE ESCTYP = V001 AND VENDOR = V002 AND LOAN = ?";
		Long loanNumber = loanList.get(0);
		escrowDto = (List<EscrowDto>) webJdbcTemplate.query(SQL, new Object[] { loanNumber }, new EsrowMapper());
		LOGGER.debug("getEscrowInformationByLoanNum(): found {} escrowDto records", escrowDto.size());
		return escrowDto;
	}

	@Override
	public List<PaymentDto> getPaymentHistory(List<LoanDto> loans) {
		LOGGER.debug("getPaymentHistory(): for {} loans", loans.size());
		if (loans.isEmpty()) {
			return Collections.emptyList();
		}
		List<PaymentDto> paymentList = null;
		String SQL = "SELECT p.*, item.PAYMENT_ID, item.LOAN_NUMBER, item.AMOUNT, item.ITEM_TYPE, pb.ID as batch_id, "
				+ "pb.BATCH_TIME, pb.STATUS as batch_status, sp.SCHEDULED_TYPE, sp.PAYMENT_SOURCE, sp.NEXT_SCHEDULED_DATE, sp.CREATE_DATE, sp.CANCELED_DATE "
				+ "FROM " + homeowner + ".PAYMENT p " + " join " + homeowner
				+ ".PAYMENT_ITEM item ON p.ID = item.PAYMENT_ID " + "  LEFT JOIN " + homeowner
				+ ".SCHEDULED_PAYMENT sp ON sp.ID = p.SCHEDULED_PAYMENT_ID" + " LEFT JOIN " + homeowner
				+ ".PAYMENT_PAYMENT_BATCH ppb ON p.ID = ppb.PAYMENT_ID " + "  LEFT JOIN " + homeowner
				+ ".PAYMENT_BATCH pb on ppb.PAYMENT_BATCH_ID = pb.ID " + "WHERE "
				+ "((ppb.PAYMENT_BATCH_ID IS NOT NULL AND pb.STATUS NOT IN ('FAILED', 'RUNNING')) OR  p.CANCELED = 1)"
				+ " AND (sp.PAYMENT_SOURCE = 'MBL' OR sp.PAYMENT_SOURCE = 'WEB' OR sp.PAYMENT_SOURCE IS NULL) "
				+ "AND sp.CREATE_DATE >= CURRENT_DATE - 24 MONTH "
				+ "AND item.loan_number in (?";

		Long[] loanNumbers = new Long[loans.size()];
		loanNumbers[0] = loans.get(0).getLoanNumber();
		for (int i = 1; i < loans.size(); i++) {
			loanNumbers[i] = loans.get(i).getLoanNumber();
			SQL += ",?";
		}
		SQL += ")";
		paymentList = webJdbcTemplate.query(SQL, loanNumbers, new PaymentExtractor());
		LOGGER.debug("getPaymentHistory(): found {} payment history records for {} loans",
				(paymentList != null ? paymentList.size() : 0), loans.size());
		return paymentList;
	}

	@Override
	public List<ScheduledPaymentHistoryDto> getScheduledPaymentHistory(List<LoanDto> loans) {
		LOGGER.debug("getScheduledPaymentHistory(): for {} loans", loans.size());
		List<ScheduledPaymentHistoryDto> scheduledPaymentHistoryList = null;
		String SQL = "SELECT p.ID as PAYMENT_ID, sp.ID as SCHEDULED_PAYMENT_ID"
				+ ", sp.CONFIRMATION_NUMBER, sp.PAYMENT_SOURCE, sp.CREATE_DATE "
				+ ", sp.PROCESSED_DATE, sp.CANCELED, sp.CANCELED_DATE, sp.NEXT_SCHEDULED_DATE, sp.SCHEDULED_TYPE,"
				+ " sp.LOAN_NUMBER, sp.LATE_FEE, sp.NSF_FEE, sp.EXTRA_PRINCIPAL, sp.EXTRA_ESCROW "
				+ ", sp.MONTHLY_PAYMENT " + "FROM " + homeowner + ".SCHEDULED_PAYMENT sp LEFT JOIN " + homeowner
				+ ".PAYMENT p ON p.SCHEDULED_PAYMENT_ID = sp.ID " + "WHERE sp.CANCELED = 1 AND p.ID IS NULL AND"
				+ " sp.PAYMENT_SOURCE<>'TEL'" + " AND sp.LOAN_NUMBER IN (?";

		Long[] loanNumbers = new Long[loans.size()];
		loanNumbers[0] = loans.get(0).getLoanNumber();
		for (int i = 1; i < loans.size(); i++) {
			loanNumbers[i] = loans.get(i).getLoanNumber();
			SQL += ",?";
		}
		SQL += ") ";

		scheduledPaymentHistoryList = webJdbcTemplate.query(SQL, loanNumbers, new ScheduledPaymentHistoryMapper());
		LOGGER.debug("getScheduledPaymentHistory(): found {} history records for {} loans",
				(scheduledPaymentHistoryList != null ? scheduledPaymentHistoryList.size() : 0), loans.size());
		return scheduledPaymentHistoryList;
	}

	@Override
	public CommunicationDto getCommunicationMessageDetailsByMessageId(long messageId) {
		LOGGER.debug("getCommunicationMessageDetailsByMessageId: messageID: {}", messageId);

		CommunicationDto communicationDto = null;
		final String SQL = "SELECT ID as messageId, MESSAGE,DISPLAY_START,DISPLAY_END,POP_UP,DISPLAY_ONCE,PRIORITY"
				+ " FROM "+ homeowner + ".Communications" + " WHERE ID = ? ";
		List<CommunicationDto> list = (List<CommunicationDto>) webJdbcTemplate.query(SQL, new Object[] { messageId },
				new CommunicationMapper());

		if (list != null && !list.isEmpty()) {
			communicationDto = list.get(0);
		} else {
			LOGGER.debug("invalid message id");
		}
		LOGGER.debug("getMessageDetailsByMessageId(): messageId: {}, {}", messageId,
				(communicationDto != null ? communicationDto.toString() : null));
		return communicationDto;

	}

	@Override
	public List<CommunicationDto> getCommunicationMessageList() {
		LOGGER.debug("getCommunicationMessageList()");
		List<CommunicationDto> communicationMessageList = null;
		final String SQL = "SELECT ID as messageID,MESSAGE,DISPLAY_START,DISPLAY_END,POP_UP,DISPLAY_ONCE,PRIORITY FROM "+ homeowner + ".Communications";
		communicationMessageList = webJdbcTemplate.query(SQL,new CommunicationMapper());
		LOGGER.debug("getCommunicationMessageList(): found {} CommunicationDto records",
				(communicationMessageList != null ? communicationMessageList.size() : 0));
		return communicationMessageList;
	}

	@Override
	public CommunicationViewDto getMessageViewedByMessageIdAndUserId(CommunicationViewMessageReq messageReq) {
		LOGGER.debug("getMessageViewedByMessageIdAndUserId: messageID: {}, userID: {}", messageReq.getMessageId(),messageReq.getUserId());

		CommunicationViewDto viewDto = null;
		final String SQL = "SELECT ID , USER_ID as userID, COMMUNICATIONS_ID as messageID, DATE_VIEWED"
				+ " FROM "+ homeowner + ".communications_viewed" + " WHERE USER_ID = ?  and COMMUNICATIONS_ID=?";
		List<CommunicationViewDto> list = (List<CommunicationViewDto>) webJdbcTemplate.query(SQL, new Object[] {messageReq.getUserId(),messageReq.getMessageId()},
				new CommunicationViewMapper());

		if (list != null && !list.isEmpty()) {
			viewDto = list.get(0);
		} else {
			LOGGER.debug("invalid message id or userId");
		}
		LOGGER.debug("getMessageViewedByMessageIdAndUserId(): messageId: {}, userID: {}", messageReq.getMessageId(),messageReq.getUserId(),
				(viewDto != null ? viewDto.toString() : null));
		return viewDto;
	}

	@Override
	public boolean saveMessageViewedRecord(CommunicationViewDto viewDto) {
		int updatedRowCount = 0;
		final String SQL = "INSERT INTO " + homeowner + ".communications_viewed (USER_ID, COMMUNICATIONS_ID)"
				+ " VALUES (:userId, :messageID)";
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userId", viewDto.getUserId())
				.addValue("messageID", viewDto.getMessageId());
		updatedRowCount = namedParameterJdbcTemplate.update(SQL, params);
		LOGGER.debug("insertPushNotificationFlag(): Updated {} records", updatedRowCount);
		return (updatedRowCount > 0);
	}

	@Override
	public LatestVersionDto getOSLatestVersionInfoByOSName(String osName) {
		LOGGER.debug("getOSLatestVersionInfoByOSName: osName: {}", osName);

		LatestVersionDto latestVersionDto = null;
		final String SQL = "SELECT ID as versionId, OS ,MAJOR_VERSION,MINOR_VERSION,PATCH_VERSION,PRODUCTION_DATE"
				+ " FROM "+ homeowner + ".mobile_version" + " WHERE OS = ? ";
		List<LatestVersionDto> list = (List<LatestVersionDto>) webJdbcTemplate.query(SQL, new Object[] { osName },
				new LatestOSVersionMapper());

		if (list != null && !list.isEmpty()) {
			latestVersionDto = list.get(0);
		} else {
			LOGGER.debug("invalid OS name");
		}
		LOGGER.debug("getOSLatestVersionInfoByOSName(): osName: {}, {}", osName,
				(latestVersionDto != null ? latestVersionDto.toString() : null));
		return latestVersionDto;

	}

	@Override
	public boolean updatePassword(int id, RecoveryAccountReq recoverAccountReq) {
		LOGGER.debug("resetPassword(): id {}, RecoverAccountReq: {}", id, recoverAccountReq);
		int updatedRowCount = 0;
		final String SQL = "UPDATE " + homeowner + ".USER SET PWD = ?, LOCKED = ?, LOGIN_FAILS = '0' WHERE ID = ?";
		updatedRowCount = webJdbcTemplate.update(SQL, recoverAccountReq.getNewPassword(), "", id);
		LOGGER.debug("resetPassword(): update {} USER.pwd fields for {}", updatedRowCount, recoverAccountReq);
		return (updatedRowCount > 0);
	}

	@Override
	public boolean updateAccount(int id, RecoveryAccountReq recoverAccountReq) {
		LOGGER.debug("resetAccount(): id {}, RecoverAccountReq: {}", id, recoverAccountReq);
		int updatedRowCount = 0;
		final String SQL = "UPDATE " + homeowner
				+ ".USER SET PWD = ?, EMAIL = ?, LOCKED = ?, LOGIN_FAILS = '0' WHERE ID = ?";
		updatedRowCount = webJdbcTemplate.update(SQL, recoverAccountReq.getNewPassword(),
				recoverAccountReq.getRecoveryEmail(), "", id);
		LOGGER.debug("resetAccount(): update {} USER.pwd fields for {}", updatedRowCount, recoverAccountReq);
		return (updatedRowCount > 0);
	}



	@Override
	public List<FAQCategoryDto> getFAQCategoryList() {
		LOGGER.debug("getgetFAQCategoryList()");
		List<FAQCategoryDto> categoryList = null;
		final String SQL = "SELECT ID ,NAME as categoryName  FROM "+ homeowner + ".FAQ_CATEGORY";
		List<FAQCategoryDto> list = webJdbcTemplate.query(SQL,new FAQCategoryMapper());

		if (list != null && !list.isEmpty()) {
			categoryList = list;
		} else {
			LOGGER.debug("There is no any category exist");
		}

		LOGGER.debug("getFAQCategoryList(): found {} FAQCategoryDto records",
				(categoryList != null ? categoryList.size() : 0));
		return categoryList;
	}

	@Override
	public List<FAQDto> getFAQByCategoryIdAndApp(long categoryId, String app) {
		LOGGER.debug("getFAQByCategoryIdAndApp: categoryId: {}, app: {}", categoryId ,app);

		List<FAQDto> faqDto = null;
		final String SQL = "SELECT ID , CATEGORY_ID, QUESTION_SEQUENCE, QUESTION_TITLE, QUESTION_ANSWER, APPLICATION, ACTIVE"
				+ " FROM "+ homeowner + ".FAQ" + " WHERE CATEGORY_ID = ?  and APPLICATION=?";
		List<FAQDto> list =  webJdbcTemplate.query(SQL, new Object[] {categoryId,app},
				new FAQMapper());

		if (list != null && !list.isEmpty()) {
			faqDto = list;
		} else {
			LOGGER.debug("There is no question answer exist on given categoryId");
		}
		LOGGER.debug("getFAQByCategoryIdAndApp(): categoryId: {}, app: {}", categoryId,app);
		return faqDto;
	}

	@Override
	public List<FAQDto> getFAQByCategoryId(long categoryId) {
		LOGGER.debug("getFAQByCategoryIdAndApp: categoryId: {}", categoryId);

		List<FAQDto> faqDto = null;
		final String SQL = "SELECT ID , CATEGORY_ID, QUESTION_SEQUENCE, QUESTION_TITLE, QUESTION_ANSWER, APPLICATION, ACTIVE"
				+ " FROM "+ homeowner + ".FAQ" + " WHERE CATEGORY_ID = ? and ACTIVE != 0";
		List<FAQDto> list =  webJdbcTemplate.query(SQL, new Object[] {categoryId},
				new FAQMapper());

		if (list != null && !list.isEmpty()) {
			faqDto = list;
		} else {
			LOGGER.debug("There is no question answer exist on given categoryId");
		}
		LOGGER.debug("getFAQByCategoryIdAndApp(): categoryId: {}", categoryId);
		return faqDto;
	}

	@Override
	public List<FAQDto> getFAQByWordSpecific(String searchString) {
		LOGGER.debug("getFAQByWordSpecific: searchString: {}", searchString);

		List<FAQDto> faqDto = null;
		final String SQL = "SELECT ID , CATEGORY_ID, QUESTION_SEQUENCE, QUESTION_TITLE, QUESTION_ANSWER, APPLICATION, ACTIVE \r\n" +
				"FROM home3.FAQ WHERE  ACTIVE != 0 and LOWER(QUESTION_TITLE) like LOWER('%"+searchString+"%') OR LOWER(QUESTION_ANSWER) like LOWER('%"+searchString+"%')";
		List<FAQDto> list =  webJdbcTemplate.query(SQL,new FAQMapper());

		if (list != null && !list.isEmpty()) {
			faqDto = list;
		} else {
			LOGGER.debug("There is no question answer exist on given searchString");
		}
		LOGGER.debug("getFAQByCategoryIdAndApp(): searchString: {}", searchString);
		return faqDto;
	}
}
