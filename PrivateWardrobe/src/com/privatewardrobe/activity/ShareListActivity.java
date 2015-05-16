package com.privatewardrobe.activity;

import java.util.ArrayList;

import android.os.Bundle;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.ActionBar.ActionItem;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.R;
import com.privatewardrobe.adapter.ShareCommentListAdapter;
import com.privatewardrobe.adapter.ShareListAdapter;
import com.privatewardrobe.control.LoadMoreListView;
import com.privatewardrobe.model.Share;

public class ShareListActivity extends BaseActivity {

	private ActionBar mActionBar;
	private LoadMoreListView mListView;
	private ArrayList<Share> mDataList;
	
	private ShareListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.activity_share_list);
		mActionBar = getMyActionBar();
		mActionBar.setTitle("����Ȧ");
		mActionBar.addActionItem(0, null, R.drawable.icon_add,
				ActionItem.SHOWACTION_SHOW);
		findView();
		initView();
		loadData();
	}

	private void findView() {
		mListView = (LoadMoreListView) findViewById(R.id.activity_share_list_listview);
	}

	private void initView() {
		mDataList = new ArrayList<Share>();
		mAdapter = new ShareListAdapter(ShareListActivity.this,mDataList);
		mListView.setAdapter(mAdapter);
	}

	private void loadData() {
		//private ArrayList<Share> mShareList;

	}
}
