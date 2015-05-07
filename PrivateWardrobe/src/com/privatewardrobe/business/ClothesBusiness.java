package com.privatewardrobe.business;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.privatewardrobe.PWApplication;
import com.privatewardrobe.common.PWHttpClient;
import com.privatewardrobe.common.PWHttpResponseHandler;
import com.privatewardrobe.model.Clothes;
import com.privatewardrobe.model.ClothesType;
import com.privatewardrobe.model.User;

public class ClothesBusiness {
	public void addClothes(String userId, int color, int category, String img,
			final BusinessListener<Clothes> listener) {
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("userId", userId);
		params.put("color", color);
		params.put("category", category);
		params.put("img", img);
		client.post("clothes/add", params, new PWHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject data) {
				Log.i("xionglu", "data:" + data.toString());
				try {
					JSONObject clothesData = new JSONObject(data
							.getString("clothes"));
					Clothes clothes = new Clothes(clothesData);
					listener.onSuccess(clothes);
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

	public void deleteClothes(String clothesId,
			final BusinessListener<Clothes> listener) {

	}

	public void updateClothes(String id, int color, int category, String img,
			final BusinessListener<Clothes> listener) {

	}

	public void queryClothesById(String id,
			final BusinessListener<Clothes> listener) {

	}

	public void queryClothesByKey(String key,
			final BusinessListener<Clothes> listener) {

	}

	public void findAllClothes(String userId,
			final BusinessListener<Clothes> listener) {

	}
	public void showClothesType(final BusinessListener<ClothesType> listener){
		PWHttpClient client = new PWHttpClient();
		client.post("clothes/showClothesType", new PWHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray data) {
				Log.i("xionglu", "data:" + data.toString());
				ArrayList<ClothesType> typeList = new ArrayList<ClothesType>();
				try {
					for(int i=0;i<data.length();i++){
						JSONObject obj = data.getJSONObject(i);
						ClothesType type = new ClothesType(new JSONObject(obj.getString("clothesType")));
						typeList.add(type);
					}
					if(typeList!=null){
						listener.onSuccess(typeList);
						Log.i("xionglu", "typeList:" + typeList.toString());
					}
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

}
