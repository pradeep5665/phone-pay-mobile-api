/* 
 * ===========================================================================
 * File Name SendNotificationsViaFCMServerHelper.java
 * 
 * Created on Jul 31, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: SendNotificationsViaFCMServerHelper.java,v $
 * ===========================================================================
 */

package org.uhc.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author nehas3
 * @date Jul 31, 2018
 * @Description : This is SendNotificationsViaFCMServerHelper class to make a
 *              connection with google and send the notifications accordingly
 */
@PropertySource("classpath:notification.properties")
@Component
public class SendNotificationsViaFCMServerHelper {

	private static final Logger LOGGER = LogManager.getLogger(SendNotificationsViaFCMServerHelper.class);

	@Value("${fcm.server.scope}")
	private String scope;

	@Value("${fcm.server.endpoint}")
	private String fcmEndpoint;

	/**
	 * @author nehas3
	 * @date Sep 5, 2018
	 * @return boolean
	 * @param token
	 * @param notificationTitle
	 * @param notificationBody
	 * @Description : Sending FCM push notification as a payment confirmation to
	 *              users
	 */
	public boolean sendNotificationForPaymentConfirmation(String token, String notificationTitle,
			String notificationBody) {
		return sendMessageToFcm(getFCMNotificationMessage(notificationTitle, notificationBody, token));
	}

	/**
	 * @author nehas3
	 * @date Sep 5, 2018
	 * @return boolean
	 * @param token
	 * @param notificationTitleForPaymentRem
	 * @param notificationBodyForPaymentRem
	 * @Description : Sending FCM push notification as a payment reminder to users
	 */
	public boolean sendNotificationForPaymentReminder(String token, String notificationTitleForPaymentRem,
			String notificationBodyForPaymentRem) {
		return sendMessageToFcm(getFCMNotificationMessage(notificationTitleForPaymentRem, notificationBodyForPaymentRem, token));
	}

	/**
	 * @author nehas3
	 * @date Sep 5, 2018
	 * @return boolean
	 * @param jsonMessage
	 * @Description : sending message to firebase cloud messaging server using
	 *              okhttp
	 */
	private boolean sendMessageToFcm(String jsonMessage) {
		final MediaType mediaType = MediaType.parse("application/json");
		OkHttpClient httpClient = new OkHttpClient();
		String accessToken = getAccessToken();
		Request request = null;
		Response response = null;
		boolean isMessageSent = false;
		if (accessToken!=null) {
			try {
				request = new Request.Builder().url(fcmEndpoint)
					.addHeader("Content-Type", "application/json; UTF-8")
					.addHeader("Authorization", "Bearer " + accessToken)
					.post(RequestBody.create(mediaType, jsonMessage)).build();
				
				response = httpClient.newCall(request).execute();
				
				if (response.isSuccessful()) {
					isMessageSent = true;
					LOGGER.info("Message has been sent to FCM server :{}", response.isSuccessful() ? response.body().toString():"");
				}

			} catch (IOException ioeExp) {
				LOGGER.error("Error in sending message to FCM server ", ioeExp);
			}
		} else {
			LOGGER.error("Could not found Access Token.");
		}
		return isMessageSent;
	}

	/**
	 * @author nehas3
	 * @date Sep 5, 2018
	 * @return String
	 * @throws IOException
	 * @Description : getting access token to access FCM registered application
	 */
	private String getAccessToken() {
		String responseToken = null;
		GoogleCredential googleCredential = null;
		
		try {
			InputStream accessTokenFile = getClass().getResourceAsStream("/firstfcmtest-adc9f-firebase-adminsdk-lwmjp-87223211fb.json"); 
			googleCredential = GoogleCredential.fromStream(accessTokenFile).createScoped(Arrays.asList(scope));
			googleCredential.refreshToken();
			responseToken = googleCredential.getAccessToken();
		} catch (FileNotFoundException fnfExp) {
			fnfExp.printStackTrace();
			LOGGER.error("File not found: {}", fnfExp.getMessage());
		} catch (IOException ioExp) {
			LOGGER.error("IO Exception occur:{}", ioExp.getMessage());
		} catch (Exception exp) {
			exp.printStackTrace();
		} 
		return responseToken;	
	}
	

	/**
	 * @author nehas3
	 * @date Sep 5, 2018
	 * @return String
	 * @param title
	 * @param msg
	 * @param token
	 * @Description : client registration key is sent as token in the message to FCM
	 *              server
	 */
	private String getFCMNotificationMessage(String title, String msg, String token) {
		JsonObject jsonObj = new JsonObject();
		// client registration key is sent as token in the message to FCM server
		jsonObj.addProperty("token", token);

		JsonObject notification = new JsonObject();
		notification.addProperty("body", msg);
		notification.addProperty("title", title);
		jsonObj.add("notification", notification);

		JsonObject message = new JsonObject();
		message.add("message", jsonObj);

		LOGGER.info("notification message:{} ",  message);

		return message.toString();
	}

}
