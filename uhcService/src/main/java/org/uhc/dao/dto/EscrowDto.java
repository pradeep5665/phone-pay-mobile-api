/* 
 * ===========================================================================
 * File Name EscrowDto.java
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
 * $Log: EscrowDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author nehas3
 * @date May 25, 2018
 */
public class EscrowDto {

	private String type;
	private String vendor;
	private String vendorID;
	private String date;
	private String amount;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVendor() {
		return vendor.trim();
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getVendorID() {
		return vendorID.trim();
	}

	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.amount = df.format(amount);
	}

	@Override
	public String toString() {
		return "EscrowDto{" +
				"type='" + type + '\'' +
				", vendor='" + vendor + '\'' +
				", vendorID='" + vendorID + '\'' +
				", date='" + date + '\'' +
				", amount='" + amount + '\'' +
				'}';
	}
}
