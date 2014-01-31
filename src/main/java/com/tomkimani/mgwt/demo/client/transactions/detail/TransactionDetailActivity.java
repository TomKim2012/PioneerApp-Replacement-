package com.tomkimani.mgwt.demo.client.transactions.detail;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.notification.AlertCallback;
import com.googlecode.gwtphonegap.client.notification.ConfirmCallback;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.ProgressIndicator;
import com.tomkimani.mgwt.demo.client.ClientFactory;
import com.tomkimani.mgwt.demo.client.MyBeanFactory;
import com.tomkimani.mgwt.demo.client.MyDialogs;
import com.tomkimani.mgwt.demo.client.MyRequestBuilder;
import com.tomkimani.mgwt.demo.client.MyRequestCallback;
import com.tomkimani.mgwt.demo.client.base.BaseActivity;
import com.tomkimani.mgwt.demo.client.customerSearch.CustomerSearchActivity.Customer;
import com.tomkimani.mgwt.demo.client.places.CustomerSearchPlace;
import com.tomkimani.mgwt.demo.client.places.DashboardPlace;
import com.tomkimani.mgwt.demo.client.places.TransactionDetailPlace;
import com.tomkimani.mgwt.demo.client.places.TransactionsPlace;
import com.tomkimani.mgwt.demo.client.transactions.Transaction;

public class TransactionDetailActivity extends BaseActivity {
		private ITransactionDetailView view;
		private MyBeanFactory beanFactory;
		private Boolean isMiniStatement=false;
		//private Logger log = Logger.getLogger(getClass().getName());
		
		public interface ITransactionDetailView extends IView{
			public HasTapHandlers getBackButton();
			void renderDisplay(Customer cust1,Boolean isMiniStatement);
			HasTapHandlers getSaveButton();
			String getAmountTextBox();
			ProgressIndicator getProgressIndicator();
			
		}
		public TransactionDetailActivity(ClientFactory factory) {
			super(factory);
		}
		
		@Override
		public void start(AcceptsOneWidget panel, EventBus eventBus) {
			view = factory.getTransactionDetailView();
			setView(view);
			
			super.start(panel, eventBus);
			
			// AutoBean Factory
			beanFactory = GWT.create(MyBeanFactory.class);
			
			Place place = factory.getPlaceController().getWhere();
			
			if(place instanceof TransactionDetailPlace){
				TransactionDetailPlace transactionDetailPlace = (TransactionDetailPlace)place;
				
				final Customer cust1 = transactionDetailPlace.getCustomer();
				isMiniStatement = transactionDetailPlace.getIsMiniStatement();
				
				System.out.println(cust1.getLastName());
				
				if(!(cust1 == null)){
					if(isMiniStatement){
						view.renderDisplay(cust1,isMiniStatement);
						
						//Perform Transaction Button
						addHandlerRegistration(view.getSaveButton().addTapHandler(new TapHandler() {
							@Override
							public void onTap(TapEvent event) {
								MyDialogs.confirm("Confirm", "Send Mini-Statement to "+cust1.getFirstName()+" "+cust1.getLastName(),
										new ConfirmCallback() {
											@Override
											public void onConfirm(int button) {
												if(button == 1){
												view.getProgressIndicator().setVisible(true);
												performTransaction(cust1.getCustomerId());
												}
											}
										});
								}
						}));
						
						addHandlerRegistration(view.getBackButton().addTapHandler(new TapHandler() {
							
							@Override
							public void onTap(TapEvent event) {
								factory.getPlaceController().goTo(new CustomerSearchPlace());
							}
						}));
						
					}else{
					view.renderDisplay(cust1,false);
					
					final ConfirmCallback confirmBack =new ConfirmCallback(){
						@Override
						public void onConfirm(int button) {
							if(button==1){	
							performTransaction(view.getAmountTextBox(),cust1.getCustomerId());
							}
						}
						
					};
					//Perform Transaction Button
					addHandlerRegistration(view.getSaveButton().addTapHandler(new TapHandler() {
						@Override
						public void onTap(TapEvent event) {
							if(view.getAmountTextBox().isEmpty()){
								MyDialogs.alert("Error", "Please Enter a valid Amount");
								return;
							}
							MyDialogs.confirm("Confirm", "Deposit Ksh "+view.getAmountTextBox()+" to "+
											cust1.getFirstName()+" "+cust1.getLastName(),confirmBack);
						}
					}));
					addHandlerRegistration(view.getBackButton().addTapHandler(new TapHandler() {
						@Override
						public void onTap(TapEvent event) {
							factory.getPlaceController().goTo(new CustomerSearchPlace());
						}
					}));
				  
				  }
				}
				
			}
			panel.setWidget(view);
			
		}
		
		
		private void performTransaction(String Amount, String CustomerId) {
			  view.showBusy(true);
			  String customUrl="transactions";
			  
			  JSONObject jrequest = new JSONObject(); jrequest.put("customerId", new JSONString(CustomerId));
			  jrequest.put("transaction_amount", new JSONString(Amount));
			  jrequest.put("transaction_type", new JSONString("Deposit"));
			  String postData = jrequest.toString();
			 

			MyRequestBuilder rqs = new MyRequestBuilder(RequestBuilder.POST, customUrl);

			try {
				Request request = rqs.getBuilder().sendRequest(postData, new MyRequestCallback() {
					
					public void onResponseReceived(Request request,
							Response response) {
						if (200 == response.getStatusCode()) {
							view.showBusy(false);
							
							TransactionResult result = deserializeFromJson(response.getText());
							if(result.getSuccess()){
								showResponseSuccess(result);
							}else{
								MyDialogs.alert("Transaction Failure", "There was a problem posting the transaction");
							}
							
						} else {
							MyDialogs.alert("Transaction Failure", "There was a problem posting the transaction");
						}
					}

				
				});
			} catch (RequestException e) {
				System.err.println("Couldn't retrieve JSON");
			}
			
			showTransactionComplete();
		}
		
