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
import com.privatewardrobe.R;
import com.privatewardrobe.adapter.SuitDetailAdapter;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.ClothesBusiness;
import com.privatewardrobe.business.SuitBusiness;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.Clothes;
import com.privatewardrobe.model.Suit;

public class SuitDetailActivity extends BaseActivity{
	final static public String EXTRA_INPUT = "suit";
	private TextView mDescriptionTextView,mWeatherTextView,mOccasionTextView;
	private TextView mCreateTimeTextView,mLastEditTextView,mIsLike;
	private ImageView mImgImageView;
	private GridView mClothesListView;
	
	private SuitDetailAdapter mSuitDetailAdapter;
	private Suit mSuit;
	private ArrayList<Clothes> mClothesList;
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mActionBar = getMyActionBar();
		mActionBar.setLeftDrawable(null);
		setContentView(R.layout.activity_suit_detail);
		findView();
		initView();
		loadData();
	}

	private void loadData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();

		Suit suit = (Suit) intent
				.getSerializableExtra(EXTRA_INPUT);
		
		if (suit != null) {
			mSuit = suit;
			SuitBusiness suitBusiness = new SuitBusiness();
			suitBusiness.queryClothesBySuitId(mSuit.getId(), new BusinessListener<Clothes>(){
				@Override
				public void onSuccess(ArrayList<Clothes> clotheslist) {
					mClothesList.clear();
					mClothesList.addAll(clotheslist);
					notifyDataSetChanged();
				}
			});
		}		
		notifyDataSetChanged();
	}

	private void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		mDescriptionTextView.setText(mSuit.getDescription());
		mWeatherTextView.setText(SuitBusiness.checkWeather(mSuit.getWeather()));
		mOccasionTextView.setText(SuitBusiness.checkOccasion(mSuit.getOccasion()));
		mCreateTimeTextView.setText(Utils.getDateString(mSuit.getCreateTime()));
		mLastEditTextView.setText(Utils.getDateString(mSuit.getLastEdit()));
		ImageLoader.getInstance().displayImage("http://" + mSuit.getImg(), mImgImageView,Utils.buildNoneDisplayImageOptions());
		mSuitDetailAdapter.notifyDataSetChanged();
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		mClothesList = new ArrayList<Clothes>();
		mSuitDetailAdapter = new SuitDetailAdapter(mClothesList, this);
		mClothesListView.setAdapter(mSuitDetailAdapter);
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
		mDescriptionTextView = (TextView)findViewById(R.id.activity_suit_detail_description);
		mWeatherTextView = (TextView)findViewById(R.id.activity_suit_detail_weather);
		mOccasionTextView = (TextView)findViewById(R.id.activity_suit_detail_occasion);
		mCreateTimeTextView = (TextView)findViewById(R.id.activity_suit_detail_createTime);
		mLastEditTextView = (TextView)findViewById(R.id.activity_suit_detail_lastEdit);
		mIsLike = (TextView)findViewById(R.id.activity_suit_detail_isLike);
		mImgImageView = (ImageView)findViewById(R.id.activity_suit_detail_img);
		mClothesListView = (GridView)findViewById(R.id.activity_suit_detail_clothesList);
		
	}
}
