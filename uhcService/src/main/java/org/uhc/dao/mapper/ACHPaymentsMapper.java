/* 
 * ===========================================================================
 * File Name ACHPaymentsMapper.java
 * 
 * Created on Sep 27, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ACHPaymentsMapper.java,v $
 * ===========================================================================
 */
 package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.ACHPaymentsDto;

public class ACHPaymentsMapper implements RowMapper<ACHPaymentsDto>{
	@Override
	public  ACHPaymentsDto mapRow(ResultSet rs, int i) throws SQLException {
		
		ACHPaymentsDto aCHPaymentsDto = new ACHPaymentsDto();
		aCHPaymentsDto.setAchLoanNumber(rs.getLong("ACHLOAN#"));
		aCHPaymentsDto.setAchType(rs.getString("ACHTYPE"));
		aCHPaymentsDto.setAchDays(rs.getInt("ACHDDAYS"));
		return aCHPaymentsDto;
		
	}
}
