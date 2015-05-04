package com.privatewardrobe.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.privatewardrobe.R;

public class ClothesFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main_clothes, container,
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
