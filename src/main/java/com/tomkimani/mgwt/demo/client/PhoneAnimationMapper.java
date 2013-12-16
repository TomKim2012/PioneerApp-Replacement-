
package com.tomkimani.mgwt.demo.client;

import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;
import com.tomkimani.mgwt.demo.client.places.DashboardPlace;
import com.tomkimani.mgwt.demo.client.places.LoginPlace;
import com.tomkimani.mgwt.demo.client.places.TransactionDetailPlace;
import com.tomkimani.mgwt.demo.client.places.TransactionsPlace;

/**
 * 
 */
public class PhoneAnimationMapper implements AnimationMapper {

	@Override
	public Animation getAnimation(Place oldPlace, Place newPlace) {
		if(oldPlace instanceof TransactionDetailPlace && newPlace instanceof TransactionsPlace){
			return Animation.SLIDE_REVERSE;
		}
		if(oldPlace instanceof DashboardPlace && newPlace instanceof LoginPlace){
			return Animation.SLIDE_REVERSE;
		}
		
		////////////DASHBOARD ANIMATIONS//////////////////////////////////
		if(oldPlace instanceof DashboardPlace && newPlace instanceof TransactionsPlace){
			return Animation.FADE;
		}
		if(oldPlace instanceof TransactionsPlace && newPlace instanceof DashboardPlace){
			return Animation.FADE;
		}
		
		return Animation.SLIDE;
	}

}
