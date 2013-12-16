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
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.googlecode.mgwt.ui.client.widget.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
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
			void renderAllocation(Allocation allocation);
			MCheckBox getActivateCheck();
			WidgetList getUserSettingList();
		}

		protected List<User> myList;
		private MyBeanFactory beanFactory;
		public SettingsActivity(ClientFactory factory) {
			super(factory);
		}
		
		@Override
		public void start(AcceptsOneWidget panel, EventBus eventBus) {
			final ISettingsView view = factory.getSettingsView();
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
			
			
			addHandlerRegistration(view.getActivateCheck().addTouchEndHandler(new TouchEndHandler() {
				@Override
				public void onTouchEnd(
					com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent event) {
					
					view.getUserSettingList().clear();
					/*if (view.getActivateCheck().getValue()) {
						FetchDataFromServer(view,"users");
					}*/
					
				}
			}));
			
			super.start(panel, eventBus);
			
			//Is it allocated?
			FetchDataFromServer(view,"allocation/imeiCode/2536-89567-56");
			
			panel.setWidget(view);
		}
		
		
		
	  private void FetchDataFromServer(final ISettingsView view, final String customUrl) {
			
			
			MyRequestBuilder rqs = new MyRequestBuilder(RequestBuilder.GET, customUrl);
			try {
			      Request request = rqs.getBuilder().sendRequest(null, new RequestCallback() {

					public void onError(Request request, Throwable exception) {
						Dialogs.alert("Error", "An error occured while retrieving data from server", null);
			        }

			        public void onResponseReceived(Request request, Response response) {
			          if (200 == response.getStatusCode()) {
			        	  myList = new ArrayList<User>();
			        	
			        	  if(response.getText().isEmpty()){
				          }
			           	 
			        	  if(customUrl.equals("users")){
				        	  UsersList lst = deserializeFromJson("{\"usersList\":"+response.getText()+"}");
				        	  myList=lst.getUsersList();
				        	  view.renderUsers(myList);
				        	  return;
				        	  
			        	  }else{
				        	  Allocation allocation = allocationFromJson(response.getText());
				        	  if(allocation.getisAllocated()){
				        	  view.renderAllocation(allocation);
				        	  }else{
				        		  view.getActivateCheck().setValue(false);
				        		  FetchDataFromServer(view, "users");
				        	  }
			        	  }
			        	  
			          } else {
			        	  System.err.println("Couldn't retrieve JSON (" + response.getStatusText()
			                + ")");
			        	  Dialogs.alert("Error", "An error occured while retrieving data from server", null);
			          }
			        }
			      });
			    } catch (RequestException e) {
			    	System.err.println("Couldn't retrieve JSON");
			    	Dialogs.alert("Error", "An error occured while retrievin data from server", null);
			    }
		}
		
	  	public interface UsersList{
			List<User> getUsersList();
		}
		
		UsersList deserializeFromJson (String json){
			AutoBean<UsersList> bean = AutoBeanCodex.decode(beanFactory, UsersList.class, json);
			return bean.as();
		}	
		
	
		
		///////--------Allocation Object from Server Side--------////////
		public interface Allocation{
			String getallocationDate();
			String getallocationTime();
			String getallocatedName();
			String getallocateeName();
			Boolean getisAllocated();
		}
		
		Allocation allocationFromJson (String json){
			AutoBean<Allocation> bean = AutoBeanCodex.decode(beanFactory, Allocation.class, json);
			return bean.as();
		}	
		
	  	/*User deserializeFromJson(String json){
			AutoBean<User> bean = AutoBeanCodex.decode(beanFactory, User.class, json);
			return bean.as();
		}	*/
		
}
