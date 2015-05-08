package com.privatewardrobe.business;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.privatewardrobe.common.PWHttpClient;
import com.privatewardrobe.common.PWHttpResponseHandler;
import com.privatewardrobe.model.Suit;

public class SuitBusiness {

	public void addSuit(String userId,String img,int weather,int occasion,
			final BusinessListener<Suit> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("userId", userId);
		params.put("img", img);
		params.put("weather", weather);
		params.put("ocassion", occasion);
		client.post("suit/add", params, new PWHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject data) {
//				Log.i("xionglu", "data:" + data.toString());
				try {
					JSONObject suitData = new JSONObject(data
							.getString("suit"));
					Suit suit = new Suit(suitData);
					listener.onSuccess(suit);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}	
	}
		});
	}

	
	public void deleteSuit(String id,final BusinessListener<Suit> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", id);
		client.post("suit/delete", params, new PWHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject data) {
//				Log.i("xionglu", "data:" + data.toString());
				try {
					JSONObject suitData = new JSONObject(data
							.getString("suit"));
					Suit suit = new Suit(suitData);
					listener.onSuccess(suit);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}	
	}
		});
	}
	
	public void updateSuit(String id,int weather,int occasion,
			final BusinessListener<Suit> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", id);
		params.put("weather",weather);
		params.put("occasion", occasion);
		client.post("suit/update", params, new PWHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject data) {
//				Log.i("xionglu", "data:" + data.toString());
				try {
					JSONObject suitData = new JSONObject(data
							.getString("suit"));
					Suit suit = new Suit(suitData);
					listener.onSuccess(suit);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}	
	}
		});
		
	}
	
	public void querySuitById(String id,final BusinessListener<Suit> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		client.post("suit/queryById", params, new PWHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject data) {
//				Log.i("xionglu", "data:" + data.toString());
				try {
					JSONObject suitData = new JSONObject(data
							.getString("suit"));
					Suit suit = new Suit(suitData);
					listener.onSuccess(suit);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}	
	}
		});
		
	}
	
	public void querySuitByKeyWord(String keyWord,final BusinessListener<Suit> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("description", keyWord);
		client.post("suit/queryByKeyWord", params, new PWHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray data) {

				try {
					ArrayList<Suit> suitsList = new ArrayList<Suit>();
					for(int i = 0;i < data.length();i++)
					{
						JSONObject obj = data.getJSONObject(i);
						Suit suit = new Suit(new JSONObject(obj.getString("Suit")));
						suitsList.add(suit);


					}
					listener.onSuccess(suitsList);
			
				} catch (Exception e1) {
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
	
	public void querySuitByUserId(String userId,final BusinessListener<Suit> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("userId", userId);
		client.post("suit/queryByUserId", params, new PWHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray data) {

				try {
					ArrayList<Suit> suitsList = new ArrayList<Suit>();
					for(int i = 0;i < data.length();i++)
					{
						JSONObject obj = data.getJSONObject(i);
						Suit suit = new Suit(new JSONObject(obj.getString("Suit")));
						suitsList.add(suit);

					}
					listener.onSuccess(suitsList);
			
				} catch (Exception e1) {
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
}
	
