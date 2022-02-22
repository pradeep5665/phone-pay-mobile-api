/* 
 * ===========================================================================
 * File Name EsrowMapper.java
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
 * $Log: EsrowMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.EscrowDto;

/**
 * @author nehas3
 * @date May 25, 2018
 * Description : This is The EsrowMapper class that is mapping the concerned table values to EscrowDto class.
 */
public class EsrowMapper implements RowMapper<EscrowDto> {

	@Override
	public EscrowDto mapRow(ResultSet rs, int i) throws SQLException {

		EscrowDto escrow = new EscrowDto();
		escrow.setType(rs.getString("type"));
		escrow.setVendor(rs.getString("vendor"));
		escrow.setVendorID(rs.getString("vendorID"));
		escrow.setDate(rs.getString("date"));
		escrow.setAmount(rs.getBigDecimal("amount"));
		return escrow;

	}

}
