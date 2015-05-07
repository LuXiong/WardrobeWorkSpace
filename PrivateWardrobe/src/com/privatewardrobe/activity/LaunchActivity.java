package com.privatewardrobe.activity;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LaunchActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		if(!PWApplication.getInstance().isWeclomed()){
			Intent intent = new Intent(LaunchActivity.this, FunctionActivity.class);
			startActivity(intent);
		}else{
			(new Handler()).postDelayed(new Runnable() {

				@Override
				public void run() {
					
					Intent intent = new Intent(LaunchActivity.this,
							LoginActivity.class);
					startActivity(intent);
					LaunchActivity.this.finish();
				}
			}, 3000);
		}
		
	}
}
