package com.privatewardrobe.business;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.privatewardrobe.common.PWHttpClient;
import com.privatewardrobe.common.PWHttpResponseHandler;
import com.privatewardrobe.model.Clothes;
import com.privatewardrobe.model.Share;

public class ShareBusiness {

	public void addShare(String userId,String suitId,String content,int isPublic,
			final BusinessListener<Share> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("userId", userId);
		params.put("suitId", suitId);
		params.put("content", content);
		params.put("isPublic", isPublic);
		client.post("share/add", params, new PWHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject data) {
				Log.i("zyz", "data:" + data.toString());
				try {
					JSONObject shareData = new JSONObject(data
							.getString("share"));
					Share share = new Share(shareData);
					listener.onSuccess(share);
				} catch (JSONException e1) {
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
	
	public void deleteShare(String id,final BusinessListener<Share> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", id);
		client.post("share/delete", params, new PWHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject data) {
				// Log.i("xionglu", "data:" + data.toString());
				try {
					JSONObject shareData = new JSONObject(data
							.getString("share"));
					Share share = new Share(shareData);
					listener.onSuccess(share);
				} catch (JSONException e1) {
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
	
	public void queryShareByUserId(String userId,final BusinessListener<Share> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("userId", userId);
		client.post("share/queryByUserId", params, new PWHttpResponseHandler(){
			public void onSuccess(JSONArray data) {

				try {
					ArrayList<Share> shareList = new ArrayList<Share>();
					for (int i = 0; i < data.length(); i++) {
						JSONObject obj = data.getJSONObject(i);
						Share share = new Share(new JSONObject(
								obj.getString("share")));
						shareList.add(share);

					}
					listener.onSuccess(shareList);

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
	
	public void updateCurrentShare(final BusinessListener<Share> listener){
		
	}
	
	public void addComment(){
		
	}
	/**
	 * µ„‘ﬁ
	 */
	public void addLike(){
		
	}
	/**
	 *  ’≤ÿ
	 */
	public void addCollect(){
		
	}
}
