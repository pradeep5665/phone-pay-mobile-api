/* 
 * ===========================================================================
 * File Name DeleteNotificatiService.java
 * 
 * Created on Aug 24, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: DeleteNotificatiService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.DeleteNotificationRequest;
import org.uhc.controller.envelop.response.DeleteNotificationResponse;

/**
 * @author nehas3
 * @date Aug 24, 2018
 */
public interface DeleteNotificationService {

	/**
	 * @author nehas3
	 * @date Aug 24, 2018
	 * @return DeleteNotificationResponse 
	 * @param deleteNotificationRequest
	 * @Description : this is deleteNotification method that returns true or false when the notification is deleted by user.
	 */
	DeleteNotificationResponse deleteNotification(DeleteNotificationRequest deleteNotificationRequest);
}
