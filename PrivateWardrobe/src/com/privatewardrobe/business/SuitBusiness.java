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
import com.privatewardrobe.model.Clothes;
import com.privatewardrobe.model.Suit;

public class SuitBusiness {

	public void addSuit(String userId,String img,String thumb,int weather,int occasion,String description,
			final BusinessListener<Suit> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("userId", userId);
		params.put("img", img);
		params.put("thumb", thumb);
		params.put("weather", weather);
		params.put("ocassion", occasion);
		params.put("description", description);
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
	
	public void updateSuit(String id,int weather,int occasion,String description,int isLike,
			final BusinessListener<Suit> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", id);
		params.put("weather",weather);
		params.put("occasion", occasion);
		params.put("description", description);
		params.put("isLike", isLike);
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
	
	public void querySuitByUserId(String userId,final int page,final BusinessListener<Suit> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("userId", userId);
		client.post("suit/queryByUserId", params, new PWHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray data) {

				try {
					ArrayList<Suit> suitsList = new ArrayList<Suit>();
					if(page == 0)
					{
					for(int i = 0;i < data.length();i++)
					{
						JSONObject obj = data.getJSONObject(i);
						Suit suit = new Suit(new JSONObject(obj.getString("Suit")));
						suitsList.add(suit);

					}
					listener.onSuccess(suitsList);
					}else if (page*20<=data.length()){
						for(int i = (page-1)*20;i<page*20;i++)
						{
							JSONObject obj = data.getJSONObject(i);
							Suit suit = new Suit(new JSONObject(obj.getString("Suit")));
							suitsList.add(suit);

						}
						listener.onSuccess(suitsList);
					}else if(((page-1)*20<=data.length())&&(data.length()<=page*20)){
						for(int i = (page-1)*20; i<data.length();i++)
						{
							JSONObject obj = data.getJSONObject(i);
							Suit suit = new Suit(new JSONObject(obj.getString("Suit")));
							suitsList.add(suit);

						}
						listener.onSuccess(suitsList);
					}else if(page*20>data.length()){
						listener.onFailure("no more results");
					}
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
	/**
	 * 
	 * @param clothesList
	 * @return
	 */
	public String mergeClothesId(List<Clothes> clothesList){

		String mergeClothesIdTotal = null;
					for(int i = 0;i<clothesList.size();i++)
					{
						mergeClothesIdTotal = mergeClothesIdTotal + clothesList.get(i).getId()+ "-";
					}

		return mergeClothesIdTotal;
	}
	
	public List<Clothes> parseClothesId(String clothes){
		ArrayList<Clothes> clothesList = new ArrayList<Clothes>();
		String[] temp = clothes.split("-");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < temp.length; i++) {
			builder.append(temp[i]);
		}
		return clothesList;
	}
	
	public void updateSuitIsLike(int like,final BusinessListener<Suit> listener) {
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("like", like);
		client.post("suit/updateIsLike", new PWHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject data) {
				// Log.i("xionglu", "data:" + data.toString());
				try {
					JSONObject suitData = new JSONObject(data
							.getString("suit"));
					Suit suit = new Suit(suitData);
					listener.onSuccess(suit);
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
	
