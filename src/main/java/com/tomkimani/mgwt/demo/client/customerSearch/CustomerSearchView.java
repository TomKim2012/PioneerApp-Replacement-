package com.tomkimani.mgwt.demo.client.customerSearch;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.ProgressIndicator;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.tomkimani.mgwt.demo.client.base.BaseView;
import com.tomkimani.mgwt.demo.client.customerSearch.CustomerSearchActivity.ICustomerSearchView;

public class CustomerSearchView extends BaseView implements ICustomerSearchView{

	/*private static CustomerSearchUiBinder uiBinder = GWT
			.create(CustomerSearchUiBinder.class);*/
	
	interface CustomerSearchUiBinder extends UiBinder<Widget,CustomerSearchView> {
	}
	
	LayoutPanel LayoutPanel;
	WidgetList widgetList;
	private Button searchButton;
	private MListBox mListBox;
	private MTextBox custInput = new MTextBox();
	private FormListEntry entry;
	private HTML IssuesArea;
	private ProgressIndicator progressIndicator;
	private LayoutPanel SearchFilterPanel;
	protected String selectedValue;
	
	//private final Widget widget;
	
	public CustomerSearchView() {
		//widget = uiBinder.createAndBindUi(this);
		LayoutPanel = new LayoutPanel();
		SearchFilterPanel = new LayoutPanel();
		widgetList = new WidgetList();
		IssuesArea = new HTML();
		widgetList.setRound(true);
		
		
		//Customer Input - Number
		custInput.getElement().getFirstChildElement().setAttribute("type","number");
		
	    //Select Box
	    mListBox = new MListBox();
	    mListBox.addItem("Phone Number", "phone");
	    mListBox.addItem("First Name", "clname");
	    mListBox.addItem("Last Name", "clsurname");
	    //mListBox.addItem("Id Number", "idcard");
	    //mListBox.addItem("Client Code", "clCode");
	    mListBox.addItem("Reference Number", "refno");
	    widgetList.add(new FormListEntry("Search By",mListBox));
	    
	    mListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String value=mListBox.getItemText(mListBox.getSelectedIndex());
				entry.setWidget(value,custInput);
				selectedValue = mListBox.getValue(mListBox.getSelectedIndex());
				
				if(selectedValue.equals("phone")){
					custInput.getElement().getFirstChildElement().setAttribute("type","number");
				}else{
					custInput.getElement().getFirstChildElement().setAttribute("type","text");
				}
			}
		});
	    
	    entry = new FormListEntry(mListBox.getItemText(mListBox.getSelectedIndex()),custInput);
	    widgetList.add(entry);
	    SearchFilterPanel.add(widgetList);
	    
		//Progress Indicator
		progressIndicator = new ProgressIndicator();
		progressIndicator.getElement().setAttribute("style", "margin:auto; margin-top:50px");
		progressIndicator.setVisible(false);
		SearchFilterPanel.add(progressIndicator);
		
	    //IssuesArea
  		IssuesArea.getElement().getStyle().setColor("Red");
  		IssuesArea.setVisible(false);
  		IssuesArea.getElement().getStyle().setMarginLeft(20.0, Unit.PCT);
  		
		SearchFilterPanel.add(IssuesArea);
	    searchButton = new Button("Search");
	    searchButton.setConfirm(true);
	    SearchFilterPanel.add(searchButton);
		
		
		//Layout Panel
		LayoutPanel.add(SearchFilterPanel);
		
		//Add it to the Base Panel
		headerPanel.setCenter("Customer Search");
		createContent(LayoutPanel);
		backButton.setVisible(true);
		logoutButton.setVisible(false);
	}
	
	@Override
	public Widget asWidget() {
		return super.asWidget();
	}
	
	public HasTapHandlers getSearchButton() {
		return searchButton;
	}
	
	public String getmListBox() {
		return mListBox.getValue(mListBox.getSelectedIndex());
	}
	
	public String getCustInput() {
		return custInput.getValue();
	}
	
	public void showBusy(boolean status){
		if(status){
		IssuesArea.setVisible(false);
		progressIndicator.setVisible(true);
		}else{
			IssuesArea.setVisible(true);
			progressIndicator.setVisible(false);
		}
	}
	
	public HTML getIssuesArea() {
		return IssuesArea;
	}
}
