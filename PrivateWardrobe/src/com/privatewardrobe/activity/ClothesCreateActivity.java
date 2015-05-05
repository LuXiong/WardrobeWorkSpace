package com.privatewardrobe.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.ActionBar.ActionItem;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.R;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.ClothesBusiness;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.Clothes;
import com.privatewardrobe.photo.PhotoHelper;
import com.privatewardrobe.photo.PhotoHelper.PhotoProcessListener;

public class ClothesCreateActivity extends BaseActivity {

	private ActionBar mActionBar;
	private ImageView mClothesImg;
	private EditText mDescriptionEdit;
	private LinearLayout mColorLayout, mCategoryLayout;
	private PhotoHelper mPhotoHelper;

	private int mColor, mCategory, mExponent;
	private Uri mLargeImg, mThumbnail;

	private AlertDialog mLoadingDialog;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_create_clothes);
		mActionBar = getMyActionBar();
		mActionBar.setTitle("Ìí¼ÓÒÂ·þ");
		mActionBar.addActionItem(0, null, R.drawable.icon_finish,
				ActionItem.SHOWACTION_SHOW);
		findView();
		initView();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mPhotoHelper.process(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	protected void onActionBarItemSelected(int itemId, ActionItem item) {
		if (itemId == 0) {
			mLoadingDialog.show();
			ClothesBusiness clothesBusiness =new ClothesBusiness();
			clothesBusiness.addClothes("ssss", 1, 4, "wefsde", new BusinessListener<Clothes>(){

				@Override
				public void onSuccess(Clothes clothes) {
					mLoadingDialog.dismiss();
				}
				
			});
		}
		super.onActionBarItemSelected(itemId, item);
	}

	private void findView() {
		mClothesImg = (ImageView) findViewById(R.id.activity_create_clothes_img);
		mDescriptionEdit = (EditText) findViewById(R.id.activity_create_clothes_description_edit);
		mColorLayout = (LinearLayout) findViewById(R.id.activity_create_clothes_color_layout);
		mCategoryLayout = (LinearLayout) findViewById(R.id.activity_create_category_layout);
	}

	private void initView() {
		bindEvents();
		mLoadingDialog = Utils.buildLoadingDialog(ClothesCreateActivity.this);
		mPhotoHelper = new PhotoHelper(this, new PhotoProcessListener() {

			@Override
			public void onComplete(Uri source, Uri large, Uri thumbnail) {
				mLargeImg = large;
				mThumbnail = thumbnail;
				imageLoader.displayImage(thumbnail.toString(), mClothesImg);
			}
		});
	}

	private void bindEvents() {
		mClothesImg.setOnClickListener(mClothesImgClickListener);
		mColorLayout.setOnClickListener(mColorLayoutClickListener);
		mCategoryLayout.setOnClickListener(mCategoryLayoutClickListener);

	}

	private OnClickListener mClothesImgClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Utils.buildPhotoHelperListDialog(ClothesCreateActivity.this,
					mPhotoHelper);
		}
	};

	private OnClickListener mColorLayoutClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	private OnClickListener mCategoryLayoutClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};
}
