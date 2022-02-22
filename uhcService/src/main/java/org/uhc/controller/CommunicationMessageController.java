package org.uhc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uhc.controller.envelop.request.AccountRequest;
import org.uhc.controller.envelop.request.CommunicationRequest;
import org.uhc.controller.envelop.request.CommunicationViewMessageReq;
import org.uhc.controller.envelop.request.GetScheduledPaymentRequest;
import org.uhc.controller.envelop.request.LatestVersionRequest;
import org.uhc.controller.envelop.request.LoginRequest;
import org.uhc.exception.LockedUserLoginException;
import org.uhc.service.AccountService;
import org.uhc.service.CommunicationService;

@RestController
@RequestMapping(value = "/")
public class CommunicationMessageController {

	private static final Logger LOGGER = LogManager.getLogger(CommunicationMessageController.class);
	
	@Autowired
	private CommunicationService communicationService;
	
	/**
	 * communication API have been created to get message info of communication
	 * @author pradeepy
	 * @date Jan 29, 2021
	 * @return Object
	 * @param comRequest
	 * @return communicationResponse 
	 */
	@PostMapping(value = "/communication")
	public Object communicationAPI(@RequestBody CommunicationRequest comRequest) {
		LOGGER.info("accountAPI: {}" , comRequest);
		return  communicationService.getCommunicationMessageInfo(comRequest);
	}
	
	/**
	 * This is getCommunicationMessageList Service API to Get message list 
	 * @author pradeepy
	 * @date feb 1, 2021
	 * @return Object 
	 * @return CommunicationMessagesResponse
	 */
	@GetMapping(value = "/getCommunicationMessages")
	public Object getCommunicationMessages() {
		LOGGER.info("Get Communication Message List");
		return communicationService.getCommunicationMessages();
	}
	
	/**
	 * displayMessage API have been created to get message to display
	 * @author pradeepy
	 * @date feb 05, 2021
	 * @return Object
	 * @param comRequest
	 * @return communicationResponse 
	 */
	@PostMapping(value = "/getDisplayMessage")
	public Object getDisplayMessage(@RequestBody CommunicationViewMessageReq comRequest) {
		LOGGER.info("accountAPI: {}" , comRequest);
		return  communicationService.getDisplayMessageService(comRequest);
	}
	
	/**
	 * OS Latest version API have been created to get latest version of OS
	 * @author pradeepy
	 * @date feb 25, 2021
	 * @return Object
	 * @param vRequest
	 * @return vResponse 
	 */
	@PostMapping(value = "/getOSLatestVersion")
	public Object getOSLatestVersionAPI(@RequestBody LatestVersionRequest vRequest) {
		LOGGER.info("accountAPI: {}" , vRequest);
		return  communicationService.getOSLatestVersionInfo(vRequest);
	}
	

	/**
	 * getViewedMessageByUser API have been created to get message viewed by user
	 * @author pradeepy
	 * @date September 24, 2021
	 * @return Object
	 * @param comRequest
	 * @return communicationResponse 
	 */
	@PostMapping(value = "/getViewedMessageByUser")
	public Object getViewedMessage(@RequestBody CommunicationViewMessageReq comRequest) {
		LOGGER.info("accountAPI: {}" , comRequest);
		return  communicationService.getVewedMessageByUserService(comRequest);
	}
	
}
