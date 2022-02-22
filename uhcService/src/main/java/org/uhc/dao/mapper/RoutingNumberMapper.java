/* 
 * ===========================================================================
 * File Name RoutingNumberMapper.java
 * 
 * Created on Jun 25, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: RoutingNumberMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.RoutingNumberDto;

/**
 * @author nehas3
 * @date jun 25, 2018
 * Description : This is The RoutingNumberMapper class that is mapping the concerned table values to PropertyDto class.
 */
public class RoutingNumberMapper implements RowMapper<RoutingNumberDto>{

	@Override
	public RoutingNumberDto mapRow(ResultSet rs, int i) throws SQLException {
		
		RoutingNumberDto routNumb = new RoutingNumberDto();
		routNumb.setRoutingNum(rs.getString("RNUMB"));
		return routNumb;
	}
}
