package com.privatewardrobe.activity;

import java.io.File;
import java.net.URI;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.R;
import com.privatewardrobe.UploadHelper;
import com.privatewardrobe.UploadHelper.UpCompletionListener;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.photo.PhotoHelper;
import com.privatewardrobe.photo.PhotoHelper.PhotoProcessListener;

public class ClothesCreateActivity extends BaseActivity {

	private ActionBar mActionBar;
	private ImageView mClothesImg;
	private PhotoHelper mPhotoHelper;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_create_clothes);
		mActionBar = getMyActionBar();
		findView();
		initView();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mPhotoHelper.process(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);

	}

	private void findView() {
		mClothesImg = (ImageView) findViewById(R.id.activity_create_clothes_img);
		mClothesImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.buildPhotoHelperListDialog(ClothesCreateActivity.this,
						mPhotoHelper);
			}
		});

	}

	private void initView() {
		mPhotoHelper = new PhotoHelper(this, new PhotoProcessListener() {

			@Override
			public void onComplete(Uri source, Uri large, Uri thumbnail) {
				UploadHelper helper = new UploadHelper();
				helper.upload(
						new File(Utils.getRealPathFromURI(source,
								ClothesCreateActivity.this)),
						new UpCompletionListener() {

							@Override
							public void onSuccess(String img) {
								// TODO Auto-generated method stub

							}
						});
			}
		});
	}

}
