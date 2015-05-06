package com.privatewardrobe;

import java.io.File;

import org.json.JSONObject;

import android.util.Log;

import com.privatewardrobe.common.Utils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;

public class UploadHelper {

	
	private String mBaseUrl = "7xioul.com1.z0.glb.clouddn.com/";
	private Auth auth = Auth.create("iP5j-cuQEeVMifha7Dma4kc_RrYm4o__Kj-l0KPb", "6H9pGV6JhYeclyENlR_g1YVuqJqRvTq40a7fOcez");
	private String getUpToken(){
		return auth.uploadToken("pwweb");
	}
	public void upload(File file, final UpCompletionListener listener){
		Log.i("xionglu", "begin upload");
		UploadManager uploadManager = new UploadManager();
		String token = getUpToken();
		uploadManager.put(file, Utils.getPhotoFileName(), token, new UpCompletionHandler() {
			
			@Override
			public void complete(String key, ResponseInfo info, JSONObject response) {
				Log.i("xionglu", "complete upload");
				if(info.isOK()){
					String name = response.optString("key");
					String url = mBaseUrl + name;
					listener.onSuccess(url);
					
				}
				else{
					Log.i("xionglu", info.error);
				}
			}
		}, null);
	}
	
	public interface UpCompletionListener{
		public void onSuccess(String img);
	}
}
