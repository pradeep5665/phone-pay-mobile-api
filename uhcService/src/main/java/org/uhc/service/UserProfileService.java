/* 
 * ===========================================================================
 * File Name UserProfileService.java
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
 * $Log: UserProfileService.java,v $
 * ===========================================================================
 */
package org.uhc.service;

import org.uhc.controller.envelop.request.UserProfileRequest;
import org.uhc.controller.envelop.response.UserProfileResponse;

/**
 * @author nehas3
 * @date May 25, 2018
 */
public interface UserProfileService {

	/**
	 * 
	 * @author nehas3
	 * @date May 25, 2018
	 * @return UserProfileResponse 
	 * @param UserProfileRequest
	 * @return user profile
	 * Description : this is getUserProfile method to get user profile info on basis of requested parameters.
	 */
	UserProfileResponse getUserProfile(UserProfileRequest UserProfileRequest);
}
