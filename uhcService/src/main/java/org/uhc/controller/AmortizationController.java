package org.uhc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uhc.controller.envelop.request.AmortizationPdfRequest;
import org.uhc.service.AmortizationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author pradeepy
 * @date October 15, 2021
 * @Description : This is AmortizationController class to create Amortization PDF 
 * and send in mail attachment.
 */
@RestController
@RequestMapping(value = "/")
public class AmortizationController {

	private static final Logger LoGGER = LogManager.getLogger(AmortizationController.class);
	
	@Autowired
	private AmortizationService amortizationService;
	/**
	 * This is amortizationPdfAPI to create amortization Pdf
	 * @author pradeepy
	 * @date October 15, 2021
	 * @return Object
	 * @param pdfRequest
	 * @return pdfResponse
	 */
	/*@PostMapping(value = "/amortizationPdfBase64API")
	 
	@ApiOperation("create amortization pdf and return in base64 format")
	public Object amortizationPdfBase64API(@RequestBody AmortizationPdfRequest pdfRequest) {
		LoGGER.info("{\"amortizationPdfBase64API\": {} }", pdfRequest);
		return amortizationService.getAmortizationGeneratePdfAndReturnInBase64(pdfRequest);
	}
	
	@PostMapping(value = "/amortizationPdfbyteAPI")
 	@ApiOperation("create amortization pdf and return in byte format")
	public Object amortizationPdfbyteAPI(@RequestBody AmortizationPdfRequest pdfRequest) {
		LoGGER.info("{\"amortizationPdfbyteAPI\": {} }", pdfRequest);
		return amortizationService.getAmortizationGeneratePdfAndReturnInByte(pdfRequest);
	}*/
	
	
	
	/**
	 * This is amortizationPdfAPI to create amortization Pdf
	 * @author pradeepy
	 * @date October 15, 2021
	 * @return Object
	 * @param pdfRequest
	 * @return get PDF resource in Response
	 */
	@PostMapping(value ="/amortizationPdfResourseAPI")
	@ApiOperation("create amortization pdf and return in resource format")
	public     ResponseEntity<Resource>  amortizationPdfResourseAPI (@RequestBody AmortizationPdfRequest pdfRequest)  {
		LoGGER.info("{\"amortizationPdfResourseAPI\": {} }", pdfRequest);
	       return amortizationService.getAmortizationGeneratePdfInfo(pdfRequest);
}
}
