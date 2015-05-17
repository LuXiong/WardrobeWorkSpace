package com.privatewardrobe.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.Clothes;

public class ClothesListAdapter extends BaseAdapter {
	private ArrayList<Clothes> mClothes;
	private Context mContext;

	public ClothesListAdapter(Context context, ArrayList<Clothes> clothes) {
		this.mClothes = clothes;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return mClothes.size();
	}

	@Override
	public Object getItem(int position) {
		return mClothes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Clothes clothes = mClothes.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_clohthes, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		PWApplication.getInstance().getImageLoader()
				.displayImage("http://"+clothes.getImg(), holder.img,Utils.buildNoneDisplayImageOptions());
		holder.description.setText(clothes.getDescription());
		if (clothes.getLike()==1) {
			holder.like.setImageResource(R.drawable.icon_like);
		} else {
			holder.like.setImageResource(R.drawable.icon_dislike);
		}
		return convertView;
	}

	private class ViewHolder {
		ImageView img;
		TextView description;
		ImageView like;

		public ViewHolder(View v) {
			img = (ImageView) v.findViewById(R.id.item_clothes_img);
			description = (TextView) v
					.findViewById(R.id.item_clothes_description_text);
			like = (ImageView) v.findViewById(R.id.item_clothes_like_img);
		}
	}
}
