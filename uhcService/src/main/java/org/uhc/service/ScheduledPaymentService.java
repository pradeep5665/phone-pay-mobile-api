/* 
 * ===========================================================================
 * File Name ScheduledPaymentService.java
 * 
 * Created on Jun 26, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: ScheduledPaymentService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.LoanPaymentRequest;
import org.uhc.controller.envelop.response.ScheduledPaymentResponse;

/**
 * @author nehas3
 * @date Jun 26, 2018
 */
public interface ScheduledPaymentService {

	/**
	 * @author nehas3
	 * @date Jun 26, 2018
	 * @return ScheduledPaymentResponse 
	 * @param schedulePaymentReq
	 * @Description : this is schedulePayment method that returns ScheduledPaymentResponse on basis of requested parameters.
	 */
	ScheduledPaymentResponse schedulePayment(LoanPaymentRequest schedulePaymentReq);
	 
}
