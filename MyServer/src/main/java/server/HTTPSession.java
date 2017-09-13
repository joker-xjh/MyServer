package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import constant.HTTPConstants;
import constant.ServerSettings;
import util.Debug;
import util.HTTPUtils;

public class HTTPSession implements Runnable{
	private Socket client;
	private ConnectionStatus connectionStatus;
	private static int debugCode = 0x4;
	
	public HTTPSession(Socket client, ConnectionStatus connectionStatus) {
		this.client = client;
		this.connectionStatus = connectionStatus;
	}

	public void run() {
		InputStream in = null;
		OutputStream out = null;
		try{
			in = client.getInputStream();
			out = client.getOutputStream();
			boolean keepAlive = ServerSettings.running;
			while(keepAlive) {
				HTTPObject request = null;
				try {
					request = HTTPUtils.receive(in, connectionStatus);
				} catch (Exception e) {
					//解析Request出错,bad Request
					Debug.print("Bad Request", debugCode);
					HTTPObject response = HTTPResponse.BadRequestResponse(request, connectionStatus);
					HTTPUtils.sendResponse(response, connectionStatus, out);
					LogProcessor.addStatus(connectionStatus);
					continue;
				}
				String keepAliveFlag = (String) request.getHeader().getHeaders().get(HTTPConstants.CONNECTION.toLowerCase());
				if(keepAliveFlag != null && keepAliveFlag.equals(HTTPConstants.CONNECTION_CLOSE))
				{
					keepAlive = false;
				}
				HTTPObject response =HTTPUtils.getResponse(request, connectionStatus);
				HTTPUtils.sendResponse(response, connectionStatus, out);
				LogProcessor.addStatus(connectionStatus);
			}
			
		} catch (IOException e) {
			Debug.print("Internal Error", debugCode);
			// 发送 Internal Error Response
			if(out != null) {
				HTTPObject response = HTTPResponse.internalErrorResponse(connectionStatus);
				try {
					HTTPUtils.sendResponse(response, connectionStatus, out);
				} catch (IOException e1) {
					
				}
			}
		}
		
		
		Debug.print("HTTP Session ended. Closing down connection and other stuff", debugCode);
		close();
		
	}
	
	public void close() {
		try {
			client.close();
		} catch (IOException e) {
			Debug.print("Couldnt close Session. Still exiting", debugCode);
		}
	}

}
