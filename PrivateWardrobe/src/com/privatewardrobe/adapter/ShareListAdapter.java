package com.privatewardrobe.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.activity.ShareListActivity;
import com.privatewardrobe.activity.UserProfileActivity;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.control.StaticListView;
import com.privatewardrobe.model.Comment;
import com.privatewardrobe.model.Share;

public class ShareListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Share> mShareList;
	private ShareCommentListener mListener;

	public ShareListAdapter(Context context, ArrayList<Share> shareList,
			ShareCommentListener l) {
		this.mContext = context;
		this.mShareList = shareList;
		this.mListener = l;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		final Share share = mShareList.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_share, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		PWApplication
				.getInstance()
				.getImageLoader()
				.displayImage("http://" + share.getUserImg(), holder.userImg,
						Utils.buildNoneDisplayImageOptions());
		holder.userImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, UserProfileActivity.class);
				intent.putExtra(UserProfileActivity.EXTRA_INPUT,
						share.getUserId());
				mContext.startActivity(intent);
			}
		});
		holder.userName.setText(share.getUserName());
		holder.createTime.setText(Utils.getDeltaTime(share.getCreateTime()
				.getTime()));
		holder.content.setText(share.getContent());
		PWApplication
				.getInstance()
				.getImageLoader()
				.displayImage("http://" + share.getSuitImg(), holder.suitImg,
						Utils.buildNoneDisplayImageOptions());
		holder.comment.setText("����(" + share.getCommentCount() + ")");
		holder.comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mListener != null) {
					((ShareListActivity) mContext).popInputMethod();
					mListener.onComment(position);
				}

			}
		});
		holder.like.setText("����(" + share.getLikeCount() + ")");
		holder.collect.setText("�ղ�");
		holder.suitDescription.setText(share.getSuitDescription());
		ArrayList<Comment> comments = share.getCommentList();
		if (comments != null) {
			ShareCommentListAdapter adapter = new ShareCommentListAdapter(
					comments, mContext);
			holder.commentListView.setAdapter(adapter);
		}

		return convertView;
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
		TextView suitDescription;
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
			suitDescription = (TextView) v
					.findViewById(R.id.item_share_suit_description_text);
			commentListView = (StaticListView) v
					.findViewById(R.id.item_share_comment_list);
		}
	}

	public interface ShareCommentListener {
		public void onComment(int which);
	}
}
