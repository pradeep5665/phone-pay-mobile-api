/* 
 * ===========================================================================
 * File Name ScheduledPaymentHistoryService.java
 * 
 * Created on Jul 13, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: ScheduledPaymentHistoryService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.ScheduledPaymentHistoryByUserIdAndLoanNumberReq;
import org.uhc.controller.envelop.request.ScheduledPaymentHistoryReq;
import org.uhc.controller.envelop.response.ScheduledPaymentHistoryRes;

/**
 * @author nehas3
 * @date Jul 13, 2018
 */
public interface ScheduledPaymentHistoryService {

	/**
	 * @author nehas3
	 * @date Jul 13, 2018
	 * @return ScheduledPaymentHistoryRes 
	 * @param scheduledPaymentHistoryReq
	 * @Description : this is getScheduledPaymentHistory method that returns the scheduled payment history of user on basis of requested parameters.
	 */
	ScheduledPaymentHistoryRes getScheduledPaymentHistoryByLoanNumber(ScheduledPaymentHistoryByUserIdAndLoanNumberReq scheduledPaymentHistoryReq);
	ScheduledPaymentHistoryRes getScheduledPaymentHistory(ScheduledPaymentHistoryReq scheduledPaymentHistoryReq);
}
