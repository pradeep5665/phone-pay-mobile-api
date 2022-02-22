/* 
 * ===========================================================================
 * File Name RegistrationService.java
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
 * $Log: RegistrationService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.RegistrationRequest;
import org.uhc.controller.envelop.response.RegistrationResponse;

/**
 * @author nehas3
 * @date May 25, 2018
 */
public interface RegistrationService {

	/**
	 * 
	 * @author nehas3
	 * @date May 25, 2018
	 * @return RegistrationResponse 
	 * @param regRequest
	 * @return success or failure for registering a valid user.
	 * Description : this is registerUser method that returns success or failure for registering a valid user.
	 */
	RegistrationResponse registerUser(RegistrationRequest regRequest);
	
	/**
	 * 
	 * @author pradeepy
	 * @date May 21, 2021
	 * @return RegistrationResponse 
	 * @param regRequest
	 * @return success or failure for registering a valid user.
	 * Description : this is registerUser method that returns success or failure for registering a valid user.
	 */
	RegistrationResponse registerUserWithSecureCode(RegistrationRequest regRequest);
}
