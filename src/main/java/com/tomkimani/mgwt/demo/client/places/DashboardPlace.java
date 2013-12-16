package com.tomkimani.mgwt.demo.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DashboardPlace extends Place {
	public static class DashboardPlaceTokenizer implements PlaceTokenizer<DashboardPlace> {

		@Override
		public DashboardPlace getPlace(String token) {
			return new DashboardPlace();
		}

		@Override
		public String getToken(DashboardPlace place) {
			return "";
		}

	}
}