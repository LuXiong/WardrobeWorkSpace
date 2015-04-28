package com.privatewardrobe;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.privatewardrobe.control.DrawView;
import com.privatewardrobe.control.DrawView.RefreshListener;

public class ActionBar {

	public static final int LEFT_ID = -1000;

	private ViewGroup mActionBarView;
	private TextView mTitleTextView;
	private LinearLayout mRightLayout;
	private DrawView mPullView;
	private ActionItem mLeftItem;
	private Context mContext;
	private SparseArray<ActionItem> mItemArray = new SparseArray<ActionBar.ActionItem>();
	private boolean isCollapse = false;
	private PopupWindow mPopupWindow;
	private LinearLayout mPopLayout;
	private AnimationSet appearAnim, disappearAnim;
	private int textColor = Color.parseColor("#ffffff");

	public ActionBar(Context context, ViewGroup actionBar) {
		this.mContext = context;
		this.mActionBarView = actionBar;
		this.mPullView = (DrawView) actionBar.findViewById(R.id.actionbar_bg);
		this.mTitleTextView = (TextView) actionBar
				.findViewById(R.id.action_bar_title_text);
		this.mRightLayout = (LinearLayout) actionBar
				.findViewById(R.id.actiong_bar_right_layout);
		this.mLeftItem = newActionItem(LEFT_ID);
		this.mLeftItem.setCustomActionView(actionBar
				.findViewById(R.id.action_bar_left_btn));
		this.mPopLayout = new LinearLayout(mContext);
		this.mPopLayout.setBackgroundResource(android.R.color.white);
		this.mPopLayout.setOrientation(LinearLayout.VERTICAL);
		mPopLayout.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		this.mPopupWindow = new PopupWindow(mPopLayout,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT);
		this.mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
		this.mPopupWindow.setOutsideTouchable(true);
		this.mPopupWindow.setFocusable(true);
		this.mPopupWindow.update();
	}

	public boolean isVisible() {
		return mActionBarView.getVisibility() == View.VISIBLE ? true : false;
	}

	public void setAllRightItemVisiblity(int visible) {
		mRightLayout.setVisibility(visible);
	}

	public void completeRefresh() {
		mPullView.completeRefresh();
	}
	
	public void setOnRefreshListener(RefreshListener listener){
		mPullView.setRefreshListener(listener);
	}

