/* 
 * ===========================================================================
 * File Name ScheduledPaymentRsToPaymentDtoMapper.java
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
 * $Log: ScheduledPaymentRsToPaymentDtoMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.PaymentDto;
import org.uhc.dao.dto.PaymentItemDto;
import org.uhc.util.Constants.PaymentItemType;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author nehas3
 * @date Jul 13, 2018
 * @Description : This is The ScheduledPaymentRsToPaymentDtoMapper class that is mapping the concerned table values to PaymentDto class.
 */
public class ScheduledPaymentRsToPaymentDtoMapper implements RowMapper<PaymentDto> {

    private static final Logger LOG = LogManager.getLogger(ScheduledPaymentRsToPaymentDtoMapper.class.getName());

    /**
     * overriding map row method to set the result
     */
    @Override
    public PaymentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        LOG.info("ScheduledPaymentRsToPaymentDtoMapper; Converting Scheduled Payment data into PaymentDto.");
        PaymentDto payment = new PaymentDto();
        payment.setUserId(rs.getInt("USER_ID"));
        payment.setPaymentTime(rs.getTimestamp("NEXT_SCHEDULED_DATE").toLocalDateTime());
        payment.setCanceled(rs.getInt("CANCELLED")==1);
        return addPaymentItems(rs, payment);
    }

    /**
     * @author nehas3
     * @date Jul 13, 2018
     * @return PaymentDto 
     * @param rs
     * @param payment
     * @return
     * @throws SQLException
     * @Description : setting payment details to payment dto
     */
    private PaymentDto addPaymentItems(ResultSet rs, PaymentDto payment) throws SQLException {
        long loanNumber = rs.getLong("LOAN_NUMBER");
        BigDecimal monthlyPmt = rs.getBigDecimal("MONTHLY_PAYMENT");
        BigDecimal lateFees = rs.getBigDecimal("LATE_FEE");
        BigDecimal nsfFees = rs.getBigDecimal("NSF_FEE");
        BigDecimal extraPrincipals = rs.getBigDecimal("EXTRA_PRINCIPAL");
        BigDecimal extraEscrows = rs.getBigDecimal("EXTRA_ESCROW");

        PaymentItemDto monthlyPayment = new PaymentItemDto();
        monthlyPayment.setAmount(monthlyPmt);
        monthlyPayment.setLoanNumber(loanNumber);
        monthlyPayment.setType(PaymentItemType.MONTHLYPMT);
        payment.addPaymentItem(monthlyPayment);


        if(lateFees.compareTo(BigDecimal.ZERO) > 0){
            LOG.info("Adding late fee while converting from scheduled payment to payment");
            PaymentItemDto lateFee = new PaymentItemDto();
            lateFee.setAmount(lateFees);
            lateFee.setLoanNumber(loanNumber);
            lateFee.setType(PaymentItemType.LATE_FEE);
            payment.addPaymentItem(lateFee);
        }

        if(nsfFees.compareTo(BigDecimal.ZERO) > 0){
            LOG.info("Adding nsf fee while converting from scheduled payment to payment");
            PaymentItemDto nsfFee = new PaymentItemDto();
            nsfFee.setAmount(nsfFees);
            nsfFee.setLoanNumber(loanNumber);
            nsfFee.setType(PaymentItemType.NSF_FEE);
            payment.addPaymentItem(nsfFee);
        }

        if(extraPrincipals.compareTo(BigDecimal.ZERO) > 0){
            LOG.info("Adding extra principal while converting from scheduled payment to payment");
            PaymentItemDto extraPrincipal = new PaymentItemDto();
            extraPrincipal.setAmount(extraPrincipals);
            extraPrincipal.setLoanNumber(loanNumber);
            extraPrincipal.setType(PaymentItemType.EXTRA_PRINCIPAL);
            payment.addPaymentItem(extraPrincipal);
        }

        if(extraEscrows.compareTo(BigDecimal.ZERO) > 0){
            LOG.info("Adding extra escrow while converting from scheduled payment to payment");
            PaymentItemDto extraEscrow = new PaymentItemDto();
            extraEscrow.setAmount(extraEscrows);
            extraEscrow.setLoanNumber(loanNumber);
            extraEscrow.setType(PaymentItemType.EXTRA_ESCROW);
            payment.addPaymentItem(extraEscrow);
        }
        return payment;
    }
}
