/* 
 * ===========================================================================
 * File Name UpdateBankingInfoService.java
 * 
 * Created on June 5, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateBankingInfoService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.UpdateBankingInfoRequest;
import org.uhc.controller.envelop.response.UpdateBankingInfoResponse;

/**
 * @author nehas3
 * @date June 5, 2018
 */
public interface UpdateBankingInfoService {

	/**
	 * 
	 * @author nehas3
	 * @date June 5, 2018
	 * @return UpdateBankingInfoResponse 
	 * @param updateBankingInfoRequest
	 * @return success or failure on update Banking info for user
	 * Description : this is updateBankingInfo that returns success or failure on update Banking info for user.
	 */
	UpdateBankingInfoResponse updateBankingInfo(UpdateBankingInfoRequest updateBankingInfoRequest);
}
