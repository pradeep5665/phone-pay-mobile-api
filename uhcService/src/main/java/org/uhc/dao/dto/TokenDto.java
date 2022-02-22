/* 
 * ===========================================================================
 * File Name TokenDto.java
 * 
 * Created on Aug 13, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: TokenDto.java,v $
 * ===========================================================================
 */
package org.uhc.dao.dto;

/**
 * @author nehas3
 * @date Aug 13, 2018
 */
public class TokenDto {

	private int tokenId;
	private int userId;
	private String token;
	
	public int getTokenId() {
		return tokenId;
	}
	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
