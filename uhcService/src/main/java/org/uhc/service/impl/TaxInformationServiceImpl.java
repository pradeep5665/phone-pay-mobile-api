/* 
 * ===========================================================================
 * File Name TaxInformationServiceImpl.java
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
 * $Log: TaxInformationServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.response.TaxInformationResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.TaxInfoDto;
import org.uhc.service.TaxInformationService;
import org.uhc.util.MessageReader;

/**
 * @author nehas3
 * @date May 25, 2018
 * @description Implementing TaxInformationService to get the
 *              TaxInformationResponse
 */
@Service
public class TaxInformationServiceImpl implements TaxInformationService {

	private static final Logger LOGGER = LogManager.getLogger(TaxInformationServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	@Value("${server.download.TaxPath}")
	private String downloadAction;
	
	@Value("${server.download.statements}")
	private String serverUrl;

	/**
	 * 
	 * @author nehas3
	 * @date May 25, 2018
	 * @return TaxInformationResponse
	 * @param loanNumber
	 * @return tax info for user Description : this is getTaxInfo that returns
	 *         tax info for user on basis of loan number.
	 */
	@Override
	public TaxInformationResponse getTaxInfo(Long loanNumber) {
		LOGGER.info("Entering into getTaxInfo method");
		List<TaxInfoDto> updatedTaxDto;
		TaxInformationResponse taxInfoResponse = new TaxInformationResponse();
		try {
			updatedTaxDto = new ArrayList<>();

			List<TaxInfoDto> taxInfoDtoList = userDao.getTaxInfobyLoanNumber(loanNumber);

			if (taxInfoDtoList != null) {
				taxInfoResponse.setIsSuccessful(true);
				taxInfoResponse.setError("");

				//
				for (TaxInfoDto taxInfoDto : taxInfoDtoList) {
					TaxInfoDto taxStatement = new TaxInfoDto();
					String taxPath = serverUrl.concat(downloadAction).concat(taxInfoDto.getPath());
					taxStatement.setPath(taxPath);
					taxStatement.setLoanNumber(taxInfoDto.getLoanNumber());
					taxStatement.setType(taxInfoDto.getType().trim());
					if("9c".equals(taxInfoDto.getType())) {
						taxStatement.setYear(taxInfoDto.getYear() + " 1099-C");
					}else if("9m".equals(taxInfoDto.getType())){
						taxStatement.setYear(taxInfoDto.getYear() + " 1099-MISC");
					}else {
						taxStatement.setYear(taxInfoDto.getYear());
					}
					
					updatedTaxDto.add(taxStatement);
				}
				taxInfoResponse.setTaxInformation(updatedTaxDto);

			} else {
				taxInfoResponse.setIsSuccessful(false);
				taxInfoResponse.setError(messageReader.getPropertyFileMesssages().get("taxinfo.error"));
			}
		} catch (Exception exp) {
			taxInfoResponse.setIsSuccessful(false);
			taxInfoResponse.setError(exp.getMessage());
			LOGGER.error("TaxInformationServiceImpl :: ", exp);
		}
		LOGGER.info("Exit From getTaxInfo method");
		return taxInfoResponse;
	}
}
