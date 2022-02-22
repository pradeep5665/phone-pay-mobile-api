/* 
 * ===========================================================================
 * File Name LoanStatementMapper.java
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
 * $Log: LoanStatementMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.LoanStatementDto;

/**
 * @author nehas3
 * @date May 25, 2018
 * Description : This is The LoanStatementMapper class that is mapping the concerned table values to LoanStatementDto class.
 */
public class LoanStatementMapper implements RowMapper<LoanStatementDto> {
	
	@Override
	public LoanStatementDto mapRow(ResultSet rs, int i) throws SQLException{
		
		LoanStatementDto loanStatementDto = new LoanStatementDto();
		
		loanStatementDto.setStatementDate(rs.getDate("statement_date"));
		loanStatementDto.setFrontPath(rs.getString("front_path"));
		return loanStatementDto;
	}

}
