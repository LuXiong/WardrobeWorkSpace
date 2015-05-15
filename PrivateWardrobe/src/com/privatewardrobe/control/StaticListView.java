package com.privatewardrobe.control;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class StaticListView extends ListView {
	public StaticListView(Context context) {
		super(context);
	}

	public StaticListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StaticListView(Context context, AttributeSet attrs, int defStyle) {
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
