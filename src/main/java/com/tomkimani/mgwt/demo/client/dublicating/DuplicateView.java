package com.tomkimani.mgwt.demo.client.dublicating;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.tomkimani.mgwt.demo.client.dublicating.DuplicateActivity.IDuplicatingView;

public class DuplicateView implements IDuplicatingView{

	private static TransactionsViewUiBinder uiBinder = GWT
			.create(TransactionsViewUiBinder.class);
	
	interface TransactionsViewUiBinder extends UiBinder<Widget,DuplicateView> {
	}
	
	LayoutPanel LayoutPanel;
	WidgetList widgetList;
	
	//private final Widget widget;
	
	public DuplicateView() {
		//widget = uiBinder.createAndBindUi(this);
		
		widgetList = new WidgetList();
		LayoutPanel = new LayoutPanel();
	}
	
	@Override
	public Widget asWidget() {
		return LayoutPanel;
	}
	
}
