/* 
 * ===========================================================================
 * File Name ValidateAccountRecoveryKeyService.java
 * 
 * Created on Jan 27, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateAccountRecoveryKeyService.java,v $
 * ===========================================================================
 */
 package org.uhc.service;

import org.uhc.controller.envelop.request.ValidateAccountRecoveryKeyReq;
import org.uhc.controller.envelop.response.ValidateAccountRecoveryKeyRes;

/**
 * @author nehas3
 * Oct 13, 2020
 */
public interface ValidateAccountRecoveryKeyService {

	/**
	 * this will help to get account recovery data on basis of account recovery key
	 * @author nehas3
	 * @date JAN 27, 2020
	 * @return it returns the response of validate account recovery API.
	 * @param acountRecKeyReq it contains the recovery key
	 * @return account recovery data from database
	 */
	ValidateAccountRecoveryKeyRes validateAccountRecoveryKey(ValidateAccountRecoveryKeyReq acountRecKeyReq);
}
