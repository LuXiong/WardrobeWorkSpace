package com.privatewardrobe.control;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class RectView extends View {

	private OnRectDrawListener listener;
	private int mHeight = 0;

	public void setHeight(int height) {
		if (mHeight != height) {
			LayoutParams lp = getLayoutParams();
			lp.height = height;
			mHeight = height;
			setLayoutParams(lp);
		}

	}

	public RectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
	}

	public RectView(Context context) {
		super(context);
		setWillNotDraw(false);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (listener != null) {
			listener.draw(canvas);
		}
	}

	public void setOnRectDrawListener(OnRectDrawListener listener) {
		this.listener = listener;
	}

	public interface OnRectDrawListener {
		public void draw(Canvas canvas);
	}
}
