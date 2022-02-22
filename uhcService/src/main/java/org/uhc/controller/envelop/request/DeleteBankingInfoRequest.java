/* 
 * ===========================================================================
 * File Name DeleteBankingInfoRequest.java
 * 
 * Created on Jun 7, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: DeleteBankingInfoRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date June 7, 2018
 * Description : This is DeleteBankingInfoRequest request bean to delete Current Banking Info of user on basis of userId.
 */
public class DeleteBankingInfoRequest {

	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
