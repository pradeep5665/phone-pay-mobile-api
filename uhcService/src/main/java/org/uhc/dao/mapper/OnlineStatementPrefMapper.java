/* 
 * ===========================================================================
 * File Name OnlineStatementPrefMapper.java
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
 * $Log: OnlineStatementPrefMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.OnlineStatementsPrefDto;

/**
 * @author nehas3
 * @date May 25, 2018
 * Description : This is The OnlineStatementPrefMapper class that is mapping the concerned table values to OnlineStatementsPrefDto class.
 */
public class OnlineStatementPrefMapper implements RowMapper<OnlineStatementsPrefDto> {

	@Override
	public OnlineStatementsPrefDto mapRow(ResultSet rs, int i) throws SQLException{
		OnlineStatementsPrefDto onlineStatementsPrefDto = new OnlineStatementsPrefDto();
		onlineStatementsPrefDto.setLoanNumber(rs.getLong("LOAN"));
		onlineStatementsPrefDto.setOnlineStatements(rs.getInt("ONLINE_STATEMENTS") == 1);
		
		return onlineStatementsPrefDto;
	}
	
}
