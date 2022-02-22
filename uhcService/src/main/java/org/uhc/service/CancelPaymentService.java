/* 
 * ===========================================================================
 * File Name CancelPaymentService.java
 * 
 * Created on Jul 11, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: CancelPaymentService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.CancelPaymentRequest;
import org.uhc.controller.envelop.response.CancelPaymentResponse;

/**
 * @author nehas3
 * @date Jul 11, 2018
 */
public interface CancelPaymentService {

	/**
	 * @author nehas3
	 * @date Jul 11, 2018
	 * @return CancelPaymentResponse 
	 * @param cancelPaymentRequest
	 * @Description : this is cancelPayment method that is used to cancel the scheduled payment on basis of requested parameters.
	 */
	CancelPaymentResponse cancelPayment(CancelPaymentRequest cancelPaymentRequest);
}
