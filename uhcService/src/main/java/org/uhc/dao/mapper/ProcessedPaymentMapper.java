/* 
 * ===========================================================================
 * File Name ProcessedPaymentMapper.java
 * 
 * Created on Apr 10, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: ProcessedPaymentMapper.java,v $
 * ===========================================================================
 */
 package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.ProcessedPaymentDto;

public class ProcessedPaymentMapper implements RowMapper<ProcessedPaymentDto>{

	@Override
	public ProcessedPaymentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ProcessedPaymentDto processedPaymentDto = new ProcessedPaymentDto();
		processedPaymentDto.setUserId(rs.getInt("ID"));
		processedPaymentDto.setPaymentId(rs.getInt("PAYMENT_ID"));
		processedPaymentDto.setUserId(rs.getInt("USER_ID"));
		processedPaymentDto.setScheduledType(rs.getString("SCHEDULED_TYPE"));
		processedPaymentDto.setLoanNumber(rs.getLong("LOAN_NUMBER"));
		
		if ("0".equals(rs.getString("CANCELED"))) {
			processedPaymentDto.setCanceled(false);
		} else {
			processedPaymentDto.setCanceled(true);
		}
		Date processedDate = rs.getDate("PROCESSED_DATE");
		processedPaymentDto.setProcessedDate(processedDate != null ? new Date(processedDate.getTime()) : null);
		return processedPaymentDto;
		
	}
	
}
