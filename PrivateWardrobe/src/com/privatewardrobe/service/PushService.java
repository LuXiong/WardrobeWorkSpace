package com.privatewardrobe.service;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import android.content.Context;
import android.util.Log;

import com.privatewardrobe.base.PWApplication;
import com.privatewardrobe.base.PWConstant;
import com.privatewardrobe.service.sample.ActionListener;
import com.privatewardrobe.service.sample.ActionListener.Action;
import com.privatewardrobe.service.sample.Connection;
import com.privatewardrobe.service.sample.Connection.ConnectionStatus;
import com.privatewardrobe.service.sample.MqttCallbackHandler;
import com.privatewardrobe.service.sample.MqttTraceCallback;

public class PushService {

	public void connectAction(Context context) {
		MqttConnectOptions conOpt = new MqttConnectOptions();
		String clientId = "FirstUser";
		String uri = PWConstant.BASEURL;
		MqttAndroidClient client = new MqttAndroidClient(context, uri, clientId);
		String clientHandle = uri + clientId;
		PWApplication.connection = new Connection(clientHandle, clientId,
				PWConstant.BASESEVER, PWConstant.BASEPORT, context, client,
				false);

		String[] actionArgs = new String[1];
		actionArgs[0] = clientId;
		PWApplication.connection
				.changeConnectionStatus(ConnectionStatus.CONNECTING);
		conOpt.setCleanSession(true);
		final ActionListener callback = new ActionListener(context,
				ActionListener.Action.CONNECT, clientHandle, actionArgs);
		client.setCallback(new MqttCallbackHandler(context, clientHandle));

		client.setTraceCallback(new MqttTraceCallback());
		PWApplication.connection.getClient().registerResources(context);
		PWApplication.connection.getClient().setCallback(
				new MqttCallbackHandler(context, PWApplication.connection
						.getClient().getServerURI()
						+ PWApplication.connection.getClient().getClientId()));
		PWApplication.connection.addConnectionOptions(conOpt);
		try {
			PWApplication.connection.getClient()
					.connect(conOpt, null, callback);


		} catch (MqttException e) {
			Log.e(this.getClass().getCanonicalName(), "MqttException Occured",
					e);
		}

	}
}
