package org.uhc.service;

import org.uhc.controller.envelop.request.CommunicationRequest;
import org.uhc.controller.envelop.request.CommunicationViewMessageReq;
import org.uhc.controller.envelop.request.LatestVersionRequest;
import org.uhc.controller.envelop.response.CommunicationMessagesResponse;
import org.uhc.controller.envelop.response.CommunicationResponse;
import org.uhc.controller.envelop.response.CommunicationViewResponse;
import org.uhc.controller.envelop.response.LatestVersionResponse;

public interface CommunicationService {

	/**
	 * @author pradeepy
	 * @date Jan 29, 2021
	 * @return CommunicationResponse 
	 * @param communicationRequest
	 * @return message details
	 */
	 CommunicationResponse getCommunicationMessageInfo(CommunicationRequest communicationRequest);
	 
		/**
		 * @author pradeepy
		 * @date feb 1, 2021
		 * @return CommunicationResponse
		 * @Description this is getCommunicationMessages method that returns list of messages.
		 */
	 CommunicationMessagesResponse getCommunicationMessages ();
	 
	 /**
		 * @author pradeepy
		 * @date feb 5, 2021
		 * @return CommunicationResponse 
		 * @param communicationRequest
		 * @return message details
		 */
		 CommunicationResponse getDisplayMessageService(CommunicationViewMessageReq messageReq);
		 
		 /**
			 * @author pradeepy
			 * @date feb 25, 2021
			 * @return vResponse 
			 * @param vRequest
			 * @return OS version details
			 */
		 LatestVersionResponse getOSLatestVersionInfo(LatestVersionRequest latestVersionRequest);	
		 
		 /**
			 * @author pradeepy
			 * @date September 24, 2021
			 * @return CommunicationResponse 
			 * @param communicationRequest
			 * @return message details
			 */
		 CommunicationViewResponse getVewedMessageByUserService(CommunicationViewMessageReq messageReq);
}
