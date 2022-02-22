/* 
 * ===========================================================================
 * File Name UserMapper.java
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
 * $Log: UserMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.UserDto;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author nehas3
 * @date May 25, 2018
 * Description : This is The UserMapper class that is mapping the concerned table values to UserDto class.
 */
public class UserMapper implements RowMapper<UserDto> {
	@Override
	public UserDto mapRow(ResultSet rs, int i) throws SQLException {
		UserDto user = new UserDto();
		user.setUserId(rs.getInt("ID"));
		user.setUsername(rs.getString("USERNAME"));
		user.setFirstName(rs.getString("FIRST_NAME"));
		user.setLastName(rs.getString("LAST_NAME"));
		String eMail = rs.getString("EMAIL");
		if (eMail == null || eMail.isEmpty()) {
			eMail = "EMAIL UNKNOWN";
		}
		user.setEmail(eMail);
		user.setRole(rs.getString("ROLE"));
		user.setLoginFails(rs.getInt("LOGIN_FAILS"));
		user.setLocked("Y".equals(rs.getString("LOCKED")));
		user.setLoginStatus(rs.getInt("LOGIN_STATUS")==1);
		user.setNotificationEnabled(rs.getInt("FLAG_VALUE")==1);
		return user;
	}
}
