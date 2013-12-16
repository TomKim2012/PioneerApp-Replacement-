package com.tomkimani.mgwt.demo.client.customer.detail;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.tomkimani.mgwt.demo.client.dublicating.DuplicateActivity.IDuplicatingView;

public class CustomerDetailView implements IDuplicatingView{

	private static TransactionsViewUiBinder uiBinder = GWT
			.create(TransactionsViewUiBinder.class);
	
	interface TransactionsViewUiBinder extends UiBinder<Widget,CustomerDetailView> {
	}
	
	LayoutPanel LayoutPanel;
	WidgetList widgetList;
	
	//private final Widget widget;
	
	public CustomerDetailView() {
		//widget = uiBinder.createAndBindUi(this);
		
		widgetList = new WidgetList();
		LayoutPanel = new LayoutPanel();
	}
	
	@Override
	public Widget asWidget() {
		return LayoutPanel;
	}
	
}
