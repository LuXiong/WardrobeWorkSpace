package com.privatewardrobe.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

public class Utils {
	static public String getCurDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static String getStringSharedPreferences(String preferenceName,
			String key, Context mContext) {
		int mode = Context.MODE_PRIVATE;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mode = mode | Context.MODE_MULTI_PROCESS;
		}
		SharedPreferences mPreferences = mContext.getSharedPreferences(
				preferenceName, mode);
		String tempValue = mPreferences.getString(key, "");
		return tempValue;
	}

	public static int getIntSharedPreferences(String preferenceName,
			String key, Context mContext) {

		return getIntSharedPreferences(preferenceName, key, mContext, 0);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static int getIntSharedPreferences(String preferenceName,
			String key, Context mContext, int defaultVal) {
		int mode = Context.MODE_PRIVATE;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mode = mode | Context.MODE_MULTI_PROCESS;
		}
		SharedPreferences mPreferences = mContext.getSharedPreferences(
				preferenceName, mode);
		int tempValue = mPreferences.getInt(key, defaultVal);
		return tempValue;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static boolean getBooleanSharedPreferences(String preferenceName,
			String key, Context mContext, boolean defaultVal) {
		int mode = Context.MODE_PRIVATE;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mode = mode | Context.MODE_MULTI_PROCESS;
		}
		SharedPreferences mPreferences = mContext.getSharedPreferences(
				preferenceName, mode);
		boolean tempValue = mPreferences.getBoolean(key, defaultVal);
		return tempValue;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static long getLongSharedPreferences(String preferenceName,
			String key, Context mContext, long defaultVal) {
		int mode = Context.MODE_PRIVATE;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mode = mode | Context.MODE_MULTI_PROCESS;
		}
		SharedPreferences mPreferences = mContext.getSharedPreferences(
				preferenceName, mode);
		long tempValue = mPreferences.getLong(key, defaultVal);
		return tempValue;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void setSharedPreferences(String preferenceName, String key,
			String value, Context mContext) {
		int mode = Context.MODE_PRIVATE;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mode = mode | Context.MODE_MULTI_PROCESS;
		}
		SharedPreferences mPreferences = mContext.getSharedPreferences(
				preferenceName, mode);
		Editor editor = mPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void setSharedPreferences(String preferenceName, String key,
			int value, Context mContext) {
		int mode = Context.MODE_PRIVATE;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mode = mode | Context.MODE_MULTI_PROCESS;
		}
		SharedPreferences mPreferences = mContext.getSharedPreferences(
				preferenceName, mode);
		Editor editor = mPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void setSharedPreferences(String preferenceName, String key,
			boolean value, Context mContext) {
		int mode = Context.MODE_PRIVATE;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mode = mode | Context.MODE_MULTI_PROCESS;
		}
		SharedPreferences mPreferences = mContext.getSharedPreferences(
				preferenceName, mode);
		Editor editor = mPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void setSharedPreferences(String preferenceName, String key,
			long value, Context mContext) {
		int mode = Context.MODE_PRIVATE;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mode = mode | Context.MODE_MULTI_PROCESS;
		}
		SharedPreferences mPreferences = mContext.getSharedPreferences(
				preferenceName, mode);
		Editor editor = mPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void removeSharedPreferences(String preferenceName,
			String key, Context mContext) {
		int mode = Context.MODE_PRIVATE;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mode = mode | Context.MODE_MULTI_PROCESS;
		}
		SharedPreferences mPreferences = mContext.getSharedPreferences(
				preferenceName, mode);
		Editor editor = mPreferences.edit();
		editor.remove(key);
		editor.commit();
	}
}
