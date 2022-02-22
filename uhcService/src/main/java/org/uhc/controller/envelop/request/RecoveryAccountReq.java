package org.uhc.controller.envelop.request;

public class RecoveryAccountReq {

	private int userId;
	private String recoveryEmail;
	private String newPassword;
	public final int getUserId() {
		return userId;
	}
	public final void setUserId(int userId) {
		this.userId = userId;
	}
	public final String getRecoveryEmail() {
		return recoveryEmail;
	}
	public final void setRecoveryEmail(String recoveryEmail) {
		this.recoveryEmail = recoveryEmail;
	}
	public final String getNewPassword() {
		return newPassword;
	}
	public final void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	@Override
	public String toString() {
		return "RecoveryAccountReq [userId=" + userId + ", recoveryEmail=" + recoveryEmail + ", newPassword="
				+ newPassword + "]";
	}
}
