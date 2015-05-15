package com.privatewardrobe.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.Suit;

public class ChooseSuitListAdapter extends BaseAdapter {

	private ArrayList<Suit> mSuitList;
	private Context mContext;

	public ChooseSuitListAdapter(ArrayList<Suit> list, Context context) {
		this.mSuitList = list;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return mSuitList.size();
	}

	@Override
	public Object getItem(int position) {
		return mSuitList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Suit suit = mSuitList.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_suit_choose_list, null);
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
		holder.text.setText(suit.getDescription());
		return convertView;
	}

	private class ViewHolder {
		ImageView img;
		TextView text;

		public ViewHolder(View v) {
			img = (ImageView) v.findViewById(R.id.item_suit_choose_img);
			text = (TextView) v.findViewById(R.id.item_suit_choose_text);
		}
	}

}
