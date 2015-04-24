package com.privatewardrobe;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ActionBar {

	public static final int LEFT_ID = -1000;

	private ViewGroup mActionBarView;
	private TextView mTitleTextView;
	private LinearLayout mRightLayout;
	private ActionItem mLeftItem;
	private Context mContext;
	private SparseArray<ActionItem> mItemArray = new SparseArray<ActionBar.ActionItem>();
	private boolean isCollapse = false;
	private PopupWindow mPopwindow;
	private LinearLayout mPopLayout;
	private int textColor = Color.parseColor("#ffffff");

	public class ActionItem {

		private String mTitle;
		private View mActionView;
		private View mCustomActionView;
		private boolean isInvalid = false;
		private ActionBar mBar;
		private Drawable mDrawable;
		private int id;
		private boolean isVisible = true;

		public static final int SHOWACTION_SHOW = 1;
		public static final int SHOWACTION_COLLAPSE = 4;
		int showAction = SHOWACTION_SHOW;

		ActionItem(ActionBar mActionBar) {
			mBar = mActionBar;
		}

		ActionItem(ActionBar mActionBar, int id) {
			mBar = mActionBar;
			setId(id);
		}

		void setId(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		/**
		 * 设置完 可能需要调用 下 acitonbar的 notiftyitemchanged方法
		 * 
		 * @param title
		 */
		public void setTitle(String title) {
			this.mTitle = title;
		}

		public String getTitle() {
			return mTitle;
		}

		/**
		 * 设置完 可能需要调用 下 acitonbar的 notiftyitemchanged方法 该方法还有问题，先注释掉
		 * 
		 * @param title
		 */
		private void setActionView(View view) {
			if (mActionView != view) {
				mActionView = view;
				setInvalid();
			}
		}

		private void setCustomActionView(View view) {
			if (mCustomActionView != view) {
				mCustomActionView = view;
				setInvalid();
			}
		}

		public boolean getVisibility() {
			return this.isVisible;
		}

		public void setInvisible() {
			this.isVisible = false;
			this.getView().setVisibility(View.GONE);
		}

		/**
		 * 设置完 可能需要调用 下 acitonbar的 notiftyitemchanged方法
		 * 
		 * @param title
		 */
		public void setShowAction(int showAction) {
			if (showAction != SHOWACTION_COLLAPSE
					&& showAction != SHOWACTION_SHOW) {
				throw new IllegalArgumentException(
						"必须是SHOWACTION_COLLAPSE或者SHOWACTION_SHOW");
			}
			this.showAction = showAction;
		}

		public int getShowAction() {
			return this.showAction;
		}

		/**
		 * 设置完 可能需要调用 下 acitonbar的 notiftyitemchanged方法
		 * 
		 * @param title
		 */
		public void setVisible() {
			this.isVisible = true;
			this.getView().setVisibility(View.VISIBLE);
		}

		// 还没有写完。。。先private掉,不暴露
		private void setInvalid() {
			isInvalid = true;
		}

		/**
		 * 设置完 可能需要调用 下 acitonbar的 notiftyitemchanged方法
		 * 
		 * @param title
		 */
		public void setDrawable(Drawable drawable) {
			mDrawable = drawable;
		}

		/**
		 * 设置完 可能需要调用 下 acitonbar的 notiftyitemchanged方法
		 * 
		 * @param title
		 */
		public void setDrawable(int resId) {
			Drawable drawable = null;
			try {
				drawable = mContext.getResources().getDrawable(resId);
			} catch (NotFoundException e) {
			} finally {
				setDrawable(drawable);
			}
		}

		View getView() {
			if (mCustomActionView != null) {
				return mCustomActionView;
			}
			if (mActionView == null) {
				if (mDrawable != null) {
					mActionView = LayoutInflater.from(mContext).inflate(
							R.layout.image_button, null);
				} else {
					mActionView = LayoutInflater.from(mContext).inflate(
							R.layout.text_button, null);
					((TextView) mActionView).setTextColor(textColor);
				}
			}
			if (mActionView instanceof ImageView) {
				((ImageView) mActionView).setImageDrawable(mDrawable);
			} else if (mActionView instanceof TextView) {
				((TextView) mActionView).setText(this.mTitle);
			}
			return mActionView;
		}

	}

}
