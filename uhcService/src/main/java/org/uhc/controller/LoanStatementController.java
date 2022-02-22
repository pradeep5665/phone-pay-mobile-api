/* 
 * ===========================================================================
 * File Name LoanStatementController.java
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
 * $Log: LoanStatementController.java,v $
 * ===========================================================================
 */
package org.uhc.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.naming.MalformedLinkException;
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
import org.uhc.controller.envelop.request.LoanStatementsRequest;
import org.uhc.service.LoanStatementService;

/**
 * @author nehas3
 * @date May 25, 2018
 * @Description : This is LoanStatementController class to move the control of
 *              the concerned Request URL to the correct Service.
 */
@RestController
@RequestMapping(value = "/")
public class LoanStatementController {

	private static final Logger LOGGER = LogManager.getLogger(LoanStatementController.class);

	private LoanStatementService loanStatementService;

	@Value("${uhc.base_Path_Statement}")
	private String basePath;
	private String pdfServerBase;

	@Autowired
	protected LoanStatementController(LoanStatementService loanStatementService,
									  @Value("${uhc.pdf_server_base}") String pdfServerBase) {
		this.loanStatementService = loanStatementService;
		this.pdfServerBase = pdfServerBase;
	}

	/**
	 * loanStatementsResponse Loan Statement API to call the loan statement Service
	 * @author nehas3
	 * @date May 25, 2018
	 * @return Object
	 * @param loanStatementsRequest
	 * @return 
	 */
	@PostMapping(value = "/loanStatements")
	public Object loanStatementAPI(@RequestBody LoanStatementsRequest loanStatementsRequest) {
		LOGGER.info("LoanStatementService API is called: {}" ,loanStatementsRequest!=null ? loanStatementsRequest.toString() :"");
		return loanStatementService.getLoanStatement(loanStatementsRequest);
	}

	/**
	 * downloadPDFResource API have been created to download loan statement for user in pdf format
	 * @author nehas3
	 * @date June 2, 2018
	 * @return void
	 * @param request
	 * @param response
	 * @param statement
	 * @param year
	 * @param month
	 * @param fileName
	 */
	@RequestMapping("/downloadLoanStatement/{statement}/{year}/{month}/{fileName:.+}")
	public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("statement") String statement, @PathVariable("year") String year,
			@PathVariable("month") String month, @PathVariable("fileName") String fileName) {

		LOGGER.info("downloadPDFResource(): Authorized user will download the file: statement: {}, year: {}, month: {}, fileName: {}",
				statement, year, month, fileName);
		String pdfUrl = pdfServerBase.concat("/").concat(statement).concat("/").concat(year).concat("/").concat(month).concat("/").concat(fileName);
		try {
			URL pdfServerURL = new URL(pdfUrl);
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
			try {
				//pdfServerURL.openStream().transferTo(response.getOutputStream());
				response.getOutputStream().flush();
				LOGGER.info("downloadPDFResource(): File {} copied to response output stream.", pdfServerURL);
			} catch (IOException ex) {
				LOGGER.error("downloadPDFResource(): PDF file could not be downloaded", ex);
			}
		}
		catch( MalformedURLException mue) {
			LOGGER.error("downloadPDFResource(): Statement doesn't exist! Path: {}", pdfUrl);
		}
	}

	/**
	 * @author nehas3
	 * @date June 3, 2018
	 * @return void
	 * @param pdf
	 * @param fileName
	 * @exception IOException Description : This downloadPDFResource to get the
	 *                        requested PDF download for loan statements for Back
	 *                        path URL.
	 */
	@RequestMapping("/downloadLoanStatement/{pdf}/{fileName:.+}")
	public void downloadBackStatement(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("pdf") String pdf, @PathVariable("fileName") String fileName) {

		LOGGER.info("downloadBackStatement(): Authorized user will download the file: pdf: {}, fileName: {}",
				pdf, fileName);
		String pdfUrl = pdfServerBase.concat("/").concat(pdf).concat("/").concat(fileName);
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
		try {
			URL pdfServerURL = new URL(pdfUrl);
		//	pdfServerURL.openStream().transferTo(response.getOutputStream());
			response.getOutputStream().flush();
			LOGGER.info("downloadBackStatement(): File {} copied to response output stream.", pdfServerURL);
		} catch (IOException ex) {
			LOGGER.error("downloadBackStatement(): PDF file could not be downloaded", ex);
		}
	}
}
