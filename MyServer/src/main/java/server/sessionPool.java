package server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import constant.ServerSettings;

public class sessionPool {
	
	private static ExecutorService sessionPool = Executors.newFixedThreadPool(ServerSettings.maxSessionThread);
	
	public static void executeSession(HTTPSession session) {
		sessionPool.submit(session);
	}

}
