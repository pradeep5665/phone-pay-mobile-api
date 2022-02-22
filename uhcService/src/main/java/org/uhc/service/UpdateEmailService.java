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

import org.uhc.controller.envelop.request.UpdateEmailRequest;
import org.uhc.controller.envelop.response.UpdateEmailResponse;

/**
 * @author nehas3
 * @date May 28, 2018
 */
public interface UpdateEmailService {

	/**
	 * 
	 * @author nehas3
	 * @date May 30, 2018
	 * @return UpdateEmailResponse 
	 * @param updateEmailRequest
	 * @return success or failure on update email for user
	 * Description : this is updateEmail method that returns success or failure on update email for user.
	 */
	UpdateEmailResponse updateEmail(UpdateEmailRequest updateEmailRequest);
}