		private void showResponseSuccess(TransactionResult result) {
			final Transaction trx = makeTransaction();
			
			trx.setCustNames(result.getCustNames());
			trx.setTransactionAmount(result.getTransactionAmount());
			trx.setTransactionCode(result.getTransactionCode());
			trx.setTransactionTime(result.getTransactionTime());
			trx.setTransactionDate(result.getTransactionDate());
			trx.setTransactionType(result.getTransactionType());
			
			MyDialogs.alert("Success", "Transaction successfull completed.",new AlertCallback() {
				@Override
				public void onOkButtonClicked() {
					factory.getPlaceController().goTo(new TransactionsPlace(trx));
				}
			},"OK");
			MyDialogs.Beep(1);
			MyDialogs.vibrate(1000);
			MyDialogs.vibrate(1000);
		}
	
		private void showTransactionComplete() {
			factory.getPlaceController().goTo(new DashboardPlace());
			MyDialogs.alert("Transaction Success", "Sent. Wait for response");
		}

		private void performTransaction(String customerId) {
			  String customUrl="custTransactions";
			  
			  JSONObject jrequest = new JSONObject();
			  jrequest.put("clCode", new JSONString(customerId));
			  String postData = jrequest.toString();
		
			MyRequestBuilder rqs = new MyRequestBuilder(RequestBuilder.POST, customUrl);

			try {
				Request request = rqs.getBuilder().sendRequest(postData, new MyRequestCallback() {
					
					public void onResponseReceived(Request request,
							Response response) {
						if (200 == response.getStatusCode()) {
							TransactionResult result = deserializeFromJson(response.getText());
							if(result.getSuccess()){
								showResponseSuccess(result);
							}else{
								MyDialogs.alert("Transaction Failure", "Server could not post your transaction");
							}
						} else {
							MyDialogs.alert("Transaction Failure", "There was a problem posting the transaction");
						}
					}
				});
			} catch (RequestException e) {
				System.err.println("Couldn't retrieve JSON");
			}
			
			showTransactionComplete();
		}
		
		public interface TransactionResult{
			Boolean getSuccess();
			Boolean getSms();
			String getCustNames();
			String getTransactionAmount();
			String getTransactionCode();
			String getTransactionType();
			String getTransactionDate();
			String getTransactionTime();
		}
		
		TransactionResult makeTransactionResult(){
			AutoBean<TransactionResult> transactionResult = beanFactory.transactionResult();
			return transactionResult.as();
		}
		
		TransactionResult deserializeFromJson (String json){
			AutoBean<TransactionResult> bean = AutoBeanCodex.decode(beanFactory, TransactionResult.class, json);
			return bean.as();
		}	
		
		public Transaction makeTransaction() {
			AutoBean<Transaction> transaction = beanFactory.transaction();
			return transaction.as();
		}

}
