/* 
 * ===========================================================================
 * File Name TransactionMapper.java
 * 
 * Created on May 25, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: TransactionMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.TransactionDto;

/**
 * @author nehas3
 * @date May 25, 2018
 * Description : This is The TransactionMapper class that is mapping the concerned table values to TransactionDto class.
 */
public class TransactionMapper implements RowMapper<TransactionDto> {

	@Override
	public TransactionDto mapRow(ResultSet rs, int i) throws SQLException {

		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setLoan(rs.getLong("loan"));
		transactionDto.setPosted(rs.getDate("posted"));
		transactionDto.setNextDue(rs.getDate("nextDue"));
		transactionDto.setDesc(rs.getString("desc"));
		transactionDto.setTotalAmount(rs.getBigDecimal("totalAmount"));
		transactionDto.setPrincipal(rs.getBigDecimal("principal"));
		transactionDto.setInterest(rs.getBigDecimal("interest"));
		transactionDto.setEscrow(rs.getBigDecimal("escrow"));
		transactionDto.toString();
		return transactionDto;

	}

}
