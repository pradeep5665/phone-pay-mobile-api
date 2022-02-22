package org.uhc.controller.envelop.response;

public class ValidateAccountRecoveryBySecureCodeRes {

	private Boolean isSuccessFull;
	private int statusCode;
	private String message;
	private int userId;
	private String userName;
	private String email;
	public Boolean getIsSuccessFull() {
		return isSuccessFull;
	}


	public void setIsSuccessFull(Boolean isSuccessFull) {
		this.isSuccessFull = isSuccessFull;
	}


	public int getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public final String getUserName() {
		return userName;
	}


	public final void setUserName(String userName) {
		this.userName = userName;
	}


	public final String getEmail() {
		return email;
	}


	public final void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "ValidateAccountRecoveryBySecureCodeRes [isSuccessFull=" + isSuccessFull + ", statusCode=" + statusCode
				+ ", message=" + message + ", userId=" + userId + "]";
	}
	
}
