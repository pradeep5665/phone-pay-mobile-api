/* 
 * ===========================================================================
 * File Name Contants.java
 * 
 * Created on Jun 5, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: Contants.java,v $
 * ===========================================================================
 */
package org.uhc.util;


/**
 * @author nehas3
 * @date Jun 5, 2018
 * @Description : This is Constants class to declare all the constants variables
 */
public class Constants {

	private Constants() {
		//
	}
	
	/**
	 * @Description : making enum to define the scheduled type of a payment
	 */
	public enum ScheduledPaymentType {
		TODAY("Today"), ONE_TIME("One-Time"), RECURRING("Recurring"), AUTO_DRAFT("Auto Draft");

		private final String desc;

		private ScheduledPaymentType(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}

		public static ScheduledPaymentType getScheduledPaymentType(String desc) {
			switch (desc.toUpperCase()) {
			case "TODAY":
				return TODAY;
			case "ONE_TIME":
				return ONE_TIME;
			case "RECURRING":
				return RECURRING;
			case "AUTO_DRAFT":
				return AUTO_DRAFT;	
			default:
				return null;
			}
		}
	}

	public enum GetScheduledPaymentStatus {
		PENDING("Pending"), PROCESSED("Processed");

		private final String desc;

		private GetScheduledPaymentStatus(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}

		public static GetScheduledPaymentStatus getScheduledPaymentStatus(String desc) {
			switch (desc.toUpperCase()) {
			case "Pending":
				return PENDING;
			case "Processed":
				return PROCESSED;
			default:
				return null;
			}
		}
	}

	public enum PaymentSource {
		MBL("MBL"), TEL("TEL"), WEB("WEB"), ACH("ACH");
		
		private final String desc;

		private PaymentSource(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}

		public static PaymentSource getPaymentSource(String desc) {
			switch (desc.toUpperCase()) {

			case "MBL":
				return MBL;

			case "TEL":
				return TEL;
				
			case "WEB":
				return WEB;
				
			case "ACH":
				return ACH;
				
			default:
				return null;
			}
		}
	}

	/**
	 * @Description : making enum to define the bank account type of users.
	 */
	public enum AccountType {
		CHECKING('C'), SAVINGS('S');

		private final char dbValue;

		private AccountType(char dbValue) {
			this.dbValue = dbValue;
		}

		public static AccountType constructAccountType(char dbValue) {
			switch (dbValue) {
			case 'C':
				return CHECKING;
			case 'S':
				return SAVINGS;
			default:
				return null;
			}
		}

		public char getKey() {
			return this.dbValue;
		}
	}

	/**
	 * @Description : making enum to define the payment item types type for a loan
	 *              payment
	 */
	public enum PaymentItemType {
		MONTHLYPMT("Monthly Payment"), LATE_FEE("Late Fee"), NSF_FEE("NSF Fee"), EXTRA_PRINCIPAL(
				"Extra Principal"), EXTRA_ESCROW("Extra Escrow"), PHONEPAY_FEE("Phonepay Fee") ;

		private final String desc;

		private PaymentItemType(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return this.desc;
		}

		public static PaymentItemType constructPaymentItemType(String desc) {
			switch (desc.toUpperCase()) {
			case "MONTHLY PAYMENT":
				return MONTHLYPMT;
			case "LATE FEE":
				return LATE_FEE;
			case "NSF FEE":
				return NSF_FEE;
			case "EXTRA PRINCIPAL":
				return EXTRA_PRINCIPAL;
			case "EXTRA ESCROW":
				return EXTRA_ESCROW;
			case "PHONEPAY FEE":
				return PHONEPAY_FEE;
			default:
				return null;
			}
		}
	}

	/**
	 * @Description : making enum to define the status of a payment
	 */
	public enum PaymentStatus {
		CANCELED('C'), PROCESSED('P'), PENDING('S');

		private final char dbValue;

		private PaymentStatus(char dbValue) {
			this.dbValue = dbValue;
		}

		public char getDbValue() {
			return dbValue;
		}

		public static PaymentStatus constructPaymentStatus(char dbValue) {
			switch (dbValue) {
			case 'C':
				return CANCELED;
			case 'P':
				return PROCESSED;
			case 'S':
				return PENDING;
			default:
				return null;
			}
		}
	}

	/**
	 * @Description : making enum to format the date
	 */
	public enum DateFormat {
		DATE_FORMAT("MM/dd/yyyy"), START_DATE("/01/"), DATETIME_FORMAT("MM/dd/yyyy HH:mm.ss");

		private final String dateValue;

		private DateFormat(String dateValue) {
			this.dateValue = dateValue;
		}

		public static DateFormat constructDateFormat(String dateValue) {
			switch (dateValue) {
			case "MM/dd/yyyy":
				return DATE_FORMAT;
			case "MM/dd/yyyy HH:mm.ss":
				return DATETIME_FORMAT;
			case "/01/":
				return START_DATE;
			default:
				return null;
			}
		}

		public String getValue() {
			return this.dateValue;
		}
	}
	
}
