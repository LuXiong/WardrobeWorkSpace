package com.privatewardrobe.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.ActionBar.ActionItem;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.PWApplication;
import com.privatewardrobe.PWConstant;
import com.privatewardrobe.R;
import com.privatewardrobe.adapter.ShareListAdapter;
import com.privatewardrobe.adapter.ShareListAdapter.ShareCommentListener;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.CommentOnSendListener;
import com.privatewardrobe.business.ShareBusiness;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.control.CommentFragment;
import com.privatewardrobe.control.DrawView.RefreshListener;
import com.privatewardrobe.control.LoadMoreListView;
import com.privatewardrobe.control.LoadMoreListView.OnLoadMoreListener;
import com.privatewardrobe.model.Comment;
import com.privatewardrobe.model.Share;

public class ShareListActivity extends BaseActivity {

	private ActionBar mActionBar;
	private LoadMoreListView mListView;
	private ArrayList<Share> mDataList;

	private ShareListAdapter mAdapter;
	private AlertDialog mLoadingDialog;
	private CommentFragment commentFragment;
	private RelativeLayout fragmentLayout;

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
		FragmentManager fm = getSupportFragmentManager();
		commentFragment = (CommentFragment) fm
				.findFragmentById(R.id.activity_share_comment_send_fragment);
		fragmentLayout = (RelativeLayout) findViewById(R.id.activity_share_comment_fragment_wraplayout);
	}

	@Override
	protected void onActionBarItemSelected(int itemId, ActionItem item) {
		if (itemId == 0) {
			Intent intent = new Intent(ShareListActivity.this,
					ShareCreateActivity.class);
			startActivityForResult(intent, ShareCreateActivity.REQUEST_CODE);
		}
		super.onActionBarItemSelected(itemId, item);
	}

	private void initView() {
		mDataList = new ArrayList<Share>();
		mAdapter = new ShareListAdapter(ShareListActivity.this, mDataList,
				new ShareCommentListener() {

					@Override
					public void onComment(int which) {
						fragmentLayout.setVisibility(View.VISIBLE);
						commentFragment
								.setCommentClickListener(new MCommentOnSendListener(
										which));
					}
				});
		mListView.setAdapter(mAdapter);
		mLoadingDialog = Utils.buildLoadingDialog(this);
		hideSystemInputMethod();
		fragmentLayout.setVisibility(View.GONE);
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
				if (mDataList != null) {
					long time = mDataList.get(mDataList.size() - 1)
							.getCreateTime().getTime();
					loadShare(time, false);
				}
			}
		});
		// mListView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// fragmentLayout.setVisibility(View.VISIBLE);
		// }
		// });

	}

	// private CommentOnSendListener = new MCommentOnSendListener(1);
	// private CommentOnSendListener commentClickListener = new
	// MCommentOnSendListener(); {
	// int position;
	// @Override
	// public void OnSendClick(View v) {
	// if (TextUtils.isEmpty(((EditText) v).getText().toString())) {
	// return;
	// }
	// FeedBusiness service = new FeedBusiness();
	// final String commentStr = ((EditText) v).getText().toString();
	// final View tmpV = v;
	// service.addComment(feedId, commentStr, replayUserId,
	// new BusinessListener<Comment>() {
	// @Override
	// public void onStart() {
	// Comment comment = new Comment();
	// User me = HSApplication.getInstance()
	// .getCurrentUser();
	// comment.setUser(me);
	// comment.setContent(commentStr);
	// comment.setCreateTime(new Date());
	// if (replayUser != null) {
	// comment.setReplyUser(replayUser);
	// }
	// commentList.add(comment);
	// notifyDataSetChanged();
	// ((EditText) tmpV).setText("");
	// commentFinish();
	// }
	// });
	// }
	// };
	public void hideSystemInputMethod() {
		if (commentFragment != null) {
			commentFragment.hiddenSystemInputMethod();
		}
	}

	public void popInputMethod() {
		(new Handler()).postDelayed(new Runnable() {
			public void run() {
				EditText editText = commentFragment.getEditText();
				InputMethodManager imm = (InputMethodManager) ShareListActivity.this
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
				editText.setFocusable(true);
				editText.requestFocus();
			}
		}, 50);
	}

	private void loadData() {
		ArrayList<Share> shareList = (ArrayList<Share>) PWApplication
				.getInstance().getCache(PWConstant.CACHE_SHARE);
		if (shareList != null) {
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

	private class MCommentOnSendListener implements CommentOnSendListener {
		int which;

		public MCommentOnSendListener(int i) {
			this.which = i;
		}

		@Override
		public void OnSendClick(View v) {
			fragmentLayout.setVisibility(View.GONE);
			final String commentStr = ((EditText) v).getText().toString();
			ShareBusiness shareBusiness = new ShareBusiness();
			shareBusiness.addComment(mDataList.get(which).getShareId(),
					PWApplication.getInstance().getUserId(), commentStr,
					new BusinessListener<Comment>() {

					});
		}

	}

	private void loadShare(long time, final boolean isClear) {
		ShareBusiness shareBusiness = new ShareBusiness();
		shareBusiness.updateCurrentShare(PWApplication.getInstance().getUserId(),time, new BusinessListener<Share>() {
			@Override
			public void onSuccess(ArrayList<Share> list) {
				if (list != null) {
					if (isClear) {
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

	public void notifyDataSetChanged() {
		mAdapter.notifyDataSetChanged();
	}
}
