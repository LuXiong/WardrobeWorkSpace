package com.privatewardrobe.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.R;
import com.privatewardrobe.control.DrawView.RefreshListener;

public class MainActivity extends BaseActivity {

	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setHasRefresh(true);
		setRefreshListener(new RefreshListener() {

			@Override
			public void refreshing() {
				(new Handler()).postDelayed(new Runnable() {

					@Override
					public void run() {
						completeRefresh();

					}
				}, 2000);

			}

			@Override
			public void refreshBegin() {
				// TODO Auto-generated method stub

			}

			@Override
			public void refreshComplete() {

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
