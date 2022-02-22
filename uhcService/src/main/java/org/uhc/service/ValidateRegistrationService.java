/* 
 * ===========================================================================
 * File Name ValidateRegistrationService.java
 * 
 * Created on Jun 12, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateRegistrationService.java,v $
 * ===========================================================================
 */
 package org.uhc.service;

import org.uhc.controller.envelop.request.ValidateRegistrationReq;
import org.uhc.controller.envelop.request.ValidateRegistrationWithSecureCodeReq;
import org.uhc.controller.envelop.response.ValidateRegistrationRes;

public interface ValidateRegistrationService {

	 ValidateRegistrationRes validateRegisteringUser(ValidateRegistrationReq validateRegistraitionReq);
	 
	 ValidateRegistrationRes validateRegisteringUserWithSecureCode(ValidateRegistrationWithSecureCodeReq validateRegistrationWithSecureCodeReq);

}
