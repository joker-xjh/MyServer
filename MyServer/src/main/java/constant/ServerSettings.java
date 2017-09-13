package constant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentSkipListSet;

public class ServerSettings {
	
	public static boolean running = true;
	
	public static int socketTimeout = 10000;
	public static boolean keepalive = true;
	public static int portNum  = 9090;
	
	public static int fileCacheTimeout = 6000000;
	public static boolean fileCacheEnabled = true;
	public static int fileCacheSize = 24;
	
	
	
	public static int debugLevel = 0x0;
	
	public static boolean multiThreaded = true;
	
	
	
	public static String notFoundFilePath = "src/main/java/resource/notFound.html";
	public static String redirFilePath = "src/main/java/resource/redirect.html";
	public static String internalErrorFilePath = "src/main/java/resource/internalError.html";
	public static String badRequestFilePath = "src/main/java/resource/badRequest.html";
	public static ConcurrentSkipListSet <String> textTypes = new ConcurrentSkipListSet<String>(Arrays.asList("txt", "html", "css", "js", "htm"));

	public static String webRoot = "src/main/java/pub/";
	
	public static String defaultPage = "index.html";
	
	public static String supportedEncode = "gzip";
	
	public static String logFilePath = "src/main/java/log/";
	
	public static int maxSessionThread = 10;
	
	
	public static void loadServerSettings(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while( (line = reader.readLine()) != null ) {
			String[] entry = line.split(":");
			if(entry.length != 2)
				continue;
			String name = entry[0].trim();
			String value = entry[1].trim();
			if(name.equals("socketTimeout")) {
				socketTimeout = Integer.parseInt(value);
			}
			else if(name.equals("portNum")) {
				portNum = Integer.parseInt(value);
			}
			else if(name.equals("fileCacheTimeout")) {
				fileCacheTimeout = Integer.parseInt(value);
			}
			else if(name.equals("fileCacheEnabled")) {
				fileCacheEnabled = Boolean.parseBoolean(value);
			}
			else if(name.equals("fileCacheSize")) {
				fileCacheSize = Integer.parseInt(value);
			}
			else if(name.equals("webRoot")) {
				webRoot = value;
			}
			else if(name.equals("defaultPage")) {
				defaultPage = value;
			}
			else if(name.equals("logFilePath")) {
				logFilePath = value;
			}
			else if(name.equals("maxSessionThread")) {
				maxSessionThread = Integer.parseInt(value);
			}
		}
		reader.close();
	}
	
	
	
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
