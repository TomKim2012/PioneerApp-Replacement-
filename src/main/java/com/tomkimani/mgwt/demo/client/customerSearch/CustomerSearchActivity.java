package com.tomkimani.mgwt.demo.client.customerSearch;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.tomkimani.mgwt.demo.client.ClientFactory;
import com.tomkimani.mgwt.demo.client.MyBeanFactory;
import com.tomkimani.mgwt.demo.client.MyRequestBuilder;
import com.tomkimani.mgwt.demo.client.base.BaseActivity;
import com.tomkimani.mgwt.demo.client.places.CustomerSearchPlace;
import com.tomkimani.mgwt.demo.client.places.DashboardPlace;
import com.tomkimani.mgwt.demo.client.places.SearchResultsPlace;

public class CustomerSearchActivity extends BaseActivity {
		private MyBeanFactory beanFactory;
		private List<Customer> custList;
		private Boolean isMinistatement=false;
		
		public interface ICustomerSearchView extends IView{
			HasTapHandlers getSearchButton();
			String getmListBox();
			String getCustInput();
			HTML getIssuesArea();
			void showBusy(boolean status);
		}
		public CustomerSearchActivity(ClientFactory factory) {
			super(factory);
		}
		
		@Override
		public void start(AcceptsOneWidget panel, EventBus eventBus) {
			//View Factory
			final ICustomerSearchView view = factory.getCustomerSearchView();
			setView(view);
			
			super.start(panel, eventBus);
			//AutoBean Factory
			beanFactory = GWT.create(MyBeanFactory.class);
			
			Place place = factory.getPlaceController().getWhere();
			
			if(place instanceof CustomerSearchPlace){
				CustomerSearchPlace searchPlace = (CustomerSearchPlace)place;
				
				isMinistatement = searchPlace.getMiniStatement();
			}
			
			addHandlerRegistration(view.getSearchButton().addTapHandler(new TapHandler() {
				
				@Override
				public void onTap(TapEvent event) {
					String parameter = view.getmListBox(); 
					String value = view.getCustInput();
					if(!value.isEmpty()){
						postDataToServer(parameter,value,view);
					}else{
						view.getIssuesArea().setText("You have not entered any value");
						view.getIssuesArea().setVisible(true);
					}
				}
			}));
			
			addHandlerRegistration(view.getBackButton().addTapHandler(new TapHandler() {
				@Override
				public void onTap(TapEvent event) {
					factory.getPlaceController().goTo(new DashboardPlace());
				}
			}));
		
			panel.setWidget(view);
		}

		private void postDataToServer(String searchParameter, String searchValue,final ICustomerSearchView view) {
			String customUrl ="custDetails/parameter/"+searchParameter + "/value/"+searchValue;
			
			MyRequestBuilder rqs = new MyRequestBuilder(RequestBuilder.GET, customUrl);
			try {
				  view.showBusy(true);
			      Request request = rqs.getBuilder().sendRequest(null, new RequestCallback() {

					public void onError(Request request, Throwable exception) {
			          System.err.println("Couldn't retrieve JSON");
			        }

			        public void onResponseReceived(Request request, Response response) {
			          if (200 == response.getStatusCode()) {
			        	  view.showBusy(false);
			        	  custList = new ArrayList<Customer>();
			        	
			        	  if(response.getText().isEmpty()){
				        	  view.getIssuesArea().setText("Customer Records not Found"); 
				        	  view.getIssuesArea().setVisible(true);
				        	  return;
				           }
			           	 
			        	  CustomerList lst = deserializeFromJson("{\"customerList\": "+response.getText()+"}");
			        	  custList = lst.getCustomerList();
			        	  
			        	  //Check if search is Mini-Statement OR Deposit
			        	  if(isMinistatement){
			        		  factory.getPlaceController().goTo(new SearchResultsPlace(custList,true));
			        	  }else{
			        		  factory.getPlaceController().goTo(new SearchResultsPlace(custList));
			        	  }
			        	  
			          } else {
			        	  view.showBusy(false);
			        	  System.err.println("Couldn't retrieve JSON (" + response.getStatusText()
			                + ")");
			        	  Dialogs.alert("Error", "An error occured while retrieving the data", null);
			          }
			        }
			      });
			    } catch (RequestException e) {
			    	System.err.println("Couldn't retrieve JSON");
			    }
		}
		
		public interface Customer{
			String getRefNo();
			String getFirstName();
			String getLastName();
			String getIdNo();
			String getMobileNo();
			String getCustomerId();
			String getAccountNo();
		}
		
		public interface CustomerList{
			List<Customer> getCustomerList();
		}
		
		CustomerList deserializeFromJson (String json){
			System.out.println(json);
			AutoBean<CustomerList> bean = AutoBeanCodex.decode(beanFactory, CustomerList.class, json);
			return bean.as();
		}	
}
