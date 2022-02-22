/* 
 * ===========================================================================
 * File Name TokenMapper.java
 * 
 * Created on Aug 13, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: TokenMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.TokenDto;

/**
 * @author nehas3
 * @date Aug 13, 2018
 * Description : This is The TokenMapper class that is mapping the concerned table values to TokenDto class.
 */
public class TokenMapper implements RowMapper<TokenDto>{

	@Override
	public TokenDto mapRow(ResultSet rs, int i) throws SQLException{
		
		TokenDto tokenDto = new TokenDto();
		tokenDto.setTokenId(rs.getInt("ID"));
		tokenDto.setUserId(rs.getInt("USER_ID"));
		tokenDto.setToken(rs.getString("TOKEN"));
		return tokenDto;
	}
}
