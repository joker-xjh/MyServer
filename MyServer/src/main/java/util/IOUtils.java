package util;

import java.io.IOException;
import java.io.InputStream;

import constant.HTTPConstants;
import server.ConnectionStatus;

public class IOUtils {
	
	
	
	public static String readLine(InputStream in, ConnectionStatus status) throws IOException{
		StringBuilder line = new StringBuilder();
		boolean cr = false;
		int read = -1;
		while(( read = in.read() ) != -1) {
			
			if(read == '\r') {
				cr = true;
				continue;
			}
			else if(read == '\n' && !cr) {
				continue;
			}
			else if(read == '\n' && cr) {
				return line.toString();
			}
			else {
				line.append((char)read);
				status.setBytesReceived(status.getBytesReceived()+1);
				cr = false;
			}
			
		}
		
		throw new IOException(HTTPConstants.BAD_REQUEST);
	}
	

}
