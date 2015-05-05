package com.privatewardrobe.model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;



public class User {
	// Fields

	private String uid;
	private String name;
	private int gender;
	private String password;
	private String phone;
	private String avatar;
	private Date createTime;
	private Date lastUse;
	private String deviceId;

	// Constructors

	/** default constructor */
	public User() {
	}

	public User(JSONObject data) {
		try {
			if (data.has("uid")) {
				this.uid = data.getString("uid");
			}
			if (data.has("name")) {
				this.name = data.getString("name");
			}
			if (data.has("gender")) {
				this.gender = data.getInt("gender");
			}
			if (data.has("password")) {
				this.password = data.getString("password");
			}
			if (data.has("phone")) {
				this.phone = data.getString("phone");
			}
			if (data.has("avatar")) {
				this.avatar = data.getString("avatar");
			}
			if (data.has("deviceId")) {
				this.deviceId = data.getString("deviceId");
			}
			if (data.has("createTime")) {
				this.createTime = new Date(data.getLong("createTime"));
			}
			if (data.has("lastUse")) {
				this.lastUse = new Date(data.getLong("lastUse"));
			}
		} catch (JSONException e) {
			// Log.i("xionglu", "dataexception");
			e.printStackTrace();
		}

	}

	/** minimal constructor */
	public User(String uid, String name, Integer gender, String password,
			String phone, String deviceId) {
		this.uid = uid;
		this.name = name;
		this.gender = gender;
		this.password = password;
		this.phone = phone;
		// this.createTime = createTime;
		// this.lastUse = lastUse;
		this.deviceId = deviceId;
	}

	/** full constructor */
	public User(String uid, String name, Integer gender, String password,
			String phone, String avatar, Date createTime, Date lastUse,
			String deviceId) {
		this.uid = uid;
		this.name = name;
		this.gender = gender;
		this.password = password;
		this.phone = phone;
		this.avatar = avatar;
		this.createTime = createTime;
		this.lastUse = lastUse;
		this.deviceId = deviceId;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return this.gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUse() {
		return this.lastUse;
	}

	public void setLastUse(Date lastUse) {
		this.lastUse = lastUse;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
