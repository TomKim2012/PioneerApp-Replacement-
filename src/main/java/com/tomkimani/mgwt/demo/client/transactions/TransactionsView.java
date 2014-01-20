package com.tomkimani.mgwt.demo.client.transactions;

import java.util.List;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList.CellGroup;
import com.googlecode.mgwt.ui.client.widget.HeaderList;
import com.googlecode.mgwt.ui.client.widget.ProgressBar;
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
		
		createContent(headerList);
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
