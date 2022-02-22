/* 
 * ===========================================================================
 * File Name BankingInfoController.java
 * 
 * Created on Jun 5, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: BankingInfoController.java,v $
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
import org.uhc.controller.envelop.request.BankingInfoRequest;
import org.uhc.controller.envelop.request.DeleteBankingInfoRequest;
import org.uhc.controller.envelop.request.UpdateBankingInfoRequest;
import org.uhc.service.BankingInfoService;
import org.uhc.service.DeleteBankingInfoService;
import org.uhc.service.UpdateBankingInfoService;

/**
 * @author nehas3
 * @date June 5, 2018
 * @Description : This is BankingInfoController class to move the control of the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class BankingInfoController {
	
	private static final Logger LOGGER = LogManager.getLogger(BankingInfoController.class);

	@Autowired
	private BankingInfoService bankingInfoService;
	
	@Autowired
	UpdateBankingInfoService updateBankingInfoService;
	
	@Autowired
	DeleteBankingInfoService deleteBankingInfoService;
	
	/**
	 * bankingInfoAPI have been created to get bank details of user on basis of their account details
	 * @author nehas3
	 * @date Jun 5, 2018
	 * @return Object 
	 * @param bankingInfoRequest
	 * @return bankingInfoResponse Service API to call the Account Service
	 */
	@PostMapping(value = "/bankingInfo")
	public Object bankingInfoAPI(@RequestBody BankingInfoRequest bankingInfoRequest) {
		LOGGER.info("Banking_Info Service API is called: {}", bankingInfoRequest!=null ? bankingInfoRequest.toString(): "");
		return bankingInfoService.getBankingInfo(bankingInfoRequest);
	}
	
	/**
	 * updateBankingInfoAPI have been created to update bank info of user if required
	 * @author nehas3
	 * @date Jun 5, 2018
	 * @return Object 
	 * @param updateBankingInfoRequest
	 * @return updateBankingInfoResponse 
	 */
	@PostMapping(value = "/updateBankingInfo")
	public Object updateBankingInfoAPI(@RequestBody UpdateBankingInfoRequest updateBankingInfoRequest) {
		LOGGER.info("UpdateBankingInfo Service API is called: {}", updateBankingInfoRequest!=null ? updateBankingInfoRequest.toString():"");
		return updateBankingInfoService.updateBankingInfo(updateBankingInfoRequest);
	}
	
	/**
	 * deleteBankingInfoAPI have been created to delete bank info
	 * @author nehas3
	 * @date June 7, 2018
	 * @return Object 
	 * @param deleteBankingInfoRequest
	 * @return
	 */
	@PostMapping(value = "/deleteBankingInfo")
	public Object deleteBankingInfoAPI(@RequestBody DeleteBankingInfoRequest deleteBankingInfoRequest) {
		LOGGER.info("Delete Banking Info Service API is called: {}", deleteBankingInfoRequest!=null ? deleteBankingInfoRequest.toString(): "");
		return deleteBankingInfoService.deleteBankingInfo(deleteBankingInfoRequest);
	}
}
