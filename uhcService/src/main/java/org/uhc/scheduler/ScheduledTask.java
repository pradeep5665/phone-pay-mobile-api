/* 
 * ===========================================================================
 * File Name ScheduledTask.java
 * 
 * Created on Jul 23, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: ScheduledTask.java,v $
 * ===========================================================================
 */
package org.uhc.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.uhc.service.SchedulerService;

/**
 * @author nehas3
 * @date Jul 23, 2018
 * @Description : This is ScheduledTask class to run the scheduler at a particular time.
 */
@PropertySource("classpath:schedulerTimingConfiguration.properties")
@Component
public class ScheduledTask {

	private static final Logger LOGGER = LogManager.getLogger(ScheduledTask.class.getName());

	@Autowired
	SchedulerService schedulerService;

	/**
	 * @author nehas3
	 * @date Aug 20, 2018
	 * @return void 
	 * @Description : This is paymentNotificationTask method to send payment confirmation notification
	 */
	
	/*the uhc.paymentConfirmation.cronExpression contains the specific time when scheduler runs, and it is defined in 
	schedulerTimingConfiguration.properties file*/
	@Scheduled(cron = "${uhc.paymentConfirmation.cronExpression}", zone = "America/Denver")
	public void paymentConfirmationTask() {
		LOGGER.info("###### Scheduler start processing for paymentConfirmationTask....");
		schedulerService.sendPaymentConfirmation();
		LOGGER.info("###### Scheduler processing done for paymentConfirmationTask.....");
	}

	/**
	 * @author nehas3
	 * @date Jul 23, 2018
	 * @return void 
	 * @Description : This is paymentReminderTask method to send payment reminder notification
	 */
	@Scheduled(cron = "${uhc.paymentReminder.cronExpression}", zone = "America/Denver")
	public void paymentReminderTask() {
		LOGGER.info("###### Scheduler start processing for paymentReminderTask.....");
		schedulerService.sendPaymentReminderToUser();
		LOGGER.info("###### Scheduler processing done for paymentReminderTask.....");
	}
	
	/**
	 * @author nehas3
	 * @date Aug 27, 2018
	 * @return void 
	 * @Description : This is deleteNotificationPeriodically method to delete the notification in every 3 months.
	 */
	@Scheduled(cron = "${uhc.removeNotifications.cronExpression}", zone = "America/Denver")
	public void deleteNotificationPeriodically() {
		LOGGER.info("###### Scheduler start processing for deleteNotificationPeriodically.....");
		schedulerService.deletenotificationsByScheduler();
		LOGGER.info("###### Scheduler processing done for deleteNotificationPeriodically.....");
	}
}
