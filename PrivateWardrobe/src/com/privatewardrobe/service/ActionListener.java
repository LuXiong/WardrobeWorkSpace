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

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.service.Connection.ConnectionStatus;

/**
 * This Class handles receiving information from the {@link MqttAndroidClient}
 * and updating the {@link Connection} associated with the action
 */
public class ActionListener implements IMqttActionListener {

	/**
	 * Actions that can be performed Asynchronously <strong>and</strong>
	 * associated with a {@link ActionListener} object
	 * 
	 */
	private boolean isConnected = false;

	public enum Action {
		/** Connect Action **/
		CONNECT,
		/** Disconnect Action **/
		DISCONNECT,
		/** Subscribe Action **/
		SUBSCRIBE,
		/** Publish Action **/
		PUBLISH
	}

	/**
	 * The {@link Action} that is associated with this instance of
	 * <code>ActionListener</code>
	 **/
	private Action action;
	/** The arguments passed to be used for formatting strings **/
	private String[] additionalArgs;
	/** Handle of the {@link Connection} this action was being executed on **/
	private String clientHandle;
	/** {@link Context} for performing various operations **/
	private Context context;
	private OnConnectedListener mConnectedListener;

	/**
	 * Creates a generic action listener for actions performed form any activity
	 * 
	 * @param context
	 *            The application context
	 * @param action
	 *            The action that is being performed
	 * @param clientHandle
	 *            The handle for the client which the action is being performed
	 *            on
	 * @param additionalArgs
	 *            Used for as arguments for string formating
	 */
	public ActionListener(Context context, Action action, String clientHandle,
			String... additionalArgs) {
		this.context = context;
		this.action = action;
		this.clientHandle = clientHandle;
		this.additionalArgs = additionalArgs;
	}

	public ActionListener(Context context, OnConnectedListener listener,
			Action action, String clientHandle, String... additionalArgs) {
		this.context = context;
		this.mConnectedListener = listener;
		this.action = action;
		this.clientHandle = clientHandle;
		this.additionalArgs = additionalArgs;
	}

	/**
	 * The action associated with this listener has been successful.
	 * 
	 * @param asyncActionToken
	 *            This argument is not used
	 */
	@Override
	public void onSuccess(IMqttToken asyncActionToken) {
		switch (action) {
		case CONNECT:
			connect();
			break;
		case DISCONNECT:
			disconnect();
			break;
		case SUBSCRIBE:
			subscribe();
			break;
		case PUBLISH:
			publish();
			break;
		}

	}

	/**
	 * A publish action has been successfully completed, update connection
	 * object associated with the client this action belongs to, then notify the
	 * user of success
	 */
	private void publish() {
		String actionTaken = context.getString(R.string.toast_pub_success,
				(Object[]) additionalArgs);
		PWApplication.connection.addAction(actionTaken);
	}

	/**
	 * A subscribe action has been successfully completed, update the connection
	 * object associated with the client this action belongs to and then notify
	 * the user of success
	 */
	private void subscribe() {
		String actionTaken = context.getString(R.string.toast_sub_success,
				(Object[]) additionalArgs);
		PWApplication.connection.addAction(actionTaken);

	}

	/**
	 * A disconnection action has been successfully completed, update the
	 * connection object associated with the client this action belongs to and
	 * then notify the user of success.
	 */
	private void disconnect() {
		PWApplication.connection
				.changeConnectionStatus(ConnectionStatus.DISCONNECTED);
		String actionTaken = context.getString(R.string.toast_disconnected);
		PWApplication.connection.addAction(actionTaken);

	}

	/**
	 * A connection action has been successfully completed, update the
	 * connection object associated with the client this action belongs to and
	 * then notify the user of success.
	 */
	private void connect() {

		if (mConnectedListener != null) {
			if (!isConnected) {
				mConnectedListener.onConnected();
			}
		}
		PWApplication.connection
				.changeConnectionStatus(Connection.ConnectionStatus.CONNECTED);
		PWApplication.connection.addAction("Client Connected");
		isConnected = true;
	}

	/**
	 * The action associated with the object was a failure
	 * 
	 * @param token
	 *            This argument is not used
	 * @param exception
	 *            The exception which indicates why the action failed
	 */
	@Override
	public void onFailure(IMqttToken token, Throwable exception) {
		Log.i("xionglu", "connect pushService failed");
		switch (action) {
		case CONNECT:
			connect(exception);
			break;
		case DISCONNECT:
			disconnect(exception);
			break;
		case SUBSCRIBE:
			subscribe(exception);
			break;
		case PUBLISH:
			publish(exception);
			break;
		}

	}

	/**
	 * A publish action was unsuccessful, notify user and update client history
	 * 
	 * @param exception
	 *            This argument is not used
	 */
	private void publish(Throwable exception) {
		String action = context.getString(R.string.toast_pub_failed,
				(Object[]) additionalArgs);
		PWApplication.connection.addAction(action);

	}

	/**
	 * A subscribe action was unsuccessful, notify user and update client
	 * history
	 * 
	 * @param exception
	 *            This argument is not used
	 */
	private void subscribe(Throwable exception) {
		String action = context.getString(R.string.toast_sub_failed,
				(Object[]) additionalArgs);
		PWApplication.connection.addAction(action);

	}

	/**
	 * A disconnect action was unsuccessful, notify user and update client
	 * history
	 * 
	 * @param exception
	 *            This argument is not used
	 */
	private void disconnect(Throwable exception) {
		PWApplication.connection
				.changeConnectionStatus(ConnectionStatus.DISCONNECTED);
		PWApplication.connection
				.addAction("Disconnect Failed - an error occured");
		isConnected = false;
	}

	/**
	 * A connect action was unsuccessful, notify the user and update client
	 * history
	 * 
	 * @param exception
	 *            This argument is not used
	 */
	private void connect(Throwable exception) {
		PWApplication.connection
				.changeConnectionStatus(Connection.ConnectionStatus.ERROR);
		PWApplication.connection.addAction("Client failed to connect");

	}

	public void setOnConnectedListener(OnConnectedListener listener) {
		this.mConnectedListener = listener;
	}

	public interface OnConnectedListener {
		public void onConnected();
	}

}