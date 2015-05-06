package com.privatewardrobe.common;



import java.io.File;

import com.privatewardrobe.PWApplication;

import android.os.Environment;

public class FileManager {
	private static final String APP_NAME = "privateWardrobe";
	private static final String LOG = "log";
	private static final String AUDIO= "audio";
	private static final String ALARM= "alarm";
	private static final String TEMP = "temp";

	public static boolean isExternalStorageMounted() {
		boolean canRead = Environment.getExternalStorageDirectory().canRead();
		boolean onlyRead = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED_READ_ONLY);
		boolean unMounted = Environment.getExternalStorageState().equals(
				Environment.MEDIA_UNMOUNTED);

		return !(!canRead || onlyRead || unMounted);
	}

	public static String getLogDir() {
		if (!isExternalStorageMounted())
			return "";
		else {
			String path = getAppSdCardPath() + File.separator + LOG;
			if (!new File(path).exists()) {
				new File(path).mkdirs();
			}
			return path;
		}
	}
	
	public static String getAudioDir() {
		if (!isExternalStorageMounted())
			return "";
		else {
			String path = getAppSdCardPath() + File.separator + AUDIO;
			if (!new File(path).exists()) {
				new File(path).mkdirs();
			}
			return path;
		}
	}
	
	public static String getAlarmDir() {
		if (!isExternalStorageMounted())
			return "";
		else {
			String path = getAppSdCardPath() + File.separator + ALARM;
			if (!new File(path).exists()) {
				new File(path).mkdirs();
			}
			return path;
		}
	}
	
	public static String getTempDir() {
		if (!isExternalStorageMounted())
			return "";
		else {
			String path = getAppSdCardPath() + File.separator + TEMP;
			if (!new File(path).exists()) {
				new File(path).mkdirs();
			}
			return path;
		}
	}
	
	
	
	public static String getAppSdCardPath(){
		return getSdCardPath()+ File.separator+APP_NAME;
	}

	private static String getSdCardPath() {
		if (isExternalStorageMounted()) {
			File path =PWApplication.getInstance().getExternalCacheDir();
			if(path != null){
				return path.getAbsolutePath();
			}
		}
		return "";
	}

}
