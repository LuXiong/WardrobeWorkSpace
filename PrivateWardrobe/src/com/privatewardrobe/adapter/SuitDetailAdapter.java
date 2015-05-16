package com.privatewardrobe.adapter;

import com.privatewardrobe.model.Suit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SuitDetailAdapter extends BaseAdapter{
	
	private Suit mSuit;
	private Context mContext;

	
	public SuitDetailAdapter(Suit mSuit, Context mContext) {
		this.mSuit = mSuit;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView descriptionTextView = null,weatherTextView = null,occasionTextView = null;
		TextView createTimeTextView = null,lastEditTextView = null,isLike = null;
		ImageView imgImageView = null;
		ListView clothesListView = null;
		
		descriptionTextView.setText(mSuit.getDescription());
		weatherTextView.setText(mSuit.getWeather());
		occasionTextView.setText(mSuit.getOccasion());
		//Î´Íê¼ÌÐø
		return null;
	}

}
