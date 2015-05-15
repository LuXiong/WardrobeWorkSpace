package com.privatewardrobe.model;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

public class Share implements Serializable{
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
	private Date creatTime;
	
	public Share(JSONObject obj){
		
	}
	
	public Share(String userName, String userId, String userImg, String suitId,
			String suitImg, String content, String suitDescription,
			int likeCount, int commentCount, int isLike, int isCollect,
			Date creatTime) {
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
		this.creatTime = creatTime;
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
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	
}
