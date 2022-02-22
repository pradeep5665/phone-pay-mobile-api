/* 
 * ===========================================================================
 * File Name FormBean.java
 * 
 * Created on Aug 10, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: FormBean.java,v $
 * ===========================================================================
 */
package org.uhc.bean;

/**
 *  @author nehas3
 *  @date Aug 10, 2018
 *  Description : This is FormBean bean to get forms Info required for users.
 */
public class FormBean {
	private String formName;
	private StringBuilder formUrl;
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public StringBuilder getFormUrl() {
		return formUrl;
	}
	public void setFormUrl(StringBuilder formUrl) {
		this.formUrl = formUrl;
	}

}
