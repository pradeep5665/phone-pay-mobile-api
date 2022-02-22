/* 
 * ===========================================================================
 * File Name UpdateUserNameService.java
 * 
 * Created on May 29, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateUserNameService.java,v $
 * ===========================================================================
 */
 package org.uhc.service;


import org.uhc.controller.envelop.request.UpdateUserNameRequest;
import org.uhc.controller.envelop.response.UpdateUserNameResponse;

/**
 * @author nehas3
 * @date May 29, 2018
 */
public interface UpdateUserNameService {

	/**
	 * 
	 * @author nehas3
	 * @date May 29, 2018
	 * @return UpdateUserNameResponse 
	 * @param updateUserNameRequest
	 * @return
	 * Description : this is updateUserName method to update user name on basis of requested parameters. 
	 */
	 UpdateUserNameResponse updateUserName(UpdateUserNameRequest updateUserNameRequest);
}
