/* 
 * ===========================================================================
 * File Name RoutingNumberDto.java
 * 
 * Created on Jun 25, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: RoutingNumberDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

/**
 * @author nehas3
 * @date jun 25, 2018
 */
public class RoutingNumberDto {

	private String routingNum;

	public String getRoutingNum() {
		return routingNum;
	}

	public void setRoutingNum(String routingNum) {
		this.routingNum = routingNum;
	}

	@Override
	public String toString() {
		return "RoutingNumberDto{" +
				"routingNum='" + routingNum + '\'' +
				'}';
	}
}
