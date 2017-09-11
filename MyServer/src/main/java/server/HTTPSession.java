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
		
		try (InputStream in = client.getInputStream();OutputStream out = client.getOutputStream()){
			
			boolean keepAlive = ServerSettings.getKeepAlive();
			while(keepAlive) {
				HTTPObject request = HTTPUtils.receive(in, connectionStatus);
				String keepAliveFlag = (String) request.getHeader().getHeaders().get(HTTPConstants.CONNECTION.toLowerCase());
				if(keepAliveFlag != null && keepAliveFlag.equals(HTTPConstants.CONNECTION_CLOSE))
				{
					keepAlive = false;
				}
				HTTPObject response =HTTPUtils.getResponse(request);
				HTTPUtils.sendResponse(response, connectionStatus, out);
			}
			
		} catch (IOException e) {
			Debug.print("Finished rec proc send loop.", debugCode);
		}
		
		Debug.print("HTTP Session ended. Closing down connection and other stuff", debugCode);
		connectionStatus.setEndTime(System.currentTimeMillis());
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
