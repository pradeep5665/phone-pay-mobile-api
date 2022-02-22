/* 
 * ===========================================================================
 * File Name ValidateLoanNumberService.java
 * 
 * Created on Aug 13, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateLoanNumberService.java,v $
 * ===========================================================================
 */
 package org.uhc.service;

import org.uhc.controller.envelop.request.ValidateRoutingNumberReq;
import org.uhc.controller.envelop.response.ValidateRoutingNumberRes;

public interface ValidateRoutingNumberService {

	ValidateRoutingNumberRes validateRoutingNumber(ValidateRoutingNumberReq validateRoutingNumberReq);
}
