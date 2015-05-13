package com.privatewardrobe;

import java.util.ArrayList;

import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.ClothesBusiness;
import com.privatewardrobe.model.ClothesType;

public class ClothesTypeHelper {
	private ArrayList<ClothesType> mList = new ArrayList<ClothesType>();
	private static ClothesTypeHelper instance = new ClothesTypeHelper();

	public static ClothesTypeHelper getInstance() {
		return instance;
	}

	private ClothesTypeHelper() {
		init();
		System.out.print("s");
	}

	private void init() {
		ArrayList<ClothesType> types = (ArrayList<ClothesType>) PWApplication
				.getInstance().getCache(PWConstant.CLOTHES_TYPE);
		if(types!=null){
			mList.clear();
			mList.addAll(types);
		}else{
			ClothesBusiness clothesBusiness = new ClothesBusiness();
			clothesBusiness.showClothesType(new BusinessListener<ClothesType>(){
				@Override
				public void onSuccess(ArrayList<ClothesType> list) {
					mList.clear();
					mList.addAll(list);
					PWApplication
					.getInstance().putCache(PWConstant.CLOTHES_TYPE,list);
				}
			});
		}
	}

	public ArrayList<String> getGender() {
		ArrayList<String> genders = new ArrayList<String>();
		genders.add("ÄÐ");
		genders.add("Å®");
		return genders;
	}

	public ArrayList<String> getTypes(int gender) {
		ArrayList<String> types = new ArrayList<String>();
		for (int i = 0; i < mList.size(); i++) {
			ClothesType type = mList.get(i);
			if (type.getGenderCode() == gender) {
				String typeName = type.getType();
				if (!types.contains(typeName)) {
					types.add(typeName);
				}
			}

		}
		return types;
	}

	public ArrayList<String> getDetailNames(int typeCode) {
		ArrayList<String> detailNames = new ArrayList<String>();
		for (int i = 0; i < mList.size(); i++) {
			ClothesType type = mList.get(i);
			if (type.getTypeCode() == typeCode) {
				String detailName = type.getName();
				if (!detailNames.contains(detailName)) {
					detailNames.add(detailName);
				}
			}

		}
		return detailNames;
	}

	public int getGenderCode(String gender) {
		for (int i = 0; i < mList.size(); i++) {
			ClothesType type = mList.get(i);
			if (type.getGender().equals(gender)) {
				return type.getGenderCode();
			}

		}
		return 0;
	}

	public int getTypeCode(int gender, String typeName) {
		for (int i = 0; i < mList.size(); i++) {
			ClothesType type = mList.get(i);
			if (type.getGenderCode() == gender
					&& type.getType().equals(typeName)) {
				return type.getTypeCode();
			}

		}
		return 0;
	}

	public int getDetailCode(String name) {
		for (int i = 0; i < mList.size(); i++) {
			ClothesType type = mList.get(i);
			if (type.getName().equals(name)) {
				return type.getDetailCode();
			}

		}
		return 0;
	}

	public String getDetailName(int detailCode) {
		for (int i = 0; i < mList.size(); i++) {
			ClothesType type = mList.get(i);
			if (type.getDetailCode() == detailCode) {
				return type.getName();
			}

		}
		return null;
	}

}