/* 
 * ===========================================================================
 * File Name EscrowService.java
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
 * $Log: EscrowService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.EscrowRequest;
import org.uhc.controller.envelop.request.EscrowRequestByLoanNumber;
import org.uhc.controller.envelop.response.EscrowResponse;

/**
 * @author nehas3
 * @date May 25, 2018
 */
public interface EscrowService {

	/**
	 * 
	 * @author nehas3
	 * @date May 25, 2018
	 * @return EscrowResponse 
	 * @param escrowRequest
	 * @return escrow info for user
	 * Description : this is getEscrowInfo to get escrow information for a user.
	 */
	EscrowResponse getEscrowInfoByLoanNumber(EscrowRequestByLoanNumber escrowRequest);
	
	EscrowResponse getEscrowInfo(EscrowRequest escrowRequest);
}
