package com.privatewardrobe.model;

import java.util.Date;


import org.json.JSONException;
import org.json.JSONObject;

public class ClothesType {
	// Fields

		private int detailCode;
		private String name;
		private String gender;
		private int genderCode;
		private String type;
		private int typeCode;
		private int exponent;
		public ClothesType() {
		}

		/** full constructor */
		public ClothesType(String name, String gender, Integer genderCode,
				String type, Integer typeCode, Integer exponent) {
			this.name = name;
			this.gender = gender;
			this.genderCode = genderCode;
			this.type = type;
			this.typeCode = typeCode;
			this.exponent = exponent;
		}
		
		public ClothesType(JSONObject data){
			try {
				if (data.has("gender_code")) {
				    this.genderCode = data.getInt("gender_code");
				}
				if (data.has("detail_code")) {
					this.detailCode = data.getInt("detail_code");
				}
				if (data.has("exponent")) {
					this.exponent = data.getInt("exponent");
				}
				if (data.has("name")) {
					this.name = data.getString("name");
				}
				if (data.has("gender")) {
					this.gender = data.getString("gender");
				}
				if (data.has("type")) {
					this.type = data.getString("type");
				}
				if (data.has("type_code")) {
					this.typeCode = data.getInt("type_ode");
				}
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		public int getDetailCode() {
			return detailCode;
		}

		public void setDetailCode(int detailCode) {
			this.detailCode = detailCode;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public int getGenderCode() {
			return genderCode;
		}

		public void setGenderCode(int genderCode) {
			this.genderCode = genderCode;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getTypeCode() {
			return typeCode;
		}

		public void setTypeCode(int typeCode) {
			this.typeCode = typeCode;
		}

		public int getExponent() {
			return exponent;
		}

		public void setExponent(int exponent) {
			this.exponent = exponent;
		}

		
		
		
}	
