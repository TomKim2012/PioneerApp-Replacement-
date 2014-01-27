package com.tomkimani.mgwt.demo.client.settings;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.tomkimani.mgwt.demo.client.ClientFactory;
import com.tomkimani.mgwt.demo.client.MyBeanFactory;
import com.tomkimani.mgwt.demo.client.MyDialogs;
import com.tomkimani.mgwt.demo.client.MyRequestBuilder;
import com.tomkimani.mgwt.demo.client.MyRequestCallback;
import com.tomkimani.mgwt.demo.client.PioneerAppEntryPoint;
import com.tomkimani.mgwt.demo.client.base.BaseActivity;
import com.tomkimani.mgwt.demo.client.login.LoginActivity.User;
import com.tomkimani.mgwt.demo.client.places.DashboardPlace;

public class SettingsActivity extends BaseActivity {
		
		public interface ISettingsView extends IView{
			Button getButtonSave();
			void renderUsers(List<User> usersList);
			void renderAllocation(Allocation allocation);
			MCheckBox getActivateCheck();
			WidgetList getUserSettingList();
			void renderDeviceSettings(String deviceName, String deviceImei);
			void showUserSettings(boolean status);
			Allocation getUserAllocation();
			Allocation getUserdeAllocation();
		}

		protected List<User> myList;
		public static MyBeanFactory beanFactory;
		private Terminal terminal;
		private String postData;
		private Allocation allocation;
		public SettingsActivity(ClientFactory factory) {
			super(factory);
		}
		
		
		@Override
		public void start(AcceptsOneWidget panel, EventBus eventBus) {
			final ISettingsView view = factory.getSettingsView();
			setView(view);
			
			
			//AutoBean Factory
			beanFactory = GWT.create(MyBeanFactory.class);
			//allocation
			allocation=view.getUserdeAllocation();
			
			//Render Device Settings
			if(!PioneerAppEntryPoint.deviceImei.isEmpty()){
			view.renderDeviceSettings(PioneerAppEntryPoint.deviceName, PioneerAppEntryPoint.deviceImei);
			terminal = new Terminal(PioneerAppEntryPoint.deviceName, PioneerAppEntryPoint.deviceImei);
			}
			
			addHandlerRegistration(view.getButtonSave().addTapHandler(new TapHandler() {
				
				@Override
				public void onTap(TapEvent event) {
					allocation = view.getUserAllocation();
					
					if(!(allocation== null)){
					CommunicateWithServer(RequestBuilder.POST, view, "allocation");
					}else{
						MyDialogs.alert("Warning", "You have not selected a user");
					}
					
					//factory.getPlaceController().goTo(new DashboardPlace());
				}
			}));
			
			
			addHandlerRegistration(view.getBackButton().addTapHandler(new TapHandler() {
				
				@Override
				public void onTap(TapEvent event) {
					factory.getPlaceController().goTo(new DashboardPlace());
				}
			}));
			
			
			addHandlerRegistration(view.getActivateCheck().addTouchEndHandler(new TouchEndHandler() {
				private boolean isCleared=false;

				@Override
				public void onTouchEnd(TouchEndEvent event) {
					if (view.getActivateCheck().getValue()) {
					
					CommunicateWithServer(RequestBuilder.POST, view, "terminal");
					
					if(isCleared){
					 CommunicateWithServer(RequestBuilder.GET, view, "users");
					}
					
					System.out.println(isCleared);
					view.showUserSettings(true);
					}else{
						if(!(allocation.getallocationId() == null)){	
							CommunicateWithServer(RequestBuilder.POST, view, "deallocation");
							view.showUserSettings(false);
						}
						
						view.showUserSettings(false);
						view.getUserSettingList().clear();
						isCleared =true;
					}
					
				}	
			}));
			
			
			
			super.start(panel, eventBus);
			
			//Is it allocated?
			CommunicateWithServer(RequestBuilder.GET,view,"allocation/imeiCode/"+PioneerAppEntryPoint.deviceImei);
			
			panel.setWidget(view);
		}
		
		
		
