package org.uhc.controller.envelop.response;

import org.uhc.dao.dto.LatestVersionDto;

public class LatestVersionResponse {

	private Boolean isSuccessful;
	private String message;
	private LatestVersionDto latestVersionDto;
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
	public LatestVersionDto getLatestVersionDto() {
		return latestVersionDto;
	}
	public void setLatestVersionDto(LatestVersionDto latestVersionDto) {
		this.latestVersionDto = latestVersionDto;
	}
	
	
}
