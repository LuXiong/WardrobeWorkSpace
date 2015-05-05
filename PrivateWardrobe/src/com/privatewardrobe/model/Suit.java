package com.privatewardrobe.model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Suit {
	
	private String id;
	private String userId;
	private String img;
	private String clothes;
	private Integer weather;
	private Integer occasion;
	private Date createTime;
	private Date lastEdit;
	
	public Suit(){
		
	}
	public Suit(JSONObject data) {
		try {
			if (data.has("id")) {
				this.id = data.getString("id");
			}
			if (data.has("userId")) {
				this.userId = data.getString("userId");
			}
			if (data.has("img")) {
				this.img = data.getString("img");
			}
			if (data.has("clothes")) {
				this.clothes = data.getString("clothes");
			}
			if (data.has("weather")) {
				this.weather = data.getInt("weather");
			}
			if (data.has("occasion")) {
				this.occasion = data.getInt("occasion");
			}
			
			if (data.has("createTime")) {
				this.createTime = new Date(data.getLong("createTime"));
			}
			if (data.has("lasEdit")) {
				this.lastEdit = new Date(data.getLong("lastEdit"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	/**minimal constructor*/
	public Suit(String id, String userId, String img, String clothes,
			Integer weather, Integer occasion) {
		super();
		this.id = id;
		this.userId = userId;
		this.img = img;
		this.clothes = clothes;
		this.weather = weather;
		this.occasion = occasion;
	}
	/**full constructor*/
	public Suit(String id, String userId, String img, String clothes,
			Integer weather, Integer occasion, Date createTime, Date lastEdit) {
		super();
		this.id = id;
		this.userId = userId;
		this.img = img;
		this.clothes = clothes;
		this.weather = weather;
		this.occasion = occasion;
		this.createTime = createTime;
		this.lastEdit = lastEdit;
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getClothes() {
		return clothes;
	}
	public void setClothes(String clothes) {
		this.clothes = clothes;
	}
	public Integer getWeather() {
		return weather;
	}
	public void setWeather(Integer weather) {
		this.weather = weather;
	}
	public Integer getOccasion() {
		return occasion;
	}
	public void setOccasion(Integer occasion) {
		this.occasion = occasion;
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
	
	

}
