package util;

import java.io.BufferedInputStream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class FileUtil {
	
	public static byte[] readBytes(String file) throws IOException{
		File f = new File(file);
		BufferedInputStream reader = new BufferedInputStream(new FileInputStream(f));
		byte[] buff = new byte[(int)f.length()];
		reader.read(buff);
		reader.close();
		return buff;
	}
	
	public static boolean checkFileExists(String file) {
		File f = new File(file);
		return f.exists();
	}
	
	public static boolean isDirectory(String file) {
		File f = new File(file);
		return f.isDirectory();
	}
	
	public static byte[] comoressGZIP(byte[] file) throws IOException{
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(bao);
		gzip.write(file);
		gzip.flush();
		bao.flush();
		gzip.close();
		bao.close();
		return bao.toByteArray();
	}

}
