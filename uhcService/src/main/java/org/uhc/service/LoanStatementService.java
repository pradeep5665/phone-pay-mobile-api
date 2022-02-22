/* 
 * ===========================================================================
 * File Name LoanStatementService.java
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
 * $Log: LoanStatementService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.LoanStatementsRequest;
import org.uhc.controller.envelop.response.LoanStatementsResponse;

/**
 * @author nehas3
 * @date May 25, 2018
 */
public interface LoanStatementService {
	
	/**
	 * 
	 * @author nehas3
	 * @date Jun 7, 2018
	 * @return LoanStatementsResponse 
	 * @param loanStatementsRequest
	 * @return loan statements for user.
	 * Description : this is getLoanStatement method to get loan statements for user on basis of requested parameters. 
	 */
	LoanStatementsResponse getLoanStatement(LoanStatementsRequest loanStatementsRequest );
	

}
