
package com.tomkimani.mgwt.demo.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.tomkimani.mgwt.demo.client.SearchResults.SearchResultsActivity;
import com.tomkimani.mgwt.demo.client.customerSearch.CustomerSearchActivity;
import com.tomkimani.mgwt.demo.client.dashboard.DashboardActivity;
import com.tomkimani.mgwt.demo.client.login.LoginActivity;
import com.tomkimani.mgwt.demo.client.places.CustomerSearchPlace;
import com.tomkimani.mgwt.demo.client.places.DashboardPlace;
import com.tomkimani.mgwt.demo.client.places.LoginPlace;
import com.tomkimani.mgwt.demo.client.places.SearchResultsPlace;
import com.tomkimani.mgwt.demo.client.places.SettingsPlace;
import com.tomkimani.mgwt.demo.client.places.TransactionDetailPlace;
import com.tomkimani.mgwt.demo.client.places.TransactionsPlace;
import com.tomkimani.mgwt.demo.client.settings.SettingsActivity;
import com.tomkimani.mgwt.demo.client.transactions.TransactionsActivity;
import com.tomkimani.mgwt.demo.client.transactions.detail.TransactionDetailActivity;


/**
 * 
 */
public class PhoneActivityMapper implements ActivityMapper {

	private final ClientFactory clientFactory;

	public PhoneActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		//Dialogs.alert("Going", place.toString(), null);
		if(LoginActivity.loggedUserId == null){
			place = null;
		}
		
		if(place instanceof DashboardPlace){
			return new DashboardActivity(clientFactory);
		}
		if(place instanceof TransactionsPlace){
			return new TransactionsActivity(clientFactory);
		}
		if(place instanceof LoginPlace){
			return new LoginActivity(clientFactory);
		}
		if(place instanceof TransactionDetailPlace){
			return new TransactionDetailActivity(clientFactory);
		}
		if(place instanceof CustomerSearchPlace){
			return new CustomerSearchActivity(clientFactory);
		}
		if(place instanceof SearchResultsPlace){
			return new SearchResultsActivity(clientFactory);
		}
		if(place instanceof SettingsPlace){
			if(!LoginActivity.loggedUserGroup.equals("Admin")){
				MyDialogs.alert("Not Allowed", "The Settings page is for Administrator.");
				return new DashboardActivity(clientFactory);
			}
			return new SettingsActivity(clientFactory);
		}
		
		return new LoginActivity(clientFactory);
	}
	
}
