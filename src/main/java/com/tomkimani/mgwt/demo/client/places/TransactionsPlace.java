package com.tomkimani.mgwt.demo.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class TransactionsPlace extends Place {
	public static class TransactionsPlaceTokenizer implements PlaceTokenizer<TransactionsPlace> {

		@Override
		public TransactionsPlace getPlace(String token) {
			return new TransactionsPlace();
		}

		@Override
		public String getToken(TransactionsPlace place) {
			return "";
		}

	}
}