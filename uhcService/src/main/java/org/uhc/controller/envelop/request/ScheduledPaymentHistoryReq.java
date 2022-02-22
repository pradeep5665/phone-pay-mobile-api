/* 
 * ===========================================================================
 * File Name ScheduledPaymentHistoryReq.java
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
 * $Log: ScheduledPaymentHistoryReq.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date Jul 13, 2018
 * @Description : This is ScheduledPaymentHistoryReq request bean to get history
 *              of scheduled payment on the basis of userId.
 */
public class ScheduledPaymentHistoryReq {

	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
