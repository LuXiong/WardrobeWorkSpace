package com.privatewardrobe.common;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.privatewardrobe.PWApplication;

public class PWHttpClient {
	private static AsyncHttpClient mClient = new AsyncHttpClient();
	private boolean mSilence = false;
	static {
		mClient.setTimeout(60000);
		mClient.setMaxConnections(20);
	}

	public void setSilence(boolean silence) {
		this.mSilence = silence;
	}

	public void post(String url, final PWHttpResponseHandler handler) {
		post(url, null, handler);
	}

	public void post(String url, RequestParams params,
			final PWHttpResponseHandler handler) {
		post(null, url, params, handler);
	}

	public void post(Context context, String url, RequestParams params,
			final PWHttpResponseHandler handler) {
		if (params == null) {
			params = new RequestParams();
		}
		if (PWApplication.getInstance().isLogin()) {
			params.put("access_token", PWApplication.getInstance().getToken());
		}

		AsyncHttpResponseHandler asyncHttpResponseHandler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				if (responseBody != null) {
					if (!mSilence) {
						Toast.makeText(PWApplication.getInstance(),
								"服务器君挂掉了，请稍后再戳", Toast.LENGTH_SHORT).show();
					}
				} else {
					if (!mSilence) {
						Toast.makeText(PWApplication.getInstance(),
								"您的网络好像有点问题...", Toast.LENGTH_SHORT).show();
					}
				}

				handler.onFailure();

			}

			@Override
			public void onCancel() {
				handler.onFinish();
			}

			@Override
			public void onFinish() {
				handler.onFinish();
			}

			@Override
			public void onStart() {
				handler.onStart();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				try {
					String json = new String(responseBody);
					if (json.substring(0, 1).equals("[")) {
						JSONArray data = new JSONArray(json);
						handler.onSuccess(data);
					} else {
						JSONObject data = new JSONObject(json);
						if (!data.has("error")) {
							handler.onSuccess(data);
						} else {
							if (!mSilence) {
								Toast.makeText(PWApplication.getInstance(),
										"有个参数错掉了...", Toast.LENGTH_SHORT)
										.show();
							}
							handler.onFailure();
						}
					}
				} catch (JSONException e) {
					if (!mSilence) {
						Toast.makeText(PWApplication.getInstance(),
								"数据格式错了，赶紧找叶子同学问问...", Toast.LENGTH_SHORT)
								.show();
					}
				}
			}

		};

		if (context == null) {
			mClient.post(PWApplication.getInstance().getBaseUrl() + url,
					params, asyncHttpResponseHandler);
		} else {
			mClient.post(context, PWApplication.getInstance().getBaseUrl()
					+ url, params, asyncHttpResponseHandler);
		}

	}

	public static void cancelRequests(Context context) {
		if (context != null) {
			mClient.cancelRequests(context, true);
		}
	}


}
