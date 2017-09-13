package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import constant.ServerSettings;
import util.Debug;

public class ConnectionListener {
	
	public static int debugCode = 0x02;
	private ServerSocket serverSocket;
	private boolean running;
	
	public ConnectionListener(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		running = ServerSettings.running;
	}
	
	public void beginListening() {
		while(running) {
			Debug.print("Trying to accept incoming TCP connection", debugCode);
			HTTPSession session = null;
			try {
				Socket client = serverSocket.accept();
				ConnectionStatus status = new ConnectionStatus(client.getInetAddress().toString(), client.getPort());
				session = new HTTPSession(client, status);
			} catch (IOException e) {
				e.printStackTrace();
				Debug.print("Exception occurred while listening for connection", debugCode);
				continue;
			}
			sessionPool.executeSession(session);
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
