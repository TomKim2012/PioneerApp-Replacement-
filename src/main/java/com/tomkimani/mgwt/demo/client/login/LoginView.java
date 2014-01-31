package com.tomkimani.mgwt.demo.client.login;
 
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.ProgressIndicator;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.tomkimani.mgwt.demo.client.css.LogoBundle;
import com.tomkimani.mgwt.demo.client.login.LoginActivity.ILoginView;
import com.tomkimani.mgwt.demo.client.ui.PasswordField;
import com.tomkimani.mgwt.demo.client.ui.TextField;

public class LoginView implements ILoginView{

	private static LoginViewUiBinder uiBinder = GWT
			.create(LoginViewUiBinder.class);
	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}
	
	LayoutPanel LayoutPanel;
	WidgetList widgetList;
	Button loginButton;
	private HTML IssuesArea;
	private TextField userName;
	private PasswordField passWord;
	private ProgressIndicator progressIndicator;
	private MTextBox serverAddress;
	
	//private final Widget widget;
	
	public LoginView() {
		//widget = uiBinder.createAndBindUi(this);
		widgetList = new WidgetList();
		//ipList = new 
		LayoutPanel = new LayoutPanel();
		IssuesArea = new HTML();
		loginButton = new Button("LOGIN");
	
		//Logo
		Image logo = new Image(LogoBundle.INSTANCE.logo());
		logo.getElement().getStyle().setMarginLeft(20.0, Unit.PCT);
		LayoutPanel.add(logo);
		
		
		//UserName And Password TexFields
		widgetList.setRound(true);
		userName = new TextField("UserName");
		passWord = new PasswordField("Password");
		serverAddress = new MTextBox();
		widgetList.add(userName);
		widgetList.add(passWord);
		
		widgetList.add(serverAddress);
		
		LayoutPanel.add(widgetList);
		
		//Progress Indicator
		progressIndicator = new ProgressIndicator();
		progressIndicator.getElement().setAttribute("style", "margin:auto; margin-top: 50px");
		progressIndicator.setVisible(false);
		LayoutPanel.add(progressIndicator);
		
		//IssuesArea
		IssuesArea.getElement().getStyle().setColor("Red");
		IssuesArea.setVisible(false);
		IssuesArea.getElement().getStyle().setMarginLeft(20.0, Unit.PCT);
		LayoutPanel.add(IssuesArea);
		
		//LoginButton
		loginButton.setConfirm(true);
		LayoutPanel.add(loginButton);
	}
	
	@Override
	public Widget asWidget() {
		return LayoutPanel;
	}
	
	public HasTapHandlers getLoginButton() {
		return loginButton;
	}
	
	public HTML getIssuesArea() {
		return IssuesArea;
	}

	@Override
	public String getuserName() {
		return userName.getValue();
	}

	@Override
	public String getpassword() {
		return passWord.getValue();
	}
	
	public void showBusy(boolean status){
		if(status){
			progressIndicator.setVisible(true);
		}else{
			progressIndicator.setVisible(false);
		}
	}
	
	public MTextBox getServerAddress() {
		return serverAddress;
	}


}
