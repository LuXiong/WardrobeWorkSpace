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
import com.privatewardrobe.model.Clothes;

public class ChooseClothesListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Clothes> mClohtesList;
	private ArrayList<Clothes> mChoosedList;

	public ChooseClothesListAdapter(Context context,
			ArrayList<Clothes> clohtesList, ArrayList<Clothes> choosedList) {
		this.mContext = context;
		this.mClohtesList = clohtesList;
		this.mChoosedList = choosedList;
	}

	@Override
	public int getCount() {
		return mClohtesList.size();
	}

	@Override
	public Object getItem(int position) {
		return mClohtesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Clothes clothes = mClohtesList.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_cloths_choose_list, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (mChoosedList.contains(clothes)) {
			holder.state.setImageResource(R.drawable.icon_choosed);;
		} else {
			holder.state.setImageResource(R.drawable.icon_choose);;
		}
		PWApplication
				.getInstance()
				.getImageLoader()
				.displayImage("http://" + clothes.getImg(), holder.img,
						Utils.buildNoneDisplayImageOptions());
		holder.description.setText(clothes.getDescription());
		return convertView;
	}

	class ViewHolder {
		ImageView state;
		ImageView img;
		TextView description;

		public ViewHolder(View v) {
			state = (ImageView) v
					.findViewById(R.id.item_clothes_choose_state_img);
			img = (ImageView) v.findViewById(R.id.item_clothes_choose_img);
			description = (TextView) v
					.findViewById(R.id.item_clothes_choose_text);
		}
	}

}
