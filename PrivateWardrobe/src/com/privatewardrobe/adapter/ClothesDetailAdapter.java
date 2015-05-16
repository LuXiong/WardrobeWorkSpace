package com.privatewardrobe.adapter;

import com.privatewardrobe.model.Clothes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ClothesDetailAdapter extends BaseAdapter {

	private Clothes mClothes;
	private Context mContext;
	
	
	public ClothesDetailAdapter(Clothes mClothes, Context mContext) {
		this.mClothes = mClothes;
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
	    TextView descriptionTextView = null,colorTextView = null,categoryView = null,exponentTextView = null;
	    TextView createTimeTextView = null,lastEditTextView = null,isLike = null;
		ImageView imgImageView = null;
		ListView suitListView = null;
		
		descriptionTextView.setText(mClothes.getDescription());
		colorTextView.setText(mClothes.getColor());
		categoryView.setText(mClothes.getCategory());
		exponentTextView.setText(mClothes.getExponent());
		createTimeTextView.setText(mClothes.getCreateTime().toString());
		lastEditTextView.setText(mClothes.getLastEdit().toString());
		isLike.setText(mClothes.getLike());
		
//		imgImageView.setImageResource(mClothes.getImg());
		
		return null;
	}

}
