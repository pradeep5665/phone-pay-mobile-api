/* 
 * ===========================================================================
 * File Name CancelPaymentRequest.java
 * 
 * Created on Jul 11, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: CancelPaymentRequest.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.request;

/**
 * @author nehas3
 * @date Jul 11, 2018
 * @Description : This is CancelPaymentRequest request bean to cancel the
 *              scheduled payment on the basis of userId.
 */
public class CancelPaymentRequest {

	private int userId;
	private String[] paymentIdOfCheckBox;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String[] getPaymentIdOfCheckBox() {
		return paymentIdOfCheckBox;
	}

	public void setPaymentIdOfCheckBox(String[] paymentIdOfCheckBox) {
		this.paymentIdOfCheckBox = paymentIdOfCheckBox;
	}

}
