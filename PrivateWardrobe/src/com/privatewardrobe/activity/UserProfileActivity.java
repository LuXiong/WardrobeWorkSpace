package com.privatewardrobe.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.R;
import com.privatewardrobe.adapter.ShareListAdapter;
import com.privatewardrobe.adapter.ShareListAdapter.ShareCommentListener;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.ShareBusiness;
import com.privatewardrobe.business.SuitBusiness;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.Clothes;
import com.privatewardrobe.model.Share;
import com.privatewardrobe.model.Suit;
import com.privatewardrobe.model.User;


public class UserProfileActivity extends BaseActivity{
	final static public String EXTRA_INPUT = "user";

	
	private TextView mNameTextView,mPhoneTextView,mCreateTimeTextView;
	private ListView mShareListView;
	private ImageView mAvatarImageView;
	
	private ArrayList<Share> mShareList;
	private ShareListAdapter mShareListAdapter;
	private User mUser;

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
		Intent intent = getIntent();

		User user = (User) intent
				.getSerializableExtra(EXTRA_INPUT);
		
		if (user != null) {
			mUser = user;
			ShareBusiness shareBusiness = new ShareBusiness();
			shareBusiness.queryShareByUserId(mUser.getUid(),new BusinessListener<Share>(){
				@Override
				public void onSuccess(ArrayList<Share> sharelist) {
					mShareList.clear();
					mShareList.addAll(sharelist);
					notifyDataSetChanged();
				}
			});
		}
		notifyDataSetChanged();
		
	}

	private void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		mNameTextView.setText(mUser.getName());
		mCreateTimeTextView.setText(Utils.getDateString(mUser.getCreateTime()));
		mPhoneTextView.setText(mUser.getPhone());
		ImageLoader.getInstance().displayImage("http://" + mUser.getAvatar(), mAvatarImageView);
		mShareListAdapter.notifyDataSetChanged();
		
	}

//	private ShareCommentListener listener = new ShareCommentListener() {
//
//		@Override
//		public void onComment(int which) {
//			// TODO Auto-generated method stub
//
//		}
//	};

	private void initView() {
		// TODO Auto-generated method stub
		mShareList = new ArrayList<Share>();
		mShareListAdapter = new ShareListAdapter(this, mShareList, null);
		mShareListView.setAdapter(mShareListAdapter);
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
		mShareListView = (ListView) findViewById(R.id.activity_userproflie_share_list);
	}
}
