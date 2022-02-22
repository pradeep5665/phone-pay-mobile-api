/*
 * ===========================================================================
 * File Name FormModel.java
 * 
 * Created on Aug 16, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: FormModel.java,v $
 * ===========================================================================
 */
package org.uhc.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author nehas3
 * @date Aug 16, 2018
 * @Description : This is FormModel class to load all the forms from .properties file once and use it at required places
 */
@Component
@Scope("singleton")
@PropertySource("classpath:forms.properties")
public class FormModel {

	@Value("#{'${forms.orignal.name}'.split(',')}")
	private List<String> formsOrignalNameList;

	@Value("#{'${forms.display.name}'.split(',')}")
	private List<String> formsDisplayNameList;

	private Map<String, String> formNameMap;

	public FormModel() {
		// default constructor
	}

	/**
	 * 
	 * @author nehas3
	 * @date Sep 5, 2018
	 * @return Map<String,String>
	 * @Description : Loading all the forms in a map
	 */
	@PostConstruct
	public Map<String, String> init() {
		formNameMap = new HashMap<>();
		// creating list by reading form.properties.
		for (int index = 0; index <= formsOrignalNameList.size() - 1; index++) {
			formNameMap.put(formsOrignalNameList.get(index), formsDisplayNameList.get(index));
		}
		return formNameMap;
	}

	public Map<String, String> getFormNameMap() {
		return formNameMap;
	}

	public void setFormNameMap(Map<String, String> formNameMap) {
		this.formNameMap = formNameMap;
	}
	
	public List<String> getFormsOrignalNameList() {
		return formsOrignalNameList;
	}
	
	public List<String> getFormsDisplayNameList() {
		return formsDisplayNameList;
	}
}
