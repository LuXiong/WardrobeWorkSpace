package com.privatewardrobe.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.activity.ShareListActivity;
import com.privatewardrobe.activity.UserProfileActivity;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.ShareBusiness;
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
		holder.comment.setText("∆¿¬€(" + share.getCommentCount() + ")");
		holder.commentLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mListener != null) {
					((ShareListActivity) mContext).popInputMethod();
					mListener.onComment(position);
				}

			}
		});
		holder.like.setText("µ„‘ﬁ(" + share.getLikeCount() + ")");
		holder.likeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ShareBusiness shareBusiness = new ShareBusiness();
				shareBusiness.addLike(PWApplication.getInstance().getUserId(),
						share.getShareId(), (share.getIsLike() + 1) % 2,
						new BusinessListener<Share>() {
							@Override
							public void onSuccess() {
								if (mListener != null) {
									((ShareListActivity) mContext)
											.notifyDataSetChanged();
								}
							}
						});
				share.setIsLike((share.getIsLike() + 1) % 2);
				((ShareListActivity) mContext).notifyDataSetChanged();
			}
		});
		if (share.getIsLike() == 1) {
			holder.likeImg.setImageResource(R.drawable.icon_like);
		} else {
			holder.likeImg.setImageResource(R.drawable.icon_dislike);
		}
		if (share.getIsCollect() == 1) {
			holder.collectImg.setImageResource(R.drawable.icon_collected);
		} else {
			holder.collectImg.setImageResource(R.drawable.icon_collect);
		}
		holder.collectImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ShareBusiness shareBusiness = new ShareBusiness();
				shareBusiness.addCollect(PWApplication.getInstance()
						.getUserId(), share.getSuitId(),
						(share.getIsCollect() + 1) % 2,
						new BusinessListener<Share>() {
							@Override
							public void onSuccess() {
								if (mListener != null) {
									((ShareListActivity) mContext)
											.notifyDataSetChanged();
								}
							}
						});
			}
		});
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
		TextView suitDescription;
		StaticListView commentListView;
		LinearLayout commentLayout, likeLayout;
		ImageView likeImg, collectImg;

		public ViewHolder(View v) {
			userImg = (ImageView) v.findViewById(R.id.item_share_user_img);
			userName = (TextView) v
					.findViewById(R.id.item_share_user_name_text);
			createTime = (TextView) v.findViewById(R.id.item_share_time_text);
			content = (TextView) v.findViewById(R.id.item_share_content_text);
			suitImg = (ImageView) v.findViewById(R.id.item_share_suit_img);
			comment = (TextView) v
					.findViewById(R.id.item_share_comment_count_text);
			like = (TextView) v.findViewById(R.id.item_share_like_count_text);
			suitDescription = (TextView) v
					.findViewById(R.id.item_share_suit_description_text);
			commentListView = (StaticListView) v
					.findViewById(R.id.item_share_comment_list);
			commentLayout = (LinearLayout) v
					.findViewById(R.id.item_share_comment_layout);
			likeLayout = (LinearLayout) v
					.findViewById(R.id.item_share_like_layout);
			likeImg = (ImageView) v.findViewById(R.id.item_share_like_img);
			collectImg = (ImageView) v
					.findViewById(R.id.item_share_collect_img);
		}
	}

	public interface ShareCommentListener {
		public void onComment(int which);
	}
}
