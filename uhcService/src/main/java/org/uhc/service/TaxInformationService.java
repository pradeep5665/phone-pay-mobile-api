/* 
 * ===========================================================================
 * File Name TaxInformationService.java
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
 * $Log: TaxInformationService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.response.TaxInformationResponse;

/**
 * @author nehas3
 * @date May 25, 2018
 */
public interface TaxInformationService {

	/**
	 * 
	 * @author nehas3
	 * @date May 25, 2018
	 * @return TaxInformationResponse 
	 * @param loanNumber
	 * @return tax info for user
	 * Description : this is getTaxInfo that returns tax info for user on basis of loan number.
	 */
	TaxInformationResponse getTaxInfo(Long loanNumber);
}
