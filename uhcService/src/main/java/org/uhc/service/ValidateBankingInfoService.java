/* 
 * ===========================================================================
 * File Name ValidateBankingInfoService.java
 * 
 * Created on Aug 23, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateBankingInfoService.java,v $
 * ===========================================================================
 */
 package org.uhc.service;

import org.uhc.controller.envelop.request.ValidateBankingInfoRequest;
import org.uhc.controller.envelop.response.ValidateBankingInfoResponse;

/**
 * @author nehas3
 * @date Aug 23, 2019
 */
public interface ValidateBankingInfoService {

	/**
	 * @author nehas3
	 * @date Aug 23, 2019
	 * @return ValidateBankingInfoResponse 
	 * @param ValidateBankingInfoRequest
	 * @Description : this is validateBankingInfo method to validate if banking information of user is correct.
	 */
	 ValidateBankingInfoResponse validateBankingInfo(ValidateBankingInfoRequest validateBankingInfoReq);
}
