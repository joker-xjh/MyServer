package util;


public class Debug {
	
	public static void print(String message, int debugCode) {
		//if((ServerSettings.getDebugLevel() & debugCode) != 0) {
			System.out.println(message);
		//}
	}

}
