package com.privatewardrobe.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.Clothes;

public class ImgHorizenGridAdapter extends BaseAdapter {
	Context mContext;
	ArrayList<Clothes> mUris;

	public ImgHorizenGridAdapter(Context context, ArrayList<Clothes> uris) {
		this.mContext = context;
		this.mUris = uris;
	}

	@Override
	public int getCount() {
		return mUris.size();
	}

	@Override
	public Object getItem(int position) {
		return mUris.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(mContext).inflate(
				R.layout.item_img_grid, null);
		ViewHolder holder = new ViewHolder(convertView);
		PWApplication
				.getInstance()
				.getImageLoader()
				.displayImage("http://" + mUris.get(position).getImg(),
						holder.img, Utils.buildNoneDisplayImageOptions());
		return convertView;
	}

	class ViewHolder {
		ImageView img;

		public ViewHolder(View v) {
			img = (ImageView) v.findViewById(R.id.item_img_img);
		}
	}
}
