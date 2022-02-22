/* 
 * ===========================================================================
 * File Name PaymentExtractor.java
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
 * $Log: PaymentExtractor.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.uhc.dao.dto.PaymentDto;

/**
 * @author nehas3
 * @date Jul 13, 2018
 * @Description : This is The PaymentExtractor class that is mapping the
 *              concerned table values to PaymentExtractor class.
 */
public class PaymentExtractor extends AbstractPaymentExtractor<PaymentDto> {

	@Override
	protected PaymentDto getDtoInstance() {
		return new PaymentDto();
	}

	@Override
	protected void populateRoutingAndAccount(ResultSet rs, PaymentDto payment) throws SQLException {
		String routing = rs.getString("routing_number").trim();
		if (routing != null) {
			payment.setRoutingNumberLastFour(routing.substring(routing.length() - 4));
		} else {
			payment.setRoutingNumberLastFour("N/A");
		}
		String account = rs.getString("payment_account_number").trim();
		if (account != null) {
			payment.setPaymentAccountNumberLastFour(
					account.length() <= 4 ? account : account.substring(account.length() - 4));
		} else {
			payment.setPaymentAccountNumberLastFour("N/A");
		}
	}
}
