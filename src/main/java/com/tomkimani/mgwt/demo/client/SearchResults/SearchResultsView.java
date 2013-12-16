package com.tomkimani.mgwt.demo.client.SearchResults;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.celllist.BasicCell;
import com.googlecode.mgwt.ui.client.widget.celllist.CellListWithHeader;
import com.googlecode.mgwt.ui.client.widget.celllist.HasCellSelectedHandler;
import com.tomkimani.mgwt.demo.client.SearchResults.SearchResultsActivity.ISearchResultsView;
import com.tomkimani.mgwt.demo.client.base.BaseView;
import com.tomkimani.mgwt.demo.client.customerSearch.CustomerResult;

public class SearchResultsView extends BaseView implements ISearchResultsView{

	private static TransactionsViewUiBinder uiBinder = GWT
			.create(TransactionsViewUiBinder.class);
	
	interface TransactionsViewUiBinder extends UiBinder<Widget,SearchResultsView> {
	}
	
	LayoutPanel LayoutPanel;
	private CellListWithHeader<CustomerResult> cellList;
	
	//private final Widget widget;
	
	public SearchResultsView() {
		LayoutPanel = new LayoutPanel();
		
		cellList = new CellListWithHeader<CustomerResult>(new BasicCell<CustomerResult>() {
			@Override
			public String getDisplayString(CustomerResult model) {
				return model.getDisplayName();	
			}

			@Override
			public boolean canBeSelected(CustomerResult model) {
				return true;
			}
		});
	    
		cellList.getCellList().setRound(true);

		//Scroll Panel
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setWidget(cellList);
		scrollPanel.setScrollingEnabledX(false);
		LayoutPanel.add(scrollPanel);
		
		//Add to Main Panel
		createContent(LayoutPanel);
		logoutButton.setVisible(false);
		backButton.setVisible(true);
		headerPanel.setCenter("Search Results");
		
	}
	
	@Override
	public Widget asWidget() {
		return super.asWidget();
	}
	
	public void setTopics(List<CustomerResult> createTopicsList) {
		cellList.getCellList().render(createTopicsList);
	}
	
	public HasCellSelectedHandler getCellSelectedHandler() {
		return cellList.getCellList();
	}
	
}
