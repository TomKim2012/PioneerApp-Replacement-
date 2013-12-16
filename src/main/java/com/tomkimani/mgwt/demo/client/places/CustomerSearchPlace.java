package com.tomkimani.mgwt.demo.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CustomerSearchPlace extends Place {
	Boolean miniStatement=false;
	
	public CustomerSearchPlace(Boolean miniStatement) {
		this.miniStatement = miniStatement;
	}
	
	public CustomerSearchPlace() {
	}
	
	public Boolean getMiniStatement() {
		return miniStatement;
	}
	public static class CustomerSearchPlaceTokenizer implements PlaceTokenizer<CustomerSearchPlace> {
		@Override
		public CustomerSearchPlace getPlace(String token) {
			return new CustomerSearchPlace();
		}

		@Override
		public String getToken(CustomerSearchPlace place) {
			return "";
		}

	}
}