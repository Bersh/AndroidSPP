package com.example.androidspp.prasers.truepulse;

public class Utils {

	public static int parseHex(String hex) {
		return Integer.parseInt(hex, 16);
	}

	public static String getTruePulseMessageBody(String message) {
		return message.substring(new String(TruePulseConstants.CR400_MSG_ID
				+ TruePulseConstants.ITEMS_DIVIDER
				+ TruePulseConstants.MSG_TYPE_HV
				+ TruePulseConstants.ITEMS_DIVIDER).length(),
				message.length() - 1);
	}
}
