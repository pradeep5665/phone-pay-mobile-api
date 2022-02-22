/* 
 * ===========================================================================
 * File Name PasswordValidation.java
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
 * $Log: PasswordValidation.java,v $
 * ===========================================================================
 */
 package org.uhc.util;

import org.springframework.stereotype.Component;
import org.uhc.exception.InvalidPasswordException;

@Component
public class PasswordValidation {

	private PasswordValidation(){
		// constructor
	}
	 public static boolean validatePassword(String password) throws InvalidPasswordException {
	        int strength = 0;
	        boolean isValidPassoword = false;
	        if (password.length() < 8 || password.length() > 20) {
	            throw new InvalidPasswordException();
	        }
	        if (password.matches(".*\\d+.*")) {
	            strength++;
	        }
	        if (password.matches(".*[A-Z]+.*")) {
	            strength++;
	        }
	        if (password.matches(".*[a-z]+.*")) {
	            strength++;
	        }
	        if (password.matches(".*[`~!@#$%^&*.,()_+;:'<>=?|-].*")) {
	            strength++;
	        }	        
	        if (strength >= 3) {
	        	isValidPassoword = true; 
	        }
	        return isValidPassoword;
	    }
}
