package com.tomkimani.mgwt.demo.client.settings;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.tomkimani.mgwt.demo.client.base.BaseView;
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
	private HTML depositTitle;
	private MListBox mListBox;
	private Button buttonSave;
	private HTML deviceTitle;
	
	//private final Widget widget;
	
	public SettingsView() {
		//widget = uiBinder.createAndBindUi(this);
		
		LayoutPanel = new LayoutPanel();
		deviceSettingList = new WidgetList();
		userSettingList = new WidgetList();
		
		headerPanel.setCenter("Settings");
		
		backButton.setVisible(true);
		
		
		//Requirements for user Settings
		activateCheck = new MCheckBox();
		deviceName = new HTML();
		deviceImei = new HTML();
		
		//deviceImei.setValue()
		
		//activate
		activateCheck.setImportant(true);
		
		
		deviceName.setText("Huawei Ideos");
		deviceTitle =new HTML("Device Settings");
		deviceTitle.getElement().getStyle().setMarginTop(10, Unit.PX);
		deviceTitle.getElement().getStyle().setMarginLeft(20, Unit.PX);
		deviceTitle.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		LayoutPanel.add(deviceTitle);
		
		//Device Settings
		deviceSettingList.add(new FormListEntry("Device Allocation:",activateCheck ));
		deviceSettingList.add(new FormListEntry("Device Name:", deviceName ));
		deviceSettingList.add(new FormListEntry("Device IMEI:", deviceImei));
		deviceSettingList.setRound(true);
		
		LayoutPanel.add(deviceSettingList);
		
		//User Settings
		depositTitle=new HTML("User Settings");
		depositTitle.getElement().getStyle().setMarginLeft(20, Unit.PX);
		depositTitle.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		LayoutPanel.add(depositTitle);
	    mListBox = new MListBox();
		userSettingList.setRound(true);
		
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
	}
	
	@Override
	public Widget asWidget() {
		return super.asWidget();
	}
	
	public HasTapHandlers getButtonSave() {
		return buttonSave;
	}
	
	@Override
	public void renderUsers(List<User> usersList) {
		userSettingList.add(new FormListEntry("Allocated User",mListBox));
		mListBox.addItem("---Select---");
		for(User user: usersList){
			mListBox.addItem(user.getFirstName()+" "+ user.getLastName(),
							user.getUserId());
		}
	}
	
	@Override
	public void renderAllocation(Allocation allocation) {
		userSettingList.add(new FormListEntry("Allocated Date:", new HTML(allocation.getallocationDate())));
		userSettingList.add(new FormListEntry("Allocated User:", new HTML(allocation.getallocatedName())));
		userSettingList.add(new FormListEntry("Allocated By:", new HTML(allocation.getallocateeName())));
		userSettingList.add(new FormListEntry("Allocated Date:", new HTML(allocation.getallocationTime())));
	}
	
	
	public MCheckBox getActivateCheck() {
		return activateCheck;
	}
	
	public WidgetList getUserSettingList() {
		return userSettingList;
	}
	
	
}
