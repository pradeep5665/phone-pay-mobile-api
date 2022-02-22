/* 
 * ===========================================================================
 * File Name TaxInfoMapper.java
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
 * $Log: TaxInfoMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.TaxInfoDto;

/**
 * @author nehas3
 * @date May 25, 2018
 * Description : This is The TaxInfoMapper class that is mapping the concerned table values to TaxInfoDto class.
 */
public class TaxInfoMapper implements RowMapper<TaxInfoDto> {

	@Override
	public TaxInfoDto mapRow(ResultSet rs, int i) throws SQLException {
		TaxInfoDto taxInfoDto = new TaxInfoDto();
		taxInfoDto.setLoanNumber(rs.getLong("LOAN_NUMBER"));
		taxInfoDto.setPath(rs.getString("PATH"));
		taxInfoDto.setYear(rs.getString("YEAR"));
		taxInfoDto.setType(rs.getString("TYPE"));
		return taxInfoDto;
	}
}
