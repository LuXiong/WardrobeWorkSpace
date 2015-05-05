
package com.privatewardrobe.business;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.privatewardrobe.PWApplication;
import com.privatewardrobe.common.PWHttpClient;
import com.privatewardrobe.common.PWHttpResponseHandler;
import com.privatewardrobe.model.User;
/**
 * when we have to connect the server then we will use this 
 * @author Dean
 *
 */
public class PassBusiness {
	/**
	 * 
	 * @param phone
	 * @param password
	 * @param listener
	 */
	public void login(String phone, String password,
			final BusinessListener<User> listener) {
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("phone", phone);
		params.put("password", password);
		client.post("pass/login", params, new PWHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject data) {
				try {
					JSONObject userData = new JSONObject(data.getString("user"));
					User user = new User(userData);
					listener.onSuccess(user);
					JSONObject tokenData = new JSONObject(data
							.getString("token"));
					PWApplication.getInstance().Login(
							tokenData.getString("token"), user.getUid());
				} catch (JSONException e1) {
					Log.i("xionglu", "JSonException13297988492" + e1.toString());
					e1.printStackTrace();
				}

			}

			@Override
			public void onStart() {
				listener.onStart();
			}

			@Override
			public void onFinish() {
				listener.onFinish();
			}

			@Override
			public void onFailure() {
				listener.onFailure("post failed");
			}

		});
	}
	
	public void regist(String name, Integer gender, String password,
			String phone, String avatar, String deviceId, String code,
			final BusinessListener<User> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("name", name);
		params.put("gender", gender);
		params.put("password", password);
		params.put("phone", phone);
		params.put("avatar", avatar);
		params.put("deviceId", deviceId);
		params.put("code", code);
		client.post("pass/regist", params, new PWHttpResponseHandler(){
			public void onSuccess(JSONObject data){
				try{
					JSONObject userData = new JSONObject(data.getString("user"));
					User user = new User(userData);
					listener.onSuccess(user);
					
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			
		});
	}
}
