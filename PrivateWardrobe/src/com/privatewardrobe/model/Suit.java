package com.privatewardrobe.model;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Suit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6586034866332060422L;
	private String id;
	private String userId;
	private String img;
	private String clothes;
	private Integer weather;
	private Integer occasion;
	private Date createTime;
	private Date lastEdit;
	private String description;
	private Integer isLike;
	private String thumb;

	// Constructors

	/** default constructor */
	public Suit() {
	}

	/** minimal constructor */
	public Suit(String userId, Date createTime, Date lastEdit) {
		this.userId = userId;
		this.createTime = createTime;
		this.lastEdit = lastEdit;
	}

	/** full constructor */
	public Suit(String id,String userId, String img, String clothes, Integer weather,
			Integer occasion, Date createTime, Date lastEdit,
			String description, Integer isLike, String thumb) {
		this.id = id;
		this.userId = userId;
		this.img = img;
		this.clothes = clothes;
		this.weather = weather;
		this.occasion = occasion;
		this.createTime = createTime;
		this.lastEdit = lastEdit;
		this.description = description;
		this.isLike = isLike;
		this.thumb = thumb;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getClothes() {
		return this.clothes;
	}

	public void setClothes(String clothes) {
		this.clothes = clothes;
	}

	public Integer getWeather() {
		return this.weather;
	}

	public void setWeather(Integer weather) {
		this.weather = weather;
	}

	public Integer getOccasion() {
		return this.occasion;
	}

	public void setOccasion(Integer occasion) {
		this.occasion = occasion;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastEdit() {
		return this.lastEdit;
	}

	public void setLastEdit(Date lastEdit) {
		this.lastEdit = lastEdit;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsLike() {
		return this.isLike;
	}

	public void setIsLike(Integer isLike) {
		this.isLike = isLike;
	}

	public String getThumb() {
		return this.thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public Suit(JSONObject data) {
		try {
			if (data.has("id")) {
				this.id = data.getString("id");
			}
			if (data.has("user_id")) {
				this.userId = data.getString("user_id");
			}
			if (data.has("img")) {
				this.img = data.getString("img");
			}
			if (data.has("thumb")) {
				this.thumb = data.getString("thumb");
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

			if (data.has("create_time")) {
				this.createTime = new Date(data.getLong("create_time"));
			}
			if (data.has("last_edit")) {
				this.lastEdit = new Date(data.getLong("last_edit"));
			}
			if (data.has("is_like")) {
				this.isLike = data.getInt("is_like");
			}
			if (data.has("description")) {
				this.description = data.getString("description");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	
}
