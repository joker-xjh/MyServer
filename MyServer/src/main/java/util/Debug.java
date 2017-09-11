package util;

import constant.ServerSettings;

public class Debug {
	
	public static void print(String message, int debugCode) {
		if((ServerSettings.getDebugLevel() & debugCode) != 0) {
			System.err.println(message);
		}
	}

}
