/* 
 * ===========================================================================
 * File Name ScheduledPaymentItemDto.java
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
 * $Log: ScheduledPaymentItemDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;
import org.uhc.util.Constants.PaymentItemType;

/**
 * @author nehas3
 * @date Jul 13, 2018
 */
public class ScheduledPaymentItemDto {
    private int scheduledPaymentId;
    private BigDecimal amount;
    private PaymentItemType type;
    private long loanNumber;

    /*********************
     *  Constructors
     *********************/

    public ScheduledPaymentItemDto(ScheduledPaymentItemDto spid){
        this.scheduledPaymentId = spid.scheduledPaymentId;
        this.amount = spid.amount;
        this.type = spid.type;
        this.loanNumber = spid.loanNumber;
    }

    public ScheduledPaymentItemDto() {
        //Needed a copy constructor in certain circumstances but the default no arg constructor is used most places
    }

    /***********************
     *  Setters and Getters
     ***********************/

    public int getScheduledPaymentId() {return scheduledPaymentId;}

    public void setScheduledPaymentId(int scheduledPaymentId) {this.scheduledPaymentId = scheduledPaymentId;}

    public BigDecimal getAmount() {return amount;}

    public void setAmount(BigDecimal amount) {this.amount = amount;}

    public PaymentItemType getType() {return type;}

    public void setType(PaymentItemType type) {this.type = type;}

    public long getLoanNumber() {return loanNumber;}

    public void setLoanNumber(long loanNumber) {this.loanNumber = loanNumber;}
}
