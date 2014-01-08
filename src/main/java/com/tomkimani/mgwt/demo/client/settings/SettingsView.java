package com.tomkimani.mgwt.demo.client.settings;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.tomkimani.mgwt.demo.client.base.BaseView;
import com.tomkimani.mgwt.demo.client.login.LoginActivity;
import com.tomkimani.mgwt.demo.client.login.LoginActivity.User;
import com.tomkimani.mgwt.demo.client.settings.SettingsActivity.Allocation;
import com.tomkimani.mgwt.demo.client.settings.SettingsActivity.ISettingsView;

public class SettingsView extends BaseView implements ISettingsView{

	private static TransactionsViewUiBinder uiBinder = GWT
			.create(TransactionsViewUiBinder.class);
	
	interface TransactionsViewUiBinder extends UiBinder<Widget,SettingsView> {
	}
	
	LayoutPanel LayoutPanel;
	WidgetList deviceSettingList;
	private WidgetList userSettingList;
	private MCheckBox activateCheck;
	private HTML deviceName;
	private HTML deviceImei;
	private HTML userSettingTitle;
	private MListBox mListBox;
	private Button buttonSave;
	private HTML deviceTitle;
	
	//private final Widget widget;
	
	public SettingsView() {
		//widget = uiBinder.createAndBindUi(this);
		
		LayoutPanel = new LayoutPanel();
		deviceSettingList = new WidgetList();
		userSettingList = new WidgetList();
		userSettingList.setRound(true);
		
		headerPanel.setCenter("Settings");
		
		backButton.setVisible(true);
		
		
		//Requirements for user Settings
		activateCheck = new MCheckBox();
		activateCheck.setImportant(true);
		activateCheck.setValue(false);
		
		
		deviceTitle =new HTML("Device Settings");
		deviceTitle.getElement().getStyle().setMarginTop(10, Unit.PX);
		deviceTitle.getElement().getStyle().setMarginLeft(20, Unit.PX);
		deviceTitle.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		LayoutPanel.add(deviceTitle);
		
		//Device Settings
		deviceSettingList.add(new FormListEntry("Allocation:",activateCheck ));
		
		//User Settings
		userSettingTitle=new HTML("User Settings");
		userSettingTitle.getElement().getStyle().setMarginLeft(20, Unit.PX);
		userSettingTitle.getElement().getStyle().setFontWeight(FontWeight.BOLD);
	   
		
		LayoutPanel.add(deviceSettingList);
		LayoutPanel.add(userSettingTitle);
		LayoutPanel.add(userSettingList);
		
		//Save Button
		buttonSave = new Button("Save");
		buttonSave.setConfirm(true);
		LayoutPanel.add(buttonSave);
		
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setScrollingEnabledX(false);
		scrollPanel.add(LayoutPanel);
		createContent(scrollPanel);
		
		buttonBar.setVisible(false);
		userSettingTitle.setVisible(false);
		buttonSave.setVisible(false);
	}
	
	@Override
	public Widget asWidget() {
		return super.asWidget();
	}
	
	public Button getButtonSave() {
		return buttonSave;
	}
	
	public void renderDeviceSettings(String deviceName, String deviceImei) {
		this.deviceName = new HTML(deviceName);
		this.deviceImei = new HTML(deviceImei);
		deviceSettingList.add(new FormListEntry("Device Name:", this.deviceName ));
		deviceSettingList.add(new FormListEntry("Device IMEI:", this.deviceImei));
		deviceSettingList.setRound(true);
	}
	
	@Override
	public void renderUsers(List<User> usersList) {
		mListBox = new MListBox();
		
		userSettingList.add(new FormListEntry("Allocate User:",mListBox));
		mListBox.addItem("---Select----");
		for(User user: usersList){
			mListBox.addItem(user.getFirstName()+" "+ user.getLastName(),
							user.getUserId());
		}
	}
	
	@Override
	public void renderAllocation(Allocation allocation) {
		userSettingTitle.setVisible(true);
		activateCheck.setValue(true);
		userSettingList.add(new FormListEntry("Allocated Date:", new HTML(allocation.getallocationDate())));
		userSettingList.add(new FormListEntry("Allocated User:", new HTML(allocation.getallocatedName())));
		userSettingList.add(new FormListEntry("Allocated By:", new HTML(allocation.getallocateeName())));
	}
	
	
	public void showUserSettings(boolean status){
		if(status){
			userSettingList.setVisible(true);
			userSettingTitle.setVisible(true);
			buttonSave.setVisible(true);
		}else{
			//userSettingList.clear();
			userSettingList.setVisible(false);
			userSettingTitle.setVisible(false);
			buttonSave.setVisible(false);
		}
	}
	public MCheckBox getActivateCheck() {
		return activateCheck;
	}
	
	
	public WidgetList getUserSettingList() {
		return userSettingList;
	}
	
	
	public Allocation getUserAllocation(){
		Allocation allocate = createAllocation();
		String date = DateTimeFormat.getFormat("yyyy-MM-dd").format(new Date());
		if(mListBox.getSelectedIndex() != 0){
		allocate.setAllocatedTo(mListBox.getValue(mListBox.getSelectedIndex()));
		allocate.setAllocatedBy(LoginActivity.loggedUserId);
		allocate.setdeAllocatedBy(null);
		return allocate;
		}
		return null;
	}
	
	public Allocation getUserdeAllocation(){
		Allocation allocate = createAllocation();
		allocate.setdeAllocatedBy(LoginActivity.loggedUserId);
		return allocate;
	}
	
	 public  Allocation createAllocation(){
			AutoBean<Allocation> allocation = SettingsActivity.beanFactory.Allocation();
			// Return the Person interface shim
			return allocation.as();
	}
}
