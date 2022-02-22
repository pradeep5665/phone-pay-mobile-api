package org.uhc.controller.envelop.response;

import org.uhc.dao.dto.CommunicationDto;

public class CommunicationResponse {

	private Boolean isSuccessful;
	private int statusCode;
	private CommunicationDto communicationDto;
	public Boolean getIsSuccessful() {
		return isSuccessful;
	}
	public void setIsSuccessful(Boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public CommunicationDto getCommunicationDto() {
		return communicationDto;
	}
	public void setCommunicationDto(CommunicationDto communicationDto) {
		this.communicationDto = communicationDto;
	}
	
}
