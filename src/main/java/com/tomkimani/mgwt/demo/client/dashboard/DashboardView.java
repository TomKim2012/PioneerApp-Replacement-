package com.tomkimani.mgwt.demo.client.dashboard;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.tomkimani.mgwt.demo.client.base.BaseView;
import com.tomkimani.mgwt.demo.client.dashboard.DashboardActivity.IDashboardView;
import com.tomkimani.mgwt.demo.client.login.LoginActivity;
import com.tomkimani.mgwt.demo.client.ui.IconButton;

public class DashboardView extends BaseView implements IDashboardView{

	private static DashboardViewUiBinder uiBinder = GWT.create(DashboardViewUiBinder.class);
	
	private final Widget widget;
	
	@UiField VerticalPanel vPanel;
	@UiField IconButton btnDeposit;
	@UiField IconButton btnStatement;
	@UiField InlineLabel spnUserName;
	@UiField HTMLPanel divUser;
	@UiField HorizontalPanel downsideDiv;
	private LayoutPanel dashboadPanel;

	interface DashboardViewUiBinder extends UiBinder<Widget, DashboardView> {
	}

	public DashboardView() {
		widget = uiBinder.createAndBindUi(this);
		dashboadPanel = new LayoutPanel();
		
		//downsideDiv.setVisible(false);
		//UserName Details
		spnUserName.setText("Welcome "+ LoginActivity.loggedFullNames);
		spnUserName.getElement().getStyle().setMargin(5.0, Unit.PCT);
		spnUserName.getElement().getStyle().setColor("Blue");
		dashboadPanel.add(divUser);
		
		dashboadPanel.setHeight("100%");
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
