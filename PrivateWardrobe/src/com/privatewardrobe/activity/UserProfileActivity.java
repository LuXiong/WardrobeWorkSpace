package com.privatewardrobe.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;

import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.R;
import com.privatewardrobe.model.Suit;
import com.privatewardrobe.model.User;


public class UserProfileActivity extends BaseActivity{
	final static public String EXTRA_INPUT = "user";
	private User mUser;
	
	private TextView mNameTextView,mPhoneTextView,mCreateTimeTextView;
	private ListView mSuitList;
	private ImageView mAvatarImageView;
	
	private ArrayList<Suit> mSuitDList;

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mActionBar = getMyActionBar();
		mActionBar.setLeftDrawable(null);
		setContentView(R.layout.activity_userprofile);
		findView();
		initView();
		loadData();
	}

	private void loadData() {
		// TODO Auto-generated method stub
		mUser = (User) getIntent().getSerializableExtra(
				UserProfileActivity.EXTRA_INPUT);
		notifyDatasetChanged();
		
	}

	private void notifyDatasetChanged() {
		// TODO Auto-generated method stub
		
	}

	private void initView() {
		// TODO Auto-generated method stub
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
		mAvatarImageView = (ImageView) findViewById(R.id.activity_userprofile_avatar);
		mNameTextView = (TextView) findViewById(R.id.activity_userprofile_name);
		mPhoneTextView = (TextView) findViewById(R.id.activity_userprofile_phone);
		mCreateTimeTextView = (TextView) findViewById(R.id.activity_userprofile_createTime);
		mSuitList = (ListView) findViewById(R.id.activity_userproflie_suit_list);
	}
}
