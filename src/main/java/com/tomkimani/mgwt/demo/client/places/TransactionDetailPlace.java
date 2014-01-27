package com.tomkimani.mgwt.demo.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.tomkimani.mgwt.demo.client.customerSearch.CustomerSearchActivity.Customer;
import com.tomkimani.mgwt.demo.client.transactions.Transaction;

public class TransactionDetailPlace extends Place{

	private Transaction transaction;
	private Customer customer;
	private Boolean isMiniStatement=false;

	public TransactionDetailPlace(Transaction transaction) {
		this.transaction = transaction;
	}
	public TransactionDetailPlace() {
	}
	
	public TransactionDetailPlace(Customer customer) {
		this.customer=customer;
	}
	
	public TransactionDetailPlace(Customer customer, Boolean isMinistatement) {
		this.customer =customer;
		this.isMiniStatement = isMinistatement;
	}
	
	public Transaction getTransaction() {
		return transaction;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public Boolean getIsMiniStatement() {
		return isMiniStatement;
	}
	
	public static class TransactionDetailPlaceTokenizer implements PlaceTokenizer<TransactionDetailPlace> {

		@Override
		public TransactionDetailPlace getPlace(String token) {
			return new TransactionDetailPlace();
		}
		@Override
		public String getToken(TransactionDetailPlace place) {
			return "";
		}
	}
}
