package server;

import java.io.IOException;
import java.net.ServerSocket;

import util.Debug;

public class ConnectionListener {
	
	public static int debugCode = 0x02;
	private ServerSocket serverSocket;
	private boolean running;
	
	public ConnectionListener(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		running = true;
	}
	
	public void beginListening() {
		while(running) {
			Debug.print("Trying to accept incoming TCP connection", debugCode);	
		}
	}
	
	public void closeListening() {
		running = false;
		try {
			serverSocket.close();
			Debug.print("Server closed successfully", debugCode);
		} catch (IOException e) {
			e.printStackTrace();
			Debug.print("Something bad happened when closing Server", debugCode);
		}
	}
	

}
