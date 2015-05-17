package com.privatewardrobe.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.ActionBar.ActionItem;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.PWApplication;
import com.privatewardrobe.PWConstant;
import com.privatewardrobe.R;
import com.privatewardrobe.adapter.ShareCommentListAdapter;
import com.privatewardrobe.adapter.ShareListAdapter;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.ShareBusiness;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.control.LoadMoreListView;
import com.privatewardrobe.control.DrawView.RefreshListener;
import com.privatewardrobe.control.LoadMoreListView.OnLoadMoreListener;
import com.privatewardrobe.fragment.ClothesFragment;
import com.privatewardrobe.fragment.SuitFragment;
import com.privatewardrobe.model.Share;

public class ShareListActivity extends BaseActivity {

	private ActionBar mActionBar;
	private LoadMoreListView mListView;
	private ArrayList<Share> mDataList;
	
	private ShareListAdapter mAdapter;
	private AlertDialog mLoadingDialog;

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.activity_share_list);
		mActionBar = getMyActionBar();
		mActionBar.setTitle("¥Ó≈‰»¶");
		mActionBar.addActionItem(0, null, R.drawable.icon_add,
				ActionItem.SHOWACTION_SHOW);
		setRefreshListener(mRefreshListener);
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
		mLoadingDialog = Utils.buildLoadingDialog(this);
		mListView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (mListView.getFirstVisiblePosition() == 0
						|| event.getAction() == MotionEvent.ACTION_UP) {
					mActionBar.onTouch(event);
				}
				return false;
			}
		});
		mListView.setOnLoadMoreListener(new OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				if(mDataList!=null){
					long time=mDataList.get(mDataList.size()-1).getCreateTime().getTime();
					loadShare(time, false);
				}
			}
		});
	}

	private void loadData() {
		ArrayList<Share> shareList = (ArrayList<Share>) PWApplication.getInstance().getCache(PWConstant.CACHE_SHARE);
		if(shareList!=null){
			mDataList.clear();
			mDataList.addAll(shareList);
			notifyDataSetChanged();
		}
		loadShare(System.currentTimeMillis(), true);

	}
	private RefreshListener mRefreshListener = new RefreshListener() {

		@Override
		public void refreshing() {
			mLoadingDialog.show();
			loadShare(System.currentTimeMillis(), true);
		}

		@Override
		public void refreshComplete() {
			
		}

		@Override
		public void refreshBegin() {
			
		}
	};
	private void loadShare(long time, final boolean isClear){
		ShareBusiness shareBusiness = new ShareBusiness();
		shareBusiness.updateCurrentShare(time, new BusinessListener<Share>(){
			@Override
			public void onSuccess(ArrayList<Share> list) {
				if(list!=null){
					if(isClear){
						mDataList.clear();
						completeRefresh();
					}
					mDataList.addAll(list);
					mLoadingDialog.dismiss();
					notifyDataSetChanged();
				}
			}
		});
	}

	private void notifyDataSetChanged() {
		mAdapter.notifyDataSetChanged();
	}
}
