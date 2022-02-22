/* 
 * ===========================================================================
 * File Name ScheduledPaymentHistoryMapper.java
 * 
 * Created on Jul 13, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: ScheduledPaymentHistoryMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.ScheduledPaymentHistoryDto;
import org.uhc.util.ConfirmationNumberHelper;

/**
 * @author nehas3
 * @date Jul 13, 2018
 * @Description : This is The ScheduledPaymentHistoryMapper class that is
 *              mapping the concerned table values to ScheduledPaymentHistoryDto
 *              class.
 */
public class ScheduledPaymentHistoryMapper implements RowMapper<ScheduledPaymentHistoryDto> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledPaymentHistoryMapper.class);

	@Override
	public ScheduledPaymentHistoryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		LOGGER.info("scheduled payment history row mapper.");
		ScheduledPaymentHistoryDto scheduledPaymentDto = new ScheduledPaymentHistoryDto();
		scheduledPaymentDto.setId(rs.getInt("PAYMENT_ID"));
		scheduledPaymentDto.setScheduledPaymentId(rs.getInt("SCHEDULED_PAYMENT_ID"));
		scheduledPaymentDto.setConfirmationNumber(ConfirmationNumberHelper.formatConfirmationNumber(rs.getString("CONFIRMATION_NUMBER")));
		scheduledPaymentDto.setScheduledType(rs.getString("SCHEDULED_TYPE"));
		scheduledPaymentDto.setPaymentSource(rs.getString("PAYMENT_SOURCE"));
		Date createDateTime = rs.getTimestamp("CREATE_DATE");
		scheduledPaymentDto.setCreateDate(createDateTime == null ? null
				: createDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

		Date processDateTime = rs.getTimestamp("PROCESSED_DATE");
		scheduledPaymentDto.setProcessDate(processDateTime == null ? null : processDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

		scheduledPaymentDto.setCanceled(rs.getBoolean("CANCELED"));

		Date canceledDateTime = rs.getTimestamp("CANCELED_DATE");
		scheduledPaymentDto.setCanceledDate(canceledDateTime == null ? null: canceledDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

		scheduledPaymentDto.setNextScheduledDate(rs.getDate("NEXT_SCHEDULED_DATE"));

		scheduledPaymentDto.setLoanNumber(rs.getLong("LOAN_NUMBER"));
		scheduledPaymentDto.setMonthlyPayment(rs.getBigDecimal("MONTHLY_PAYMENT"));
		scheduledPaymentDto.setLateFees(rs.getBigDecimal("LATE_FEE"));
		scheduledPaymentDto.setNsfFees(rs.getBigDecimal("NSF_FEE"));
		scheduledPaymentDto.setExtraPrincipal(rs.getBigDecimal("EXTRA_PRINCIPAL"));
		scheduledPaymentDto.setExtraEscrow(rs.getBigDecimal("EXTRA_ESCROW"));

		return scheduledPaymentDto;
	}
}
