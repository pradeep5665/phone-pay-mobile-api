/* 
 * ===========================================================================
 * File Name AbstractPaymentExtractor.java
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
 * $Log: AbstractPaymentExtractor.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.uhc.dao.dto.PaymentBatchDto;
import org.uhc.dao.dto.PaymentDto;
import org.uhc.dao.dto.PaymentItemDto;
import org.uhc.util.ConfirmationNumberHelper;
import org.uhc.util.Constants.AccountType;
import org.uhc.util.Constants.PaymentItemType;
import org.uhc.util.Constants.PaymentStatus;

/**
 * @author nehas3
 * @date Jul 13, 2018
 */
public abstract class AbstractPaymentExtractor<PaymentDtoT extends PaymentDto>
		implements ResultSetExtractor<List<PaymentDtoT>> {
	/**
	 * This is used in two different places; for Pending Payment Lookup query to get
	 * pending payments only and Payment History query. Pending Payment Lookup query
	 * will select p.*, item.*, 0 as batch_id, '' as batch_status for every payments
	 * that are in Payment table only and not yet been batched. Payment History
	 * query will select payment.*, item.payment_id, item.loan_number, item.amount,
	 * item.item_type, pb.ID as batch_id, pb.STATUS as batch_status for every
	 * payments that has been batched.
	 */
	@Override
	public List<PaymentDtoT> extractData(ResultSet rs) throws SQLException {

		Map<Integer, PaymentDtoT> paymentIdToPaymentDtoMap = new HashMap<>();

		while (rs.next()) {

			PaymentDtoT payment = paymentIdToPaymentDtoMap.get(rs.getInt("id"));

			if (payment == null) {

				payment = getDtoInstance();

				payment.setId(rs.getInt("id"));
				payment.setUserId(rs.getInt("user_id"));
				payment.setConfirmationNumber(
						ConfirmationNumberHelper.formatConfirmationNumber(rs.getString("confirmation_number").trim()));
				Date paymentDateTime = rs.getTimestamp("payment_time");
				payment.setPaymentTime(paymentDateTime == null ? null
						: paymentDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

				Date createDateTime = rs.getTimestamp("CREATE_DATE");
				payment.setCreateDate(createDateTime == null ? null
						: createDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				payment.setPaymentBatch(rs.getInt("batch_id"));

				Date batchTime = rs.getTimestamp("BATCH_TIME");
				payment.setProcessedDate(batchTime == null ? null
						: batchTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

				payment.setCanceled(rs.getBoolean("canceled"));
				payment.setNameOnPaymentAccount(rs.getString("name_on_payment_account").trim());
				payment.setScheduledPaymentId(rs.getInt("scheduled_payment_id"));
				populateRoutingAndAccount(rs, payment);

				Date canceledDateTime = rs.getTimestamp("CANCELED_DATE");
				payment.setCanceledDate(canceledDateTime == null ? null
						: canceledDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

				Date scheduleDate = rs.getTimestamp("NEXT_SCHEDULED_DATE");

				payment.setScheduledPaymentTime(scheduleDate == null ? null
						: Instant.ofEpochMilli(scheduleDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());

				payment.setScheduleType(rs.getString("SCHEDULED_TYPE"));

				payment.setPaymentSource(rs.getString("PAYMENT_SOURCE"));

				payment.setPaymentAccountType(
						AccountType.constructAccountType(rs.getString("payment_account_type").toUpperCase().charAt(0)));

				if (payment.isCanceled()) {
					payment.setPaymentStatus(PaymentStatus.CANCELED);
				} else if (PaymentBatchDto.Status.SUCCEEDED.name().equals(rs.getString("batch_status"))) {
					payment.setPaymentStatus(PaymentStatus.PROCESSED);
				} else {
					payment.setPaymentStatus(PaymentStatus.PENDING);
				}

				paymentIdToPaymentDtoMap.put(payment.getId(), payment);
			} else if (payment.getPaymentStatus().equals(PaymentStatus.PENDING)
					&& rs.getString("batch_status").equals(PaymentBatchDto.Status.SUCCEEDED.name())) {
				payment.setPaymentStatus(PaymentStatus.PROCESSED);
			}

			// build payment items
			PaymentItemDto paymentItem = new PaymentItemDto();
			paymentItem.setPaymentId(rs.getInt("payment_id"));
			paymentItem.setLoanNumber(rs.getLong("loan_number"));
			paymentItem.setAmount(rs.getBigDecimal("amount"));
			paymentItem.setType(PaymentItemType.valueOf(rs.getString("item_type")));

			if (!payment.contains(paymentItem)) {
				payment.addPaymentItem(paymentItem);
			}
		}
		return new ArrayList<PaymentDtoT>(paymentIdToPaymentDtoMap.values());
	}

	protected abstract PaymentDtoT getDtoInstance();

	protected abstract void populateRoutingAndAccount(ResultSet rs, PaymentDtoT paymentDto) throws SQLException;

}
