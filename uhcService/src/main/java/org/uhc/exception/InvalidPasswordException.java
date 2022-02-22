/* 
 * ===========================================================================
 * File Name InvalidPasswordException.java
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
 * $Log: InvalidPasswordException.java,v $
 * ===========================================================================
 */
package org.uhc.exception;

public class InvalidPasswordException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidPasswordException() {
		//default constructor.
	}

	public InvalidPasswordException(String s) {
		super(s);
	}

	public InvalidPasswordException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public InvalidPasswordException(Throwable throwable) {
		super(throwable);
	}

	public InvalidPasswordException(String s, Throwable throwable, boolean b, boolean b1) {
		super(s, throwable, b, b1);
	}
}
