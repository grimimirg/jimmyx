package com.jmxf.core.constant;

public class Constants {
	public static final String CODE_NO_RECORDS_FOUND = "0";
	public static final String MESSAGE_NO_RECORDS_FOUND = "No records found";

	public enum MAIL_TYPES {
		GENERIC("GENERIC"), PASSWORD_CHANGE("PASSWORD_CHANGE");

		private String type;

		MAIL_TYPES(String type) {
			this.type = type;
		}

		public String getType() {
			return this.type;
		}

	}
}
