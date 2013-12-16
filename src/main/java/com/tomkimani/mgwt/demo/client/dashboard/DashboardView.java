package com.tomkimani.mgwt.demo.client.dashboard;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.tomkimani.mgwt.demo.client.base.BaseView;
import com.tomkimani.mgwt.demo.client.dashboard.DashboardActivity.ITestView;
import com.tomkimani.mgwt.demo.client.ui.IconButton;

public class DashboardView extends BaseView implements ITestView{

	private static TestViewUiBinder uiBinder = GWT.create(TestViewUiBinder.class);
	
	private final Widget widget;
	
	@UiField VerticalPanel vPanel;
	@UiField IconButton btnDeposit;
	@UiField IconButton btnStatement;

	private LayoutPanel dashboadPanel;

	interface TestViewUiBinder extends UiBinder<Widget, DashboardView> {
	}

	public DashboardView() {
		widget = uiBinder.createAndBindUi(this);
		dashboadPanel = new LayoutPanel();
		
		vPanel.setSpacing(15);
		dashboadPanel.setHeight("81%");
		dashboadPanel.add(widget);
		ScrollPanel scroller = new ScrollPanel();
		scroller.add(dashboadPanel);
		
		createContent(scroller);
	}
	
	@Override
	public Widget asWidget() {
		return super.asWidget();
	}

	public HasTapHandlers getLogoutButton() {
		return logoutButton;
	}
	
	public HasTapHandlers getTransactionButton() {
		return transactionButton;
	}
	
	public HasTapHandlers getBtnDeposit() {
		return btnDeposit;
	}
	
	
	public HasTapHandlers getBtnStatement() {
		return btnStatement;
	}
			
}
