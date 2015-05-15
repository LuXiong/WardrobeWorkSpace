package com.privatewardrobe.model;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5035966730135970224L;
	private String id;
	private String userId;
	private String shareId;
	private String content;
	private Date createTime;
	private String userImg;
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
	
}
