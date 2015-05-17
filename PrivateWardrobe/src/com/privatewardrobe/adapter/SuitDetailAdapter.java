package com.privatewardrobe.adapter;

import java.util.ArrayList;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.Suit;
import com.privatewardrobe.model.Clothes;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SuitDetailAdapter extends BaseAdapter{
	
	private ArrayList<Clothes> mClothesList;
	private Context mContext;

	
	public SuitDetailAdapter(ArrayList<Clothes> mClothesList, Context mContext) {
		this.mClothesList = mClothesList;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mClothesList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mClothesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		Clothes clothes = mClothesList.get(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_detail_suit_list, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		PWApplication
				.getInstance()
				.getImageLoader()
				.displayImage("http://" + clothes.getImg(), holder.img,
						Utils.buildNoneDisplayImageOptions());
		holder.descriptionTextView.setText(clothes.getDescription());
		holder.createTimeTextView.setText(clothes.getCreateTime().toString());
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
        	img = (ImageView) v.findViewById(R.id.item_detail_suit_clothes_img);
			descriptionTextView = (TextView) v.findViewById(R.id.item_detail_suit_description_text);
			createTimeTextView = (TextView) v.findViewById(R.id.item_detail_suit_time_text);
			
		}
	}

}
