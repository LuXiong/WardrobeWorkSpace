package com.privatewardrobe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.activity.LoginActivity;
import com.privatewardrobe.activity.ShareListActivity;

public class MoreFragment extends Fragment {
	private TextView logoutText;
	private RelativeLayout mShareListLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.fragment_main_more, container, false);
		findView(v);
		initView(v);
		return v;
	}

	private void findView(View v) {
		mShareListLayout = (RelativeLayout) v.findViewById(R.id.fragment_more_share_layout);
		logoutText = (TextView) v.findViewById(R.id.logout_text);
	}

	private void initView(View v) {
		mShareListLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ShareListActivity.class);
				startActivity(intent);
			}
		});
		logoutText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PWApplication.getInstance().Logout();
				startActivity(new Intent(getActivity(),
						LoginActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
								| Intent.FLAG_ACTIVITY_NEW_TASK));
				getActivity().finish();
			}
		});
	}
}
