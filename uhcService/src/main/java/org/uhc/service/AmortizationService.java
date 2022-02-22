package org.uhc.service;


import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.uhc.controller.envelop.request.AmortizationPdfRequest;
import org.uhc.controller.envelop.response.AmortizationResponse;

/**
 * @author pradeepy
 * @date October 15, 2021
 */
public interface AmortizationService {

	/**
	 * @author pradeepy
	 * @date October 15, 2021
	 * @return pdfResponse 
	 * @param pdfRequest
	 * Description : getAmortizationGeneratePdfInfo that returns generate pdf status.
	 */
	
    ResponseEntity<Resource>  getAmortizationGeneratePdfInfo(AmortizationPdfRequest pdfRequest);
}
