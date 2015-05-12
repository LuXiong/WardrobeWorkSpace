package com.privatewardrobe.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.R;

public class SuitDetailActivity extends BaseActivity{
	
	private TextView mDescriptionTextView,mWeatherTextView,mOccasionTextView;
	private TextView mCreateTimeTextView,mLastEditTextView,mIsLike;
	private ImageView mImgImageView;
	private ListView mClothesListView;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mActionBar = getMyActionBar();
		mActionBar.setLeftDrawable(null);
		setContentView(R.layout.activity_suit_detail);
		findView();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		
	}

	private void findView() {
		// TODO Auto-generated method stub
		mDescriptionTextView = (TextView)findViewById(R.id.activity_suit_detail_description);
		mWeatherTextView = (TextView)findViewById(R.id.activity_suit_detail_weather);
		mOccasionTextView = (TextView)findViewById(R.id.activity_suit_detail_occasion);
		mCreateTimeTextView = (TextView)findViewById(R.id.activity_suit_detail_createTime);
		mLastEditTextView = (TextView)findViewById(R.id.activity_suit_detail_lastEdit);
		mIsLike = (TextView)findViewById(R.id.activity_suit_detail_isLike);
		mImgImageView = (ImageView)findViewById(R.id.activity_suit_detail_img);
		mClothesListView = (ListView)findViewById(R.id.activity_suit_detail_clothesList);
		
	}
}
