package com.privatewardrobe.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.ClothesTypeHelper;
import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.adapter.ClothesDetailAdapter;
import com.privatewardrobe.adapter.ImgHorizenGridAdapter;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.ClothesBusiness;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.Clothes;
import com.privatewardrobe.model.Suit;


public class ClothesDetailActivity extends BaseActivity{
	final static public String EXTRA_INPUT = "clothes";
	private TextView mDescriptionTextView,mColorTextView,mCategoryView,mExponentTextView;
	private TextView mCreateTimeTextView,mLastEditTextView,mIsLike;
	private ImageView mImgImageView;
	private GridView mSuitListView;
	
	private ArrayList<Suit> mSuitList;
	private ClothesDetailAdapter mClothesDetailAdapter;
	private Clothes mClothes;
@Override
protected void onCreate(Bundle bundle) {
	super.onCreate(bundle);
	mActionBar = getMyActionBar();
	mActionBar.setLeftDrawable(null);
	setContentView(R.layout.activity_clothes_detail);
	findView();
	initView();
	loadData();
}
private void loadData() {
	// TODO Auto-generated method stub
	Intent intent = getIntent();

	Clothes clothes = (Clothes) intent
			.getSerializableExtra(EXTRA_INPUT);
	
	if (clothes != null) {
		mClothes = clothes;
		ClothesBusiness clothesBusiness = new ClothesBusiness();
		clothesBusiness.querySuitByClothesId(mClothes.getId(), new BusinessListener<Suit>(){
			@Override
			public void onSuccess(ArrayList<Suit> suitlist) {
				mSuitList.clear();
				mSuitList.addAll(suitlist);
				notifyDataSetChanged();
			}
		});
	}
		notifyDataSetChanged();
	
	
}
private void notifyDataSetChanged() {
	// TODO Auto-generated method stub
	//全屏刷新，在控件中显示 内容
	mDescriptionTextView.setText(mClothes.getDescription());
	mColorTextView.setText(ClothesBusiness.checkColor(mClothes.getColor()));
	mCategoryView.setText(ClothesTypeHelper.getInstance().getDetailName(mClothes.getCategory()));
	mCreateTimeTextView.setText(Utils.getDateString(mClothes.getCreateTime()));
	mLastEditTextView.setText(Utils.getDateString(mClothes.getLastEdit()));
	ImageLoader.getInstance().displayImage("http://" + mClothes.getImg(), mImgImageView,Utils.buildNoneDisplayImageOptions());
	mClothesDetailAdapter.notifyDataSetChanged();
	
}


private void initView() {
	// TODO Auto-generated method stub
	mSuitList = new ArrayList<Suit>();
	mClothesDetailAdapter = new ClothesDetailAdapter(mSuitList, this);
	mSuitListView.setAdapter(mClothesDetailAdapter);
	
	bindEvents();
	notifyPage();
	
}
private void notifyPage() {
	// TODO Auto-generated method stub
	
}
private void bindEvents() {
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
	mSuitListView = (GridView)findViewById(R.id.activity_clothes_detail_suitList);
}
}
