package com.privatewardrobe.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class InterceptFrameLayout extends FrameLayout{
	private boolean intercept = false;;

	public InterceptFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		if(intercept){
//			return true;
//		}
		return super.onInterceptTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean result = super.onTouchEvent(event);
		if(event.equals(MotionEvent.ACTION_UP)){
			intercept = false;
		}
		return result;
//		if(intercept){
//			return true;
//		}else{
//			return result;
//		}
	}
	public void setIntercept(boolean b){
		intercept = b;
	}
}
