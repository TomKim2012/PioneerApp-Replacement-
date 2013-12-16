package com.tomkimani.mgwt.demo.client.base;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.tomkimani.mgwt.demo.client.ClientFactory;
import com.tomkimani.mgwt.demo.client.places.DashboardPlace;
import com.tomkimani.mgwt.demo.client.places.LoginPlace;
import com.tomkimani.mgwt.demo.client.places.SettingsPlace;

public class BaseActivity extends MGWTAbstractActivity {
	protected ClientFactory factory;
	//TransactionsActivity transaction= new TransactionsActivity(factory);
	
	public interface IView extends IsWidget{
		public HasTapHandlers getLogoutButton();
		public HasTapHandlers getTransactionButton();
		HasTapHandlers getHomeButton();
		public void showBusy(boolean status);
		HasTapHandlers getBackButton();
		HasTapHandlers getSettingsButton();
	}
	
	protected IView baseView=null;
	
	public BaseActivity(ClientFactory factory) {
		this.factory= factory;
	}
	
	protected void setView(IView view){
		this.baseView = view;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget((IsWidget)baseView);
		
		addHandlerRegistration(baseView.getLogoutButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				factory.getPlaceController().goTo(new LoginPlace());
			}
		}));
		
		addHandlerRegistration(baseView.getHomeButton().addTapHandler(new TapHandler() {
			@Override
			public void onTap(TapEvent event) {
				factory.getPlaceController().goTo(new DashboardPlace());
			}
		}));
		
		addHandlerRegistration(baseView.getSettingsButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				factory.getPlaceController().goTo(new SettingsPlace());
			}
		}));
	}
}
