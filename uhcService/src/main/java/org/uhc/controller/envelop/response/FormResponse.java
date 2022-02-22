/* 
 * ===========================================================================
 * File Name FormResponse.java
 * 
 * Created on Aug 9, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: FormResponse.java,v $
 * ===========================================================================
 */
package org.uhc.controller.envelop.response;

import java.util.List;

import org.uhc.bean.FormBean;

/**
 * @author nehas3
 * @date Aug 9, 2018
 * @description creating Form Service Response for success or failure
 */
public class FormResponse {

	private List<FormBean> formInfo;

	public List<FormBean> getFormInfo() {
		return formInfo;
	}

	public void setFormInfo(List<FormBean> formInfo) {
		this.formInfo = formInfo;
	}
}
