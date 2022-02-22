/* 
 * ===========================================================================
 * File Name BankingInfoService.java
 * 
 * Created on Jun 5, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: BankingInfoService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.BankingInfoRequest;
import org.uhc.controller.envelop.response.BankingInfoResponse;

/**
 * @author nehas3
 * @date June 5, 2018
 */
public interface BankingInfoService {

	/**
	 * 
	 * @author nehas3
	 * @date June 7, 2018
	 * @return BankingInfoResponse 
	 * @param bankingInfoRequest
	 * @return banking info of user
	 * Description : this is getBankinginfo method that returns the banking information of user
	 */
	BankingInfoResponse getBankingInfo(BankingInfoRequest bankingInfoRequest);
}
