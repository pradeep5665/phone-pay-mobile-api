/* 
 * ===========================================================================
 * File Name PaymentBatchDto.java
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
 * $Log: GetScheduledPaymentDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

import java.time.LocalDateTime;

/**
 * @author nehas3
 * @date Jul 13, 2018
 */
public class PaymentBatchDto{
    private long id;
    private LocalDateTime batchTime;
    private Status status;

    public PaymentBatchDto(long id, LocalDateTime batchTime, Status status) {
        this.id = id;
        this.batchTime = batchTime;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getBatchTime() {
        return batchTime;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        RUNNING,
        FAILED,
        SUCCEEDED
    }
}
