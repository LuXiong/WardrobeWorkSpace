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
import com.privatewardrobe.model.Suit;

public class SuitListAdapter extends BaseAdapter {
	private ArrayList<Suit> mSuits;
	private Context mContext;

	public SuitListAdapter(Context context, ArrayList<Suit> suits) {
		this.mContext = context;
		this.mSuits = suits;
	}

	@Override
	public int getCount() {
		return mSuits.size();
	}

	@Override
	public Object getItem(int position) {
		return mSuits.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Suit suit = mSuits.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_suit, null);
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
		holder.description.setText(suit.getDescription());
		holder.time.setText(Utils.getDateString(suit.getCreateTime()));
		if (suit.getIsLike() == 1) {
			holder.like.setText("Ï²»¶");
		} else {
			holder.like.setText("²»Ï²»¶");
		}
		return convertView;
	}

	private class ViewHolder {
		ImageView img;
		TextView time;
		TextView description;
		Button like;

		public ViewHolder(View v) {
			img = (ImageView) v.findViewById(R.id.item_suit_img);
			time = (TextView) v.findViewById(R.id.item_suit_time_text);
			description = (TextView) v
					.findViewById(R.id.item_suit_description_text);
			like = (Button) v.findViewById(R.id.item_suit_like_btn);
		}
	}
}
