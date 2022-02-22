package org.uhc.controller.envelop.response;

import java.util.List;

import org.uhc.dao.dto.CommunicationDto;

public class CommunicationMessagesResponse {

	private Boolean isSuccessful;
	private String message;
	private List<CommunicationDto> communicationMessagesList;
	
	public Boolean getIsSuccessful() {
		return isSuccessful;
	}
	public void setIsSuccessful(Boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<CommunicationDto> getCommunicationMessagesList() {
		return communicationMessagesList;
	}
	public void setCommunicationMessagesList(List<CommunicationDto> communicationMessagesList) {
		this.communicationMessagesList = communicationMessagesList;
	}
	@Override
	public String toString() {
		return "CommunicationMessagesResponse [isSuccessful=" + isSuccessful + ", message=" + message
				+ ", communicationMessages=" + communicationMessagesList + "]";
	}
	
	
	
}
