package com.privatewardrobe.business;

import com.privatewardrobe.model.Suit;

public class SuitBusiness {

	public void addSuit(String userId,String img,int weather,int ocassion,
			final BusinessListener<Suit> listener){
		
	}
	
	public void deleteSuit(String id,final BusinessListener<Suit> listener){
		
	}
	
	public void updateSuit(String id,int weather,int occasion,
			final BusinessListener<Suit> listener){
		
	}
	
	public void querySuitById(String id,final BusinessListener<Suit> listener){
		
	}
	
	public void querySuitByKey(String key,final BusinessListener<Suit> listener){
		
	}
	
	public void findAllSuit(String userId,final BusinessListener<Suit> listener){
		
	}
}
