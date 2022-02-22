/* 
 * ===========================================================================
 * File Name AccountRecoveryController.java
 * 
 * Created on Jan 24, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: AccountRecoveryController.java,v $
 * ===========================================================================
 */
package org.uhc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uhc.controller.envelop.request.RecoverAccountReq;
import org.uhc.controller.envelop.request.RecoveryAccountReq;
import org.uhc.controller.envelop.request.ValidateAccountRecoveryKeyReq;
import org.uhc.controller.envelop.request.ValidateAccountRecoveryReq;
import org.uhc.controller.envelop.request.ValidateAccountRecoverySecureCodeReq;
import org.uhc.service.RecoverAccountService;
import org.uhc.service.ValidateAccountRecoveryKeyService;
import org.uhc.service.ValidateAccountRecoveryService;

@RestController
@RequestMapping(value = "/")
public class AccountRecoveryController {
	private static final Logger LoGGER = LogManager.getLogger(AccountRecoveryController.class);

	@Autowired
	private ValidateAccountRecoveryService validateAccountRecoveryService;

	@Autowired
	private ValidateAccountRecoveryKeyService validateAccountRecoveryKeyService;

	@Autowired
	private RecoverAccountService recoverAccountService;

	/**
	 * validateAccountRecoveryAPI have been created to check if user, who are trying
	 * to recover their account, have correct details
	 * 
	 * @author nehas3
	 * @since Oct 13, 2020
	 * @param validateAccountRecoveryReq
	 * @return response of validate account recovery
	 */
	@PostMapping(value = "/validateAccountRecovery")
	public Object validateAccountRecoveryAPI(@RequestBody ValidateAccountRecoveryReq validateAccountRecoveryReq) {
		LoGGER.info("validateAccountRecovery API is called:{}",
				validateAccountRecoveryReq != null ? validateAccountRecoveryReq : "");
		return validateAccountRecoveryService.validateAccountRecoveryDetails(validateAccountRecoveryReq);
	}

	/**
	 * validateAccountRecoveryKey API have been created to validate the key being
	 * used by user, while recovering their account, if it is not expired or invalid
	 * @author nehas3
	 * @since Oct 13, 2020
	 * @param aacountRecKeyReq
	 * @return response of validate account recovery key api
	 */
	@PostMapping(value = "/validateAccountRecoveryKey")
	public Object validateAccountRecoveryKey(@RequestBody ValidateAccountRecoveryKeyReq aacountRecKeyReq) {
		LoGGER.info("validateAccountRecovery API is called:{}", aacountRecKeyReq);
		return validateAccountRecoveryKeyService.validateAccountRecoveryKey(aacountRecKeyReq);
	}

	/**
	 * recoverAccountByKey API have been created to make user recover their account on basis of key they used, if it is valid.
	 * @author nehas3
	 * @since Oct 13, 2020
	 * @param recoveryAccReq
	 * @return response of actual account recovery process
	 */
	@PostMapping(value = "/recoverAccount")
	public Object recoverAccountByKey(@RequestBody RecoverAccountReq recoveryAccReq) {
		LoGGER.info("recoverAccountByKey API is called:{}", recoveryAccReq);
		return recoverAccountService.recoverAccountByKey(recoveryAccReq);
	}
	
	

	/**
	 * validateAccountRecoveryAPI have been created to check if user, who are trying
	 * to recover their account, have correct details
	 * 
	 * @author pradeepy
	 * @since May 21, 2021
	 * @param validateAccountRecoveryWithSecureCodeReq
	 * @return response of validate account recovery
	 */
	@PostMapping(value = "/validateAccountRecoveryWithSecureCodeAPI")
	public Object validateAccountRecoveryWithSecureCodeAPI(@RequestBody ValidateAccountRecoverySecureCodeReq validateAccountRecoveryReq) {
		LoGGER.info("validateAccountRecovery API is called:{}",
				validateAccountRecoveryReq != null ? validateAccountRecoveryReq : "");
		return validateAccountRecoveryService.validateAccountRecoveryDetailsWithSecureCode(validateAccountRecoveryReq);
	}
	
	/**
	 * recoverAccountByUserId API have been created to make user recover their account on basis of userId.
	 * @author pradeepy
	 * @since May 24, 2021
	 * @param RecoveryAccountReq
	 * @return response of actual account recovery process
	 */
	@PostMapping(value = "/recoverAccountAPI")
	public Object accountRecoveryByUserId(@RequestBody RecoveryAccountReq recoveryAccReq) {
		LoGGER.info("recoverAccountByKey API is called:{}", recoveryAccReq);
		return recoverAccountService.recoverAccountByUserId(recoveryAccReq);
	}
}
