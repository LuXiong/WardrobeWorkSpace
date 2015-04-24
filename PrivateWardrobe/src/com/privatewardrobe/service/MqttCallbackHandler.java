/*******************************************************************************
 * Copyright (c) 1999, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 */
package com.privatewardrobe.service;

import java.io.Serializable;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.service.Connection.ConnectionStatus;

/**
 * Handles call backs from the MQTT Client
 * 
 */
public class MqttCallbackHandler implements MqttCallback {

	/**
	 * {@link Context} for the application used to format and import external
	 * strings
	 **/
	private Context context;
	/**
	 * Client handle to reference the connection that this handler is attached
	 * to
	 **/
	private String clientHandle;

	/**
	 * Creates an <code>MqttCallbackHandler</code> object
	 * 
	 * @param context
	 *            The application's context
	 * @param clientHandle
	 *            The handle to a {@link Connection} object
	 */
	public MqttCallbackHandler(Context context, String clientHandle) {
		this.context = context;
		this.clientHandle = clientHandle;
	}

	/**
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
	 */
	@Override
	public void connectionLost(Throwable cause) {
		// cause.printStackTrace();
		if (cause != null) {
			PWApplication.connection.addAction("Connection Lost");
			PWApplication.connection
					.changeConnectionStatus(ConnectionStatus.DISCONNECTED);

			// format string to use a notification text
			Object[] args = new Object[2];
			args[0] = PWApplication.connection.getId();
			args[1] = PWApplication.connection.getHostName();

			String message = context.getString(R.string.connection_lost, args);

			// build intent
			Intent intent = new Intent();
			intent.setClassName(context,
					"org.eclipse.paho.android.service.sample.ConnectionDetails");
			intent.putExtra("handle", clientHandle);

			// notify the user
			// Notify.notifcation(context, message, intent,
			// R.string.notifyTitle_connectionLost);
		}
	}

	/**
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String,
	 *      org.eclipse.paho.client.mqttv3.MqttMessage)
	 */
	@Override
	public void messageArrived(String topic, MqttMessage message)
			throws Exception {
		// create arguments to format message arrived notifcation string

		String s = message.toString();
		String cacheMsg = (String) PWApplication.getInstance().getCache(
				"CurrentMsg");
		if (cacheMsg == null || !cacheMsg.equals(s)) {
			PWApplication.getInstance().putCache("CurrentMsg", s);
			Intent intent = new Intent();
			intent.setClassName(context,
					"org.eclipse.paho.android.service.sample.ConnectionDetails");
			intent.putExtra("handle", clientHandle);
			JSONObject obj = new JSONObject(new String(message.getPayload()));
			Notify.notifcation(context, (String) obj.get("content"), intent,
					R.string.notifyTitle);
		}

	}

	/**
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
	 */
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// Do nothing
	}

}
