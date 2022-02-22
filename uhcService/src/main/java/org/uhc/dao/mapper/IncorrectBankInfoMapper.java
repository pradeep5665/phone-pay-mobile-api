/* 
 * ===========================================================================
 * File Name IncorrectBankInfoMapper.java
 * 
 * Created on Jun 27, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: IncorrectBankInfoMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.IncorrectBankingInfoDto;

/**
 * @author nehas3
 * @date Jun 27, 2018
 * Description : This is The IncorrectBankInfoMapper class that is mapping the concerned table values to IncorrectBankingInfoDto class.
 */
public class IncorrectBankInfoMapper implements RowMapper<IncorrectBankingInfoDto>{

	@Override
	public IncorrectBankingInfoDto mapRow(ResultSet rs, int i) throws SQLException{
		
		IncorrectBankingInfoDto incorrectBankInfoDto = new IncorrectBankingInfoDto();
		incorrectBankInfoDto.setId(rs.getInt("ID"));
		incorrectBankInfoDto.setUserId(rs.getInt("USER_ID"));
		incorrectBankInfoDto.setRoutingNum(rs.getString("ROUTING_NUMBER"));
		incorrectBankInfoDto.setAccountNum(rs.getString("ACCOUNT_NUMBER"));
		incorrectBankInfoDto.setErrorCode(rs.getInt("ERROR_ID"));
		incorrectBankInfoDto.setNeedsNotification(rs.getString("NEEDS_NOTIFICATION"));
		return incorrectBankInfoDto;
	}
}
