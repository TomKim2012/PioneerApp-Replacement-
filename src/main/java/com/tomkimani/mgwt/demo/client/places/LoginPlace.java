package com.tomkimani.mgwt.demo.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoginPlace extends Place {
	public static class LoginPlaceTokenizer implements PlaceTokenizer<LoginPlace> {

		@Override
		public LoginPlace getPlace(String token) {
			return new LoginPlace();
		}

		@Override
		public String getToken(LoginPlace place) {
			return "";
		}

	}
}