/* 
 * ===========================================================================
 * File Name UpdateOnlineStatementPrefService.java
 * 
 * Created on Jun 1, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateOnlineStatementPrefService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.UpdateOnlineStatementPrefRequest;
import org.uhc.controller.envelop.response.UpdateOnlineStatementPrefResponse;

/**
 * @author nehas3
 * @date May 31, 2018
 */
public interface UpdateOnlineStatementPrefService {

	/**
	 * 
	 * @author nehas3
	 * @date May 30, 2018
	 * @return UpdateOnlineStatementPrefResponse 
	 * @param statementPrefRequest
	 * @return success or failure on update Online Statement Preference for user
	 * Description : this is updateOnlineStatementPref that returns success or failure on update Online Statement Preference for user
	 */
	UpdateOnlineStatementPrefResponse updateOnlineStatementPref(UpdateOnlineStatementPrefRequest statementPrefRequest);

}
