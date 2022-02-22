/* 
 * ===========================================================================
 * File Name BankingInfoMapper.java
 * 
 * Created on Jun 5, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: BankingInfoMapper.java,v $
 * ===========================================================================
 */
package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.BankingInfoDto;
import org.uhc.util.Constants;


/**
  * @author nehas3
  * @date May 25, 2018
  * Description : This is The BankingInfoMapper class that is mapping the concerned table values to BankingInfoDto class.
  */
public class BankingInfoMapper implements RowMapper<BankingInfoDto>{

	@Override
	public  BankingInfoDto mapRow(ResultSet rs, int i) throws SQLException {
		
		BankingInfoDto bankingInfoDto = new BankingInfoDto();
		bankingInfoDto.setId(rs.getInt("id"));
		bankingInfoDto.setBankAccountNumber(rs.getString("payment_account_number"));
		bankingInfoDto.setRoutingNumber(rs.getString("routing_number"));
		bankingInfoDto.setNameOnBankAccount(rs.getString("name_on_payment_account"));
		bankingInfoDto.setUserId(rs.getInt("user_id"));
		bankingInfoDto.setBankAccountType(Constants.AccountType.constructAccountType(rs.getString("payment_account_type").toUpperCase().charAt(0)));
		
		return bankingInfoDto;
		
	}
}
