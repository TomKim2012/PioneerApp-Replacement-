package com.tomkimani.mgwt.demo.client.transactions.detail;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.dialog.ConfirmDialog.ConfirmCallback;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.googlecode.mgwt.ui.client.widget.ProgressIndicator;
import com.tomkimani.mgwt.demo.client.ClientFactory;
import com.tomkimani.mgwt.demo.client.MyBeanFactory;
import com.tomkimani.mgwt.demo.client.MyRequestBuilder;
import com.tomkimani.mgwt.demo.client.customerSearch.CustomerSearchActivity.Customer;
import com.tomkimani.mgwt.demo.client.places.DashboardPlace;
import com.tomkimani.mgwt.demo.client.places.SearchResultsPlace;
import com.tomkimani.mgwt.demo.client.places.TransactionDetailPlace;
import com.tomkimani.mgwt.demo.client.places.TransactionsPlace;
import com.tomkimani.mgwt.demo.client.transactions.Transaction;

public class TransactionDetailActivity extends MGWTAbstractActivity {
		ClientFactory factory;
		private ITransactionDetailView view;
		private MyBeanFactory beanFactory;
		private Boolean isMiniStatement=false;
		
		public interface ITransactionDetailView extends IsWidget{
			public HasTapHandlers getBackButton();
			public HasText getBackButtonText();
			void renderDisplay(Transaction trx);
			void renderDisplay(Customer cust1,Boolean isMiniStatement);
			HasTapHandlers getSaveButton();
			String getAmountTextBox();
			ProgressIndicator getProgressIndicator();
			
		}
		public TransactionDetailActivity(ClientFactory factory) {
			this.factory= factory;
		}
		
		@Override
		public void start(AcceptsOneWidget panel, EventBus eventBus) {
			view = factory.getTransactionDetailView();
			
			
			// AutoBean Factory
			beanFactory = GWT.create(MyBeanFactory.class);
			
			Place place = factory.getPlaceController().getWhere();
			
			if(place instanceof TransactionDetailPlace){
				TransactionDetailPlace transactionDetailPlace = (TransactionDetailPlace)place;
				
				Transaction trx = transactionDetailPlace.getTransaction();
				final Customer cust1 = transactionDetailPlace.getCustomer();
				isMiniStatement = transactionDetailPlace.getIsMiniStatement();
				
				if(!(trx == null)){
					view.renderDisplay(trx);
					addHandlerRegistration(view.getBackButton().addTapHandler(new TapHandler() {
						
						@Override
						public void onTap(TapEvent event) {
							factory.getPlaceController().goTo(new TransactionsPlace());
							
						}
					}));
				}
				
				if(!(cust1 == null)){
					if(isMiniStatement){
						view.renderDisplay(cust1,isMiniStatement);
						
						//Perform Transaction Button
						addHandlerRegistration(view.getSaveButton().addTapHandler(new TapHandler() {
							@Override
							public void onTap(TapEvent event) {
								view.getProgressIndicator().setVisible(true);
								performTransaction(cust1.getCustomerId());
							}
						}));
						
					}else{
					view.renderDisplay(cust1,false);
					
					final ConfirmCallback confirmBack =new ConfirmCallback() {
						@Override
						public void onOk() {
							performTransaction(view.getAmountTextBox(),cust1.getCustomerId());
						}
						
						@Override
						public void onCancel() {
						}
					};
					//Perform Transaction Button
					addHandlerRegistration(view.getSaveButton().addTapHandler(new TapHandler() {
						@Override
						public void onTap(TapEvent event) {
							Dialogs.confirm("Confirm", "Deposit Ksh "+view.getAmountTextBox()+" to "+
											cust1.getFirstName()+" "+cust1.getLastName(),confirmBack);
						}
					}));
					addHandlerRegistration(view.getBackButton().addTapHandler(new TapHandler() {
						@Override
						public void onTap(TapEvent event) {
							factory.getPlaceController().goTo(new SearchResultsPlace());
						}
					}));
				  
				  }
				}
				
				view.getBackButtonText().setText("Back");
			}
			panel.setWidget(view);
			
		}
		
		
		private void performTransaction(String Amount, String CustomerId) {
			  String customUrl="transactions";
			  
			  JSONObject jrequest = new JSONObject(); jrequest.put("customerId", new JSONString(CustomerId));
			  jrequest.put("transaction_amount", new JSONString(Amount));
			  jrequest.put("transaction_type", new JSONString("Deposit"));
			  String postData = jrequest.toString();
			 

			MyRequestBuilder rqs = new MyRequestBuilder(RequestBuilder.POST, customUrl);

			try {
				Request request = rqs.getBuilder().sendRequest(postData, new RequestCallback() {
					public void onError(Request request, Throwable exception) {
						System.err.println("Couldn't retrieve JSON");
					}

					public void onResponseReceived(Request request,
							Response response) {
						if (200 == response.getStatusCode()) {
							TransactionResult result = deserializeFromJson(response.getText());
							if(result.getSuccess()){
								Dialogs.alert("Transaction Success", "The Transaction has been sent successfully.", null);
								
								factory.getPlaceController().goTo(new DashboardPlace());
							}else{
								Dialogs.alert("Transaction Failure", "There was a problem posting the transaction", null);
							}
							
						} else {
							Dialogs.alert("Transaction Failure", "There was a problem posting the transaction", null);
						}
					}
				});
			} catch (RequestException e) {
				System.err.println("Couldn't retrieve JSON");
			}

		}
		
		
		private void performTransaction(String customerId) {
			  String customUrl="custTransactions/customerId/"+customerId;
			  
		
			MyRequestBuilder rqs = new MyRequestBuilder(RequestBuilder.GET, customUrl);

			try {
				Request request = rqs.getBuilder().sendRequest(null, new RequestCallback() {
					public void onError(Request request, Throwable exception) {
						System.err.println("Couldn't retrieve JSON");
					}

					public void onResponseReceived(Request request,
							Response response) {
						if (200 == response.getStatusCode()) {
							TransactionResult result = deserializeFromJson(response.getText());
							if(result.getSuccess()){
								Dialogs.alert("Transaction Success", "The Transaction has been sent successfully.", null);
								
								factory.getPlaceController().goTo(new DashboardPlace());
							}else{
								Dialogs.alert("Transaction Failure", "There was a problem posting the transaction", null);
							}
							
						} else {
							Dialogs.alert("Transaction Failure", "There was a problem posting the transaction", null);
						}
					}
				});
			} catch (RequestException e) {
				System.err.println("Couldn't retrieve JSON");
			}

		}
		
		public interface TransactionResult{
			Boolean getSuccess();
			Boolean getSms();
		}
		
		TransactionResult makeTransactionResult(){
			AutoBean<TransactionResult> transactionResult = beanFactory.transactionResult();
			return transactionResult.as();
		}
		
		TransactionResult deserializeFromJson (String json){
			AutoBean<TransactionResult> bean = AutoBeanCodex.decode(beanFactory, TransactionResult.class, json);
			return bean.as();
		}	
}
