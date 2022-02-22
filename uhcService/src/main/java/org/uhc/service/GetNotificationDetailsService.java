/* 
 * ===========================================================================
 * File Name GetNotificationDetailsService.java
 * 
 * Created on Aug 22, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: GetNotificationDetailsService.java,v $
 * ===========================================================================
 */
 package org.uhc.service;

import org.uhc.controller.envelop.request.GetNotificationDetailsRequest;
import org.uhc.controller.envelop.response.GetNotificationDetailsResponse;

/**
 * @author nehas3
 * @date Aug 22, 2018
 */
public interface GetNotificationDetailsService {

	/**
	 * @author nehas3
	 * @date Aug 28, 2018
	 * @return GetNotificationDetailsResponse 
	 * @param notificationResquest
	 * @Description : this is getNotificationDetails method that returns notification details of user on basis of requested parameters.
	 */
	 GetNotificationDetailsResponse getNotificationDetails(GetNotificationDetailsRequest notificationResquest);
}
