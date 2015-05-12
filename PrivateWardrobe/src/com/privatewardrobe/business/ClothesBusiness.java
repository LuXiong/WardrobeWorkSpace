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
	public void addClothes(String userId, String description, int color,
			int category, String img, int isLike,
			final BusinessListener<Clothes> listener) {
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("userId", userId);
		params.put("description", description);
		params.put("color", color);
		params.put("category", category);
		params.put("img", img);
		params.put("like", isLike);
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
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("clothesId", clothesId);
		client.post("clothes/delete", params, new PWHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject data) {
				// Log.i("xionglu", "data:" + data.toString());
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

	public void updateClothes(String id, int color, int category, String img,String description,int like,
			final BusinessListener<Clothes> listener) {
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", id);
		params.put("color", color);
		params.put("category", category);
		params.put("img", img);
		params.put("description", description);
		params.put("like", like);
		client.post("clothes/update", params, new PWHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject data) {
				// Log.i("xionglu", "data:" + data.toString());
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

	public void queryClothesById(String id,
			final BusinessListener<Clothes> listener) {
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", id);
		client.post("clothes/queryById", params, new PWHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject data) {
				// Log.i("xionglu", "data:" + data.toString());
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

	public void queryClothesByKeyWord(String keyWord,
			final BusinessListener<Clothes> listener) {
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("description", keyWord);
		client.post("clothes/queryByKeyWord", params,
				new PWHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray data) {

						try {
							ArrayList<Clothes> clothesList = new ArrayList<Clothes>();
							for (int i = 0; i < data.length(); i++) {
								JSONObject obj = data.getJSONObject(i);
								Clothes clothes = new Clothes(new JSONObject(
										obj.getString("clothes")));
								clothesList.add(clothes);

							}
							listener.onSuccess(clothesList);

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

	public void queryClothesByUserId(String userId, final int page,
			final BusinessListener<Clothes> listener) {
		PWHttpClient client = new PWHttpClient();
		RequestParams params = new RequestParams();
		params.put("userId", userId);
		params.put("page", page);
		client.post("clothes/queryByUserId", params,
				new PWHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray data) {

						try {
							ArrayList<Clothes> clothesList = new ArrayList<Clothes>();
							if (page == 0) {
								for (int i = 0; i < data.length(); i++) {
									JSONObject obj = data.getJSONObject(i);
									String json = obj.getString("clothes");
									JSONObject o = new JSONObject(json);
									Clothes clothes = new Clothes(o);
									clothesList.add(clothes);

								}
								listener.onSuccess(clothesList);
							} else if ((page*20<=data.length())&&(page!=0)) {
								for (int i = (page - 1) * 20; i < page * 20; i++) {
									JSONObject obj = data.getJSONObject(i);
									String json = obj.getString("clothes");
									JSONObject o = new JSONObject(json);
									Clothes clothes = new Clothes(o);
									clothesList.add(clothes);
								}
								listener.onSuccess(clothesList);
							} else if (((page - 1) * 20 <= data.length())
									&& (data.length() <= page * 20)) {
								for (int i = (page - 1) * 20; i < data.length(); i++) {

									JSONObject obj = data.getJSONObject(i);
									String json = obj.getString("clothes");
									JSONObject o = new JSONObject(json);
									Clothes clothes = new Clothes(o);
									clothesList.add(clothes);
								}
								listener.onSuccess(clothesList);
							} else if (page * 20 > data.length()) {
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

	public void showClothesType(final BusinessListener<ClothesType> listener) {
		PWHttpClient client = new PWHttpClient();
		client.post("clothes/showClothesType", new PWHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray data) {
				Log.i("xionglu", "data:" + data.toString());
				ArrayList<ClothesType> typeList = new ArrayList<ClothesType>();
				try {
					for (int i = 0; i < data.length(); i++) {
						JSONObject obj = data.getJSONObject(i);
						ClothesType type = new ClothesType(new JSONObject(obj
								.getString("clothesType")));
						typeList.add(type);
					}
					if (typeList != null) {
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
