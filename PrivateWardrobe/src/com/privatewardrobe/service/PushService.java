package com.privatewardrobe.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import android.content.Context;
import android.util.Log;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.PWConstant;
import com.privatewardrobe.service.ActionListener.Action;
import com.privatewardrobe.service.ActionListener.OnConnectedListener;
import com.privatewardrobe.service.Connection.ConnectionStatus;

public class PushService {

	private static ActionListener connectCallback;
	private MqttAndroidClient client;
	private final static String clientId = "s5i";
	private final static String uri = PWConstant.BASE_PUSH_URL;
	private final static String clientHandle = uri + clientId;

	public void connectAction(final Context context) {
		MqttConnectOptions conOpt = new MqttConnectOptions();
		/*
		 * Mutal Auth connections could do something like this
		 * 
		 * 
		 * SSLContext context = SSLContext.getDefault(); context.init({new
		 * CustomX509KeyManager()},null,null); //where CustomX509KeyManager
		 * proxies calls to keychain api SSLSocketFactory factory =
		 * context.getSSLSocketFactory();
		 * 
		 * MqttConnectOptions options = new MqttConnectOptions();
		 * options.setSocketFactory(factory);
		 * 
		 * client.connect(options);
		 */

		// The basic client information
		String server = PWConstant.BASE_PUSH_SEVER;
		String clientId = "lll";
		int port = 1883;
		boolean cleanSession = false;

		String uri = null;
		uri = "tcp://";

		uri = uri + server + ":" + port;

		MqttAndroidClient client;
		client = new MqttAndroidClient(context, uri, clientId);

		String clientHandle = uri + clientId;

		PWApplication.connection = new Connection(clientHandle, clientId,
				server, port, context, client, false);
		// arrayAdapter.add(connection);
		//
		// connection.registerChangeListener(changeListener);
		// connect client

		String[] actionArgs = new String[1];
		actionArgs[0] = clientId;
		PWApplication.connection
				.changeConnectionStatus(ConnectionStatus.CONNECTING);

		conOpt.setCleanSession(cleanSession);
		//conOpt.setKeepAliveInterval(1);
		final ActionListener callback = new ActionListener(context,new OnConnectedListener() {
			
			@Override
			public void onConnected() {
				subscribe(context);
				
			}
		},
				ActionListener.Action.CONNECT, clientHandle, actionArgs);

		boolean doConnect = true;

		client.setCallback(new MqttCallbackHandler(context, clientHandle));

		// set traceCallback
		client.setTraceCallback(new MqttTraceCallback());

		PWApplication.connection.addConnectionOptions(conOpt);
		if (doConnect) {
			try {
				client.connect(conOpt, null, callback);
			} catch (MqttException e) {
				Log.e(this.getClass().getCanonicalName(),
						"MqttException Occured", e);
			}
		}
	}

	// public void connectAction(final Context context) {
	// createConnection(context);
	// initCallback(context);
	// connect(context, connectCallback);
	// }

	// public void disConnectAction() {
	// if (PWApplication.connection != null) {
	// try {
	// PWApplication.connection.getClient().disconnect();
	// } catch (MqttException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }

	// private void initCallback(final Context context) {
	// PWApplication.connection
	// .changeConnectionStatus(ConnectionStatus.CONNECTING);
	// String[] actionArgs = new String[] { clientId };
	// connectCallback = new ActionListener(context,
	// new OnConnectedListener() {
	//
	// @Override
	// public void onConnected() {
	//
	// subscribe(context);
	// }
	// }, ActionListener.Action.CONNECT, clientHandle, actionArgs);
	// }
	//
	// private void createConnection(final Context context) {
	// client = new MqttAndroidClient(context, uri, clientId);
	// client.setCallback(new MqttCallbackHandler(context, clientHandle));
	// client.setTraceCallback(new MqttTraceCallback());
	// PWApplication.connection = new Connection(clientHandle, clientId,
	// PWConstant.BASE_PUSH_SEVER, PWConstant.BASE_PUSH_PORT, context,
	// client, false);
	//
	// }
	//
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
	//
	// private void connect(Context context, ActionListener callback) {
	// PWApplication.connection.getClient().registerResources(context);
	// PWApplication.connection.getClient().setCallback(
	// new MqttCallbackHandler(context, PWApplication.connection
	// .getClient().getServerURI()
	// + PWApplication.connection.getClient().getClientId()));
	// MqttConnectOptions op = new MqttConnectOptions();
	// op.setCleanSession(true);
	// op.setKeepAliveInterval(1);
	// PWApplication.connection.addConnectionOptions(op);
	// try {
	//
	// PWApplication.connection.getClient().connect(op, null, callback);
	//
	// } catch (MqttException e) {
	// e.printStackTrace();
	// }
	// }
}
