package com.privatewardrobe.model;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

public class Comment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5035966730135970224L;
	private String id;
	private String userId;
	private String shareId;
	private String userName;
	private String content;
	private Date createTime;
	private String userImg;

	public Comment(JSONObject data) {
	try{
		if (data.has("comment_user_name")) {
			this.userName = data.getString("comment_user_name");
		}
		if (data.has("comment_content")) {
			this.content = data.getString("comment_content");
		}
		if (data.has("comment_create_time")) {
			this.createTime = new Date(data.getLong("comment_create_time"));
		}
		if (data.has("comment_user_id")) {
			this.userId = data.getString("comment_user_id");
		}
		if (data.has("comment_share_id")) {
			this.shareId = data.getString("comment_share_id");
		}
		if (data.has("comment_user_img")) {
			this.shareId = data.getString("comment_user_img");
		}
		if (data.has("comment_id")) {
			this.id = data.getString("comment_id");
		}
		
	} catch(Exception e){
		e.printStackTrace();
	}

	}
	public Comment(String id, String userId, String shareId, String userName,
			String content, Date createTime, String userImg) {
		super();
		this.id = id;
		this.userId = userId;
		this.shareId = shareId;
		this.userName = userName;
		this.content = content;
		this.createTime = createTime;
		this.userImg = userImg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
