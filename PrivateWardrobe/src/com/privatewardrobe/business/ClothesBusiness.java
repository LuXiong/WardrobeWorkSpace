package com.privatewardrobe.business;

import com.privatewardrobe.model.Clothes;


public class ClothesBusiness {
	public void addClothes(String userId,int color,int category,String img,
			final BusinessListener<Clothes> listener){
		
	}
	
	public void deleteClothes(String clothesId,final BusinessListener<Clothes> listener){
		
	}
	
	public void updateClothes(String id,int color,int category,String img,
			final BusinessListener<Clothes> listener){
		
	}
	
	public void queryClothesById(String id, final BusinessListener<Clothes> listener){
		
	}
	
	public void queryClothesByKey(String key,final BusinessListener<Clothes> listener){
		
	}
	
	public void findAllClothes(String userId,final BusinessListener<Clothes> listener){
		
	}

}
