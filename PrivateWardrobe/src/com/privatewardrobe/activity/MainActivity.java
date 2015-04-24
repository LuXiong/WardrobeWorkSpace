package com.privatewardrobe.activity;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.PWConstant;
import com.privatewardrobe.R;
import com.privatewardrobe.R.layout;
import com.privatewardrobe.R.menu;
import com.privatewardrobe.service.ActionListener;
import com.privatewardrobe.service.MqttAndroidClient;
import com.privatewardrobe.service.PushService;
import com.privatewardrobe.service.ActionListener.Action;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
