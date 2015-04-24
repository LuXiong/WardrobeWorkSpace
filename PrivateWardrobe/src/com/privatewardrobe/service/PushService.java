package com.privatewardrobe.service;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import android.content.Context;
import android.util.Log;

import com.privatewardrobe.PWConstant;
import com.privatewardrobe.service.ActionListener.Action;
import com.privatewardrobe.service.ActionListener.OnConnectedListener;
import com.privatewardrobe.service.Connection.ConnectionStatus;

public class PushService {

	private static Connection connection;
	private static ActionListener connectCallback;
	private final static String clientId = "45i";
	private final static String uri = PWConstant.BASEURL;
	private final static String clientHandle = uri + clientId;

	public Connection connectAction(final Context context) {
		createConnection(context);
		initCallback(context);
		connect(context, connectCallback);
		return connection;
	}

	private void initCallback(final Context context) {
		connection.changeConnectionStatus(ConnectionStatus.CONNECTING);
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
		connection = new Connection(clientHandle, clientId,
				PWConstant.BASESEVER, PWConstant.BASEPORT, context, client,
				false);

	}

	private void subscribe(Context context) {
		String[] topics = new String[1];
		String topic = "PW";
		topics[0] = topic;
		try {
			connection.getClient().subscribe(
					topic,
					0,
					null,
					new ActionListener(context, Action.SUBSCRIBE,
							clientHandle, topics));
			Log.i("xionglu", "subscribe");
		} catch (MqttException e) {
			e.printStackTrace();
		}

	}

	private void connect(Context context, ActionListener callback) {
		connection.getClient().registerResources(context);
		connection.getClient()
				.setCallback(
						new MqttCallbackHandler(context, connection.getClient()
								.getServerURI()
								+ connection.getClient().getClientId()));
		MqttConnectOptions op = new MqttConnectOptions();
		op.setCleanSession(true);
		connection.addConnectionOptions(op);
		try {
			connection.getClient().connect(op, null, callback);

		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
