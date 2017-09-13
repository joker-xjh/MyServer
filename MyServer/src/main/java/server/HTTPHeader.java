package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import constant.HTTPConstants;

public class HTTPHeader {
	
	private Map<String, String> headers;
	private String version;
	private String method;
	private String uri;
	private String code;
	private String reasonPhrase;
	
	
	public HTTPHeader() {
		headers = new HashMap<>();
		version = new String();
		method = new String();
		uri = new String();
		code = new String();
		reasonPhrase = new String();
	}
	
	public HTTPHeader(String header) throws IOException{
		headers = new HashMap<>();
		version = new String();
		method = new String();
		uri = new String();
		String [] array = header.split(HTTPConstants.CF_CR);
		String headLine = array[0];
		String[] headSplit = headLine.split(HTTPConstants.SPACE);
		if(headSplit.length != 3)
			throw new IOException(HTTPConstants.BAD_REQUEST);
		method = headSplit[0].trim();
		uri = headSplit[1].trim();
		version = headSplit[2].trim();
		
		for(int i=1; i<array.length; i++) {
			String[] temp = array[i].split(HTTPConstants.HEADER_ATTRIBUTE_SPLIT);
			if(temp.length != 2)
				continue;
			headers.put(temp[0].trim().toLowerCase(), temp[1].trim());
		}
		
		
	}
	
	
	
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getHeaders(){
		return headers;
	}

}
