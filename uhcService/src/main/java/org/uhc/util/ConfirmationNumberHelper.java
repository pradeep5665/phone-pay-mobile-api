/* 
 * ===========================================================================
 * File Name ConfirmationNumberHelper.java
 * 
 * Created on July 5, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: ConfirmationNumberHelper.java,v $
 * ===========================================================================
 */
package org.uhc.util;

import java.security.SecureRandom;

/**
 * @author nehas3
 * @date July 5, 2018
 * @Description : This is ConfirmationNumberHelper class to declare all the constants variables those are needed in payment confirmation API
 */
public class ConfirmationNumberHelper {

	private static final int LENGTH = 12;
	private static final int GROUP_SIZE = 4;
	private static final String UPPERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWERS = "abcdefghijklmnopqrstuvwxyz";
	private static final String DIGITS = "0123456789";
	private static final String ALPHANUM = UPPERS + DIGITS;
	private static final String AUTOKEYNUM = LOWERS + UPPERS + DIGITS;
	
	private ConfirmationNumberHelper() {
		//
	}

	/**
	 * @author nehas3
	 * @date Sep 5, 2018
	 * @return String 
	 * @param confirmationNumber
	 * @Description : Formatting confirmation number generated for user after payment processes 
	 */
	public static String formatConfirmationNumber(String confirmationNumber) {
		if (confirmationNumber == null) {
			return null;
		}
		int confNumLength = confirmationNumber.length();
		if (confirmationNumber.length() > 2 * GROUP_SIZE) {
			return confirmationNumber.substring(0, 4) + "-" + confirmationNumber.substring(GROUP_SIZE, 2 * GROUP_SIZE)
					+ "-" + confirmationNumber.substring(2 * GROUP_SIZE, confNumLength);
		} else if (confirmationNumber.length() > GROUP_SIZE + 1) {
			return confirmationNumber.substring(0, 4) + "-" + confirmationNumber.substring(GROUP_SIZE, confNumLength);
		} else {
			return confirmationNumber;
		}
	}

	/**
	 * @author nehas3
	 * @date Sep 5, 2018
	 * @return String
	 * @Description : generating confirmation number
	 */
	public static String generateConfirmationNumber() {
		SecureRandom random = new SecureRandom();
		char[] buf = new char[LENGTH];
		char[] symbols = ALPHANUM.toCharArray();

		for (int i = 0; i < LENGTH; i++) {
			buf[i] = symbols[random.nextInt(symbols.length)];
		}

		return new String(buf);
	}
	
	public static String generateAutoKey() {
		SecureRandom random = new SecureRandom();
		char[] buf = new char[LENGTH];
		char[] symbols = AUTOKEYNUM.toCharArray();

		for (int i = 0; i < LENGTH; i++) {
			buf[i] = symbols[random.nextInt(symbols.length)];
		}
		return new String(buf);
	}
}
