/* 
 * ===========================================================================
 * File Name UpdatePushNotificationFlagService.java
 * 
 * Created on Oct 22, 2020
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2020
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdatePushNotificationFlagService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.UpdatePushNotificationFlagRequest;
import org.uhc.controller.envelop.response.UpdatePushNotificationFlagResponse;

public interface UpdatePushNotificationFlagService {

	/**
	 * this is getAccountHistoryByLoanNumber method that returns account history of
	 * user on basis of requested parameter
	 * 
	 * @author nehas3 - Chetu
	 * @version 1.2 - Oct 22, 2020
	 * @return AccountHistoryResponse
	 * @param accountHistoryRequest
	 * @return
	 */
	UpdatePushNotificationFlagResponse updatePushNotificationFlag(
			UpdatePushNotificationFlagRequest updatePushNotificationFlagRequest);
}