	public void setVisible() {
		if (mActionBarView.getVisibility() != View.VISIBLE) {
			if (appearAnim == null) {
				appearAnim = (AnimationSet) AnimationUtils.loadAnimation(
						mContext, R.anim.translate_down_in);
				appearAnim.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
					}

					@Override
					public void onAnimationEnd(Animation animation) {
						mActionBarView.setVisibility(View.VISIBLE);
					}
				});
			}
			mActionBarView.startAnimation(appearAnim);
		}
	}

	public boolean onTouch(MotionEvent event) {
		if (mActionBarView.getVisibility() == View.VISIBLE) {
			return mPullView.onTouch(event);
		}
		return false;
	}

	public void setInvisible() {
		if (mActionBarView.getVisibility() == View.VISIBLE) {
			if (disappearAnim == null) {
				disappearAnim = (AnimationSet) AnimationUtils.loadAnimation(
						mContext, R.anim.translate_up_out);
				disappearAnim.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
					}

					@Override
					public void onAnimationEnd(Animation animation) {
						mActionBarView.setVisibility(View.GONE);
					}
				});
			}
			mActionBarView.startAnimation(disappearAnim);
		}
	}

	private ActionItem findItemByView(View view) {
		// 折叠的 actionitem 寄存在tag中
		Object object = view.getTag();
		if (object != null && object instanceof ActionItem) {
			if (((ActionItem) object).getShowAction() == ActionItem.SHOWACTION_COLLAPSE) {
				return (ActionItem) object;
			}
		}
		// 不折叠的 在mItemArray寄存
		for (int i = 0; i < mItemArray.size(); i++) {
			ActionItem item = mItemArray.valueAt(i);
			if (item.getView() == view) {
				return item;
			}
		}
		// 左右特殊的 直接返回
		if (view == mLeftItem.getView()) {
			return mLeftItem;
		}
		return null;
	}

	public void notifyItemChange() {
		mRightLayout.removeAllViews();
		mPopLayout.removeAllViews();
		this.isCollapse = false;

		for (int i = 0; i < mItemArray.size(); i++) {
			ActionItem item = mItemArray.valueAt(i);
			if (!item.getVisibility()) {
				continue;
			}

			if (item.getShowAction() == ActionItem.SHOWACTION_COLLAPSE) {
				Button child = new Button(mContext);
				child.setTag(item);// 折叠的存在view中
				child.setText(item.getTitle());
				child.setTextColor(textColor);
				child.setBackgroundResource(android.R.color.transparent);
				this.isCollapse = true;
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				child.setLayoutParams(params);
				mPopLayout.addView(child);
				child.setOnClickListener(mListener);
			} else {
				View child = item.getView();
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				params.gravity = Gravity.CENTER_VERTICAL;
				child.setLayoutParams(params);
				mRightLayout.addView(child);
				child.setOnClickListener(mListener);
			}

		}

		if (isCollapse) {
			ImageView imageView = (ImageView) LayoutInflater.from(mContext)
					.inflate(R.layout.image_button, null);
			imageView.setImageResource(R.drawable.collapse_selector);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!mPopupWindow.isShowing()) {
						mPopupWindow.showAsDropDown(mRightLayout);
					} else {
						mPopupWindow.dismiss();
					}
				}
			});
			mRightLayout.addView(imageView);
		}
		mLeftItem.getView().setOnClickListener(mListener);
		// mRightItem.getView().setOnClickListener(mListener);
		mRightLayout.invalidate();
	}

	public void removeActionItemById(int id) {
		mItemArray.remove(id);
		notifyItemChange();
	}

	// actionbar添加移除全部右侧item的方法
	public void removeAllActionItem() {
		mItemArray.clear();
		notifyItemChange();
	}

	public void addActionItem(int id, String title, Drawable drawable) {
		if (mItemArray.get(id) != null || id == LEFT_ID) {
			throw new RuntimeException("id 已经存在");
		}
		ActionItem item = newActionItem(id);
		item.setId(id);
		item.setTitle(title);
		item.setDrawable(drawable);
		mItemArray.append(id, item);
		notifyItemChange();
	}

	/**
	 * 添加item
	 * 
	 * @param id
	 * @param title
	 * @param drawableResId
	 */
	public void addActionItem(int id, String title, int drawableResId) {
		if (mItemArray.get(id) != null || id == LEFT_ID) {
			throw new RuntimeException("id 已经存在");
		}
		ActionItem item = newActionItem(id);
		item.setTitle(title);
		item.setDrawable(drawableResId);
		mItemArray.append(id, item);
		notifyItemChange();
	}

	/**
	 * 模仿android原生actionbar设计思想，凡是item都必须有自己的title，图标可以没有也可以有。 如果是
	 * SHOWACTION_COLLAPSE则只显示title不显示图标，不论设置不设置图标。
	 * 如果是SHOWACTION_SHOW有图标显示图标，没图标就显示title。
	 * 
	 * @param id
	 * @param title
	 * @param drawableResId
	 *            设置成0不显示图标
	 * @param showAction
	 */
	public void addActionItem(int id, String title, int drawableResId,
			int showAction) {
		if (mItemArray.get(id) != null || id == LEFT_ID) {
			throw new RuntimeException("id 已经存在");
		}
		ActionItem item = newActionItem(id);
		item.setTitle(title);
		item.setDrawable(drawableResId);
		item.setShowAction(showAction);
		mItemArray.append(id, item);
		notifyItemChange();
	}

	/**
	 * 在使用自定义view 的情况下 使用，如果不是特殊情况最好不要用这个方法
	 * 
	 * @param id
	 * @param title
	 * @param costumeView
	 */
	public void addActionItem(int id, String title, View costumeView) {
		if (mItemArray.get(id) != null || id == LEFT_ID) {
			throw new RuntimeException("id 已经存在");
		}
		ActionItem item = newActionItem(id);
		item.setTitle(title);
		item.setCustomActionView(costumeView);
		mItemArray.append(id, item);
		notifyItemChange();
	}

	public ActionItem findActionItemById(int id) {
		if (id == LEFT_ID) {
			return mLeftItem;
		}
		return mItemArray.get(id);
	}

	public ActionItem getLeftItem() {
		return mLeftItem;
	}

	public void setTitle(String title) {
		mTitleTextView.setText(title);
	}

	public void setTitle(int resId) {
		mTitleTextView.setText(resId);
	}

	// public void setLeftTitle(String title) {
	// mLeftItem.setTitle(title);
	// }

	// public void setLeftTitle(int resId) {
	// mLeftItem.setTitle(mContext.getString(resId));
	// }

	public void setLeftDrawable(int resId) {
		((ImageButton) mLeftItem.getView()).setImageResource(resId);
		mLeftItem.setDrawable(resId);
	}

	public void setLeftDrawable(Drawable drawable) {
		((ImageButton) mLeftItem.getView()).setImageDrawable(drawable);
		mLeftItem.setDrawable(drawable);
	}

	public void setLeftItem(int resId, String title) {
		((ImageButton) mLeftItem.getView()).setImageResource(resId);
		mLeftItem.setTitle(title);
		mLeftItem.setDrawable(resId);
	}

	public void setLeftItem(Drawable drawable, String title) {
		((ImageButton) mLeftItem.getView()).setImageDrawable(drawable);
		mLeftItem.setDrawable(drawable);
	}

	private OnItemClickListener mDelegate = null;

	public void setOnItemClickListener(OnItemClickListener delegate) {
		mDelegate = delegate;
	}

	public interface OnItemClickListener {
		public void onItemClick(int itemId, ActionItem item);
	}

	private OnClickListener mListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mDelegate != null) {
				ActionItem item = findItemByView(v);
				if (item != null) {
					// if (item.getId() == RIGTH_ID && isCollapse) {
					//
					// }
					if (item.getShowAction() == ActionItem.SHOWACTION_COLLAPSE) {
						mPopupWindow.dismiss();
					}
					mDelegate.onItemClick(item.getId(), item);
				}
			}

		}
	};

	private ActionItem newActionItem(int id) {
		return new ActionItem(this, id);
	}

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
