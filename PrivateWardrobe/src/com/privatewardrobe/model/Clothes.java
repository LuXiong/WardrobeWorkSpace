package com.privatewardrobe.model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Clothes {

	private String id;
	private String userId;
	private Integer color;
	private Integer category;
	private Integer exponent;
	private Date createTime;
	private Date lastEdit;
	private String img;
	private String suits;
	
	
	public Clothes() {
	}

	public Clothes(JSONObject data) {
		try {
			if (data.has("id")) {
			    this.id = data.getString("id");
			}
			if (data.has("userId")) {
				this.userId = data.getString("userId");
			}
			if (data.has("color")) {
				this.color = data.getInt("color");
			}
			if (data.has("category")) {
				this.category = data.getInt("category");
			}
			if (data.has("exponent")) {
				this.exponent = data.getInt("exponent");
			}
			if (data.has("createTime")) {
				this.createTime = new Date(data.getLong("createTime"));
			}
			if (data.has("lastEdit")) {
				this.lastEdit = new Date(data.getLong("lastEdit"));
			}
			if (data.has("img")) {
				this.img = data.getString("img");
			}
			if (data.has("suits")) {
				this.suits = data.getString("suits");
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	
	/** full constructor */
	public Clothes(String id, String userId, Integer color, Integer category,
			Integer exponent, Date createTime, Date lastEdit, String img,
			String suits) {
		super();
		this.id = id;
		this.userId = userId;
		this.color = color;
		this.category = category;
		this.exponent = exponent;
		this.createTime = createTime;
		this.lastEdit = lastEdit;
		this.img = img;
		this.suits = suits;
	}

	/** minimal constructor */
	public Clothes(String id, String userId, Integer color, Integer category,
			String img, String suits) {
		super();
		this.id = id;
		this.userId = userId;
		this.color = color;
		this.category = category;
		this.img = img;
		this.suits = suits;
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
	public Integer getColor() {
		return color;
	}
	public void setColor(Integer color) {
		this.color = color;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public Integer getExponent() {
		return exponent;
	}
	public void setExponent(Integer exponent) {
		this.exponent = exponent;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEdit() {
		return lastEdit;
	}
	public void setLastEdit(Date lastEdit) {
		this.lastEdit = lastEdit;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getSuits() {
		return suits;
	}
	public void setSuits(String suits) {
		this.suits = suits;
	}
	
	
}
