/* 
 * ===========================================================================
 * File Name AccountHistoryService.java
 * 
 * Created on May 25, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: AccountHistoryService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.AccountHistoryRequest;
import org.uhc.controller.envelop.response.AccountHistoryResponse;

/**
 * @author nehas3
 * @date May 25, 2018
 */
public interface AccountHistoryService {

	/**
	 * 
	 * @author nehas3
	 * @date May 25, 2018
	 * @return AccountHistoryResponse 
	 * @param accountHistoryRequest
	 * @return
	 * Description : this is getAccountHistoryByLoanNumber method that returns account history of user on basis of 
	 *   			requested parameter	
	 */
	AccountHistoryResponse getAccountHistoryByLoanNumber(AccountHistoryRequest accountHistoryRequest);
}
