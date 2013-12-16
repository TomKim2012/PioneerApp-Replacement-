package com.tomkimani.mgwt.demo.client.places;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.tomkimani.mgwt.demo.client.customerSearch.CustomerSearchActivity.Customer;

public class SearchResultsPlace extends Place {
	
	List<Customer> customerList= new ArrayList<Customer>();
	Boolean miniStatement=false;
	
	public SearchResultsPlace(List<Customer> sentList){
		this.customerList=sentList;
	}
	
	public SearchResultsPlace(List<Customer> sentList, Boolean statement){
		if(statement){
			this.miniStatement = true;
		}
		this.customerList=sentList;
	}
	public SearchResultsPlace() {
	}
	
	public List<Customer> getCustomerresults() {
		return customerList;
	}
	
	public Boolean getMiniStatement() {
		return miniStatement;
	}
	
	public static class SearchResultsPlaceTokenizer implements PlaceTokenizer<SearchResultsPlace> {

		@Override
		public SearchResultsPlace getPlace(String token) {
			return new SearchResultsPlace();
		}

		@Override
		public String getToken(SearchResultsPlace place) {
			return "";
		}

	}
}