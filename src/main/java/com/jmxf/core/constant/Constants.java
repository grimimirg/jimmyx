package com.jmxf.core.constant;

public class Constants {

	public enum ERRORS {
		GENERIC("err");

		private String msg;

		ERRORS(String msg) {
			this.msg = msg;
		}

		public String get() {
			return this.msg;
		}

	}
}
