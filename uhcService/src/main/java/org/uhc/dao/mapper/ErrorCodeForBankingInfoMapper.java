/* 
 * ===========================================================================
 * File Name ErrorCodeForBankingInfoMapper.java
 * 
 * Created on Aug 23, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ErrorCodeForBankingInfoMapper.java,v $
 * ===========================================================================
 */
 package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.ErrorCodeForBankingInfoDto;

public class ErrorCodeForBankingInfoMapper implements RowMapper<ErrorCodeForBankingInfoDto>{

	@Override
	public  ErrorCodeForBankingInfoDto mapRow(ResultSet rs, int i) throws SQLException {
		
		ErrorCodeForBankingInfoDto errorInfoDto = new ErrorCodeForBankingInfoDto();
		errorInfoDto.setId(rs.getInt("id"));
		errorInfoDto.setNotificationMessage(rs.getString("notification_message"));
		errorInfoDto.setValidationMessage(rs.getString("validation_message"));
		return errorInfoDto;
	}
}
