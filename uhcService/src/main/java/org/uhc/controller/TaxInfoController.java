/* 
 * ===========================================================================
 * File Name TaxInfoController.java
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
 * $Log: TaxInfoController.java,v $
 * ===========================================================================
 */
package org.uhc.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uhc.controller.envelop.request.TaxInformationRequest;
import org.uhc.service.TaxInformationService;

/**
 * @author nehas3
 * @date May 25, 2018 Description : This is AccountController class to move the
 *       control of the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class TaxInfoController {

	private static final Logger LOGGER = LogManager.getLogger(TaxInfoController.class);
	
	private TaxInformationService taxInfoService;

	@Value("${uhc.base_Path_Statement}")
	private String basePath;
	private String pdfServerBase;

	@Autowired
	public TaxInfoController(TaxInformationService taxInfoService, @Value("${uhc.pdf_server_base}") String pdfServerBase) {
		this.taxInfoService = taxInfoService;
		this.pdfServerBase = pdfServerBase;
	}

	/**
	 * This is Tax Information Service API to call taxInfo Service
	 * @author nehas3
	 * @date May 25, 2018
	 * @return Object
	 * @param taxInfoReq
	 * @return taxInfoResponse
	 */
	@PostMapping(value = "/taxInfo")
	public Object taxInformationAPI(@RequestBody TaxInformationRequest taxInfoReq) {

		LOGGER.info("TaxInformationService API is called: {}", taxInfoReq);
		return taxInfoService.getTaxInfo(taxInfoReq.getLoanNumber());
	}

	/**
	 * 
	 * @author nehas3
	 * @date June 2, 2018
	 * @return void
	 * @param request
	 * @param response
	 * @param statement
	 * @param year
	 * @param fileName
	 * @exception Description This downloadPDFResource to get the requested PDF
	 *                        download for tax info.
	 */
	@RequestMapping("downloadTaxStatement/{1098}/{year}/{fileName:.+}")
	public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("1098") String statement, @PathVariable("year") String year,
			@PathVariable("fileName") String fileName) {
		LOGGER.info("downloadTaxStatement(): Authorized user will download the file");
		String pdfUrl = pdfServerBase.concat("/").concat(statement).concat("/").concat(year).concat("/").concat(fileName);
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
		try {
			LOGGER.info("downloadTaxStatement(): pdfUrl: {}", pdfUrl);
			URL pdfServerURL = new URL(pdfUrl);
			//pdfServerURL.openStream().transferTo(response.getOutputStream());
			response.getOutputStream().flush();
			LOGGER.info("downloadTaxStatement(): File {} copied to response output stream.", pdfServerURL);
		} catch (IOException ex) {
			LOGGER.error("downloadTaxStatement(): PDF file could not be downloaded", ex);
		}
	}
}
