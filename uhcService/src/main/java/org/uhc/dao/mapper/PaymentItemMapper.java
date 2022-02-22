/* 
 * ===========================================================================
 * File Name PaymentItemMapper.java
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
 * $Log: PaymentItemMapper.java,v $
 * ===========================================================================
 */
 package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.PaymentItemDto;
import org.uhc.util.Constants.PaymentItemType;

public class PaymentItemMapper implements RowMapper<PaymentItemDto>{

	@Override
	public PaymentItemDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		PaymentItemDto paymentItem = new PaymentItemDto();
		paymentItem.setPaymentId(rs.getInt("PAYMENT_ID"));
		paymentItem.setLoanNumber(rs.getLong("LOAN_NUMBER"));
		paymentItem.setAmount(rs.getBigDecimal("AMOUNT"));
		paymentItem.setType(PaymentItemType.valueOf(rs.getString("ITEM_TYPE")));
		return paymentItem;
	}
}
