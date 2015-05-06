package com.privatewardrobe.fragment;

import com.privatewardrobe.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SuitFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main_suit, container,
				false);
		findView(v);
		initView(v);
		return v;
	}

	private void findView(View v) {
		// TODO Auto-generated method stub
		
	}

	private void initView(View v) {
		// TODO Auto-generated method stub
		
	}
}
