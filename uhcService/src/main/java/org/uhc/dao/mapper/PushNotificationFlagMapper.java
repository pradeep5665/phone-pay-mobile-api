/* 
 * ===========================================================================
 * File Name PushNotificationFlagMapper.java
 * 
 * Created on Oct 19, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: PushNotificationFlagMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.PushNotificationFlagDto;

public class PushNotificationFlagMapper implements RowMapper<PushNotificationFlagDto> {

	@Override
	public PushNotificationFlagDto mapRow(ResultSet rs, int rowNum) throws SQLException { 
		PushNotificationFlagDto pnfDto = new PushNotificationFlagDto();
		pnfDto.setUserId(rs.getInt("USER_ID"));
		pnfDto.setFlagNameId(rs.getInt("FLAG_NAME_ID"));
		pnfDto.setFlagValue(rs.getInt("FLAG_VALUE"));
		return pnfDto;
	}

}
