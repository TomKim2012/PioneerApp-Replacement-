package com.tomkimani.mgwt.demo.client.settings;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.tomkimani.mgwt.demo.client.ClientFactory;
import com.tomkimani.mgwt.demo.client.MyBeanFactory;
import com.tomkimani.mgwt.demo.client.MyRequestBuilder;
import com.tomkimani.mgwt.demo.client.base.BaseActivity;
import com.tomkimani.mgwt.demo.client.login.LoginActivity.User;
import com.tomkimani.mgwt.demo.client.places.DashboardPlace;

public class SettingsActivity extends BaseActivity {
		
		public interface ISettingsView extends IView{
			HasTapHandlers getButtonSave();
			void renderUsers(List<User> usersList);
		}

		protected List<User> myList;
		private MyBeanFactory beanFactory;
		public SettingsActivity(ClientFactory factory) {
			super(factory);
		}
		
		@Override
		public void start(AcceptsOneWidget panel, EventBus eventBus) {
			ISettingsView view = factory.getSettingsView();
			setView(view);
			
			//AutoBean Factory
			beanFactory = GWT.create(MyBeanFactory.class);
			
			addHandlerRegistration(view.getButtonSave().addTapHandler(new TapHandler() {
				
				@Override
				public void onTap(TapEvent event) {
					factory.getPlaceController().goTo(new DashboardPlace());
				}
			}));
			
			
			addHandlerRegistration(view.getBackButton().addTapHandler(new TapHandler() {
				
				@Override
				public void onTap(TapEvent event) {
					factory.getPlaceController().goTo(new DashboardPlace());
				}
			}));
			
			super.start(panel, eventBus);
			
			FetchDataFromServer(view);
			
			panel.setWidget(view);
		}
		
		
		
	  private void FetchDataFromServer(final ISettingsView view) {
			
			String customUrl ="users";
			
			MyRequestBuilder rqs = new MyRequestBuilder(RequestBuilder.GET, customUrl);
			try {
			      Request request = rqs.getBuilder().sendRequest(null, new RequestCallback() {

					public void onError(Request request, Throwable exception) {
			          System.err.println("Couldn't retrieve JSON");
			        }

			        public void onResponseReceived(Request request, Response response) {
			          if (200 == response.getStatusCode()) {
			        	  myList = new ArrayList<User>();
			        	
			        	  if(response.getText().isEmpty()){
				        	  //return;
				           }
			           	 
			        	  UsersList lst = deserializeFromJson("{\"usersList\":"+response.getText()+"}");
			        	  myList=lst.getUsersList();
			        	  
			        	  assert myList != null;
			        	  view.renderUsers(myList);
			        	  
			          } else {
			        	  System.err.println("Couldn't retrieve JSON (" + response.getStatusText()
			                + ")");
			        	  Dialogs.alert("Error", "An error occured while retrieving the data", null);
			          }
			        }
			      });
			    } catch (RequestException e) {
			    	System.err.println("Couldn't retrieve JSON");
			    	Dialogs.alert("Error", "An error occured while retrieving the data", null);
			    }
		}
		
	  	public interface UsersList{
			List<User> getUsersList();
		}
		
		UsersList deserializeFromJson (String json){
			System.out.println(json);
			AutoBean<UsersList> bean = AutoBeanCodex.decode(beanFactory, UsersList.class, json);
			return bean.as();
		}	
		
		
	  	/*User deserializeFromJson (String json){
			AutoBean<User> bean = AutoBeanCodex.decode(beanFactory, User.class, json);
			return bean.as();
		}	*/
		
}
