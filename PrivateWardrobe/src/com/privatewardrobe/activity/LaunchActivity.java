package com.privatewardrobe.activity;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class LaunchActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		if(!PWApplication.getInstance().isWeclomed()){
			Intent intent = new Intent(LaunchActivity.this, FunctionActivity.class);
			startActivityForResult(intent, 0);
		}else{
			(new Handler()).postDelayed(new Runnable() {

				@Override
				public void run() {	
					jump();
					LaunchActivity.this.finish();
				}
			}, 3000);
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_CANCELED){
			jump();
			LaunchActivity.this.finish();
		}
		super.onActivityResult(requestCode, resultCode, data);	
		
	}
	
	private void jump(){
		Log.i("xionglu","PWApplication.getInstance().getUserId()"+PWApplication.getInstance().getUserId());
		Log.i("xionglu","PWApplication.getInstance().getToken()"+PWApplication.getInstance().getToken());
		if(PWApplication.getInstance().isLogin()){
			Intent intent = new Intent(LaunchActivity.this,
					MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}else{
			Intent intent = new Intent(LaunchActivity.this,
					LoginActivity.class);
			startActivity(intent);
		}
	}
}
