/* 
 * ===========================================================================
 * File Name PropertyDto.java
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
 * $Log: PropertyDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

/**
 * @author nehas3
 * @date May 25, 2018
 */
public class PropertyDto {
	private String address;
	private String state;
	private String city;
	private String zipCode;

	public String getAddress() {
		return address.trim();
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state.trim();
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city.trim();
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode.trim();
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode.substring(0, 5);
	}

	@Override
	public String toString() {
		return "PropertyDto{" +
				"address='" + address + '\'' +
				", state='" + state + '\'' +
				", city='" + city + '\'' +
				", zipCode='" + zipCode + '\'' +
				'}';
	}
}
