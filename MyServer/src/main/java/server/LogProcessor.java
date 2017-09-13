package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import constant.HTTPConstants;
import constant.ServerSettings;

public class LogProcessor extends Thread{

	private static BlockingQueue<ConnectionStatus> queue;
	private BufferedWriter writer;
	
	public LogProcessor() throws IOException {
		queue = new LinkedBlockingQueue<>();
		
	}
	
	public static void addStatus(ConnectionStatus status) {
		try {
			queue.put(status);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(ServerSettings.running) {
			try {
				ConnectionStatus status = queue.take();
				String file = ServerSettings.logFilePath + status.getLogFileName();
				writer = new BufferedWriter(new FileWriter(file,true));
				String log = status.toString()+HTTPConstants.CF_CR;
				writer.write(log);
				writer.flush();
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
}
