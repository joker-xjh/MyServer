package constant;

import java.util.Arrays;
import java.util.concurrent.ConcurrentSkipListSet;

public class ServerSettings {
	
	public static int socketTimeout = 10000;
	public static boolean keepalive = true;
	public static int portNum  = 9090;
	
	public static int fileCacheTimeout = 6000000;
	public static boolean fileCacheEnabled = true;
	public static int fileCacheSize = 24;
	
	
	
	public static int debugLevel = 0x0;
	
	public static boolean multiThreaded = true;
	
	
	
	public static String notFoundFilePath = "res/notfound.html";
	public static String redirFilePath = "res/redir.html";
	public static String internalErrorFilePath = "res/internalerror.html";
	public static String badRequestFilePath = "res/badrequest.html";
	public static ConcurrentSkipListSet <String> textTypes = new ConcurrentSkipListSet<String>(Arrays.asList("txt", "html", "css", "js", "htm"));

	public static String webRoot = "";
	
	public static String defaultPage = "index.html";
	
	public static String supportedEncode = "gzip";
	
	
	
	public static int getSocketTimeout() {
		return socketTimeout;
	}
	
	public static int getDebugLevel() {
		return debugLevel;
	}
	
	public static int getPort() {
		return portNum;
	}
	
	public static boolean isKeepAlive() {
		return keepalive;
	}
	
	public static boolean isMultiThreaded() {
		return multiThreaded;
	}
	
	public static boolean getKeepAlive() {
		return keepalive;
	}
	
	public static String getWebRoot() {
		return webRoot;
	}
	
	public static String getDefaultPage() {
		return defaultPage;
	}
	
	public static boolean isTextType(String exn) {
		return textTypes.contains(exn);
	}
	

}
