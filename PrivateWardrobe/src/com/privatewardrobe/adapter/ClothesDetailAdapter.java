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
					R.layout.activity_clothes_detail, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

//		PWApplication
//				.getInstance()
//				.getImageLoader()
//				.displayImage("http://" + clothes.getImg(), holder.imgImageView,
//						Utils.buildNoneDisplayImageOptions());
//		holder.colorTextView.setText(clothes.getColor());
//		holder.categoryView.setText(clothes.getCategory());
//		holder.exponentTextView.setText(clothes.getExponent());
//		holder.descriptionTextView.setText(clothes.getDescription());
//		holder.createTimeTextView.setText(clothes.getCreateTime().toString());
//		holder.lastEditTextView.setText(clothes.getLastEdit().toString());
//		holder.isLike.setText(clothes.getLike());
//		ArrayList<Suit> suits = clothes.getSuitList();
		if (suit != null) {
//			SuitAdapter adapter = new ShareCommentListAdapter(
//					suits, mContext);
//			holder.commentListView.setAdapter(adapter);
		}

		return convertView;
	}
	private class ViewHolder {
	    TextView descriptionTextView = null,colorTextView = null,categoryView = null,exponentTextView = null;
	    TextView createTimeTextView = null,lastEditTextView = null,isLike = null;
		ImageView imgImageView = null;
		ListView suitListView = null;

		public ViewHolder(View v) {
			imgImageView = (ImageView) v.findViewById(R.id.activity_clothes_detail_img);
			colorTextView = (TextView) v.findViewById(R.id.activity_clothes_detail_color);
			descriptionTextView = (TextView) v
					.findViewById(R.id.activity_clothes_detail_description);
			categoryView = (TextView) v
					.findViewById(R.id.activity_clothes_detail_category);
			exponentTextView = (TextView) v
					.findViewById(R.id.activity_clothes_detail_exponent);
			createTimeTextView = (TextView) v
					.findViewById(R.id.activity_clothes_detail_createTime);
			lastEditTextView = (TextView) v
					.findViewById(R.id.activity_clothes_detail_lastEdit);
			isLike = (TextView) v
					.findViewById(R.id.activity_clothes_detail_isLike);
			suitListView = (ListView) v
					.findViewById(R.id.activity_clothes_detail_suitList);
			
		}
	}

}
