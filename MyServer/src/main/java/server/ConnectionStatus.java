package server;

public class ConnectionStatus {
	
	private String IP;
	private int port;
	private long startTime;
	private long endTime;
	private int requestsProcessed;
	private long bytesReceived;
	private long bytesSent;
	
	public ConnectionStatus(String IP, int port, long startTime) {
		this.IP = IP;
		this.port = port;
		this.startTime = startTime;
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
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public int getRequestsProcessed() {
		return requestsProcessed;
	}
	public void setRequestsProcessed(int requestsProcessed) {
		this.requestsProcessed = requestsProcessed;
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
	
	
	@Override
	public String toString() {
		return "ConnectionStatus [IP=" + IP + ", port=" + port + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", requestsProcessed=" + requestsProcessed + ", bytesReceived=" + bytesReceived + ", bytesSent="
				+ bytesSent + "]";
	}
	

}
