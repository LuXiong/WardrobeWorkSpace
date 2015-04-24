package com.privatewardrobe.control;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.privatewardrobe.R;
import com.privatewardrobe.control.RectView.OnRectDrawListener;


public class DrawView extends LinearLayout {
	private float downY;
	private float lastY;
	private RectView topBarView;
	private RectView extendsBarView;
	private RectView bzlPaintView;
	private int paintColor = Color.RED;
	private String colorString = "#77ff99cc";
	private int topBarHeight = 100;
	private int extendsBarHeight = 100;
	private int bzlPaintHeight = 500;

	private View deView;
	private Context context;

	private boolean isRefreshing = false;
	private boolean isRefreshFinish = false;
	private boolean isBeginRefresh = false;

	private RefreshListener listener;

	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		findView();
		initView();
	}

	private void findView() {
		LayoutInflater mInflater = LayoutInflater.from(context);
		deView = mInflater.inflate(R.layout.bzl, null);
		addView(deView);
		topBarView = (RectView) deView.findViewById(R.id.rectView1);
		extendsBarView = (RectView) deView.findViewById(R.id.rectView2);
		bzlPaintView = (RectView) deView.findViewById(R.id.rectView3);
	}

	private void initView() {
		topBarView.setOnRectDrawListener(new OnRectDrawListener() {

			@Override
			public void draw(Canvas canvas) {
				drawTopBar(canvas);
			}
		});
		extendsBarView.setOnRectDrawListener(new OnRectDrawListener() {

			@Override
			public void draw(Canvas canvas) {
				drawExtendsBar(canvas);
			}
		});
		bzlPaintView.setOnRectDrawListener(new OnRectDrawListener() {

			@Override
			public void draw(Canvas canvas) {
				drawBzl(canvas);
			}
		});
		setViewHeight(topBarHeight);
		setTopBarHeight();
		setExtendsBarHeight(0);
		setBzlHeight(0);
	}

	private void drawBzl(Canvas canvas) {
		Paint p = new Paint();
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		if (height > 500) {
			height = 500;
		}
		p.reset();
		p.setStyle(Paint.Style.FILL);
		p.setAntiAlias(true);
		p.setColor(Color.parseColor(colorString));
		Path path1 = new Path();
		path1.moveTo(0, 0);
		path1.quadTo(0, height, width / 2, height);
		path1.quadTo(width, height, width, 0);
		path1.lineTo(0, 0);
		path1.close();
		canvas.drawPath(path1, p);
	}

	protected void drawExtendsBar(Canvas canvas) {
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		if (height > extendsBarHeight) {
			height = extendsBarHeight;
		}
		Paint p = new Paint();
		p.setStyle(Paint.Style.FILL);
		p.setAntiAlias(true);
		p.setColor(Color.parseColor(colorString));
		canvas.drawRect(0, 0, width, height, p);
	}

	protected void drawTopBar(Canvas canvas) {
		int width = canvas.getWidth();
		int height = topBarHeight;
		Paint p = new Paint();
		p.setStyle(Paint.Style.FILL);
		p.setAntiAlias(true);
		p.setColor(Color.parseColor(colorString));
		canvas.drawRect(0, 0, width, height, p);
	}

	private void setBzlHeight(int height) {
		if (height >= 0) {
			if (height < bzlPaintHeight) {
				bzlPaintView.setHeight(height);
			} else {
				bzlPaintView.setHeight(bzlPaintHeight);
			}
		}
	}

	private void setExtendsBarHeight(int height) {
		if (height >= 0) {
			if (height < extendsBarHeight) {
				extendsBarView.setHeight(height);
			} else {
				extendsBarView.setHeight(extendsBarHeight);
			}
		}
	}

	private void setViewHeight(int distance) {
		ViewGroup.LayoutParams lp = deView.getLayoutParams();
		if (distance > topBarHeight) {
			lp.height = distance;
		} else {
			lp.height = topBarHeight;
		}
		deView.setLayoutParams(lp);
	}

	private void setTopBarHeight() {
		topBarView.setHeight(100);
	}

	public boolean onTouch(MotionEvent event) {
		if (isRefreshing) {
			return false;
		}
		boolean result = false;
		int distance = 0;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			downY = (int) event.getY();
			lastY = event.getY();
			result = true;
		}

		if (event.getAction() == MotionEvent.ACTION_MOVE) {

			distance = (int) (event.getY() - downY);
			if (distance > topBarHeight + extendsBarHeight + bzlPaintHeight) {
				return true;
			}
			if (distance > 0) {
				if (event.getY() - lastY > 0.5) {
					setViewHeight(distance + topBarHeight);
					setExtendsBarHeight(distance);
					setBzlHeight(distance - extendsBarHeight);
				}
			} else {
				setExtendsBarHeight(0);
				setBzlHeight(0);
			}
			Log.v("xionglu", "event.getY()" + event.getY());
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (distance > topBarHeight + extendsBarHeight + bzlPaintHeight
					&& listener != null) {
				listener.refreshBegin();
			}
			isRefreshing = true;
			startBziAnimation();
		}

		return result;
	}

	private void startBziAnimation() {
		PropertyValuesHolder pvhY = PropertyValuesHolder
				.ofFloat("scaleY", 1, 0);
		ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(
				bzlPaintView, pvhY);
		bzlPaintView.setPivotY(0);
		animator.setDuration(500);
		animator.addListener(bzlScaleAnmiListener);
		animator.setInterpolator(new DecelerateInterpolator(2.0f));
		animator.start();
	}

	private void startExtendsAnimation() {
		bzlPaintView.setScaleY(1f);
		PropertyValuesHolder pvhY = PropertyValuesHolder
				.ofFloat("scaleY", 1, 0);
		ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(
				extendsBarView, pvhY);
		extendsBarView.setPivotY(0);
		animator.setDuration(500);
		animator.addListener(extendBarScaleAnmiListener);
		animator.start();
	}

	private AnimatorListener bzlScaleAnmiListener = new AnimatorListener() {

		@Override
		public void onAnimationStart(Animator animation) {

		}

		@Override
		public void onAnimationRepeat(Animator animation) {

		}

		@Override
		public void onAnimationEnd(Animator animation) {
			setBzlHeight(0);
			setViewHeight((int) extendsBarHeight + topBarHeight);
			startExtendsAnimation();
			if (listener != null) {
				listener.refreshing();
			}
		}

		@Override
		public void onAnimationCancel(Animator animation) {

		}
	};
	private AnimatorListener extendBarScaleAnmiListener = new AnimatorListener() {

		@Override
		public void onAnimationStart(Animator animation) {

		}

		@Override
		public void onAnimationRepeat(Animator animation) {

		}

		@Override
		public void onAnimationEnd(Animator animation) {
			setExtendsBarHeight(0);
			setViewHeight(topBarHeight);
			extendsBarView.setScaleY(1f);
			isRefreshing = false;
			if (listener != null) {
				listener.completeRefresh();
			}
		}

		@Override
		public void onAnimationCancel(Animator animation) {

		}
	};

	public void setRefreshListener(RefreshListener listener) {
		this.listener = listener;
	}

	public void refreshComplete() {

	}

	interface RefreshListener {
		public void refreshBegin();

		public void refreshing();

		public void completeRefresh();
	}
}
