/* 
 * ===========================================================================
 * File Name LoginService.java
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
 * $Log: LoginService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.LoginRequest;
import org.uhc.controller.envelop.response.LoginResponse;
import org.uhc.exception.LockedUserLoginException;

/**
 * @author nehas3
 * @date May 25, 2018
 */

public interface LoginService {

	/**
	 * 
	 * @author nehas3
	 * @date May 25, 2018
	 * @return LoginResponse 
	 * @param loginRequest
	 * @return success or failure on login for user.
	 * Description : this is login method to validate the user and authenticate.
	 */
	LoginResponse login(LoginRequest loginRequest) throws LockedUserLoginException;

}
