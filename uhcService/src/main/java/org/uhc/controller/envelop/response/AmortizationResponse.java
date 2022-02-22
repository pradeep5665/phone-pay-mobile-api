package org.uhc.controller.envelop.response;

import java.io.Serializable;

/**
 * @author pradeepy
 * @date october 15, 2021
 * @description creating Amortization Service Response for success or failure
 */
public class AmortizationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean isSuccessful;
	private String message;
	private String fileName;
	private  String pdfStreamFile;
	
	public final Boolean getIsSuccessful() {
		return isSuccessful;
	}
	public final void setIsSuccessful(Boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	public final String getMessage() {
		return message;
	}
	public final void setMessage(String message) {
		this.message = message;
	}
	public final String getPdfStreamFile() {
		return pdfStreamFile;
	}
	public final void setPdfStreamFile(String pdfStreamFile) {
		this.pdfStreamFile = pdfStreamFile;
	}
	public final String getFileName() {
		return fileName;
	}
	public final void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
		
}
