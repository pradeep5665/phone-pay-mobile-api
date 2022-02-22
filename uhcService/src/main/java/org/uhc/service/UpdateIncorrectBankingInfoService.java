/* 
 * ===========================================================================
 * File Name UpdateIncorrectBankingInfoService.java
 * 
 * Created on Sep 23, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateIncorrectBankingInfoService.java,v $
 * ===========================================================================
 */
 package org.uhc.service;

import org.uhc.controller.envelop.request.UpdateIncorrectBankingInfoRequest;
import org.uhc.controller.envelop.response.UpdateIncorrectBankingInfoResponse;

/**
 * @author nehas3
 * @date Sep 23, 2019
 */
public interface UpdateIncorrectBankingInfoService {

	/**
	 * 
	 * @author nehas3
	 * @date Sep 23, 2019
	 * @return UpdateIncorrectBankingInfoResponse 
	 * @param updateIncorrectBankingInfoRequest
	 * @return success or failure on update Incorrect Banking info for user
	 * Description : this is updateIncorrectBankingInfo that returns success or failure on update Incorrect Banking info for user.
	 */
	 UpdateIncorrectBankingInfoResponse updateIncorrectBankingInfo(UpdateIncorrectBankingInfoRequest updateIncorrectBankingInfoRequest);
}
