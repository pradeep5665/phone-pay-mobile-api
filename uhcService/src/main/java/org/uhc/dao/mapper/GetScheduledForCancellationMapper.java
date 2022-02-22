/* 
 * ===========================================================================
 * File Name GetScheduledForCancellationMapper.java
 * 
 * Created on Jul 10, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetScheduledForCancellationMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.GetScheduledPaymentForCancellationDto;

/**
 * @author nehas3
 * @date Jul 10, 2018
 * @Description : This is The GetScheduledForCancellationMapper class that is mapping the concerned table values to GetScheduledPaymentForCancellationDto class.
 */
public class GetScheduledForCancellationMapper implements RowMapper<GetScheduledPaymentForCancellationDto>{

	@Override
	public GetScheduledPaymentForCancellationDto mapRow(ResultSet rs, int rowNum) throws SQLException{
		
	GetScheduledPaymentForCancellationDto getScheduledPayment = new GetScheduledPaymentForCancellationDto();
	
	getScheduledPayment.setPaymentId(rs.getInt("ID"));
	getScheduledPayment.setUserId(rs.getInt("USER_ID"));
	getScheduledPayment.setScheduledType(rs.getString("SCHEDULED_TYPE"));
	
	Date processedDate = rs.getDate("PROCESSED_DATE");
	getScheduledPayment.setProcessedDate(processedDate != null ? new Date(processedDate.getTime()) : null);
	
	Date scheduledDate = rs.getDate("NEXT_SCHEDULED_DATE");
	getScheduledPayment.setScheduledDate(new Date(scheduledDate.getTime()));
	
	BigDecimal lateFee = rs.getBigDecimal("LATE_FEE");
	BigDecimal nsfFee = rs.getBigDecimal("NSF_FEE");
	BigDecimal extraPrincipal = rs.getBigDecimal("EXTRA_PRINCIPAL");
	BigDecimal extraEscrow = rs.getBigDecimal("EXTRA_ESCROW");
	
	getScheduledPayment.setLateFeesPaid(lateFee);
	getScheduledPayment.setNsfFeesPaid(nsfFee);
	getScheduledPayment.setExtraPrincipal(extraPrincipal);
	getScheduledPayment.setExtraEscrow(extraEscrow);
	
	getScheduledPayment.setPaymentSource(rs.getString("PAYMENT_SOURCE"));
	return getScheduledPayment;
	}
}
