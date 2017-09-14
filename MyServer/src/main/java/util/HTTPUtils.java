package util;

import java.io.BufferedOutputStream;


import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;

import config.UserSettings;
import constant.HTTPConstants;
import constant.ServerSettings;
import server.ConnectionStatus;
import server.HTTPHeader;
import server.HTTPObject;
import server.HTTPResponse;

public class HTTPUtils {
	
	private static int debugCode = 0x3;
	
	
	// 从流中解析 Request对象,并记录相应的状态信息
	public static HTTPObject receive(InputStream in, ConnectionStatus connectionStatus) throws IOException{
		String header = new String();
		String line = new String();
		Debug.print("Starting to read Header", debugCode);
		while(true)
		{
			line = IOUtils.readLine(in, connectionStatus);
			if(line.length() == 0)
				break;
			header += line + HTTPConstants.CF_CR;
		}
		Debug.print("End of reading Header", debugCode);
		
		if(header.length() == 0)
			return null;
		
		Debug.print("Header is :"+header, debugCode);
		Debug.print("Starting to parse Header", debugCode);
		HTTPHeader Header = new HTTPHeader(header);
		Debug.print("End of parsing Header", debugCode);
		connectionStatus.setStartTime(new Date());
		connectionStatus.setMethod(Header.getMethod());
		connectionStatus.setUri(Header.getUri());
		connectionStatus.setVersion(Header.getVersion());
		String bodyLength = Header.getHeaders().get(HTTPConstants.CONTENT_LENGTH.toLowerCase());
		if( bodyLength == null
			|| bodyLength.equals("0")) {
			return new HTTPObject(Header, null);
		}
		Debug.print("Starting to read body", debugCode);
		byte[] body = new byte[Integer.parseInt(bodyLength.toString())];
		in.read(body);
		Debug.print("End reading body", debugCode); 
		return new HTTPObject(Header, body);
	}
	
	
	public static HTTPObject getResponse(HTTPObject request, ConnectionStatus status) {
		String file =ServerSettings.getWebRoot() + parseRequestURI(request.getHeader().getUri());
		if(isValidateGetRequest(request)) {
			return HTTPResponse.GETResponse(request, file, status);
		}
		else 
			return HTTPResponse.notFoundResponse(request, status);
	}
	
	
	
	
	public static void sendResponse(HTTPObject response, ConnectionStatus status, OutputStream out) throws IOException {
		String send = new String();
		out = new BufferedOutputStream(out);
		send = response.getHeader().getVersion() + " " + response.getHeader().getCode() + " " + response.getHeader().getReasonPhrase() + HTTPConstants.CF_CR;
		Map<String, String> header = response.getHeader().getHeaders();
		for(String item : header.keySet()) {
			send += item +": " + header.get(item) + HTTPConstants.CF_CR;
		}
		send += HTTPConstants.CF_CR;
		byte[] data = send.getBytes("UTF-8");
		out.write(data);
		status.setBytesSent(data.length + status.getBytesSent());
		data = response.getBody();
		out.write(data);
		status.setBytesSent(status.getBytesSent() + data.length);
		out.flush();
	}
	
	
	
	public static String parseRequestURI(String uri) {
		int index = uri.indexOf('?');
		if(index != -1)
			uri = uri.substring(index);
		StringTokenizer tokenizer = new StringTokenizer(uri, "/");
		if(tokenizer.countTokens() == 0) {
			return  UserSettings.getApplication() + ServerSettings.defaultPage; 
		}
		String path = new String();
		while(tokenizer.hasMoreTokens()) {
			path += "\\" + tokenizer.nextToken();
		}
		
		return path;
	}
	
	
	
	public static boolean checkKeepAlive(HTTPObject request) {
		if(request.getHeader().getHeaders().get(HTTPConstants.CONNECTION) != null &&
				(request.getHeader().getHeaders().get(HTTPConstants.CONNECTION.toLowerCase()).equals(HTTPConstants.CONNECTION_CLOSE))){
			return false;
		}
		return true;
	}
	
	
	
	public static String getRedirectUri(String uri) {
		int last = uri.length()-1;
		char c = uri.charAt(last);
		if(c == '/') {
			return uri + ServerSettings.defaultPage;
		}
		else {
			return uri + "/" + ServerSettings.defaultPage;
		}
	}
	
	private static boolean isValidateGetRequest(HTTPObject request) {
		if(!request.getHeader().getMethod().equals(HTTPConstants.GET))
			return false;
		String host = request.getHeader().getHeaders().get(HTTPConstants.HOST);
		if( host == null || host.length() == 0)
			return false;
		return true;
	}
	
	public static String getContentType(String file) {
		String exn = file.substring(file.lastIndexOf('.'));
		return ServerSettings.getContentType(exn);
	}
	
	
	

}