	  private void CommunicateWithServer(final Method httpMethod, final ISettingsView view, final String customUrl) {
			view.showBusy(true);
			
			MyRequestBuilder rqs = new MyRequestBuilder(httpMethod,customUrl);
			try {
				  if(httpMethod.equals(RequestBuilder.POST)){
					  if(customUrl.equals("terminal")){
					  postData = terminal.toString();
					  }else if(customUrl.equals("allocation")||customUrl.equals("deallocation")){
					  postData = makeJson(allocation);
					  }
				  }else{
					  postData = null;
				  }
			      Request request = rqs.getBuilder().sendRequest(postData, new MyRequestCallback() {

			        public void onResponseReceived(Request request, Response response) {
			          if (200 == response.getStatusCode()) {
			        	  view.showBusy(false);
			        	  myList = new ArrayList<User>();
			        	
			        	  //Response from server
			        	  if(response.getText().isEmpty()){
				          }
			           	 
			        	  if(customUrl.equals("users")){
				        	  UsersList lst = deserializeFromJson("{\"usersList\":"+response.getText()+"}");
				        	  myList=lst.getUsersList();
				        	  view.renderUsers(myList);
				        	  view.showUserSettings(false);
				        	  return;
				        	  
			        	  }else if((customUrl.contains("allocation")) && httpMethod.equals(RequestBuilder.GET)){
			        		  Allocation allocation = allocationFromJson(response.getText());
				        	 
				        	  if(allocation.getisAllocated()){
				        	    view.renderAllocation(allocation);
				        	    SettingsActivity.this.allocation.setallocationId(allocation.getallocationId()); //Allocation Object
				        	    
				        	    System.out.println("<<Rendered Allocation");
				        	  }else{
				        		  view.getActivateCheck().setValue(false);
				        		  CommunicateWithServer(RequestBuilder.GET, view, "users");
				        	  }
			        	  } else if(customUrl.equals("deallocation")){
			        		  MyDialogs.alert("Success", "Device successfully de-Allocated");
			        	  }
			        	  
			        	  else if((customUrl.contains("allocation")) && httpMethod.equals(RequestBuilder.POST)){
			        		  CommunicateWithServer(RequestBuilder.GET, view, "allocation/imeiCode/"+PioneerAppEntryPoint.deviceImei);
			        		  view.getUserSettingList().clear();
			        		  view.showUserSettings(true);
			        		  view.getButtonSave().setVisible(false);
			        		  
			        		  MyDialogs.alert("Success","Device successfully Allocated");
			        	  }
			        	  else if(customUrl.equals("terminal")){
			        		  if(!response.getText().isEmpty()){
			        			  terminal.setTerminalId(response.getText());
			        			  //view.renderUsers(myList);
			        			  view.showUserSettings(true);
			        		  }else{
			        			  view.showUserSettings(true);
			        			  //MyDialogs.alert("Error", "Terminal Not Saved", null);
			        		  }
			        	  }
			        	  else{
			        		  if(response.getText().equals("Saved")){
			        			  view.showUserSettings(false);
			        		  }
			        	  }
			        	  
			          } else {
			        	  System.err.println("Couldn't retrieve JSON (" + response.getStatusText()
			                + ")");
			        	  MyDialogs.alert("Error", "An error occured while retrieving data from server");
			          }
			        }
			      });
			    } catch (RequestException e) {
			    	System.err.println("Couldn't retrieve JSON");
			    	MyDialogs.alert("Error", "An error occured while retrieving data from server");
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
			String getallocationId();
			void setallocationId(String getallocationId);
			String getallocationDate();
			String getallocatedName();
			String getallocated();
			String getallocateeName();
			Boolean getisAllocated();
			String getAllocatedTo();
			String getAllocatedBy();
			void setAllocatedBy(String allocatedId);
			void setAllocatedTo(String allocateeId);
			void setdeAllocatedBy(String loggedUserId);
			String getdeAllocatedBy();
		}
		
		Allocation allocationFromJson (String json){
			AutoBean<Allocation> bean = AutoBeanCodex.decode(beanFactory, Allocation.class, json);
			return bean.as();
		}	
		
		
		////Terminal Object//////
		public class Terminal{
			private String terminalId;
			private String terminalName;
			private String imeiCode;
			
			public Terminal(String terminalName, String imeiCode) {
				this.terminalName = terminalName;
				this.imeiCode = imeiCode;
			}
			
			@Override
			public String toString() {
				JSONObject jrequest = new JSONObject();
				jrequest.put("terminalName", new JSONString(terminalName));
				jrequest.put("imeiCode", new JSONString(imeiCode));
				return jrequest.toString();
			}
			
			public void setTerminalId(String terminalId) {
				this.terminalId = terminalId;
			}
			
			public String getTerminalId() {
				return terminalId;
			}
		}
		
		public String makeJson(Allocation allocation){
			JSONObject jrequest = new JSONObject();
			
			if(!(allocation.getdeAllocatedBy() == null)){
				jrequest.put("allocationId", new JSONString(allocation.getallocationId()));
				jrequest.put("deallocatedBy", new JSONString(allocation.getdeAllocatedBy()));
				return jrequest.toString();
			}
			
			jrequest.put("allocatedTo", new JSONString(allocation.getAllocatedTo()));
			jrequest.put("allocatedBy", new JSONString(allocation.getAllocatedBy()));
			jrequest.put("terminalId", new JSONString(terminal.getTerminalId()));
			return jrequest.toString();
		}
}
