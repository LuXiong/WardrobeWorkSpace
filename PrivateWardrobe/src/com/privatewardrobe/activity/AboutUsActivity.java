package com.privatewardrobe.activity;

import android.os.Bundle;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.R;

import android.app.Activity;

public class AboutUsActivity extends BaseActivity {
	private ActionBar mActionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutus);
		mActionBar = getMyActionBar();
		mActionBar.setTitle("关于我们");
	}

}
