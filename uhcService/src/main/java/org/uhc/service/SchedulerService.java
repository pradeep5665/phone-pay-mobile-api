/* 
 * ===========================================================================
 * File Name SchedulerService.java
 * 
 * Created on Aug 7, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: SchedulerService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

/**
 * @author nehas3
 * @date Aug 6, 2018
 */
public interface SchedulerService {

	/**
	 * @author nehas3
	 * @date Aug 6, 2018
	 * @return void 
	 * @Description : This is sendPaymentConfirmation method to send payment confirmation message to user on the given time.
	 */
	void sendPaymentConfirmation();
	
	/**
	 * @author nehas3
	 * @date Aug 6, 2018
	 * @return void 
	 * @Description : This is sendPaymentReminderToUser method to send payment confirmation message to user on the given time.
	 */
	void sendPaymentReminderToUser();
	
	/**
	 * @author nehas3
	 * @date Aug 28, 2018
	 * @return void 
	 * @exception 
	 * @Description : This is deletenotificationsByScheduler method to delete FCM notifications periodically.
	 */
	void deletenotificationsByScheduler();
}
