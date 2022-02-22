package org.uhc.service;

import org.uhc.controller.envelop.request.FAQReq;
import org.uhc.controller.envelop.request.FAQSearchStrReq;
import org.uhc.controller.envelop.response.FAQAllQesAnsResponse;
import org.uhc.controller.envelop.response.FAQAllQesAnsWordSpecificResponse;
import org.uhc.controller.envelop.response.FAQCategoryResponse;
import org.uhc.controller.envelop.response.FAQResponse;

public interface FAQService {
	
	
	 
	 /**
		 * @author pradeepy
		 * @date oct 11, 2021
		 * @return FAQCategoryResponse 
		 * @Description this is getFAQCategory method that returns list of category.
		 */
	 FAQCategoryResponse getFAQCategory ();

	 /**
		 * @author pradeepy
		 * @date Oct 11, 2021
		 * @return FAQResponse 
		 * @param FAQRequest
		 * @return return FAQ list for specific category.
		 */
	 FAQResponse getFAQInfo(FAQReq faqReq);
	 
	 /**
	 * @author pradeepy
	 * @date Oct 11, 2021
	 * @return FAQAllQesAnsResponse 
	 * @return return FAQ all category question answer list.
	 */
	 FAQAllQesAnsResponse getAllQuestionAnswerListFromFAQ();
	 
	
		 
	 /**
		 * @author pradeepy
		 * @date Oct 11, 2021
		 * @return FAQResponse 
		 * @param FAQRequest
		 * @return return FAQ list for specific word.
		 */
	 FAQAllQesAnsWordSpecificResponse getFAQByWordSpecific(FAQSearchStrReq searchStrReq);
}
