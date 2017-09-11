package server;

public class HTTPObject {
	
	private HTTPHeader header;
	private byte[] body;
	
	public HTTPObject() {
		header = new HTTPHeader();
	}
	
	public HTTPObject(HTTPHeader header, byte[] body) {
		this.body = body;
		this.header = header;
	}
	
	
	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public void setHeader(HTTPHeader header) {
		this.header = header;
	}

	public HTTPHeader getHeader() {
		return header;
	}

}
