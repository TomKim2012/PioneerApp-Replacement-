package com.tomkimani.mgwt.demo.client.dublicating;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.tomkimani.mgwt.demo.client.ClientFactory;

public class DuplicateActivity extends MGWTAbstractActivity {
		ClientFactory factory;
		
		public interface IDuplicatingView extends IsWidget{
		}
		public DuplicateActivity(ClientFactory factory) {
			this.factory= factory;
		}
		
		@Override
		public void start(AcceptsOneWidget panel, EventBus eventBus) {
			//ITransactionsView view = factory.getTransactionsView();
			//panel.setWidget(view);
		
		}
}
