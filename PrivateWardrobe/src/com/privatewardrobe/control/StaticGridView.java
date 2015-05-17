package com.privatewardrobe.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.GridView;

public class StaticGridView extends GridView {

	public StaticGridView(Context context) {
		super(context);
	}

	public StaticGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StaticGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
