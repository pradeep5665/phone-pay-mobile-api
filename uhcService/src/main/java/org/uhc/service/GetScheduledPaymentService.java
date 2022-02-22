/* 
 * ===========================================================================
 * File Name GetScheduledPaymentService.java
 * 
 * Created on Jul 2, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetScheduledPaymentService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.GetScheduledPaymentRequest;
import org.uhc.controller.envelop.response.GetScheduledPaymentResponse;

/**
 * @author nehas3
 * @date Jul 2, 2018
 */
public interface GetScheduledPaymentService {

	/**
	 * @author nehas3
	 * @date Jul 2, 2018
	 * @return GetScheduledPaymentResponse
	 * @param scheduledPaymentRequest
	 * @Description this is getScheduledPayments method that returns scheduled payments info of user on basis of requested parameters.
	 */
	GetScheduledPaymentResponse getScheduledPayments (GetScheduledPaymentRequest scheduledPaymentRequest);
}
