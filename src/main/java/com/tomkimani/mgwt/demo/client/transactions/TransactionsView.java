package com.tomkimani.mgwt.demo.client.transactions;

import java.util.List;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList.CellGroup;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.HeaderList;
import com.googlecode.mgwt.ui.client.widget.ProgressBar;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.googlecode.mgwt.ui.client.widget.celllist.Cell;
import com.tomkimani.mgwt.demo.client.base.BaseView;
import com.tomkimani.mgwt.demo.client.transactions.GroupCell.Content;
import com.tomkimani.mgwt.demo.client.transactions.GroupCell.Header;
import com.tomkimani.mgwt.demo.client.transactions.TransactionsActivity.ITransactionsView;

public class TransactionsView extends BaseView implements ITransactionsView{
	
	private HTML html;
	private ProgressBar progress;
	private WidgetList wList;
	private HeaderList<Header, Content> headerList;
	private GroupingCellList<Header, Content> groupCell;
	private WidgetList detailedList;
	private HTML dateTime;
	private ScrollPanel scroll;
	
	//private final Widget widget;
	
	public TransactionsView() {
		super();
		
		//widget = uiBinder.createAndBindUi(this);
		
		headerPanel.setCenter("My Transactions");
		
		//Group Cell List
		groupCell = new GroupingCellList<Header, Content>(new ContentCell(), new HeaderCell());
		groupCell.setGroup(true);
		headerList= new HeaderList<Header, Content>(groupCell);
		
		/* Shows the progress */
		wList = new WidgetList();
		progress = new ProgressBar();
		html = new HTML("Loading Transactions..");
		wList.add(progress);
		wList.add(html);
		wList.setRound(true);
		mainPanel.add(wList);
		
		
		//List Display
		detailedList = new WidgetList();
		detailedList.setRound(true);
		scroll = new ScrollPanel();
		scroll.add(detailedList);
		scroll.setVisible(false);
		mainPanel.add(scroll);
		
		createContent(headerList);
		setButtonBar();
	}
	
	private void setButtonBar() {
		homeButton.setSelected(false);
		settingsButton.setSelected(false);
		transactionButton.setSelected(true);
	}

	/*
	 * List to display transaction Detail
	 */
	@Override
	public void renderDisplay(Transaction trx){
		showTransactions(false);
		detailedList.clear();
		headerPanel.setCenter("Transaction Detail");
		headerPanel.setCenterWidget(new HTML(trx.getTransactionType()));
		
		dateTime=new HTML(trx.getTransactionDate()+" "+ trx.getTransactionTime());
		
		detailedList.add(new FormListEntry("Transaction Date/Time:",dateTime));
		detailedList.add(new FormListEntry("Transaction Type:",new HTML(trx.getTransactionType())));
		detailedList.add(new FormListEntry("Transaction Code:",new HTML(trx.getTransactionCode())));
		detailedList.add(new FormListEntry("Transaction Amount:",new HTML(trx.getTransactionAmount())));
		detailedList.add(new FormListEntry("Customer:",new HTML(trx.getCustNames())));
	}
	
	@Override
	public void showTransactions(boolean status){
		headerPanel.setCenter("Transactions");
		if(status){
			headerList.setVisible(true);
			scroll.setVisible(false);
			backButton.setVisible(false);
		}else{
			headerList.setVisible(false);
			scroll.setVisible(true);
			backButton.setVisible(true);
		}
	}
	
	@Override
	public Widget asWidget() {
		return super.asWidget();
	}
	
	public static class ContentCell implements Cell<Content>{

		@Override
		public void render(SafeHtmlBuilder safeHtmlBuilder, Content model) {
			
			safeHtmlBuilder.appendEscaped(  model.getTransactionTime()+ " : "+
											model.getTransactionType()+" -Ksh "+
										    model.getAmount());
		}

		@Override
		public boolean canBeSelected(Content model) {
			return true;
		}
		
	}
	
	public static class HeaderCell implements Cell<Header>{

		@Override
		public void render(SafeHtmlBuilder safeHtmlBuilder, Header model) {
			safeHtmlBuilder.appendEscaped(model.getDateStr());
		}

		@Override
		public boolean canBeSelected(Header model) {
			return false;
		}
		
	}
	
	public void displayError(String message) {
		Dialogs.alert("Error Occured", message, null);
	}
	
	public void showLoading(boolean show){
		if(show){
			wList.setVisible(true);
		}else{
			wList.setVisible(false);
		}
	}
	
	
	@Override
	public void render(List<CellGroup<Header, Content>> models) {
		headerList.render(models);
	}
	
	
	
	public GroupingCellList<Header, Content> getGroupCell() {
		return groupCell;
	}
	
	
	/*
	 public void setTopics(List<Transaction> topicList) {
		cellList.getCellList().render(topicList);
	}
	public PullArrowWidget getPullHeader() {
		return pullArrowHeader;
	}
	
	public HasRefresh getPullPanel() {
		return pullToRefresh;
	}
	
	@Override
	public void refresh() {
		pullToRefresh.refresh();

	}
	
	@Override
	public void setHeaderPullHandler(Pullhandler pullHandler) {
		pullToRefresh.setHeaderPullhandler(pullHandler);
	}
	*/
}
