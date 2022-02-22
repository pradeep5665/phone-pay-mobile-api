/* 
 * ===========================================================================
 * File Name FormService.java
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
 * $Log: FormService.java,v $
 * ===========================================================================
 */
 package org.uhc.service;

import org.uhc.controller.envelop.response.FormResponse;

/**
 * @author nehas3
 * @date Aug 9, 2018
 */
public interface FormService {

	/**
	 * @author nehas3
	 * @date Aug 28, 2018
	 * @return FormResponse 
	 * @Description : this is getAllForms method that returns List of static form required.
	 */
	 FormResponse getAllForms();
}
