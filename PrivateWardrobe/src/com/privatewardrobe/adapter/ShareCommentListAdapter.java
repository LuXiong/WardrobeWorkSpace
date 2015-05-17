package com.privatewardrobe.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.Comment;

public class ShareCommentListAdapter extends BaseAdapter {
	private ArrayList<Comment> mCommentList;
	private Context mContext;

	public ShareCommentListAdapter(ArrayList<Comment> commentList,
			Context context) {
		this.mCommentList = commentList;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return mCommentList.size();
	}

	@Override
	public Object getItem(int position) {
		return mCommentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Comment comment = mCommentList.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_comment, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String time = Utils.getTime(comment.getCreateTime())+"   ";
		String user = time+comment.getUserName()+": ";
		String content = comment.getContent();
		SpannableStringBuilder text = new SpannableStringBuilder(user+content);
		ForegroundColorSpan baseColorSspan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.base_color));
		ForegroundColorSpan greyColorSspan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.grey_text));
		text.setSpan(baseColorSspan, 0, user.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		text.setSpan(greyColorSspan, user.length(), user.length()+content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		holder.text.setText(text);
		return convertView;
	}

	private class ViewHolder {
		TextView text;

		public ViewHolder(View v) {
			text = (TextView) v.findViewById(R.id.item_comment_content_text);
		}
	}

}
