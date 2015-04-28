package com.privatewardrobe.business;

import org.json.JSONObject;

import com.loopj.android.http.RequestParams;
import com.privatewardrobe.PWApplication;
import com.privatewardrobe.common.PWHttpClient;
import com.privatewardrobe.common.PWHttpResponseHandler;

public class PassBusiness {
	public void login(String phone, String password){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("phone", phone);
		params.put("password", password);
		client.post("pass/regist", params,new PWHttpResponseHandler(){

			@Override
			public void onSuccess(JSONObject data) {
				// TODO Auto-generated method stub
				super.onSuccess(data);
			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onFailure() {
				// TODO Auto-generated method stub
				super.onFailure();
			}
			
		});
	}
}
