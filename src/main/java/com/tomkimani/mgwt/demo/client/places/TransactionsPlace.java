package com.tomkimani.mgwt.demo.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.tomkimani.mgwt.demo.client.transactions.Transaction;

public class TransactionsPlace extends Place {
	
	Transaction transaction;
	
	public TransactionsPlace() {
		// TODO Auto-generated constructor stub
	}
	
	public TransactionsPlace(Transaction trx) {
		this.transaction = trx;
	}
	
	public Transaction getTransaction() {
		return transaction;
	}
	

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