package com.privatewardrobe.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.privatewardrobe.R;
import com.privatewardrobe.photo.PhotoHelper;

public class Utils {
	static public String getCurDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";

	}

	public static String getRealPathFromURI(Uri contentUri, Context context) {
		String res = null;
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(contentUri, proj,
				null, null, null);
		if (cursor.moveToFirst()) {
			;
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			res = cursor.getString(column_index);
		}
		cursor.close();
		return res;
	}
	
	public static AlertDialog buildLoadingDialog(Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(LayoutInflater.from(context).inflate(R.layout.dialog_loading, null));
		return builder.create();
	}

	public static void buildPhotoHelperListDialog(Context context,
			final PhotoHelper helper) {
		String[] choices = { "拍照", "相册" };
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setItems(choices, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {
					helper.startCapture();
				}
				if (which == 1) {
					helper.startAlbum();
				}
			}
		});
		builder.create().show();
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

	public static DisplayImageOptions buildNoneDisplayImageOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.cacheInMemory(true).cacheOnDisc(true).build();
		return options;
	}
}
