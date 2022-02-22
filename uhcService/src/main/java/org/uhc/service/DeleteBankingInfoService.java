/* 
 * ===========================================================================
 * File Name DeleteBankingInfoService.java
 * 
 * Created on Jun 7, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: DeleteBankingInfoService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.DeleteBankingInfoRequest;
import org.uhc.controller.envelop.response.DeleteBankingInfoResponse;

/**
 * @author nehas3
 * @date June 7, 2018
 */
public interface DeleteBankingInfoService {

	/**
	 * 
	 * @author nehas3
	 * @date June 7, 2018
	 * @return DeleteBankingInfoResponse 
	 * @param deleteBankingInfoRequest
	 * @return success or failure on delete Banking info for user
	 * Description : this is deleteBankingInfo method that returns success or failure on delete Banking info for user.
	 */
	DeleteBankingInfoResponse deleteBankingInfo(DeleteBankingInfoRequest deleteBankingInfoRequest);
}
