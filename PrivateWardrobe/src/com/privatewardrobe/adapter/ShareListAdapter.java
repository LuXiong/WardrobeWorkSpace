package com.privatewardrobe.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.privatewardrobe.model.Share;

public class ShareListAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<Share> mShareList;

	public ShareListAdapter(Context context, ArrayList<Share> shareList){
		this.mContext = context;
		this.mShareList = shareList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
