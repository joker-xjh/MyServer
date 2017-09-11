package server;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import constant.ServerSettings;
import util.FileUtil;

public class FileCache {
	
	public static ConcurrentHashMap<String, byte[]> fileDataCache = new ConcurrentHashMap<String, byte[]>();
	public static ConcurrentHashMap<String, Long> fileDataCacheTimeStamp = new ConcurrentHashMap<String, Long>();
	
	public static ConcurrentHashMap<String, byte[]> permanentFileCache = new ConcurrentHashMap<String, byte[]>();
	
	public static ConcurrentHashMap<String, Boolean> isDirectoryCache = new ConcurrentHashMap<String, Boolean>();
	public static ConcurrentHashMap<String, Long> isDirectoryCacheTimeStamp = new ConcurrentHashMap<String, Long>();
	
	public static ConcurrentHashMap<String, Boolean> fileIsExistCache = new ConcurrentHashMap<String, Boolean>();
	public static ConcurrentHashMap<String, Long> fileIsExistCacheTimeStamp = new ConcurrentHashMap<String, Long>();
	
	
	public static void initPermanentFileCache() throws IOException {
		permanentFileCache.put(ServerSettings.redirFilePath, FileUtil.readBytes(ServerSettings.redirFilePath));
		permanentFileCache.put(ServerSettings.internalErrorFilePath, FileUtil.readBytes(ServerSettings.internalErrorFilePath));
		permanentFileCache.put(ServerSettings.badRequestFilePath, FileUtil.readBytes(ServerSettings.badRequestFilePath));
		permanentFileCache.put(ServerSettings.notFoundFilePath, FileUtil.readBytes(ServerSettings.notFoundFilePath));
	}
	
	public static byte[] getPermanentFileCache(String file) {
		return permanentFileCache.get(file);
	}
	
	public static byte[] getFile(String file) throws IOException {
		if(ServerSettings.fileCacheEnabled) {
			if(!fileDataCache.contains(file) 
				|| fileDataCacheTimeStamp.getOrDefault(file, (long) 0) + ServerSettings.fileCacheTimeout < System.currentTimeMillis()) {
				
				if(ServerSettings.fileCacheSize < fileDataCache.size()) {
					fileDataCache.clear();
					fileDataCacheTimeStamp.clear();
				}
				
				byte[] cache = FileUtil.readBytes(file);
				fileDataCache.put(file, cache);
				fileDataCacheTimeStamp.put(file, System.currentTimeMillis());
			}
			return fileDataCache.get(file);
		}
		
		return FileUtil.readBytes(file);
	}
	
	public static boolean checkFileExists(String file) {
		if(ServerSettings.fileCacheEnabled) {
			if(!fileIsExistCache.containsKey(file) || fileIsExistCacheTimeStamp.getOrDefault(file, (long)0) + ServerSettings.fileCacheTimeout < System.currentTimeMillis()) {
				
				if(ServerSettings.fileCacheSize <= fileIsExistCache.size()) {
					fileIsExistCache.clear();
					fileIsExistCacheTimeStamp.clear();
				}
				
				boolean exists = FileUtil.checkFileExists(file);
				long curTime = System.currentTimeMillis();
				fileIsExistCache.put(file, exists);
				fileIsExistCacheTimeStamp.put(file, curTime);
			}
			return fileIsExistCache.get(file);
		}
		
		
		return FileUtil.checkFileExists(file);
	}
	
	
	public static boolean isDirectory(String file) {
		if(ServerSettings.fileCacheEnabled) {
			if(!isDirectoryCache.containsKey(file) || 
				isDirectoryCacheTimeStamp.getOrDefault(file, (long)0)+ServerSettings.fileCacheTimeout < System.currentTimeMillis() ) {
				
				if(ServerSettings.fileCacheSize <= isDirectoryCache.size()) {
					isDirectoryCache.clear();
					isDirectoryCacheTimeStamp.clear();
				}
				
				boolean isDirectory = FileUtil.isDirectory(file);
				long curTime = System.currentTimeMillis();
				isDirectoryCache.put(file, isDirectory);
				isDirectoryCacheTimeStamp.putIfAbsent(file, curTime);
			}
			
			return isDirectoryCache.get(file);
		}
		
		return FileUtil.isDirectory(file);
	}
	
	
	
	
	
	

}
