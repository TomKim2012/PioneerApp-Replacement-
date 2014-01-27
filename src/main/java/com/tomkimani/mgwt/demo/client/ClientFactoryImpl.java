/*
 * Copyright 2010 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.tomkimani.mgwt.demo.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.tomkimani.mgwt.demo.client.SearchResults.SearchResultsActivity.ISearchResultsView;
import com.tomkimani.mgwt.demo.client.SearchResults.SearchResultsView;
import com.tomkimani.mgwt.demo.client.customerSearch.CustomerSearchActivity.ICustomerSearchView;
import com.tomkimani.mgwt.demo.client.customerSearch.CustomerSearchView;
import com.tomkimani.mgwt.demo.client.dashboard.DashboardActivity.IDashboardView;
import com.tomkimani.mgwt.demo.client.dashboard.DashboardView;
import com.tomkimani.mgwt.demo.client.login.LoginActivity.ILoginView;
import com.tomkimani.mgwt.demo.client.login.LoginView;
import com.tomkimani.mgwt.demo.client.settings.SettingsActivity.ISettingsView;
import com.tomkimani.mgwt.demo.client.settings.SettingsView;
import com.tomkimani.mgwt.demo.client.transactions.TransactionsActivity.ITransactionsView;
import com.tomkimani.mgwt.demo.client.transactions.TransactionsView;
import com.tomkimani.mgwt.demo.client.transactions.detail.TransactionDetailActivity.ITransactionDetailView;
import com.tomkimani.mgwt.demo.client.transactions.detail.TransactionDetailView;

/**
 * @author Daniel Kurka
 * 
 */
public class ClientFactoryImpl implements ClientFactory {

	private EventBus eventBus;
	private PlaceController placeController;
	private PhoneGap phonegap;

	public ClientFactoryImpl() {
		eventBus = new SimpleEventBus();
		placeController = new PlaceController(eventBus);
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}
	
	public void setPhonegap(PhoneGap phonegap) {
		this.phonegap = phonegap;
	}
	
	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public IDashboardView getDashboardView() {
		return new DashboardView();
	}

	@Override
	public ILoginView getLoginView() {
		return new LoginView();
	}

	@Override
	public ITransactionsView getTransactionsView() {
		return new TransactionsView();
	}

	@Override
	public ITransactionDetailView getTransactionDetailView() {
		return new TransactionDetailView();
	}

	@Override
	public ICustomerSearchView getCustomerSearchView() {
		return new CustomerSearchView();
	}

	@Override
	public ISearchResultsView getSearchResults() {
		return new SearchResultsView();
	}

	@Override
	public ISettingsView getSettingsView() {
		return new SettingsView();
	}

	@Override
	public PhoneGap getPhonegap() {
		return this.phonegap;
	}
}
