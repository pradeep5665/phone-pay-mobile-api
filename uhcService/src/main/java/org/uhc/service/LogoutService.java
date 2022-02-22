/* 
 * ===========================================================================
 * File Name LogoutService.java
 * 
 * Created on Aug 24, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: LogoutService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.LogoutRequest;
import org.uhc.controller.envelop.response.LogoutResponse;

/**
 * @author nehas3
 * @date Aug 24, 2018
 */
public interface LogoutService {

	/**
	 * @author nehas3
	 * @date Aug 24, 2018
	 * @return LogoutResponse 
	 * @param logoutRequest
	 * @Description : this is logout to log out from the application.
	 */
	LogoutResponse logout(LogoutRequest logoutRequest);
}
