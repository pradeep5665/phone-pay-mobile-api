/* 
 * ===========================================================================
 * File Name GetNotificationDetailsMapper.java
 * 
 * Created on Aug 22, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetNotificationDetailsMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.GetNotificationDetailsDto;

/**
 * @author nehas3
 * @date Aug 22, 2018
 * Description : This is The GetNotificationDetailsMapper class that is mapping the concerned table values to GetNotificationDetailsDto class.
 */
public class GetNotificationDetailsMapper implements RowMapper<GetNotificationDetailsDto> {

	@Override
	public GetNotificationDetailsDto mapRow(ResultSet rs, int i) throws SQLException{
		
		GetNotificationDetailsDto getNotificationDetails = new GetNotificationDetailsDto();
		getNotificationDetails.setMessageId(rs.getInt("ID"));
		getNotificationDetails.setUserId(rs.getInt("USER_ID"));
		getNotificationDetails.setMessageTitle(rs.getString("MESSAGE_TITLE"));
		getNotificationDetails.setMessageBody(rs.getString("MESSAGE_BODY"));
		getNotificationDetails.setReadStatus(rs.getInt("READ_STATUS")==1);
		Timestamp messageTime = rs.getTimestamp("SENT_MESSAGE_TIME");
		getNotificationDetails.setMessageTime(messageTime.toLocalDateTime());
		return getNotificationDetails;
	}
}
