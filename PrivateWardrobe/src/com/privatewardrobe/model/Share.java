package com.privatewardrobe.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ListView;

public class Share implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6208119922279231814L;
	private String userName;
	private String userId;
	private String userImg;
	private String suitId;
	private String suitImg;
	private String content;
	private String suitDescription;
	private int likeCount;
	private int commentCount;
	private int isLike;
	private int isCollect;
	private Date createTime;
	private ArrayList<Comment> commentList;

	public Share(JSONObject data) {

		try {
			if (data.has("user_name")) {
			    this.userName = data.getString("user_name");
			}
			if (data.has("user_id")) {
				this.userId = data.getString("user_id");
			}
			if (data.has("user_img")) {
				this.userImg = data.getString("user_img");
			}
			if (data.has("suit_id")) {
				this.suitId = data.getString("suit_id");
			}
			if (data.has("suit_img")) {
				this.suitImg = data.getString("suit_img");
			}
			if (data.has("share_content")) {
				this.content = data.getString("share_content");
			}
			if (data.has("suit_description")) {
				this.suitDescription = data.getString("suit_description");
			}
			if (data.has("like_count")) {
				this.likeCount = data.getInt("like_count");
			}
			if (data.has("comment_count")) {
				this.commentCount = data.getInt("comment_count");
			}
			if (data.has("is_like")) {
				this.isLike = data.getInt("is_like");
			}
			if (data.has("is_collect")) {
				this.isCollect = data.getInt("is_collect");
			}
			if (data.has("share_create_time")) {
				this.createTime = new Date(data.getLong("share_create_time"));
			}
			if (data.has("commentList")) {
				commentList = new ArrayList<Comment>();
				Comment comment = new Comment(data.getString("comment_id"),
						data.getString("comment_user_id"),data.getString("comment_share_id"),
						data.getString("comment_user_name"),data.getString("comment_content"),
						new Date(data.getLong("comment_create_time")),data.getString("comment_user_img"));
				commentList.add(comment);
				
//				this.commentList = new ArrayList(data.getJSONArray("commentList").toString());
//				this.commentList.add(data.getJSONArray("commentList"));
		
			}
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Share(String userName, String userId, String userImg, String suitId,
			String suitImg, String content, String suitDescription,
			int likeCount, int commentCount, int isLike, int isCollect,
			Date createTime, ArrayList<Comment> commentList) {
		super();
		this.userName = userName;
		this.userId = userId;
		this.userImg = userImg;
		this.suitId = suitId;
		this.suitImg = suitImg;
		this.content = content;
		this.suitDescription = suitDescription;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.isLike = isLike;
		this.isCollect = isCollect;
		this.createTime = createTime;
		this.commentList = commentList;
	}

	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getSuitId() {
		return suitId;
	}

	public void setSuitId(String suitId) {
		this.suitId = suitId;
	}

	public String getSuitImg() {
		return suitImg;
	}

	public void setSuitImg(String suitImg) {
		this.suitImg = suitImg;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSuitDescription() {
		return suitDescription;
	}

	public void setSuitDescription(String suitDescription) {
		this.suitDescription = suitDescription;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getIsLike() {
		return isLike;
	}

	public void setIsLike(int isLike) {
		this.isLike = isLike;
	}

	public int getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(int isCollect) {
		this.isCollect = isCollect;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public ArrayList<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(ArrayList<Comment> commentList) {
		this.commentList = commentList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
