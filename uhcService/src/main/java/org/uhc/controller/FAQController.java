package org.uhc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uhc.controller.envelop.request.FAQReq;
import org.uhc.controller.envelop.request.FAQSearchStrReq;
import org.uhc.service.FAQService;

@RestController
@RequestMapping(value = "/")
public class FAQController {
	
	private static final Logger LOGGER = LogManager.getLogger(FAQController.class);
	@Autowired
	private FAQService faqService;
	
	/**
	 * saveCategory API have been created to save category
	 * @author pradeepy
	 * @date oct 11, 2021
	 * @return Object
	 * @param categoryReq
	 * @return categoryResponse 
	 */
	@GetMapping(value = "/getCategoryList")
	public Object getCategoryList() {
		LOGGER.info("Get FAQ Category List");
		return  faqService.getFAQCategory();
	}

	/**
	 * getFAQInfoAPI API have been created to get question answer based on categoryId specific.
	 * @author pradeepy
	 * @date Oct 11, 2021
	 * @return Object
	 * @param faqReq
	 * @return faqResponse 
	 */
	@PostMapping(value = "/getFAQInfo")
	public Object getFAQInfoAPI(@RequestBody FAQReq faqReq) {
		LOGGER.info("FAQAPI: {}" , faqReq);
		return  faqService.getFAQInfo(faqReq);
	}
	
	/**
	 * getAllQuestionAnswer from FAQ
	 * @author pradeepy
	 * @date oct 12, 2021
	 * @return Object
	 * @return faqAllQuestionAnswer 
	 */
	@GetMapping(value = "/getAllQuestionAnswerListFromFAQ")
	public Object getAllQuestionAnswerListFromFAQ() {
		LOGGER.info("Get All question answer from FAQ");
		return  faqService.getAllQuestionAnswerListFromFAQ();
	}
	
	/**
	 * getFAQByWordSpecific from FAQ
	 * @author pradeepy
	 * @date oct 12, 2021
	 * @return Object
	 * @return searchQuestionAnswer 
	 */
	@PostMapping(value = "/getFAQByWordSpecific")
	public Object getFAQByWordSpecific(@RequestBody FAQSearchStrReq searchStrReq) {
		LOGGER.info("Get question answer by word specific from FAQ");
		
		return  faqService.getFAQByWordSpecific(searchStrReq);
	}
}
