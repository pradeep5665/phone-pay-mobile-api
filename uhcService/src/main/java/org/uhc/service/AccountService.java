/* 
 * ===========================================================================
 * File Name AccountService.java
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
 * $Log: AccountService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.AccountRequest;
import org.uhc.controller.envelop.response.AccountResponse;

/**
 * @author nehas3
 * @date May 25, 2018
 */
public interface AccountService {

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return AccountResponse 
	 * @param accountRequest
	 * @return Account balance info of user
	 * Description : getaccountBalInfo that returns Account balance info of user on basis of requested parameters.
	 */
	 AccountResponse getAccountBalInfo(AccountRequest accountRequest);

}
