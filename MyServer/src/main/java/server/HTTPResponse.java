package server;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import constant.HTTPConstants;
import constant.ServerSettings;
import util.FileUtil;
import util.HTTPUtils;

public class HTTPResponse {
	
	
	//404 not found Response
	public static HTTPObject notFoundResponse(HTTPObject request, ConnectionStatus status) {
		HTTPObject response = new HTTPObject();
		response.getHeader().setVersion(HTTPConstants.VERSION);
		response.getHeader().setCode(HTTPConstants.NOT_FOUND_CODE);
		response.getHeader().setReasonPhrase(HTTPConstants.NOT_FOUND_PHRASE);
		status.setCode(HTTPConstants.NOT_FOUND_CODE);
		status.setEndTime(new Date());
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
			internalErrorResponse(status);
		}
		
		response.getHeader().getHeaders().put(HTTPConstants.CONTENTTYPE, HTTPConstants.TEXT_HTML);
		response.getHeader().getHeaders().put(HTTPConstants.CONTENT_LENGTH, String.valueOf(response.getBody().length));
		return response;
	}
	
	
	//请求的是一个目录，重定向到目录的默认页面(index.html)
	public static HTTPObject directoryRedirectResponse(HTTPObject request, ConnectionStatus status) {
		HTTPObject response = new HTTPObject();
		String redirect = HTTPUtils.getRedirectUri(request.getHeader().getUri());
		response.getHeader().setVersion(HTTPConstants.VERSION);
		response.getHeader().setCode(HTTPConstants.PAGE_MOVED_CODE);
		response.getHeader().setReasonPhrase(HTTPConstants.PAGE_MOVED_PHRASE);
		
		status.setCode(HTTPConstants.PAGE_MOVED_CODE);
		status.setEndTime(new Date());
		
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
	
	//返回 GET请求相应的 response,如果请求的文件是一个目录，就返回一个重定向Response
	public static HTTPObject GETResponse(HTTPObject request, String file, ConnectionStatus status) {
		File f = new File(file);
		//如果file不存在,就返回 notFound response
		if(!f.exists()) {
			return notFoundResponse(request, status);
		}
		//如果file是一个目录，就重定向到这个目录的默认页面(index.html)
		if(FileCache.isDirectory(file)) {
			return HTTPResponse.directoryRedirectResponse(request, status);
		}
		HTTPObject response = new HTTPObject();
		String contentType = HTTPUtils.getContentType(file);
		response.getHeader().setVersion(HTTPConstants.VERSION);
		response.getHeader().setCode(HTTPConstants.OK_CODE);
		response.getHeader().setReasonPhrase(HTTPConstants.OK_PHRASE);
		response.getHeader().getHeaders().put(HTTPConstants.CONTENTTYPE, contentType);
		status.setCode(HTTPConstants.OK_CODE);
		status.setEndTime(new Date());
		try {
			compressOrNot(response, request, file);
		} catch (IOException e) {
			e.printStackTrace();
			internalErrorResponse(status);
		}
		response.getHeader().getHeaders().put(HTTPConstants.CONTENT_LENGTH, String.valueOf(response.getBody().length));
		return response;
	}
	
	
	
	//500 internal error response,断开与客户端的连接
	public static HTTPObject internalErrorResponse(ConnectionStatus status) {
		HTTPObject response = new HTTPObject();
		response.getHeader().setVersion(HTTPConstants.VERSION);
		response.getHeader().setCode(HTTPConstants.INTERNAL_ERROR_CODE);
		response.getHeader().setReasonPhrase(HTTPConstants.INTERNAL_ERROR_PHRASE);
		response.getHeader().getHeaders().put(HTTPConstants.CONNECTION, HTTPConstants.CONNECTION_CLOSE);
		response.getHeader().getHeaders().put(HTTPConstants.CONTENTTYPE, HTTPConstants.TEXT_HTML);
		status.setCode(HTTPConstants.INTERNAL_ERROR_CODE);
		status.setEndTime(new Date());
		byte[] data = FileCache.getPermanentFileCache(ServerSettings.internalErrorFilePath);
		response.getHeader().getHeaders().put(HTTPConstants.CONTENT_LENGTH, String.valueOf(data.length));
		response.setBody(data);
		return response;
	}
	
	
	// 400 bad request Response
	public static HTTPObject BadRequestResponse(HTTPObject request, ConnectionStatus status) {
		HTTPObject response = new HTTPObject();
		response.getHeader().setVersion(HTTPConstants.VERSION);
		response.getHeader().setCode(HTTPConstants.BAD_REQUEST_CODE);
		response.getHeader().setReasonPhrase(HTTPConstants.BAD_REQUEST);
		response.getHeader().getHeaders().put(HTTPConstants.CONTENTTYPE, HTTPConstants.TEXT_HTML+HTTPConstants.CONTENTENCODING);
		
		status.setCode(HTTPConstants.BAD_REQUEST_CODE);
		status.setEndTime(new Date());
		
		byte[] data = FileCache.getPermanentFileCache(ServerSettings.badRequestFilePath);
		try {
			compressOrNot(response, request, ServerSettings.badRequestFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			internalErrorResponse(status);
		}
		response.setBody(data);
		return response;
	}
	
	//请求动态页面的Response,目前还不会实现。。。。。
	public static HTTPObject DynamicResponse(HTTPObject request, ConnectionStatus status) {
		HTTPObject response = new HTTPObject();
		
		
		
		return response;
	}
	
	
	
	
	//根据request Header 来判断是否压缩 response body
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
			//压缩文件
			response.setBody(FileUtil.comoressGZIP(data));
			//设置Content-Encoding,不设置的话会乱码
			response.getHeader().getHeaders().put(HTTPConstants.CONTENTENCODING, ServerSettings.supportedEncode);
		}
		else {
			response.setBody(data);
		}
		
	}
	
	
	
	
	
	
	

}
