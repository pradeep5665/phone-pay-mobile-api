/* 
 * ===========================================================================
 * File Name UserDto.java
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
 * $Log: UserDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

import javax.naming.NamingException;
import javax.naming.directory.SearchResult;

import static org.uhc.dao.dto.UserDto.UserRole.SERVICING_ADMIN;
import static org.uhc.dao.dto.UserDto.UserRole.SERVICING_USER;

/**
 * @author nehas3
 * @date May 25, 2018
 */
public class UserDto {

	public enum UserRole {
		SERVICING_USER("ServicingUser"), SERVICING_ADMIN("ServicingAdmin");

		private String userRoles;

		UserRole(String role) {
			this.userRoles = role;
		}

		public String getUserRole() {
			return userRoles;
		}
	}

	private int userId;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String role;
	private int loginFails;
	private boolean locked;
	private boolean loginStatus;
	private boolean isNotificationEnabled;

	public UserDto() {
		// constructor
	}

	public UserDto(SearchResult result) throws NamingException {
		this.role = (String) result.getAttributes().get("employeeType").get(0);
		this.firstName = (String) result.getAttributes().get("GivenName").get(0);
		this.lastName = (String) result.getAttributes().get("sn").get(0);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName.trim();
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName.trim();
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isServicing() {
		return SERVICING_USER.getUserRole().equals(role) || SERVICING_ADMIN.getUserRole().equals(role);
	}

	public boolean isServicingPlus() {
		return SERVICING_ADMIN.getUserRole().equals(role);
	}

	public int getLoginFails() {
		return loginFails;
	}

	public void setLoginFails(int loginFails) {
		this.loginFails = loginFails;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(boolean loginStatus) {
		this.loginStatus = loginStatus;
	}

	public boolean isNotificationEnabled() {
		return isNotificationEnabled;
	}

	public void setNotificationEnabled(boolean isNotificationEnabled) {
		this.isNotificationEnabled = isNotificationEnabled;
	}

	@Override
	public String toString() {
		return "UserDto{" + "userId=" + userId + ", username='" + username + '\'' + ", firstName='" + firstName + '\''
				+ ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + ", role='" + role + '\''
				+ ", loginFails=" + loginFails + ", locked=" + locked + ", loginStatus=" + loginStatus + '}';
	}
}
