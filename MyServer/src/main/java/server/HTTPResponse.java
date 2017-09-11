package server;

import java.io.IOException;

import constant.HTTPConstants;
import constant.ServerSettings;
import util.FileUtil;
import util.HTTPUtils;

public class HTTPResponse {
	
	public static HTTPObject notFoundResponse(HTTPObject request) {
		HTTPObject response = new HTTPObject();
		response.getHeader().setVersion(HTTPConstants.VERSION);
		response.getHeader().setCode(HTTPConstants.NOT_FOUND_CODE);
		response.getHeader().setReasonPhrase(HTTPConstants.NOT_FOUND_PHRASE);
		if(HTTPUtils.checkKeepAlive(request)) {
			response.getHeader().getHeaders().put(HTTPConstants.CONNECTION, HTTPConstants.KEEP_ALIVE);
		}
		else {
			response.getHeader().getHeaders().put(HTTPConstants.CONNECTION, HTTPConstants.CONNECTION_CLOSE);
		}
		try {
			compressOrNot(response, request, ServerSettings.notFoundFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			internalErrorResponse(request);
		}
		
		response.getHeader().getHeaders().put(HTTPConstants.CONTENTTYPE, HTTPConstants.TEXT_HTML);
		response.getHeader().getHeaders().put(HTTPConstants.CONTENT_LENGTH, String.valueOf(response.getBody().length));
		return response;
	}
	
	public static HTTPObject directoryRedirectResponse(HTTPObject request) {
		HTTPObject response = new HTTPObject();
		String redirect = HTTPUtils.getRedirectUri(request.getHeader().getUri());
		response.getHeader().setVersion(HTTPConstants.VERSION);
		response.getHeader().setCode(HTTPConstants.PAGE_MOVED_CODE);
		response.getHeader().setReasonPhrase(HTTPConstants.PAGE_MOVED_PHRASE);
		if(HTTPUtils.checkKeepAlive(request)) {
			response.getHeader().getHeaders().put(HTTPConstants.CONNECTION, HTTPConstants.KEEP_ALIVE);
		}
		else {
			response.getHeader().getHeaders().put(HTTPConstants.CONNECTION, HTTPConstants.CONNECTION_CLOSE);
		}
		response.getHeader().getHeaders().put(HTTPConstants.LOCATION, redirect);
		response.getHeader().getHeaders().put(HTTPConstants.CONTENTTYPE, HTTPConstants.TEXT_HTML);
		response.setBody(FileCache.getPermanentFileCache(ServerSettings.redirFilePath));
		response.getHeader().getHeaders().put(HTTPConstants.CONTENT_LENGTH, String.valueOf(response.getBody().length));
		return response;
	}
	
	public static HTTPObject GETResponse(HTTPObject request, String file) {
		HTTPObject response = new HTTPObject();
		String contentType = FileUtil.getFileExn(file);
		response.getHeader().setVersion(HTTPConstants.VERSION);
		response.getHeader().setCode(HTTPConstants.OK_CODE);
		response.getHeader().setReasonPhrase(HTTPConstants.OK_PHRASE);
		response.getHeader().getHeaders().put(HTTPConstants.CONTENTTYPE, contentType);
		try {
			compressOrNot(response, request, file);
		} catch (IOException e) {
			e.printStackTrace();
			internalErrorResponse(request);
		}
		response.getHeader().getHeaders().put(HTTPConstants.CONTENT_LENGTH, String.valueOf(response.getBody().length));
		return response;
	}
	
	
	public static HTTPObject internalErrorResponse(HTTPObject request) {
		HTTPObject response = new HTTPObject();
		response.getHeader().setVersion(HTTPConstants.VERSION);
		response.getHeader().setCode(HTTPConstants.INTERNAL_ERROR_CODE);
		response.getHeader().setReasonPhrase(HTTPConstants.INTERNAL_ERROR_PHRASE);
		response.getHeader().getHeaders().put(HTTPConstants.CONNECTION, HTTPConstants.CONNECTION_CLOSE);
		response.getHeader().getHeaders().put(HTTPConstants.CONTENTTYPE, HTTPConstants.TEXT_HTML);
		byte[] data = FileCache.getPermanentFileCache(ServerSettings.internalErrorFilePath);
		response.getHeader().getHeaders().put(HTTPConstants.CONTENT_LENGTH, String.valueOf(data.length));
		response.setBody(data);
		return response;
	}
	
	
	
	
	
	
	
	public static void compressOrNot (HTTPObject response, HTTPObject request, String file)throws IOException {
		boolean encodeFlag = false;
		String encode = request.getHeader().getHeaders().get(HTTPConstants.ACCEPT_ENCODING);
		String[] array = encode.split(",");
		for(String temp : array) {
			if(temp.toLowerCase().trim().equals(ServerSettings.supportedEncode)) {
				encodeFlag = true;
				break;
			}
		}
	
		byte[] data = FileCache.getFile(file);
		if(encodeFlag) {
			response.setBody(FileUtil.comoressGZIP(data));
		}
		else {
			response.setBody(data);
		}
		
	}
	
	
	
	
	
	
	

}
