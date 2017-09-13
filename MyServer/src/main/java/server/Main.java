package server;

import java.io.IOException;

import constant.ServerSettings;
import util.Debug;

public class Main {

	private static int debugCode = 0x111;
	
	public static void main(String[] args) {

		try {
			ServerSettings.loadServerSettings("src/main/java/resource/server.txt");
		} catch (IOException e) {
			e.printStackTrace();
			Debug.print("Server settings not loaded. Defaulting", debugCode);
		}
		
		try {
			FileCache.initPermanentFileCache();
		} catch (IOException e1) {
			//e1.printStackTrace();
			Debug.print("Could not initiate permanent cache. Disk error?", debugCode);
		}
		
		LogProcessor logProcessor = null;
		try {
			logProcessor = new LogProcessor();
		} catch (IOException e) {
			e.printStackTrace();
			Debug.print("Unable to start Log Thread", debugCode);
		}
		logProcessor.start();
		ConnectionListener listener = null;
		try {
			listener = new ConnectionListener(ServerSettings.portNum);
		} catch (IOException e) {
			e.printStackTrace();
			Debug.print("Error starting Server", debugCode);
		}
		
		Debug.print("Listening on port " + ServerSettings.portNum, debugCode);
		
		listener.beginListening();
		
		
	}

}
