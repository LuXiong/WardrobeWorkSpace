package com.privatewardrobe.business;

import org.json.JSONObject;

import com.loopj.android.http.RequestParams;
import com.privatewardrobe.common.PWHttpClient;
import com.privatewardrobe.common.PWHttpResponseHandler;
import com.privatewardrobe.model.User;

public class UserBusiness {
	public void updateUser(String id,String name,String avatar,String description,
			final BusinessListener<User> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", id);
		params.put("name", name);
		params.put("avatar", avatar);
		params.put("description", description);
		client.post("user/update", params, new PWHttpResponseHandler() {
			public void onSuccess(JSONObject data){
				try{
					JSONObject userData = new JSONObject(data.getString("user"));
					User user = new User(userData);
					listener.onSuccess(user);
					
				} catch(Exception e){
					e.printStackTrace();
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
				listener.onFailure("update failed!");
			}
		});
	}

	public void queryUserById(String id,
			final BusinessListener<User> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("uid", id);
		client.post("user/queryById", params, new PWHttpResponseHandler() {
			public void onSuccess(JSONObject data){
				try{
					JSONObject userData = new JSONObject(data.getString("user"));
					User user = new User(userData);
					listener.onSuccess(user);
					
				} catch(Exception e){
					e.printStackTrace();
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
				listener.onFailure(" failed!");
			}
		});
	}

	
}
