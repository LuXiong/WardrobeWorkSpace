package com.privatewardrobe.activity;

import java.util.ArrayList;

import android.os.Bundle;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.R;
import com.privatewardrobe.control.LoadMoreListView;
import com.privatewardrobe.model.Share;

public class ShareListActivity extends BaseActivity {
	
	private ActionBar mActionBar;
	private LoadMoreListView mListView;
	private ArrayList<Share> mDataList;
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.activity_share_list);
		mActionBar = getMyActionBar();
		mActionBar.setTitle("¥Ó≈‰»¶");
		findView();
		initView();
		loadData();
	}
	private void findView() {
		// TODO Auto-generated method stub
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		
	}
	private void loadData() {
		// TODO Auto-generated method stub
		
	}
}
