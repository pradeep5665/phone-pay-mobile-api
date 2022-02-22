/* 
 * ===========================================================================
 * File Name ValidateAccountRecoveryService.java
 * 
 * Created on Jan 24, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateAccountRecoveryService.java,v $
 * ===========================================================================
 */
 package org.uhc.service;

import org.uhc.controller.envelop.request.ValidateAccountRecoveryReq;
import org.uhc.controller.envelop.request.ValidateAccountRecoverySecureCodeReq;
import org.uhc.controller.envelop.response.ValidateAccountRecoveryBySecureCodeRes;
import org.uhc.controller.envelop.response.ValidateAccountRecoveryRes;

/**
 * 
 */
public interface ValidateAccountRecoveryService {

	/** 
	 * validateAccountRecoveryDetails have been created to check if user, who are trying
	 * to recover their account, have correct details
	 * Provide method Description here
	 * @author nehas3
	 * @since Oct 13, 2020
	 * @param validateAccountRecoveryReq
	 * @return
	 */
	 ValidateAccountRecoveryRes validateAccountRecoveryDetails(ValidateAccountRecoveryReq validateAccountRecoveryReq);
	 
	 /** 
		 * validateAccountRecoveryDetailsWithSecureCode have been created to check if user, who are trying
		 * to recover their account, have correct details
		 * Provide method Description here
		 * @author pradeepy
		 * @since May 21, 2021
		 * @param ValidateAccountRecoverySecureCodeReq
		 * @return
		 */
	 ValidateAccountRecoveryBySecureCodeRes validateAccountRecoveryDetailsWithSecureCode(ValidateAccountRecoverySecureCodeReq validateAccountRecoveryReq);
}
