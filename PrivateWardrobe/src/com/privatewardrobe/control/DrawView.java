package com.privatewardrobe.control;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
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
import android.widget.TextView;

import com.privatewardrobe.R;
import com.privatewardrobe.control.RectView.OnRectDrawListener;

public class DrawView extends LinearLayout {
	private float downY;
	private float lastY;
	private RectView topBarView;
	private RectView extendsBarView;
	private RectView bzlPaintView;
	private TextView loadingTextView;
	private int paintColor = getResources().getColor(R.color.base_color);
	private int topBarHeight = 100;
	private int extendsBarHeight = 100;
	private int bzlPaintHeight = 500;
	private int distance = 0;
	private int alpha = 0xaa;

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
		deView = mInflater.inflate(R.layout.control_drawview_bzl, null);
		addView(deView);
		topBarView = (RectView) deView.findViewById(R.id.rectView1);
		extendsBarView = (RectView) deView.findViewById(R.id.rectView2);
		bzlPaintView = (RectView) deView.findViewById(R.id.rectView3);
		loadingTextView = (TextView) deView.findViewById(R.id.loading_text);
	}

	private void showLoadingText() {
		loadingTextView.setVisibility(View.VISIBLE);
	}

	private void hideLoadingText() {
		loadingTextView.setVisibility(View.GONE);
	}

	private void setLoadingText(String text) {
		loadingTextView.setText(text);
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
		p.setColor(paintColor);
		p.setAlpha(alpha);
		Path path1 = new Path();
		path1.moveTo(0, 0);
		path1.quadTo(0, height, width / 2, height);
		path1.quadTo(width, height, width, 0);
		path1.lineTo(0, 0);
		path1.close();
		canvas.drawPath(path1, p);

		if (height > 80) {
			p.reset();
			p.setStyle(Paint.Style.FILL);
			p.setAntiAlias(true);
			p.setColor(getResources().getColor(R.color.red));
			canvas.drawCircle(width / 2, height - 50, 30, p);
		}

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
		p.setColor(paintColor);
		p.setAlpha(alpha);
		canvas.drawRect(0, 0, width, height, p);
	}

	protected void drawTopBar(Canvas canvas) {
		int width = canvas.getWidth();
		int height = topBarHeight;
		Paint p = new Paint();
		p.setStyle(Paint.Style.FILL);
		p.setAntiAlias(true);
		p.setColor(paintColor);
		p.setAlpha(alpha);
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
		if (isRefreshing && extendsBarView.getHeight() > 0) {
			return false;
		} else {
			isRefreshing = false;
		}
		boolean result = false;

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			downY = (int) event.getY();
			lastY = event.getY();
			distance = 0;
			result = true;
		}

		if (event.getAction() == MotionEvent.ACTION_MOVE) {

			distance = (int) (event.getY() - downY);
			if (distance > extendsBarHeight) {
				showLoadingText();
				setLoadingText("下拉刷新");
			}
			if (distance > topBarHeight + extendsBarHeight + bzlPaintHeight) {
				result = true;
			}
			if (distance > 300) {
				setLoadingText("松开手指刷新");
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

		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			isRefreshing = true;
			result = true;
			Log.v("xionglu", "distance:" + distance);
			if (distance > topBarHeight + extendsBarHeight + bzlPaintHeight
					&& listener != null) {
				listener.refreshBegin();

			}
			if (distance > 0) {
				startBziAnimation();
			}

		}

		return result;
	}

	private void startBziAnimation() {
		ValueAnimator animation = ValueAnimator.ofInt(bzlPaintView.getHeight(),
				0);
		animation.setDuration(500);
		animation.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				setBzlHeight((Integer) animation.getAnimatedValue());
				setViewHeight((Integer) extendsBarHeight + topBarHeight
						+ (Integer) animation.getAnimatedValue());
			}
		});
		animation.addListener(bzlScaleAnmiListener);
		animation.setInterpolator(new DecelerateInterpolator(3.0f));
		animation.start();
	}

	private void startExtendsAnimation() {
		if (extendsBarView.getHeight() > 0) {
			ValueAnimator animation = ValueAnimator.ofInt(
					extendsBarView.getHeight(), 0);
			animation.setDuration(500);
			animation.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					setExtendsBarHeight((Integer) animation.getAnimatedValue());
					setViewHeight(topBarHeight
							+ (Integer) animation.getAnimatedValue());
				}
			});

			animation.addListener(extendBarScaleAnmiListener);
			animation.start();
		}

	}

	private AnimatorListener bzlScaleAnmiListener = new AnimatorListener() {

		@Override
		public void onAnimationStart(Animator animation) {
			setLoadingText("");
		}

		@Override
		public void onAnimationRepeat(Animator animation) {

		}

		@Override
		public void onAnimationEnd(Animator animation) {
			setLoadingText("loading...");
			if (distance > 300) {

				if (listener != null) {
					listener.refreshing();
				}
			} else {
				completeRefresh();
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
			isRefreshing = false;
			if (listener != null) {
				listener.refreshComplete();
			}
		}

		@Override
		public void onAnimationCancel(Animator animation) {

		}
	};

	public void setRefreshListener(RefreshListener listener) {
		this.listener = listener;
	}

	public void completeRefresh() {
		hideLoadingText();
		startExtendsAnimation();
	}

	public interface RefreshListener {
		public void refreshBegin();

		public void refreshing();

		public void refreshComplete();
	}
}
