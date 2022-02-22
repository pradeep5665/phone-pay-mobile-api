/* 
 * ===========================================================================
 * File Name UpdatePushNotificationFlagServiceImpl.java
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
 * $Log: UpdatePushNotificationFlagServiceImpl.java,v $
 * ===========================================================================
 */
package org.uhc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.UpdatePushNotificationFlagRequest;
import org.uhc.controller.envelop.response.UpdatePushNotificationFlagResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.PushNotificationFlagDto;
import org.uhc.service.UpdatePushNotificationFlagService;
import org.uhc.util.MessageReader;

@Service
public class UpdatePushNotificationFlagServiceImpl implements UpdatePushNotificationFlagService{

	private static final Logger LOGGER = LogManager.getLogger(UpdatePushNotificationFlagServiceImpl.class.getName());
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessageReader messageReader;

	@Override
	public UpdatePushNotificationFlagResponse updatePushNotificationFlag(UpdatePushNotificationFlagRequest updatePushNotificationFlagRequest) {
		LOGGER.info("Entering into updatePushNotificationFlag");
		UpdatePushNotificationFlagResponse updatePushNotificationFlagResponse = new UpdatePushNotificationFlagResponse();
		try {
			PushNotificationFlagDto pnfDto = userDao.getNOtificationFlag(updatePushNotificationFlagRequest.getUserId());
			if(pnfDto!=null) {
				boolean isNotificationFlagUpdated = userDao.updatePushNotificationFlag(updatePushNotificationFlagRequest);
				if(isNotificationFlagUpdated) {
					updatePushNotificationFlagResponse.setIsSuccessful(true);
					updatePushNotificationFlagResponse.setMessage(messageReader.getPropertyFileMesssages().get("upadtePushNotification.success"));
				}else {
					updatePushNotificationFlagResponse.setIsSuccessful(false);
					updatePushNotificationFlagResponse.setMessage(messageReader.getPropertyFileMesssages().get("upadtePushNotification.failure"));
				}
				
			}else {
				updatePushNotificationFlagResponse.setIsSuccessful(false);
				updatePushNotificationFlagResponse.setMessage(messageReader.getPropertyFileMesssages().get("upadtePushNotification.failureForInvalidId"));
			}
			
		}catch(Exception exp) {
			updatePushNotificationFlagResponse.setIsSuccessful(false);
			updatePushNotificationFlagResponse.setMessage(messageReader.getPropertyFileMesssages().get("upadtePushNotification.failure"));
			LOGGER.error("Exception occured while updating notification flag due to ", exp);
		}
		LOGGER.info("Exit from updatePushNotificationFlag");
		return updatePushNotificationFlagResponse;
	}
}

	