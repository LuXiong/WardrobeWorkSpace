package com.privatewardrobe.service;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import android.content.Context;
import android.util.Log;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.PWConstant;
import com.privatewardrobe.service.ActionListener.Action;
import com.privatewardrobe.service.ActionListener.OnConnectedListener;
import com.privatewardrobe.service.Connection.ConnectionStatus;

public class PushService {

	private static ActionListener connectCallback;
	private final static String clientId = "45i";
	private final static String uri = PWConstant.BASE_PUSH_URL;
	private final static String clientHandle = uri + clientId;

	public void connectAction(final Context context) {
		createConnection(context);
		initCallback(context);
		connect(context, connectCallback);
	}

	public void disConnectAction() {
		if (PWApplication.connection != null) {
			try {
				PWApplication.connection.getClient().disconnect();
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void initCallback(final Context context) {
		PWApplication.connection
				.changeConnectionStatus(ConnectionStatus.CONNECTING);
		String[] actionArgs = new String[] { clientId };
		connectCallback = new ActionListener(context,
				new OnConnectedListener() {

					@Override
					public void onConnected() {

						subscribe(context);
					}
				}, ActionListener.Action.CONNECT, clientHandle, actionArgs);
	}

	private void createConnection(final Context context) {
		MqttAndroidClient client = new MqttAndroidClient(context, uri, clientId);
		client.setCallback(new MqttCallbackHandler(context, clientHandle));
		client.setTraceCallback(new MqttTraceCallback());
		PWApplication.connection = new Connection(clientHandle, clientId,
				PWConstant.BASE_PUSH_SEVER, PWConstant.BASE_PUSH_PORT, context,
				client, false);

	}

	private void subscribe(Context context) {
		String[] topics = new String[1];
		String topic = "PW";
		topics[0] = topic;
		try {
			PWApplication.connection.getClient().subscribe(
					topic,
					0,
					null,
					new ActionListener(context, Action.SUBSCRIBE, clientHandle,
							topics));
		} catch (MqttException e) {
			e.printStackTrace();
		}

	}

	private void connect(Context context, ActionListener callback) {
		PWApplication.connection.getClient().registerResources(context);
		PWApplication.connection.getClient().setCallback(
				new MqttCallbackHandler(context, PWApplication.connection
						.getClient().getServerURI()
						+ PWApplication.connection.getClient().getClientId()));
		MqttConnectOptions op = new MqttConnectOptions();
		op.setCleanSession(true);
		PWApplication.connection.addConnectionOptions(op);
		try {
			PWApplication.connection.getClient().connect(op, null, callback);

		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
