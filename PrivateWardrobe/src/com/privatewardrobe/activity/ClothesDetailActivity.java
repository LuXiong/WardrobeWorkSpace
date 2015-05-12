package com.privatewardrobe.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.R;
import com.privatewardrobe.model.Suit;

public class ClothesDetailActivity extends BaseActivity{
	private TextView mDescriptionTextView,mColorTextView,mCategoryView,mExponentTextView;
	private TextView mCreateTimeTextView,mLastEditTextView,mIsLike;
	private ImageView mImgImageView;
	private ListView mSuitListView;
	
	private ArrayList<Suit> mSuitList;
@Override
protected void onCreate(Bundle bundle) {
	super.onCreate(bundle);
	mActionBar = getMyActionBar();
	mActionBar.setLeftDrawable(null);
	setContentView(R.layout.activity_clothes_detail);
	findView();
	initView();
}
private void initView() {
	// TODO Auto-generated method stub
	
}
private void findView() {
	// TODO Auto-generated method stub
	mDescriptionTextView = (TextView)findViewById(R.id.activity_clothes_detail_description);
	mColorTextView = (TextView)findViewById(R.id.activity_clothes_detail_color);
	mCategoryView = (TextView)findViewById(R.id.activity_clothes_detail_category);
	mExponentTextView = (TextView)findViewById(R.id.activity_clothes_detail_exponent);
	mCreateTimeTextView = (TextView)findViewById(R.id.activity_clothes_detail_createTime);
	mLastEditTextView = (TextView)findViewById(R.id.activity_clothes_detail_lastEdit);
	mIsLike = (TextView)findViewById(R.id.activity_clothes_detail_isLike);
	mImgImageView = (ImageView)findViewById(R.id.activity_clothes_detail_img);
	mSuitListView = (ListView)findViewById(R.id.activity_clothes_detail_suitList);
}
}
