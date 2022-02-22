/* 
 * ===========================================================================
 * File Name UpdateUserNameValidation.java
 * 
 * Created on Oct 12, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: UpdateUserNameValidation.java,v $
 * ===========================================================================
 */
 package org.uhc.util;

import org.springframework.stereotype.Component;
import org.uhc.exception.InvalidUsernameException;

@Component
public class UserNameValidation {

	 public static final String VALID_USERNAME_PATTERN = "[0-9A-Za-z-_@.]+";
	 
	 private UserNameValidation() {
		 //private constructor
	 }
	
	 /**
	  * @author nehas3
	  * @date Apr 15, 2019
	  * @return boolean 
	  * @param username
	  * @return boolean
	  * @throws InvalidUsernameException
	  * @Description validating user name pattern 
	  */
	 public static boolean validateUsername(String username) throws InvalidUsernameException {
			boolean validUsername = username.matches(VALID_USERNAME_PATTERN);
			validUsername &= username.length() <= 20;
			return validUsername;
		}
}
