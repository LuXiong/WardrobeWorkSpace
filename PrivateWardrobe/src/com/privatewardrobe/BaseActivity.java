package com.privatewardrobe;

/**
 * @author Dean
 * This is the base activity of the project, every activity with ActionBars must extends this Activity
 */
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.privatewardrobe.ActionBar.ActionItem;
import com.privatewardrobe.control.DrawView.RefreshListener;

public class BaseActivity extends FragmentActivity {
	// the top actionbar
	protected ActionBar mActionBar;
	// the content layout, who inherit this activity must use setContentView()
	// to init this layout
	private FrameLayout mContentLayout;
	// the layout to listen the touch event
	private RelativeLayout mBaseLayout;
	// control wheather inherit activity can refresh
	private boolean hasRefresh = false;
	// quick load ImageView
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		initBaseView();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
	}

	private void initBaseView() {
		setContentViewInner(R.layout.activity_base);
		mBaseLayout = (RelativeLayout) findViewById(R.id.base_layout);
		mContentLayout = (FrameLayout) findViewById(R.id.base_content_layout);
		ViewGroup view = (ViewGroup) findViewById(R.id.action_bar_layout);
		mActionBar = new ActionBar(this, view);
		mActionBar.setOnItemClickListener(mItemClickListener);

		mBaseLayout.setOnTouchListener(baseLayoutTouchListener);
	}

	private OnTouchListener baseLayoutTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (hasRefresh) {
				return mActionBar.onTouch(event);
			} else {
				return false;
			}

		}
	};

	/**
	 * 
	 * @param itemId
	 * @param item
	 * 
	 * Ĭ������˷��ط����������ʹ���벻Ҫ�������е���super����
	 */
	protected void onActionBarItemSelected(int itemId, ActionItem item) {
		if (itemId == ActionBar.LEFT_ID) {
			BaseActivity.this.finish();
		}
	}

	public void setContentViewInner(int resId) {
		super.setContentView(resId);
	}

	private ActionBar.OnItemClickListener mItemClickListener = new ActionBar.OnItemClickListener() {
		@Override
		public void onItemClick(int itemId, ActionItem item) {
			BaseActivity.this.onActionBarItemSelected(itemId, item);
		}
	};

	@Override
	public void setContentView(int layoutResID) {
		mContentLayout.removeAllViews();
		getLayoutInflater().inflate(layoutResID, mContentLayout);
	}

	@Override
	public void setContentView(View view) {
		mContentLayout.removeAllViews();
		mContentLayout.addView(view);
	}

	protected void completeRefresh() {
		mActionBar.completeRefresh();
	}

	protected void setRefreshListener(RefreshListener listener) {
		mActionBar.setOnRefreshListener(listener);
	}

	public boolean isHasRefresh() {
		return hasRefresh;
	}

	public void setHasRefresh(boolean hasRefresh) {
		this.hasRefresh = hasRefresh;
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		mContentLayout.removeAllViews();
		mContentLayout.addView(view, params);
	}

	public ActionBar getMyActionBar() {
		return mActionBar;
	}
}
