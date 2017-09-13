package server;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionStatus {
	
	private static AtomicInteger counter = new AtomicInteger(0);
	
	private String logFileName;
	
	private String IP;
	private int port;
	private Date startTime;
	private Date endTime;
	private long bytesReceived;
	private long bytesSent;
	private String method;
	private String uri;
	private String version;
	private String code;
	
	public ConnectionStatus(String IP, int port) {
		this.IP = IP;
		this.port = port;
		this.startTime = new Date();
		logFileName = counter.getAndIncrement() +".txt";
	}
	
	public String getLogFileName() {
		return logFileName;
	}
	
	
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public long getBytesReceived() {
		return bytesReceived;
	}
	public void setBytesReceived(long bytesReceived) {
		this.bytesReceived = bytesReceived;
	}
	public long getBytesSent() {
		return bytesSent;
	}
	public void setBytesSent(long bytesSent) {
		this.bytesSent = bytesSent;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	@Override
	public String toString() {
		return "Log Message [IP=" + IP + ", port=" + port + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", bytesReceived=" + bytesReceived + ", bytesSent="
				+ bytesSent + ", method=" + method + ", uri=" + uri + ", version=" + version + ", code=" + code + "]";
	}
	
	
	
	

}
