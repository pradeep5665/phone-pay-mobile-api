/* 
 * ===========================================================================
 * File Name ScheduledPaymentMapper.java
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
 * $Log: ScheduledPaymentMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.ScheduledPaymentDto;

/**
 * @author nehas3
 * @date Jul 10, 2018
 * @Description : This is The ScheduledPaymentMapper class that is mapping the concerned table values to ScheduledPaymentDto class.
 */
public class ScheduledPaymentMapper implements RowMapper<ScheduledPaymentDto> {

	private static final Logger LOG = LogManager.getLogger(ScheduledPaymentMapper.class.getName());

	@Override
	public ScheduledPaymentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		LOG.debug("scheduled payment row mapper.");

		ScheduledPaymentDto scheduledPayment = new ScheduledPaymentDto();
		scheduledPayment.setUserDefinedDate(new Date(rs.getDate("USER_DEFINED_DATE").getTime()));
		scheduledPayment.setScheduledDate(new Date(rs.getDate("NEXT_SCHEDULED_DATE").getTime()));
		scheduledPayment.setScheduledType(rs.getString("SCHEDULED_TYPE"));
		scheduledPayment.setUserId(rs.getInt("USER_ID"));
		scheduledPayment.setLoanNumber(rs.getLong("LOAN_NUMBER"));
		Timestamp cancelDate = rs.getTimestamp("CANCELED_DATE");
		Timestamp createdDate = rs.getTimestamp("CREATE_DATE");
		scheduledPayment.setCreatedDate(createdDate != null ? createdDate.toLocalDateTime() : null);
		scheduledPayment.setCanceledDate(cancelDate != null ? cancelDate.toLocalDateTime() : null);

		if ("0".equals(rs.getString("CANCELED"))) {
			scheduledPayment.setCanceled(false);
		} else {
			scheduledPayment.setCanceled(true);
		}

		if (rs.getInt("EMAIL_REMINDER") == 0) {
			scheduledPayment.setEmailReminder(false);
		} else {
			scheduledPayment.setEmailReminder(true);
		}

		BigDecimal lateFee = rs.getBigDecimal("LATE_FEE");
		BigDecimal nsfFee = rs.getBigDecimal("NSF_FEE");
		BigDecimal extraPrincipal = rs.getBigDecimal("EXTRA_PRINCIPAL");
		BigDecimal extraEscrow = rs.getBigDecimal("EXTRA_ESCROW");

		scheduledPayment.setLateFeesPaid(lateFee);
		scheduledPayment.setNsfFeesPaid(nsfFee);
		scheduledPayment.setExtraPrincipal(extraPrincipal);
		scheduledPayment.setExtraEscrow(extraEscrow);
		Date processedDate = rs.getDate("PROCESSED_DATE");
		scheduledPayment.setProcessedDate(processedDate != null ? new Date(processedDate.getTime()) : null);
		scheduledPayment.setPaymentId(rs.getInt("ID"));

		return scheduledPayment;
	}
}
