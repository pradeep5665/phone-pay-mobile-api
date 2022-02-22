/* 
 * ===========================================================================
 * File Name PaymentBatchMapper.java
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
 * $Log: PaymentBatchMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.PaymentBatchDto;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author nehas3
 * @date Jul 13, 2018
 * @Description : This is The PaymentBatchMapper class that is mapping the concerned table values to PaymentBatchDto class.
 */
public class PaymentBatchMapper implements RowMapper<PaymentBatchDto> {
    @Override
    public PaymentBatchDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        String status = rs.getString("status");
        return new PaymentBatchDto(rs.getLong("id"),
                                   rs.getTimestamp("batch_time").toLocalDateTime(),
                                   status == null ? null : PaymentBatchDto.Status.valueOf(status));
    }
}
