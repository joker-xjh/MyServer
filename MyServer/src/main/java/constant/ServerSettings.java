package constant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServerSettings {
	
	public static boolean running = true;
	
	public static int socketTimeout = 10000;
	public static boolean keepalive = true;
	public static int portNum  = 9090;
	
	public static int fileCacheTimeout = 6000000;
	public static boolean fileCacheEnabled = true;
	public static int fileCacheSize = 24;
	
	
	
	public static int debugLevel = 0x0;
	
	//是否采用多线程
	public static boolean multiThreaded = true;
	
	
	//一些错误页面的路径
	public static String notFoundFilePath = "src/main/java/resource/notFound.html";
	public static String redirFilePath = "src/main/java/resource/redirect.html";
	public static String internalErrorFilePath = "src/main/java/resource/internalError.html";
	public static String badRequestFilePath = "src/main/java/resource/badRequest.html";
	
	//Server支持的Content-Type,根据需要,进一步添加
	public static Map<String, String> contentType = new HashMap<>();
	static {
		//asp页面
		contentType.put(".asp", "text/asp");
		//word
		contentType.put(".doc", "application/msword");
		//html文件
		contentType.put(".html", "text/html;charset=UTF-8");
		contentType.put(".htm", "text/html;charset=UTF-8");
		//图标
		contentType.put(".ico", "image/x-icon");
		//img格式图片
		contentType.put(".img", "application/x-img");
		//java文件
		contentType.put(".java", "java/*");
		contentType.put(".class", "java/*");
		//jpe jpeg jpg图片
		contentType.put(".jpe", "image/jpeg");
		contentType.put(".jpeg", "image/jpeg");
		contentType.put(".jpg", "image/jpeg");
		//jsp文件	
		contentType.put(".jsp", "text/html;charset=UTF-8");
		//pdf文件
		contentType.put(".pdf", "application/pdf");
		//png图片
		contentType.put(".png", "image/png");
		//ppt文件
		contentType.put(".ppt", "application/vnd.ms-powerpoint");
		//xml文件
		contentType.put(".xml", "text/xml;charset=UTF-8");
		//css文件
		contentType.put(".css", "text/css;charset=UTF-8");
		//js文件
		contentType.put(".js", "application/javascript;charset=UTF-8");
		
		
	}
	//静态文件类型
	public static Set<String> staticFileType = new HashSet<>();
	//动态文件类型(目前只支持.jsp)
	public static Set<String> dynamicFileType = new HashSet<>();
	
	static {
		staticFileType.add(".doc");
		staticFileType.add(".html");
		staticFileType.add(".htm");
		staticFileType.add(".ico");
		staticFileType.add(".img");
		staticFileType.add(".jpe");
		staticFileType.add(".jpeg");
		staticFileType.add(".jpg");
		staticFileType.add(".pdf");
		staticFileType.add(".png");
		staticFileType.add(".ppt");
		staticFileType.add(".xml");
		staticFileType.add(".css");
		staticFileType.add(".js");
		
		dynamicFileType.add(".jsp");
	}
	
	
	//（ 二进制流，不知道文件类型时，用这个）
	public static final String BINARY_STREAM = "application/octet-stream";
	
	//Server 根目录
	public static String webRoot = "src/main/java/pub/";
	//默认页面
	public static String defaultPage = "index.html";
	//Server 支持的压缩方式
	public static String supportedEncode = "gzip";
	//日志文件的路径
	public static String logFilePath = "src/main/java/log/";
	//最大线程数量
	public static int maxSessionThread = 10;
	
	//加载配置文件
	public static void loadServerSettings(String file) throws IOException {
		File f = new File(file);
		if(!f.exists())
			return;
		BufferedReader reader = new BufferedReader(new FileReader(f));
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
	
	public static String getContentType(String exn) {
		String val = contentType.get(exn);
		if(val != null)
			return val;
		return BINARY_STREAM;
	}
	
	public static boolean isStaticFileType(String prefix) {
		return staticFileType.contains(prefix);
	}
	
	public static boolean isDynamicFileType(String prefix) {
		return dynamicFileType.contains(prefix);
	}
	

}
