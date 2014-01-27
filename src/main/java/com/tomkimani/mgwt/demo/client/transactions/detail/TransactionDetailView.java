package com.tomkimani.mgwt.demo.client.transactions.detail;

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
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.ProgressIndicator;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.tomkimani.mgwt.demo.client.base.BaseView;
import com.tomkimani.mgwt.demo.client.customerSearch.CustomerSearchActivity.Customer;
import com.tomkimani.mgwt.demo.client.transactions.detail.TransactionDetailActivity.ITransactionDetailView;

public class TransactionDetailView extends BaseView implements ITransactionDetailView{

	private static TransactionDetailViewUiBinder uiBinder = GWT
			.create(TransactionDetailViewUiBinder.class);
	
	interface TransactionDetailViewUiBinder extends UiBinder<Widget,TransactionDetailView> {
	}

	private WidgetList customerDetail;
	private LayoutPanel main;
	private WidgetList transactionList;
	private MTextBox amountTextBox;
	private Button saveButton;
	private HTML depositTitle;
	private ScrollPanel scrollPanel;
	private ProgressIndicator progressIndicator;
	private ScrollPanel scrollPanel2;
	
	//private final Widget widget;
	
	public TransactionDetailView() {
		//widget = uiBinder.createAndBindUi(this);
		
		main = new LayoutPanel();
		
		backButton.setVisible(true);
		
		
		customerDetail = new WidgetList();
		customerDetail.setRound(true);
		main.add(customerDetail);
		
		transactionList = new WidgetList();
		transactionList.setRound(true);
		
		scrollPanel = new ScrollPanel();
		scrollPanel.setWidget(main);
		scrollPanel.setScrollingEnabledX(false);
		
		headerPanel.setVisible(true);
		createContent(scrollPanel);
		
		buttonBar.setVisible(false);
		
	}
	
	@Override
	public Widget asWidget() {
		return super.asWidget();
	}
	
	@Override
	public void renderDisplay(Customer cust1, Boolean isMiniStatement) {
		headerPanel.setCenter("Confirm Customer");
		amountTextBox = new MTextBox();
		amountTextBox.getElement().getFirstChildElement().setAttribute("type","number");
		customerDetail.add(new FormListEntry("Names:", new HTML(cust1.getFirstName()+" "+cust1.getLastName())));
		//customerDetail.add(new FormListEntry("Id Number:", new HTML(cust1.getIdNo())));
		customerDetail.add(new FormListEntry("Client Code:", new HTML(cust1.getRefNo())));
		customerDetail.add(new FormListEntry("Phone Number:", new HTML(cust1.getMobileNo())));
		
		
		//Transaction Details
		depositTitle =new HTML("Enter Deposit Amount:");
		depositTitle.getElement().getStyle().setMarginLeft(20, Unit.PX);
		depositTitle.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		main.add(depositTitle);
		transactionList.add(new FormListEntry("Amount:", amountTextBox));
		
		//ScrollPanel
		scrollPanel2= new ScrollPanel();
		scrollPanel2.add(transactionList);
		main.add(scrollPanel2);
		
		if(isMiniStatement){
			//Progress Indicator
			progressIndicator = new ProgressIndicator();
			progressIndicator.getElement().setAttribute("style", "margin:auto; margin-top: 50px");
			progressIndicator.setVisible(false);
			main.add(progressIndicator);
			
			depositTitle.setVisible(false);
			transactionList.setVisible(false);
		}
		
		//Button Complete
		saveButton = new Button("COMPLETE");
		saveButton.setConfirm(true);
		main.add(saveButton);
	}
	
	public HasTapHandlers getSaveButton() {
		return saveButton;
	}
	
	public String getAmountTextBox() {
		return amountTextBox.getValue();
	}
	
	public ProgressIndicator getProgressIndicator() {
		return progressIndicator;
	}

}
