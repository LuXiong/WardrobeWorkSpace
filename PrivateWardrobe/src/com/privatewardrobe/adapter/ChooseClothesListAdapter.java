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
					R.layout.item_choths_choose_list, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (mChoosedList.contains(clothes)) {
			holder.state.setText("已选择");
		} else {
			holder.state.setText("未选中");
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
		TextView state;
		ImageView img;
		TextView description;

		public ViewHolder(View v) {
			state = (TextView) v
					.findViewById(R.id.item_clothes_choose_state_img);
			img = (ImageView) v.findViewById(R.id.item_clothes_choose_img);
			description = (TextView) v
					.findViewById(R.id.item_clothes_choose_text);
		}
	}

}
