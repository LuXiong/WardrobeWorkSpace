package com.privatewardrobe.activity;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import com.privatewardrobe.R;
import com.privatewardrobe.R.layout;
import com.privatewardrobe.R.menu;
import com.privatewardrobe.base.PWApplication;
import com.privatewardrobe.base.PWConstant;
import com.privatewardrobe.service.PushService;
import com.privatewardrobe.service.sample.ActionListener;
import com.privatewardrobe.service.sample.ActionListener.Action;

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
		PushService p = new PushService();
		text = (TextView) findViewById(R.id.text);
		text.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String[] topics = new String[1];
				String topic = "PW";
				topics[0] = topic;
				try {
					PWApplication.connection.getClient().subscribe(
							topic,
							0,
							null,
							new ActionListener(MainActivity.this, Action.SUBSCRIBE, PWConstant.BASEURL+"FirstUser",
									topics));
				} catch (MqttException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		p.connectAction(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
