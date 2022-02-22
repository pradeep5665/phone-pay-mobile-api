/* 
 * ===========================================================================
 * File Name ScheduledPaymentToPaymentDtoMapper.java
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
 * $Log: ScheduledPaymentToPaymentDtoMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uhc.dao.dto.PaymentDto;
import org.uhc.dao.dto.PaymentItemDto;
import org.uhc.dao.dto.ScheduledPaymentHistoryDto;
import org.uhc.util.Constants.PaymentItemType;
import org.uhc.util.Constants.PaymentStatus;

/**
 * @author nehas3
 * @date Jul 13, 2018
 **/
public class ScheduledPaymentToPaymentDtoMapper {

	/**
	 * This is used to convert list of scheduled payments that are processed (if
	 * there is a processed date) or cancelled (if cancelled in scheduled_payment is
	 * true) to list of paymentDto to this play all the new payment history.
	 * 
	 * @param scheduledPayments
	 * @return
	 */
	public List<PaymentDto> mapListScheduledPaymentsToListPaymentDto(
			List<ScheduledPaymentHistoryDto> scheduledPayments) {
		Map<Integer, PaymentDto> paymentDtoMap = new HashMap<>();

		for (ScheduledPaymentHistoryDto scheduledPaymentDto : scheduledPayments) {
			PaymentDto paymentDto = paymentDtoMap
					.get(scheduledPaymentDto.isCanceled() ? scheduledPaymentDto.getScheduledPaymentId() * -1
							: scheduledPaymentDto.getId());

			if (paymentDto == null) {
				paymentDto = new PaymentDto();
				if (scheduledPaymentDto.isCanceled()) {
					paymentDto.setId(scheduledPaymentDto.getScheduledPaymentId() * -1);
					paymentDto.setPaymentStatus(PaymentStatus.CANCELED);
					paymentDto.setCanceled(true);
					paymentDto.setPaymentTime(scheduledPaymentDto.getCanceledDate());
					paymentDto.setCreateDate(scheduledPaymentDto.getCreateDate());
					paymentDto.setCanceledDate(scheduledPaymentDto.getCanceledDate());
					paymentDto.setScheduledPaymentTime(
							Instant.ofEpochMilli(scheduledPaymentDto.getNextScheduledDate().getTime())
									.atZone(ZoneId.systemDefault()).toLocalDate());
					paymentDto.setConfirmationNumber(scheduledPaymentDto.getConfirmationNumber());
					paymentDto.setScheduleType(scheduledPaymentDto.getScheduledType());
					paymentDto.setPaymentSource(scheduledPaymentDto.getPaymentSource());
				} else if (scheduledPaymentDto.getProcessDate() != null) {
					paymentDto.setId(scheduledPaymentDto.getId());
					paymentDto.setScheduledPaymentId(scheduledPaymentDto.getScheduledPaymentId());
					paymentDto.setConfirmationNumber(scheduledPaymentDto.getConfirmationNumber());
					paymentDto.setPaymentStatus(PaymentStatus.PROCESSED);
					paymentDto.setCreateDate(scheduledPaymentDto.getCreateDate());
					paymentDto.setProcessedDate(scheduledPaymentDto.getBatchTime());
					paymentDto.setPaymentTime(scheduledPaymentDto.getPaymentTime());
					paymentDto.setPaymentAccountNumberLastFour(scheduledPaymentDto.getPaymentAccountNumberLastFour());
					paymentDto.setScheduledPaymentTime(
							Instant.ofEpochMilli(scheduledPaymentDto.getNextScheduledDate().getTime())
									.atZone(ZoneId.systemDefault()).toLocalDate());
					paymentDto.setPaymentAccountType(scheduledPaymentDto.getPaymentAccountType());
					paymentDto.setScheduleType(scheduledPaymentDto.getScheduledType());
					paymentDto.setPaymentSource(scheduledPaymentDto.getPaymentSource());
				}
				paymentDtoMap.put(paymentDto.getId(), paymentDto);
			}

			PaymentItemDto paymentItemDto = new PaymentItemDto();
			paymentItemDto.setType(PaymentItemType.MONTHLYPMT);
			paymentItemDto.setLoanNumber(scheduledPaymentDto.getLoanNumber());
			paymentItemDto.setAmount(scheduledPaymentDto.getMonthlyPayment());
			paymentDto.addPaymentItem(paymentItemDto);

			if (scheduledPaymentDto.getLateFees().compareTo(BigDecimal.valueOf(0)) != 0) {
				paymentItemDto = new PaymentItemDto();
				paymentItemDto.setType(PaymentItemType.LATE_FEE);
				paymentItemDto.setLoanNumber(scheduledPaymentDto.getLoanNumber());
				paymentItemDto.setAmount(scheduledPaymentDto.getLateFees());
				paymentDto.addPaymentItem(paymentItemDto);
			}

			if (scheduledPaymentDto.getNsfFees().compareTo(BigDecimal.valueOf(0)) != 0) {
				paymentItemDto = new PaymentItemDto();
				paymentItemDto.setType(PaymentItemType.NSF_FEE);
				paymentItemDto.setLoanNumber(scheduledPaymentDto.getLoanNumber());
				paymentItemDto.setAmount(scheduledPaymentDto.getNsfFees());
				paymentDto.addPaymentItem(paymentItemDto);
			}

			if (scheduledPaymentDto.getExtraPrincipal().compareTo(BigDecimal.valueOf(0)) != 0) {
				paymentItemDto = new PaymentItemDto();
				paymentItemDto.setType(PaymentItemType.EXTRA_PRINCIPAL);
				paymentItemDto.setLoanNumber(scheduledPaymentDto.getLoanNumber());
				paymentItemDto.setAmount(scheduledPaymentDto.getExtraPrincipal());
				paymentDto.addPaymentItem(paymentItemDto);
			}

			if (scheduledPaymentDto.getExtraEscrow().compareTo(BigDecimal.valueOf(0)) != 0) {
				paymentItemDto = new PaymentItemDto();
				paymentItemDto.setType(PaymentItemType.EXTRA_ESCROW);
				paymentItemDto.setLoanNumber(scheduledPaymentDto.getLoanNumber());
				paymentItemDto.setAmount(scheduledPaymentDto.getExtraEscrow());
				paymentDto.addPaymentItem(paymentItemDto);
			}
		}

		return new ArrayList<>(paymentDtoMap.values());
	}
}
