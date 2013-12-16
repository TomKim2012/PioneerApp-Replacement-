package com.tomkimani.mgwt.demo.client.transactions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList.CellGroup;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList.StandardCellGroup;
import com.tomkimani.mgwt.demo.client.ClientFactory;
import com.tomkimani.mgwt.demo.client.MyRequestBuilder;
import com.tomkimani.mgwt.demo.client.base.BaseActivity;
import com.tomkimani.mgwt.demo.client.places.TransactionDetailPlace;
import com.tomkimani.mgwt.demo.client.transactions.GroupCell.Content;
import com.tomkimani.mgwt.demo.client.transactions.GroupCell.Header;

public class TransactionsActivity extends BaseActivity {
		private List<Transaction> trxs=new ArrayList<Transaction>();
		private ArrayList<CellGroup<Header, Content>> CellgroupList= new ArrayList<CellGroup<Header, Content>>();
		
		Date previousDate=null;
		List<Content> contentList = new ArrayList<Content>();
		Header header = null;
		CellGroup<Header, Content> cellGroup;
		
		
		public interface ITransactionsView extends IView{
			public void displayError(String message);
			void showLoading(boolean show);
			void render(List<CellGroup<Header, Content>> models);
			GroupingCellList<Header, Content> getGroupCell();
		}
		public TransactionsActivity(ClientFactory factory) {
			super(factory);
		}
		
		@Override
		public void start(AcceptsOneWidget panel, EventBus eventBus) {	
			final ITransactionsView view = factory.getTransactionsView();
			setView(view);
			
			super.start(panel, eventBus);
			
			view.getGroupCell().addSelectionHandler(new SelectionHandler<GroupCell.Content>() {
				
				@Override
				public void onSelection(SelectionEvent<Content> event) {
					System.out.println(event.getSelectedItem().getIndex());
					int index =event.getSelectedItem().getIndex();
					Transaction transaction = trxs.get(index);
					factory.getPlaceController().goTo(new TransactionDetailPlace(transaction));
					
				}
			});
			
			
			//view.setHeaderPullHandler(headerHandler);
			
			
			//Is data Loaded from server
			
			FetchDataFromServer(view);
			
			panel.setWidget(view);
		}
		private void FetchDataFromServer(final ITransactionsView view){
		view.showLoading(true);
		
		String url = "transactions";
		
		
		JsonpRequestBuilder builder = new JsonpRequestBuilder();
		try {
			builder.requestObject(MyRequestBuilder.serverUrl+url, new AsyncCallback<JsArray<MyTransaction>>() {
				@Override
				public void onSuccess(JsArray<MyTransaction> result) {
					//Hide the Loading Interface
					view.showLoading(false);
					
					if(result.length()==0){
						return;
					}
					
					for(int i=0; i<result.length(); i++){
						MyTransaction trx = result.get(i);
						trxs.add(trx);
					}
					
					//DateFormatter
					DefaultDateTimeFormatInfo info =new DefaultDateTimeFormatInfo();
					final DateTimeFormat dtf = new DateTimeFormat("yyyy-MM-dd",info){};
				
					
					////////Sorting the List
					Collections.sort(trxs, new Comparator<Transaction>() {
						@Override
						public int compare(Transaction t1, Transaction t2) {
							Date date1 = dtf.parse(t1.getTransactionDate());
							Date date2 = dtf.parse(t2.getTransactionDate());
							return -date1.compareTo(date2);
						}
					});
					
					int index=0;
					for(Transaction trx:trxs){
						if(previousDate==null){
							previousDate=dtf.parse(trx.getTransactionDate());
							header = new Header(dtf.parse(trx.getTransactionDate()));
						}
						if(previousDate.equals(dtf.parse(trx.getTransactionDate()))){
							contentList.add(new Content(index, trx.getTransactionType(), Double.parseDouble(trx.getTransactionAmount()),
									trx.getTransactionTime()));	
						}else{
							formCellgroup(header, contentList);
							header=new Header(dtf.parse(trx.getTransactionDate()));
							//System.err.println("Header<<"+ dtf.parse(trx.getTransactionDate()));
							contentList.clear();
							contentList.add(new Content(index, trx.getTransactionType(), Double.parseDouble(trx.getTransactionAmount()),
									trx.getTransactionTime()));
						}
					    previousDate =dtf.parse(trx.getTransactionDate());
					    index++;
					}
					
					formCellgroup(header, contentList);
					view.render(CellgroupList);
					//view.setTopics(trxs);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					view.showLoading(false);
					caught.printStackTrace();
				}
			});
			
		} catch (Exception e) {
			view.displayError(e.getMessage());
		}
	}
		void formCellgroup(Header header, List<Content> contentList) {
			List<Content> content2 =new ArrayList<GroupCell.Content>();
			for(Content cnt : contentList){
				content2.add(cnt);
				System.out.println(cnt.getAmount());
			}
			cellGroup = new StandardCellGroup<Header, Content>("",header,content2);
			CellgroupList.add(cellGroup);
		}
}
