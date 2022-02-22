package org.uhc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.uhc.controller.envelop.request.FAQReq;
import org.uhc.controller.envelop.request.FAQSearchStrReq;
import org.uhc.controller.envelop.response.FAQAllQesAnsResponse;
import org.uhc.controller.envelop.response.FAQAllQesAnsWordSpecificResponse;
import org.uhc.controller.envelop.response.FAQCategoryResponse;
import org.uhc.controller.envelop.response.FAQResponse;
import org.uhc.dao.UserDao;
import org.uhc.dao.dto.FAQCategoryDto;
import org.uhc.dao.dto.FAQDetailDto;
import org.uhc.dao.dto.FAQDto;
import org.uhc.dao.dto.FAQSequenceDto;
import org.uhc.dao.dto.FAQWordSpecificDto;
import org.uhc.service.FAQService;
import org.uhc.util.MessageReader;
@Service
public class FAQServiceImpl implements FAQService {
	
	private static final Logger LOGGER = LogManager.getLogger(FAQServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageReader messageReader;

	

	@Override
	public FAQCategoryResponse getFAQCategory() {
		FAQCategoryResponse faqCategoryResponse = new FAQCategoryResponse();
		try {
			List<FAQCategoryDto> categoryDtos = userDao.getFAQCategoryList();
				if (!CollectionUtils.isEmpty(categoryDtos)) {
					faqCategoryResponse.setCategoryList(categoryDtos);
					faqCategoryResponse.setIsSuccessful(true);
					faqCategoryResponse.setMessage(
							messageReader.getPropertyFileMesssages().get("faqCategoryList.success"));
				} else {
					faqCategoryResponse.setIsSuccessful(false);
					faqCategoryResponse
							.setMessage(messageReader.getPropertyFileMesssages().get("faqCategoryList.error"));
				}
			
		} catch (Exception e) {
			LOGGER.info("getFAQCategory(): Category not found");
		}
		return faqCategoryResponse;
	}



	@Override
	public FAQResponse getFAQInfo(FAQReq faqReq) {
		FAQResponse faqResponse = new FAQResponse();
		FAQDto faqDt = new FAQDto();
		FAQSequenceDto sequenceDto = null;
		List<FAQSequenceDto> faqSequenceDtos = new ArrayList<FAQSequenceDto>();
		try {
		    List<FAQDto> faqDtos = userDao.getFAQByCategoryIdAndApp(faqReq.getCategoryId(), faqReq.getApplication());
		    
		    if (!CollectionUtils.isEmpty(faqDtos)) {
		    for(FAQDto faqDto :faqDtos ) {
		    	sequenceDto = new FAQSequenceDto();
		    	if(faqDto.getActive().equals("1")) {
		    		faqDt.setCategoryID(faqDto.getCategoryID());
		    		sequenceDto.setQuestionSequence(faqDto.getQuestionSequence());
		    		sequenceDto.setQuestion(faqDto.getQuestionTitle());
		    		sequenceDto.setAnswer(faqDto.getQauestionAnswer());
		    		sequenceDto.setApplication(faqDto.getApplication());
		    		faqSequenceDtos.add(sequenceDto);
		    	}else {
		    		LOGGER.info("The FAQ is not active");
		    	}
		    	
		    }
			    faqResponse.setCategoryID(faqDt.getCategoryID());
		    	faqResponse.setIsSuccessful(true);
		    	faqResponse.setFaqSequenceList(faqSequenceDtos);
		    	faqResponse.setMessage(messageReader.getPropertyFileMesssages().get("faqList.success"));
		    }else {
		    	faqResponse.setIsSuccessful(false);
		    	faqResponse.setMessage(messageReader.getPropertyFileMesssages().get("faqList.error"));
		    }
		    
		} catch (Exception exp) {
			
			LOGGER.error("getFAQInfo(): getting FAQ failed." , exp);
		}
		return faqResponse;
	}



	
	@Override
	public FAQAllQesAnsResponse getAllQuestionAnswerListFromFAQ() {
		FAQDetailDto faqDetailDto = null;
		List<FAQDetailDto> detailDtos = new ArrayList<FAQDetailDto>();
		FAQSequenceDto sequenceDto = null;
		FAQAllQesAnsResponse qesAnsResponse = new FAQAllQesAnsResponse();
		List<FAQSequenceDto> faqSequenceDtos = null;
		try {
			List<FAQCategoryDto> categoryDtos = userDao.getFAQCategoryList();
			if (!CollectionUtils.isEmpty(categoryDtos)) {
			for(FAQCategoryDto categoryDto : categoryDtos) {
				faqDetailDto = new FAQDetailDto();
				faqSequenceDtos = new ArrayList<FAQSequenceDto>();
				faqDetailDto.setCategoryID(categoryDto.getId());
				List<FAQDto> faqDtos = userDao.getFAQByCategoryId(categoryDto.getId());
				 for(FAQDto faqDto :faqDtos ) {
		    		sequenceDto = new FAQSequenceDto();
		    		sequenceDto.setQuestionSequence(faqDto.getQuestionSequence());
		    		sequenceDto.setQuestion(faqDto.getQuestionTitle());
		    		sequenceDto.setAnswer(faqDto.getQauestionAnswer());
		    		sequenceDto.setApplication(faqDto.getApplication());
		    		faqSequenceDtos.add(sequenceDto);
		    	}
				faqDetailDto.setFaqSequenceList(faqSequenceDtos);
				detailDtos.add(faqDetailDto);
				  }
				qesAnsResponse.setIsSuccessful(true);
		    	qesAnsResponse.setCategoryList(detailDtos);
		    	qesAnsResponse.setMessage(messageReader.getPropertyFileMesssages().get("faqList.success"));
			}else {
	    	qesAnsResponse.setIsSuccessful(false);
	    	qesAnsResponse.setMessage(messageReader.getPropertyFileMesssages().get("faqList.error"));
	    }
	    
	} catch (Exception exp) {
		
		LOGGER.error("getFAQInfo(): getting FAQ failed." , exp);
	}
		return qesAnsResponse;
	}
	@Override
	public  FAQAllQesAnsWordSpecificResponse getFAQByWordSpecific(FAQSearchStrReq searchStrReq) {

		FAQAllQesAnsWordSpecificResponse ansWordSpecificResponse = new FAQAllQesAnsWordSpecificResponse();
		FAQWordSpecificDto specificDto = null;
		List<FAQWordSpecificDto> faqWordSpecificList = new ArrayList<FAQWordSpecificDto>();
		try {
			List<FAQDto> dtos = userDao.getFAQByWordSpecific(searchStrReq.getSearchString());
			if (!CollectionUtils.isEmpty(dtos)) {
			for(FAQDto faqDto : dtos) {
					specificDto = new FAQWordSpecificDto();
					specificDto.setCategoryId(faqDto.getCategoryID());
					specificDto.setQuestion(faqDto.getQuestionTitle());
					specificDto.setAnswer(faqDto.getQauestionAnswer());
					specificDto.setApplication(faqDto.getApplication());
					faqWordSpecificList.add(specificDto);
					}
					ansWordSpecificResponse.setIsSuccessful(true);
					ansWordSpecificResponse.setFaqWordSpecificList(faqWordSpecificList);
				    ansWordSpecificResponse.setMessage(messageReader.getPropertyFileMesssages().get("faqList.success"));
			}else {
					ansWordSpecificResponse.setIsSuccessful(false);
					ansWordSpecificResponse.setMessage(messageReader.getPropertyFileMesssages().get("faqList.error"));
	    }
	    
	} catch (Exception exp) {
		ansWordSpecificResponse.setIsSuccessful(false);
		ansWordSpecificResponse.setMessage(messageReader.getPropertyFileMesssages().get("faqList.error"));
		LOGGER.error("getFAQInfo(): getting FAQ failed." , exp);
	}
		return ansWordSpecificResponse;
	}

}
