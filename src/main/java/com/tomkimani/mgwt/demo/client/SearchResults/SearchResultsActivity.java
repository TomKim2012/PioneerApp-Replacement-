package com.tomkimani.mgwt.demo.client.SearchResults;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedHandler;
import com.googlecode.mgwt.ui.client.widget.celllist.HasCellSelectedHandler;
import com.tomkimani.mgwt.demo.client.ClientFactory;
import com.tomkimani.mgwt.demo.client.base.BaseActivity;
import com.tomkimani.mgwt.demo.client.customerSearch.CustomerResult;
import com.tomkimani.mgwt.demo.client.customerSearch.CustomerSearchActivity.Customer;
import com.tomkimani.mgwt.demo.client.places.CustomerSearchPlace;
import com.tomkimani.mgwt.demo.client.places.SearchResultsPlace;
import com.tomkimani.mgwt.demo.client.places.TransactionDetailPlace;

public class SearchResultsActivity extends BaseActivity {
		
		private List<Customer> customerList;
		private Boolean isMiniStatement;

		public interface ISearchResultsView extends IView{

			void setTopics(List<CustomerResult> createTopicsList);

			HasCellSelectedHandler getCellSelectedHandler();
		}
		public SearchResultsActivity(ClientFactory factory) {
			super(factory);
		}
		
		@Override
		public void start(AcceptsOneWidget panel, EventBus eventBus) {
			ISearchResultsView view = factory.getSearchResults();
			
			panel.setWidget(view);
			
			Place place =factory.getPlaceController().getWhere();
			
			if(place instanceof SearchResultsPlace){
				SearchResultsPlace resultsPlace = (SearchResultsPlace)place;
				customerList = resultsPlace.getCustomerresults();
				isMiniStatement = resultsPlace.getMiniStatement();
			}
			
			List<CustomerResult> cresults= new ArrayList<CustomerResult>();
			 
			for(Customer cst : customerList){
       		   cresults.add(new CustomerResult(cst.getRefNo(), cst.getFirstName(), cst.getLastName()));
       	   }

			addHandlerRegistration(view.getCellSelectedHandler().addCellSelectedHandler(new CellSelectedHandler(){
				@Override
				public void onCellSelected(CellSelectedEvent event) {
					Customer cust1 = customerList.get(event.getIndex());
					
					
					if(isMiniStatement){
					 factory.getPlaceController().goTo(new TransactionDetailPlace(cust1,true));
					}else{
					 factory.getPlaceController().goTo(new TransactionDetailPlace(cust1));
					}
				}
			}));
			
			addHandlerRegistration(view.getBackButton().addTapHandler(new TapHandler() {
				@Override
				public void onTap(TapEvent event) {
					factory.getPlaceController().goTo(new CustomerSearchPlace());
				}
			}));
			
			
			view.setTopics(cresults);
		}
}
