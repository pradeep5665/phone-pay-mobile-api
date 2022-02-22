/* 
 * ===========================================================================
 * File Name RecoverAccountService.java
 * 
 * Created on Jan 28, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: RecoverAccountService.java,v $
 * ===========================================================================
 */
 package org.uhc.service;

import org.uhc.controller.envelop.request.RecoverAccountReq;
import org.uhc.controller.envelop.request.RecoveryAccountReq;
import org.uhc.controller.envelop.response.RecoverAccountRes;

public interface RecoverAccountService {
	 
	 RecoverAccountRes recoverAccountByKey(RecoverAccountReq recoverAccountReq);
	 
	 RecoverAccountRes recoverAccountByUserId(RecoveryAccountReq recoverAccountReq);

}
