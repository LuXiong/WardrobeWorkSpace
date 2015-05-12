package com.privatewardrobe.activity;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.ActionBar.ActionItem;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.R;
import com.privatewardrobe.control.DrawView.RefreshListener;
import com.privatewardrobe.fragment.ClothesFragment;
import com.privatewardrobe.fragment.MoreFragment;
import com.privatewardrobe.fragment.SuitFragment;

public class MainActivity extends BaseActivity {

	private final static String TAB_FIRST = "firstTab";
	private final static String TAB_SECOND = "secondTab";
	private final static String TAB_THIRD = "thirdTab";

	public final static String TAB = "tab";
	private String mCurrentTag = TAB_FIRST;
	private static String[] titles = new String[] { "衣橱", "搭配", "更多" };
	public ActionBar mActionBar;

	private Bundle mSavedInstanceState;
	private TabManager mTabManager;
	private TabHost mTabHost;
	private long mExitTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mSavedInstanceState = savedInstanceState;
		mActionBar = getMyActionBar();
		setContentView(R.layout.activity_main);
		setHasRefresh(true);
		setRefreshListener(mRefreshListener);
		findView();
		initView();
	}

	private void findView() {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mTabManager = new TabManager(this, mTabHost, android.R.id.tabcontent);
		mTabManager
				.addTab(mTabHost.newTabSpec(TAB_FIRST).setIndicator(
						View.inflate(MainActivity.this,
								R.layout.activity_main_tab_host_clothes, null)),
						MainActivity.FirstFragment.class, null);
		mTabManager.addTab(
				mTabHost.newTabSpec(TAB_SECOND).setIndicator(
						View.inflate(MainActivity.this,
								R.layout.activity_main_tab_host_suit, null)),
				MainActivity.SecondFragment.class, null);
		mTabManager.addTab(
				mTabHost.newTabSpec(TAB_THIRD).setIndicator(
						View.inflate(MainActivity.this,
								R.layout.activity_main_tab_host_more, null)),
				MainActivity.ThirdFragment.class, null);
	}

	private void initView() {
		Intent intent = getIntent();
		// read last tab
		if (this.mSavedInstanceState != null) {
			mCurrentTag = this.mSavedInstanceState.getString(TAB);
		}
		if (intent != null) {
			mCurrentTag = intent.getStringExtra(TAB);
		}
		if (TextUtils.isEmpty(mCurrentTag)) {
			mCurrentTag = TAB_FIRST;
		}
		mTabHost.setCurrentTabByTag(mCurrentTag);
	}

	public static class FirstFragment extends ClothesFragment {

	}

	public static class SecondFragment extends SuitFragment {

	}

	public static class ThirdFragment extends MoreFragment {

	}

	@Override
	protected void onActionBarItemSelected(int itemId, ActionItem item) {
		if (itemId == 0) {
			if (mCurrentTag == TAB_FIRST) {
				Intent intent = new Intent(MainActivity.this,
						ClothesCreateActivity.class);
				startActivity(intent);
			}

		}
		super.onActionBarItemSelected(itemId, item);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(TAB, mTabHost.getCurrentTabTag());
		super.onSaveInstanceState(outState);
	}

	private RefreshListener mRefreshListener = new RefreshListener() {

		@Override
		public void refreshing() {
			(new Handler()).postDelayed(new Runnable() {

				@Override
				public void run() {
					completeRefresh();

				}
			}, 2000);
		}

		@Override
		public void refreshComplete() {
//			setIntercept(false);
		}

		@Override
		public void refreshBegin() {

		}
	};

	public static class TabManager implements TabHost.OnTabChangeListener {
		private final MainActivity mActivity;
		private final TabHost mTabHost;
		private final int mContainerId;
		private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
		TabInfo mLastTab;

		static final class TabInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;
			private Fragment fragment;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabManager(MainActivity activity, TabHost tabHost,
				int containerId) {
			mActivity = activity;
			mTabHost = tabHost;
			mContainerId = containerId;
			mTabHost.setOnTabChangedListener(this);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			String tag = tabSpec.getTag();
			tabSpec.setContent(new DummyTabFactory(mActivity));
			TabInfo info = new TabInfo(tag, clss, args);
			info.fragment = mActivity.getSupportFragmentManager()
					.findFragmentByTag(tag);
			if (info.fragment != null && !info.fragment.isHidden()) {
				FragmentTransaction ft = mActivity.getSupportFragmentManager()
						.beginTransaction();
				ft.hide(info.fragment);
				ft.commit();
			}

			mTabs.put(tag, info);

			mTabHost.addTab(tabSpec);
		}

		@Override
		public void onTabChanged(String tabId) {
			mActivity.mCurrentTag = tabId;
			mActivity.mActionBar.setAllRightItemVisiblity(View.VISIBLE);
			if (tabId.equals(TAB_FIRST)) {
				mActivity.mActionBar.setTitle(titles[0]);
				mActivity.mActionBar.removeAllActionItem();
				mActivity.mActionBar.addActionItem(0, null,
						R.drawable.icon_add, ActionItem.SHOWACTION_SHOW);
				mActivity.mActionBar.getLeftItem().setInvisible();
				mActivity.mActionBar.notifyItemChange();
			}

			else if (tabId.equals(TAB_SECOND)) {
				mActivity.mActionBar.setTitle(titles[1]);
				mActivity.mActionBar.removeAllActionItem();
				mActivity.mActionBar.addActionItem(0, null,
						R.drawable.icon_add, ActionItem.SHOWACTION_SHOW);
				mActivity.mActionBar.getLeftItem().setInvisible();
				mActivity.mActionBar.notifyItemChange();
			} else if (tabId.equals(TAB_THIRD)) {
				mActivity.mActionBar.setTitle(titles[2]);
				mActivity.mActionBar.removeAllActionItem();
				mActivity.mActionBar.getLeftItem().setInvisible();
				mActivity.mActionBar.notifyItemChange();
			}
			TabInfo newTab = mTabs.get(tabId);

			if (mLastTab != newTab) {
				FragmentTransaction ft = mActivity.getSupportFragmentManager()
						.beginTransaction();
				if (mLastTab != null) {
					if (mLastTab.fragment != null) {
						ft.hide(mLastTab.fragment);

					}
				}
				if (newTab != null) {
					if (newTab.fragment == null) {
						newTab.fragment = Fragment.instantiate(mActivity,
								newTab.clss.getName(), newTab.args);
						ft.add(mContainerId, newTab.fragment, newTab.tag);
					} else {
						ft.show(newTab.fragment);
					}
				}

				mLastTab = newTab;
				ft.commit();
				mActivity.getSupportFragmentManager()
						.executePendingTransactions();
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
