/*
 * ===========================================================================
 * File Name FormServiceImpl.java
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
 * $Log: FormServiceImpl.java,v $
 * ===========================================================================*/
package org.uhc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.uhc.bean.FormBean;
import org.uhc.controller.envelop.response.FormResponse;
import org.uhc.service.FormService;
import org.uhc.util.FormModel;

/**
 * @author nehas3
 * @date Aug 9, 2018
 * @description Implementing FormService to get the FormResponse
 */
@Service
public class FormServiceImpl implements FormService {

	private static final Logger LOGGER = LogManager.getLogger(FormServiceImpl.class);

	@Autowired
	private FormModel formModel;

	Map<String, String> formNameMap = null;

	@Value("${server.download.FormPath}")
	private StringBuffer formPath;

	/**
	 * @author nehas3
	 * @date Aug 9, 2018
	 * @return FormResponse
	 * @Description : this is getAllForms method that returns List of static form
	 *              required.
	 */
	@Override
	public FormResponse getAllForms() {
		LOGGER.info("getAllForms(): called.");
		FormResponse formResponse = new FormResponse();
		StringBuilder tempFilePath;
		List<FormBean> formBeanList = new ArrayList<>();

		try {
			FormBean form;
			formNameMap = formModel.getFormNameMap();
			List<String> formNameList = formModel.getFormsOrignalNameList();

			if (formNameMap != null && !formNameMap.isEmpty()) {
				for (String formName : formNameList) {
					tempFilePath = new StringBuilder();
					tempFilePath.append(formPath).append(formName);
					form = new FormBean();

					form.setFormName(formNameMap.get(formName));
					form.setFormUrl(tempFilePath);
					formBeanList.add(form);
				}
			}
		} catch (Exception exp) {
			LOGGER.error("getAllForms(): Exception occurred ", exp);
		}
		formResponse.setFormInfo(formBeanList);
		LOGGER.info("getAllForms(): found {} forms", formResponse.getFormInfo().size());
		return formResponse;
	}

}
