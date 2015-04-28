package com.privatewardrobe.common;

import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

public class DeviceIdFactory {

	protected static final String PREFS_FILE = "DEVICE";
	protected static final String PREFS_DEVICE_ID = "device_id";

	protected volatile static String mDeviceId = null;

	public DeviceIdFactory(Context context) {

		if (mDeviceId == null) {
			synchronized (DeviceIdFactory.class) {
				if (mDeviceId == null) {
					final SharedPreferences prefs = context
							.getSharedPreferences(PREFS_FILE, 0);
					final String id = prefs.getString(PREFS_DEVICE_ID, null);

					if (id != null) {
						// Use the ids previously computed and stored in the
						// prefs file
						mDeviceId = id;

					} else {
						// Use the Android ID unless it's broken, in which case
						// fallback on deviceId,
						// unless it's not available, then fallback on a random
						// number which we store
						// to a prefs file
						final String deviceId = ((TelephonyManager) context
								.getSystemService(Context.TELEPHONY_SERVICE))
								.getDeviceId();
						mDeviceId = (deviceId != null && !"".equals(deviceId)) ? deviceId
								: UUID.randomUUID().toString();
						// Write the value out to the prefs file
						prefs.edit().putString(PREFS_DEVICE_ID, mDeviceId)
								.commit();
					}

				}
			}
		}

	}

	public String getDeviceId() {
		return mDeviceId;
	}
}