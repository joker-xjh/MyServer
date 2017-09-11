package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import constant.ServerSettings;

public class LogProcessor extends Thread{

	private static BlockingQueue<ConnectionStatus> queue;
	private BufferedWriter writer;
	
	public LogProcessor() throws IOException {
		queue = new LinkedBlockingQueue<>();
		writer = new BufferedWriter(new FileWriter(ServerSettings.logFilePath));
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
				String log = status.toString();
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
