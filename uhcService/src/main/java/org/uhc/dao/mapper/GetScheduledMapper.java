/* 
 * ===========================================================================
 * File Name GetScheduledMapper.java
 * 
 * Created on Jul 2, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetScheduledMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.GetScheduledPaymentDto;

/**
 * @author nehas3
 * @date Jul 2, 2018
 * @Description : This is The GetScheduledMapper class that is mapping the concerned table values to GetScheduledPaymentDto class.
 */
public class GetScheduledMapper implements RowMapper<GetScheduledPaymentDto>{

	@Override
	public GetScheduledPaymentDto mapRow(ResultSet rs, int rowNum) throws SQLException{
		
	GetScheduledPaymentDto getScheduledPayment = new GetScheduledPaymentDto();
	getScheduledPayment.setUserId(rs.getInt("USER_ID"));
	getScheduledPayment.setLoanNumber(rs.getLong("LOAN_NUMBER"));
	getScheduledPayment.setScheduledType(rs.getString("SCHEDULED_TYPE"));
	
	Date processedDate = rs.getDate("PROCESSED_DATE");
	getScheduledPayment.setProcessedDate(processedDate != null ? new Date(processedDate.getTime()) : null);
	
	Date scheduledDate = rs.getDate("NEXT_SCHEDULED_DATE");
	getScheduledPayment.setScheduledDate(scheduledDate != null ? new Date(scheduledDate.getTime()) : null);
	getScheduledPayment.setPaymentSource(rs.getString("PAYMENT_SOURCE"));
	
	return getScheduledPayment;
	}
}
