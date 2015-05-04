package com.privatewardrobe;

/**
 * @author Dean
 * 
 * the application controls the connection to the pushService the 
 * instance of its self the cache that will be use
 */
import java.io.Serializable;

import android.app.Application;
import android.text.TextUtils;

import com.privatewardrobe.common.Cache;
import com.privatewardrobe.common.DeviceIdFactory;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.service.Connection;
import com.privatewardrobe.service.PushService;

public class PWApplication extends Application {
	public static Connection connection;
	private static PWApplication instance;
	private static Cache cache;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		cache = new Cache(this);
	}

	public static PWApplication getInstance() {
		return instance;
	}

	public void putCache(String key, Serializable value) {
		cache.put(key, value);
	}

	public Object getCache(String key) {
		return cache.getAsObject(key);
	}

	public String getBaseUrl() {
		return PWConstant.BASE_URL;
	}

	/**
	 * @return wheather the user has login , called when activity begin
	 */
	public boolean isLogin() {
		if (!TextUtils.isEmpty(getToken()) && !TextUtils.isEmpty(getUserId())) {
			return true;
		}
		return false;
	}

	/**
	 * @param token
	 *            token to identify the current user
	 * @param userId
	 *            current user id to save the user information in preference
	 */
	public void Login(String token, String userId) {
		Utils.setSharedPreferences(PWConstant.PREF_MAIN_NAME, "access_token",
				token, getApplicationContext());
		Utils.setSharedPreferences(PWConstant.PREF_MAIN_NAME, "user_id",
				userId, getApplicationContext());
		startPushService();
	}

	/**
	 * clear the current user information in the preference
	 */
	public void Logout() {
		Utils.setSharedPreferences(PWConstant.PREF_MAIN_NAME, "access_token",
				"", getApplicationContext());
		Utils.setSharedPreferences(PWConstant.PREF_MAIN_NAME, "user_id", "",
				getApplicationContext());
		stopPushService();
	}

	/**
	 * @return current token
	 */
	public String getToken() {
		return Utils.getStringSharedPreferences(PWConstant.PREF_MAIN_NAME,
				"access_token", getApplicationContext());
	}

	public String getUserId() {
		return Utils.getStringSharedPreferences(PWConstant.PREF_MAIN_NAME,
				"user_id", getApplicationContext());
	}

	public String getDeviceId() {
		DeviceIdFactory factory = new DeviceIdFactory(getApplicationContext());
		return factory.getDeviceId();
	}

	private void startPushService() {
		PushService ps = new PushService();
		ps.connectAction(getApplicationContext());

	}

	private void stopPushService() {

	}
}
