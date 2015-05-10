package com.privatewardrobe.model;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Clothes implements Serializable{

	private String id;
	private String userId;
	private String description;
	private Integer color;
	private Integer category;
	private Integer exponent;
	private Date createTime;
	private Date lastEdit;
	private String img;
	private String suits;
	private int like;

	public Clothes() {
	}

	public Clothes(JSONObject data) {
		try {
			if (data.has("uuid")) {
				this.id = data.getString("uuid");
			}
			if (data.has("user_id")) {
				this.userId = data.getString("user_id");
			}
			if(data.has("description")){
				this.description = data.getString("description");
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
			if (data.has("create_time")) {
				this.createTime = new Date(data.getLong("create_time"));
			}
			if (data.has("last_edit")) {
				this.lastEdit = new Date(data.getLong("last_edit"));
			}
			if (data.has("img")) {
				this.img = data.getString("img");
			}
			if (data.has("suits")) {
				this.suits = data.getString("suits");
			}
			if (data.has("is_like")) {
				this.like = data.getInt("is_like");
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/** full constructor */
	public Clothes(String id, String userId, String description,Integer color, Integer category,
			Integer exponent, Date createTime, Date lastEdit, String img,
			String suits, int like) {
		super();
		this.id = id;
		this.userId = userId;
		this.description = description;
		this.color = color;
		this.category = category;
		this.exponent = exponent;
		this.createTime = createTime;
		this.lastEdit = lastEdit;
		this.img = img;
		this.suits = suits;
		this.like = like;
	}

	/** minimal constructor */
	public Clothes(String id, String userId,String description, Integer color, Integer category,
			String img, String suits, int like) {
		super();
		this.id = id;
		this.userId = userId;
		this.description = description;
		this.color = color;
		this.category = category;
		this.img = img;
		this.suits = suits;
		this.like = like;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}



}
