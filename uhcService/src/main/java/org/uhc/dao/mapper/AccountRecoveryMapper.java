/* 
 * ===========================================================================
 * File Name AccountRecoveryMapper.java
 * 
 * Created on Jan 25, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: AccountRecoveryMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.AccountRecoveryDto;

public class AccountRecoveryMapper implements RowMapper<AccountRecoveryDto> {

	@Override
	public AccountRecoveryDto mapRow(ResultSet rs, int i) throws SQLException {

		AccountRecoveryDto accountRecoveryDto = new AccountRecoveryDto();
		accountRecoveryDto.setId(rs.getInt("ID"));
		accountRecoveryDto.setUserId(rs.getInt("USER_ID"));
		accountRecoveryDto.setAccRecoveryKey(rs.getString("ACC_RECOVERY_KEY"));
		accountRecoveryDto.setRecoveryTime(rs.getTimestamp("RECOVERY_TIME"));
		return accountRecoveryDto;

	}
}
