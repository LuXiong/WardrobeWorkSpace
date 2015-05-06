package com.privatewardrobe.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.privatewardrobe.R;
import com.privatewardrobe.adapter.WelcomeAdapter;

public class FunctionActivity extends Activity {
	private ViewPager mWelcomeViewPager;
	private ImageView mWelcomeImage0;
	private ImageView mWelcomeImage1;
	private ImageView mWelcomeImage2;
	private ImageView mWelcomeImage3;

	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		findView();
		initView();
	}

	private void findView() {
		mWelcomeViewPager = (ViewPager) findViewById(R.id.activity_welcome_view_viewPager);
		mWelcomeImage0 = (ImageView) findViewById(R.id.activity_welcome_tab_image0);
		mWelcomeImage1 = (ImageView) findViewById(R.id.activity_welcome_tab_image1);
		mWelcomeImage2 = (ImageView) findViewById(R.id.activity_welcome_tab_image2);
		mWelcomeImage3 = (ImageView) findViewById(R.id.activity_welcome_tab_image3);
	}

	private void initView() {
		bindEvents();
		notifyPage();
	}

	private void bindEvents() {
		mWelcomeViewPager.setOnPageChangeListener(mOnPageChangeListener);
	}

	private void notifyPage() {
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.avtivity_welcome_tab1, null);
		View view2 = mLi.inflate(R.layout.activity_welcome_tab2, null);
		View view3 = mLi.inflate(R.layout.activity_welcome_tab3, null);
		View view4 = mLi.inflate(R.layout.activity_welcome_tab4, null);
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		WelcomeAdapter mWelcomePagerAdapter = new WelcomeAdapter(views);
		mWelcomeViewPager.setAdapter(mWelcomePagerAdapter);
	}

	private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int page) {
			// 翻页时当前page,改变当前状态园点图片
			switch (page) {
			case 0:
				mWelcomeImage0.setImageDrawable(getResources().getDrawable(
						R.drawable.activity_welcome_dot_image1));
				mWelcomeImage1.setImageDrawable(getResources().getDrawable(
						R.drawable.activity_welcome_dot_image2));
				break;
			case 1:
				mWelcomeImage1.setImageDrawable(getResources().getDrawable(
						R.drawable.activity_welcome_dot_image1));
				mWelcomeImage0.setImageDrawable(getResources().getDrawable(
						R.drawable.activity_welcome_dot_image2));
				mWelcomeImage2.setImageDrawable(getResources().getDrawable(
						R.drawable.activity_welcome_dot_image2));
				break;
			case 2:
				mWelcomeImage2.setImageDrawable(getResources().getDrawable(
						R.drawable.activity_welcome_dot_image1));
				mWelcomeImage1.setImageDrawable(getResources().getDrawable(
						R.drawable.activity_welcome_dot_image2));
				mWelcomeImage3.setImageDrawable(getResources().getDrawable(
						R.drawable.activity_welcome_dot_image2));
				break;
			case 3:
				mWelcomeImage3.setImageDrawable(getResources().getDrawable(
						R.drawable.activity_welcome_dot_image1));
				mWelcomeImage2.setImageDrawable(getResources().getDrawable(
						R.drawable.activity_welcome_dot_image2));
				break;
			}
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	};
}
