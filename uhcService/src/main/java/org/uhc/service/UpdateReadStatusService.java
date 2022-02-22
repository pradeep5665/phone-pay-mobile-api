/* 
 * ===========================================================================
 * File Name UpdateReadStatusService.java
 * 
 * Created on Aug 22, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateReadStatusService.java,v $
 * ===========================================================================
 */
 package org.uhc.service;

import org.uhc.controller.envelop.request.UpdateReadStatusMessageRequest;
import org.uhc.controller.envelop.response.UpdateReadStatusMessageResponse;

/**
 * @author nehas3
 * @date Aug 22, 2018
 */
public interface UpdateReadStatusService {

	/**
	 * @author nehas3
	 * @date Aug 22, 2018
	 * @return UpdateReadStatusMessageResponse 
	 * @param readStatusResquest
	 * @Description : This is updateReadStatusOfMessage method that returns boolean values on basis of requested parameters.
	 */
	UpdateReadStatusMessageResponse updateReadStatusOfMessage(UpdateReadStatusMessageRequest readStatusResquest);
}
