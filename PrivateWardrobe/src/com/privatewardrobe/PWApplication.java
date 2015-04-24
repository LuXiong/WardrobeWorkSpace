package com.privatewardrobe;

import java.io.Serializable;

import android.app.Application;

import com.privatewardrobe.common.Cache;
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
		startService();
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

	private void startService() {
		PushService ps = new PushService();
		connection = ps.connectAction(getApplicationContext());

	}
}
