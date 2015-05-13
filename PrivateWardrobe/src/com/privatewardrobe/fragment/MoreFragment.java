package com.privatewardrobe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.activity.LoginActivity;

public class MoreFragment extends Fragment {
	TextView logoutText;

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
		logoutText = (TextView) v.findViewById(R.id.logout_text);
	}

	private void initView(View v) {
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
