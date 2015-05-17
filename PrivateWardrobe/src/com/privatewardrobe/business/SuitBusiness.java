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
			int isLike,
			final BusinessListener<Suit> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("userId", userId);
		params.put("img", img);
		params.put("thumb", thumb);
		params.put("weather", weather);
		params.put("occasion", occasion);
		params.put("description", description);
		params.put("isLike", isLike);
		client.post("suit/add", params, new PWHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject data) {
				Log.i("xionglu", "data:" + data.toString());
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
						Suit suit = new Suit(new JSONObject(obj.getString("suit")));
						suitsList.add(suit);

					}
					listener.onSuccess(suitsList);
					}else if ((page*20<=data.length())&&(page!=0)){
						for(int i = (page-1)*20;i<page*20;i++)
						{
							JSONObject obj = data.getJSONObject(i);
							Suit suit = new Suit(new JSONObject(obj.getString("suit")));
							suitsList.add(suit);

						}
						listener.onSuccess(suitsList);
					}else if(((page-1)*20<=data.length())&&(data.length()<=page*20)){
						for(int i = (page-1)*20; i<data.length();i++)
						{
							JSONObject obj = data.getJSONObject(i);
							Suit suit = new Suit(new JSONObject(obj.getString("suit")));
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
						if(i!=clothesList.size()-1){
						mergeClothesIdTotal = mergeClothesIdTotal + clothesList.get(i).getId()+ "-";
						}else{
						mergeClothesIdTotal = mergeClothesIdTotal + clothesList.get(i).getId();
						}
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
	/**
	 * 根据字符得出weather的代码
	 * @param weatherW
	 * @return
	 */
	public static int CheckWeatherW(String weatherW){
		int weather = 0;
//		switch(weatherW){
//		case "春装":weather = 20;break;
//		case "夏装":weather = 30;break;
//		case "秋装":weather = 15;break;
//		case "冬装":weather = 10;break;
//		}
		
		if(weatherW == "春装"){
			weather = 20;
		}else if(weatherW == "夏装"){
			weather = 30;
		}else if(weatherW == "秋装"){
			weather = 15;
		}else if(weatherW == "冬装"){
			weather = 10;
		}
		return weather;
	}
	
/**
 * 根据字符得出occasion的代码
 * @param occasionW
 * @return
 */
	
	public static int CheckOccasionW(String occasionW){
		int occasion = 0;
//		switch(occasionW){
//		case "上学":occasion = 1;break;
//		case "逛街":occasion = 2;break;
//		case "会议":occasion = 3;break;
//		case "party":occasion = 4;break;
//		case "约会 ":occasion = 5;break;
//		case "旅游 ":occasion = 6;break;
//		}
		
		if(occasionW == "上学"){
			occasion = 1;
		}else if(occasionW == "逛街"){
			occasion = 2;
		}else if(occasionW == "会议"){
			occasion = 3;
		}else if(occasionW == "party"){
			occasion = 4;
		}else if(occasionW == "约会 "){
			occasion = 5;
		}else if(occasionW == "旅游 "){
			occasion = 6;
		}
		return occasion;
	}
	/**
	 * 获取weather的整个字符表
	 * @return
	 */
	public static ArrayList<String> getWeatherStringList(){
		ArrayList<String> weatherStringList = new ArrayList<String>();
		weatherStringList.add("春装");
		weatherStringList.add("夏装");
		weatherStringList.add("秋装");
		weatherStringList.add("冬装");
		return weatherStringList;
	}
	/**
	 * 获取occasion的整个字符表
	 * @return
	 */
	public static ArrayList<String> getOccasionStringList(){
		ArrayList<String> occasionStringList = new ArrayList<String>();
		occasionStringList.add("上学");
		occasionStringList.add("逛街");
		occasionStringList.add("会议");
		occasionStringList.add("party");
		occasionStringList.add("约会 ");
		occasionStringList.add("旅游 ");
		return occasionStringList;
	}
	
	public void queryClothesBySuitId(String suitId,final BusinessListener<Clothes> listener){
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("suitId", suitId);
		client.post("suit/queryClothesBySuitId", params, new PWHttpResponseHandler(){
			public void onSuccess(JSONArray data){
			ArrayList<Clothes> clothesList = new ArrayList<Clothes>();
			try {
				for (int i = 0; i < data.length(); i++) {
					JSONObject obj = data.getJSONObject(i);
					Clothes clothes = new Clothes(new JSONObject(obj
							.getString("clothes")));
					clothesList.add(clothes);
				}
				if (clothesList != null) {
					listener.onSuccess(clothesList);
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			}
		});
		
	}
}
	
