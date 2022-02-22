/* 
 * ===========================================================================
 * File Name UpdateEmailService.java
 * 
 * Created on May 28, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateEmailService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.UpdatePasswordRequest;
import org.uhc.controller.envelop.response.UpdatePasswordResponse;

/**
 * @author nehas3
 * @date May 29, 2018
 */
public interface UpdatePasswordService {

	/**
	 * 
	 * @author nehas3
	 * @date May 29, 2018
	 * @return UpdatePasswordResponse 
	 * @param updatePasswordRequest
	 * @return success or failure on update Password for user
	 * Description : this is updatePassword that returns success or failure on update Password for user.
	 */
	UpdatePasswordResponse updatePassword(UpdatePasswordRequest updatePasswordRequest);
}
