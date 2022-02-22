/* 
 * ===========================================================================
 * File Name GetScheduledPaymentForCancellationService.java
 * 
 * Created on Jul 9, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetScheduledPaymentForCancellationService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.GetScheduledPaymentForCancellationReq;
import org.uhc.controller.envelop.response.GetScheduledPaymentForCancellationRes;

/**
 * @author nehas3
 * @date Jul 9, 2018
 */
public interface GetScheduledPaymentForCancellationService {

	/**
	 * @author nehas3
	 * @date Jul 9, 2018 
	 * @return GetScheduledPaymentForCancellationRes 
	 * @param scheduledPaymentRequest
	 * @Description : this is getScheduledPayments method that returns scheduled payments info of user for cancellation on basis of requested parameters.
	 */
	GetScheduledPaymentForCancellationRes getScheduledPayments(GetScheduledPaymentForCancellationReq scheduledPaymentRequest);

}
