package com.tomkimani.mgwt.demo.client.dashboard;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.tomkimani.mgwt.demo.client.ClientFactory;
import com.tomkimani.mgwt.demo.client.base.BaseActivity;
import com.tomkimani.mgwt.demo.client.places.CustomerSearchPlace;
import com.tomkimani.mgwt.demo.client.places.TransactionsPlace;

public class DashboardActivity extends BaseActivity {
		//TransactionsActivity transaction= new TransactionsActivity(factory);
		
		public interface IDashboardView extends IView{
			HasTapHandlers getBtnDeposit();

			HasTapHandlers getBtnStatement();
		}
		public DashboardActivity(ClientFactory factory) {
			super(factory);
		}
		
		@Override
		public void start(AcceptsOneWidget panel, EventBus eventBus) {
			IDashboardView view = factory.getDashboardView();
			setView(view);
			
			super.start(panel, eventBus);
			
			
			addHandlerRegistration(view.getTransactionButton().addTapHandler(new TapHandler() {
				
				@Override
				public void onTap(TapEvent event) {
					factory.getPlaceController().goTo(new TransactionsPlace());
				}
			}));
			
			addHandlerRegistration(view.getBtnDeposit().addTapHandler(new TapHandler() {
				@Override
				public void onTap(TapEvent event) {
					factory.getPlaceController().goTo(new CustomerSearchPlace());
				}
			}));
			
			
			addHandlerRegistration(view.getBtnStatement().addTapHandler(new TapHandler() {
				
				@Override
				public void onTap(TapEvent event) {
					factory.getPlaceController().goTo(new CustomerSearchPlace(true));
				}
			}));
			panel.setWidget(view);
		}
}
