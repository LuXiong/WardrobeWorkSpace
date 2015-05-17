package com.privatewardrobe.adapter;

import java.util.ArrayList;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.Clothes;
import com.privatewardrobe.model.Suit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ClothesDetailAdapter extends BaseAdapter {

	private ArrayList<Suit> mSuitList;
	private Context mContext;
	
	
	public ClothesDetailAdapter(ArrayList<Suit> mSuitList, Context mContext) {
		this.mSuitList = mSuitList;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mSuitList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mSuitList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		Suit suit = mSuitList.get(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_detail_clothes_list
					, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		PWApplication
				.getInstance()
				.getImageLoader()
				.displayImage("http://" + suit.getImg(), holder.img,
						Utils.buildNoneDisplayImageOptions());
		holder.descriptionTextView.setText(suit.getDescription());
		holder.createTimeTextView.setText(suit.getCreateTime().toString());
//		if (suit != null) {
//			// SuitAdapter adapter = new ShareCommentListAdapter(
//			// suits, mContext);
//			// holder.commentListView.setAdapter(adapter);
//		}

		return convertView;
	}
	private class ViewHolder {

		TextView descriptionTextView = null,createTimeTextView = null;
		ImageView img = null;
		
		public ViewHolder(View v) {
        	img = (ImageView) v.findViewById(R.id.item_detail_clothes_suit_img);
			descriptionTextView = (TextView) v.findViewById(R.id.item_detail_clothes_description_text);
			createTimeTextView = (TextView) v.findViewById(R.id.item_detail_clothes_time_text);
			
		}
	}

}
