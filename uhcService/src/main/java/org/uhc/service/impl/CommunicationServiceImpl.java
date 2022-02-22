package org.uhc.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.uhc.controller.envelop.request.CommunicationRequest;
import org.uhc.controller.envelop.request.CommunicationViewMessageReq;
import org.uhc.controller.envelop.request.LatestVersionRequest;
import org.uhc.controller.envelop.response.CommunicationMessagesResponse;
import org.uhc.controller.envelop.response.CommunicationResponse;
import org.uhc.controller.envelop.response.CommunicationViewResponse;
import org.uhc.controller.envelop.response.LatestVersionResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.CommunicationDto;
import org.uhc.dao.dto.CommunicationViewDto;
import org.uhc.dao.dto.LatestVersionDto;
import org.uhc.service.CommunicationService;
import org.uhc.util.MessageReader;

@Service
public class CommunicationServiceImpl implements CommunicationService {

	private static final Logger LOGGER = LogManager.getLogger(CommunicationServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	@Override
	public CommunicationResponse getCommunicationMessageInfo(CommunicationRequest communicationRequest) {

		CommunicationResponse communicationResponse = new CommunicationResponse();
		try {
			CommunicationDto communicationDto = userDao
					.getCommunicationMessageDetailsByMessageId(communicationRequest.getMessageID());
			if (communicationDto != null) {
				communicationResponse.setCommunicationDto(communicationDto);
				communicationResponse.setIsSuccessful(true);
				communicationResponse.setStatusCode(messageReader.getStatusCode().get("Successfully got the communication message Records."));

			} else {
				communicationResponse.setIsSuccessful(false);
				communicationResponse.setStatusCode(messageReader.getStatusCode().get("No message Information found."));
			}

		} catch (Exception exp) {
			communicationResponse.setIsSuccessful(false);
			communicationResponse.setStatusCode(messageReader.getStatusCode().get("No message Information found."));

			LOGGER.error("getCommunicationMessageDetails(): ", exp);
		}
		LOGGER.info("getCommunicationMessageDetails(): communicationResponse: {}", communicationResponse);
		return communicationResponse;
	}

	@Override
	public CommunicationMessagesResponse getCommunicationMessages() {
		CommunicationMessagesResponse messagesResponse = new CommunicationMessagesResponse();
		try {
			List<CommunicationDto> communicationMessages = userDao.getCommunicationMessageList();
			List<CommunicationDto> messages = new ArrayList<CommunicationDto>();
			if (!CollectionUtils.isEmpty(communicationMessages)) {
				Date d = new Date();
				messages = communicationMessages.stream().filter(
						messageDto -> messageDto.getDisplayStart() != null && messageDto.getDisplayEnd() != null)
						.filter(messageDto -> d.compareTo(messageDto.getDisplayStart()) >= 0
								&& d.compareTo(messageDto.getDisplayEnd()) <= 0)
						.collect(Collectors.toList());

				for (CommunicationDto comDto : communicationMessages) {
					if ((comDto.getDisplayStart() == null && comDto.getDisplayEnd() == null)
							|| ((comDto.getDisplayStart()!=null && d.after(comDto.getDisplayStart())) && comDto.getDisplayEnd() == null)
							|| (comDto.getDisplayStart()==null && (comDto.getDisplayEnd() != null && d.before(comDto.getDisplayEnd())))) {
						messages.add(comDto);
					}
				}

				if (!CollectionUtils.isEmpty(messages)) {
					messagesResponse.setCommunicationMessagesList(messages);
					messagesResponse.setIsSuccessful(true);
					messagesResponse.setMessage(
							messageReader.getPropertyFileMesssages().get("communicationMessageList.success"));
				} else {
					messagesResponse.setIsSuccessful(false);
					messagesResponse
							.setMessage(messageReader.getPropertyFileMesssages().get("communicationMessageList.error"));
				}
			} else {
				messagesResponse.setIsSuccessful(false);
				messagesResponse
						.setMessage(messageReader.getPropertyFileMesssages().get("communicationMessageList.error"));
			}
		} catch (Exception e) {
			LOGGER.info("getCommunicationMessageList(): Message not found");
		}
		return messagesResponse;
	}

	@Override
	public CommunicationResponse getDisplayMessageService(CommunicationViewMessageReq messageReq) {
		CommunicationResponse communicationResponse = new CommunicationResponse();
		CommunicationViewDto viewDto = new CommunicationViewDto();
		try {
			CommunicationDto communicationDto = userDao
					.getCommunicationMessageDetailsByMessageId(messageReq.getMessageId());
			if (communicationDto.getDisplayOnce() == 1) {
				viewDto = userDao.getMessageViewedByMessageIdAndUserId(messageReq);
				if (viewDto != null) {
					communicationResponse.setIsSuccessful(false);
					communicationResponse.setStatusCode(messageReader.getStatusCode().get("this message already viewed by user"));
				} else {
					viewDto = new CommunicationViewDto();
					viewDto.setMessageId(messageReq.getMessageId());
					viewDto.setUserId(messageReq.getUserId());
					boolean saveViewedRecord = userDao.saveMessageViewedRecord(viewDto);
					if (saveViewedRecord) {
						communicationResponse.setCommunicationDto(communicationDto);
						communicationResponse.setIsSuccessful(true);
						communicationResponse.setStatusCode(messageReader.getStatusCode().get("Communication Message Return successfully"));
					} else {
						communicationResponse.setIsSuccessful(false);
						communicationResponse.setStatusCode(messageReader.getStatusCode().get("Viewed record not saved"));
					}
				}
			} else {
				viewDto = new CommunicationViewDto();
				viewDto.setMessageId(messageReq.getMessageId());
				viewDto.setUserId(messageReq.getUserId());
				boolean saveViewedRecord = userDao.saveMessageViewedRecord(viewDto);
				if (saveViewedRecord) {
					communicationResponse.setCommunicationDto(communicationDto);
					communicationResponse.setIsSuccessful(true);
					communicationResponse.setStatusCode(messageReader.getStatusCode().get("Communication Message Return successfully"));
				} else {
					communicationResponse.setIsSuccessful(false);
					communicationResponse.setStatusCode(messageReader.getStatusCode().get("Viewed record not saved"));
				}
			}
		} catch (Exception e) {
			communicationResponse.setIsSuccessful(false);
			communicationResponse.setStatusCode(messageReader.getStatusCode().get("No message Information found"));
		}
		return communicationResponse;
	}

	@Override
	public LatestVersionResponse getOSLatestVersionInfo(LatestVersionRequest latestVersionRequest) {
		LatestVersionResponse latestVersionResponse = new LatestVersionResponse();
		try {
			LatestVersionDto latestVersionDto = userDao.getOSLatestVersionInfoByOSName(latestVersionRequest.getOsName());
			if (latestVersionDto != null) {
				
				latestVersionResponse.setLatestVersionDto(latestVersionDto);
				latestVersionResponse.setIsSuccessful(true);
				latestVersionResponse.setMessage(messageReader.getPropertyFileMesssages().get("osLatestVersion.success"));
				} else {
					latestVersionResponse.setIsSuccessful(false);
					latestVersionResponse.setMessage(messageReader.getPropertyFileMesssages().get("osLatestVersion.error"));
			}

		} catch (Exception exp) {
			latestVersionResponse.setIsSuccessful(false);
			latestVersionResponse.setMessage("osLatestVersion.error");

			LOGGER.error("getOSLatestVersionInfo(): ", exp);
		}
		LOGGER.info("getOSLatestVersionInfo(): latestVersionResponse: {}", latestVersionResponse);
		return latestVersionResponse;
	}

	@Override
	public CommunicationViewResponse getVewedMessageByUserService(CommunicationViewMessageReq messageReq) {
		CommunicationViewResponse communicationViewResponse = new CommunicationViewResponse();
		CommunicationViewDto viewDto = new CommunicationViewDto();
		try {
			CommunicationDto communicationDto = userDao
					.getCommunicationMessageDetailsByMessageId(messageReq.getMessageId());
			if (communicationDto.getDisplayOnce() == 1) {
				viewDto = userDao.getMessageViewedByMessageIdAndUserId(messageReq);
				if (viewDto != null) {
					communicationViewResponse.setIsSuccessful(false);
					communicationViewResponse.setMessage(messageReader.getPropertyFileMesssages().get("message.viewed"));
					communicationViewResponse.setUserId(viewDto.getUserId());
					communicationViewResponse.setMessageId(viewDto.getMessageId());
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
					String formattedDateTime = viewDto.getDateViewed().format(formatter); // "1986-04-08 12:30"
					communicationViewResponse.setDateViewed(formattedDateTime);
					
					
				} else {
					communicationViewResponse.setIsSuccessful(true);
					communicationViewResponse.setMessage(messageReader.getPropertyFileMesssages().get("message.not.viewed"));
					communicationViewResponse.setUserId(messageReq.getUserId());
					communicationViewResponse.setMessageId(messageReq.getMessageId());
					
					}
			}else {
				communicationViewResponse.setIsSuccessful(true);
				communicationViewResponse.setMessage(messageReader.getPropertyFileMesssages().get("message.non.displayonce"));
				communicationViewResponse.setUserId(messageReq.getUserId());
				communicationViewResponse.setMessageId(messageReq.getMessageId());
				
			}
			} catch (Exception e) {
				communicationViewResponse.setIsSuccessful(false);
				communicationViewResponse.setMessage(messageReader.getPropertyFileMesssages().get("msg.info"));
			}
		return communicationViewResponse;
	}

}
