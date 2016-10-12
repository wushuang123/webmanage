package com.xifar.common.config;

public class Enums {
	
	public enum YESNO {

		/** 否 **/
		NO(0, "否"),
		/** 是 **/
		YES(2, "是");

		private final int mValue;

		private final String mTag;

		private YESNO(int value, String tag) {
			mValue = value;
			mTag = tag;
		}

		public int getValue() {
			return mValue;
		}

		public String getTag() {
			return mTag;
		}
	}

}
