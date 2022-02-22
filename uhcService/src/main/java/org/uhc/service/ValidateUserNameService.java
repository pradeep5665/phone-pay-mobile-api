/* 
 * ===========================================================================
 * File Name ValidateUserNameService.java
 * 
 * Created on Aug 13, 2019
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2019
 * All rights reserved.
 *
 * Modification history:
 * $Log: ValidateUserNameService.java,v $
 * ===========================================================================
 */
 package org.uhc.service;

import org.uhc.controller.envelop.request.ValidateUserNameReq;
import org.uhc.controller.envelop.response.ValidateUserNameRes;

/**
 * @author nehas3
 * @date Aug 13, 2019
 */
public interface ValidateUserNameService {

	/**
	 * @author nehas3
	 * @date Aug 13, 2019
	 * @return ValidateUserNameRes 
	 * @param ValidateUserNameReq
	 * @Description : this is validateUserName method to validate if user name is correct.
	 */
	 ValidateUserNameRes validateUserName(ValidateUserNameReq validateUserNameReq);
}
