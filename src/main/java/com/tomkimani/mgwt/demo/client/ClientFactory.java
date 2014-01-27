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
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.tomkimani.mgwt.demo.client.SearchResults.SearchResultsActivity.ISearchResultsView;
import com.tomkimani.mgwt.demo.client.customerSearch.CustomerSearchActivity.ICustomerSearchView;
import com.tomkimani.mgwt.demo.client.dashboard.DashboardActivity.IDashboardView;
import com.tomkimani.mgwt.demo.client.login.LoginActivity.ILoginView;
import com.tomkimani.mgwt.demo.client.settings.SettingsActivity.ISettingsView;
import com.tomkimani.mgwt.demo.client.transactions.TransactionsActivity.ITransactionsView;
import com.tomkimani.mgwt.demo.client.transactions.detail.TransactionDetailActivity.ITransactionDetailView;


public interface ClientFactory {

	public EventBus getEventBus();

	public PlaceController getPlaceController();

	public IDashboardView getDashboardView();
	
	public ILoginView getLoginView();

	public ITransactionsView getTransactionsView();

	public ITransactionDetailView getTransactionDetailView();

	public ICustomerSearchView getCustomerSearchView();

	public ISearchResultsView getSearchResults();

	public ISettingsView getSettingsView();

	void setPhonegap(PhoneGap phonegap);
	
	PhoneGap getPhonegap();
	

}
