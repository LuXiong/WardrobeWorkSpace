package com.privatewardrobe.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.privatewardrobe.R;
import com.privatewardrobe.control.StaticListView;
import com.privatewardrobe.model.Share;

public class ShareListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Share> mShareList;

	public ShareListAdapter(Context context, ArrayList<Share> shareList) {
		this.mContext = context;
		this.mShareList = shareList;
	}

	@Override
	public int getCount() {
		return mShareList.size();
	}

	@Override
	public Object getItem(int position) {
		return mShareList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	private class ViewHolder {
		ImageView userImg;
		TextView userName;
		TextView createTime;
		TextView content;
		ImageView suitImg;
		TextView comment;
		TextView like;
		TextView collect;
		StaticListView commentListView;

		public ViewHolder(View v) {
			userImg = (ImageView) v.findViewById(R.id.item_share_user_img);
			userName = (TextView) v
					.findViewById(R.id.item_share_user_name_text);
			createTime = (TextView) v.findViewById(R.id.item_share_time_text);
			content = (TextView) v.findViewById(R.id.item_share_content_text);
			suitImg = (ImageView) v.findViewById(R.id.item_share_suit_img);
			comment = (TextView) v.findViewById(R.id.item_share_comment_text);
			like = (TextView) v.findViewById(R.id.item_share_like_text);
			collect = (TextView) v.findViewById(R.id.item_share_collect_text);
			commentListView = (StaticListView) v
					.findViewById(R.id.item_share_comment_list);
		}
	}

}
