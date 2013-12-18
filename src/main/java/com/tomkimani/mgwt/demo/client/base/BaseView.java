package com.tomkimani.mgwt.demo.client.base;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.ProgressIndicator;
import com.googlecode.mgwt.ui.client.widget.buttonbar.ButtonBar;
import com.googlecode.mgwt.ui.client.widget.tabbar.BookmarkTabBarButton;
import com.googlecode.mgwt.ui.client.widget.tabbar.FeaturedTabBarButton;
import com.googlecode.mgwt.ui.client.widget.tabbar.MoreTabBarButton;
import com.tomkimani.mgwt.demo.client.base.BaseActivity.IView;

public class BaseView implements IView{

	protected LayoutPanel mainPanel;
	protected ButtonBar buttonBar;
	protected HeaderPanel headerPanel;
	private ProgressIndicator progressIndicator;
	protected HeaderButton logoutButton;
	private HTML title;
	protected BookmarkTabBarButton transactionButton;
	protected FeaturedTabBarButton homeButton;
	protected MoreTabBarButton settingsButton;
	protected HeaderButton backButton;

	public BaseView(){
		//Container
		mainPanel = new LayoutPanel();
		headerPanel = new HeaderPanel();
		title = new HTML();
		logoutButton = new HeaderButton();
		
		//Header
		createHeader();
	}
	
	protected void createContent(Widget content) {
		mainPanel.add(content);
		createBottomButtons();
	}

	@Override
	public Widget asWidget() {
		return mainPanel;
	}
	
	private void createHeader(){
		title.setText("Pioneer FSA");
		headerPanel.setCenterWidget(title);
		
		//Logout Button
		logoutButton.setText("LogOut");
		headerPanel.setRightWidget(logoutButton);
		
		
		backButton = new HeaderButton();
		backButton.setVisible(false);
		backButton.setText("Back");
		backButton.setBackButton(true);
		headerPanel.setLeftWidget(backButton);
		mainPanel.add(headerPanel);
	}
	
	private void createBottomButtons(){
		//Initializations
		buttonBar= new ButtonBar();
		homeButton = new FeaturedTabBarButton();
		transactionButton= new BookmarkTabBarButton();
		settingsButton = new MoreTabBarButton();
		
		//TabBar Buttons
		homeButton.setText("Home");
		transactionButton.setText("Transactions");
		settingsButton.setText("Settings");
		
		buttonBar.add(homeButton);
		buttonBar.add(transactionButton);
		buttonBar.add(settingsButton);
		
		mainPanel.add(buttonBar);
	}
	
	public HasTapHandlers getLogoutButton() {
		return logoutButton;
	}
	
	public HasTapHandlers getTransactionButton() {
		return transactionButton;
	}
	
	public  HasTapHandlers getHomeButton() {
		return homeButton;
	}
	
	public HasTapHandlers getBackButton() {
		return backButton;
	}
	
	public HasTapHandlers getSettingsButton(){
		return settingsButton;
	}
	
	public void showBusy(boolean status){
		if(status){
			progressIndicator.setVisible(true);
		}else{
			progressIndicator.setVisible(false);
		}
	}

}
