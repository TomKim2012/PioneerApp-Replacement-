/*
 * Copyright 2010 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.tomkimani.mgwt.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableEvent;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableHandler;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutEvent;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutHandler;
import com.googlecode.gwtphonegap.client.event.PauseEvent;
import com.googlecode.gwtphonegap.client.event.PauseHandler;
import com.googlecode.mgwt.mvp.client.AnimatableDisplay;
import com.googlecode.mgwt.mvp.client.AnimatingActivityManager;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort.DENSITY;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.tomkimani.mgwt.demo.client.css.MyColorTheme;
import com.tomkimani.mgwt.demo.client.login.LoginActivity;
import com.tomkimani.mgwt.demo.client.places.LoginPlace;

/**
 * @author Daniel Kurka
 * 
 */
public class PioneerAppEntryPoint implements EntryPoint {

	public static String deviceName;
	public static String deviceImei;

	private void start() {
		//ViewPort
		ViewPort viewPort = new MGWTSettings.ViewPort();
	    viewPort.setTargetDensity(DENSITY.MEDIUM);
	    viewPort.setUserScaleAble(false).setMinimumScale(1.0).setMinimumScale(1.0).setMaximumScale(1.0);
		
		
		//set viewport and other settings for mobile
		MGWTSettings settings = new MGWTSettings();
	    settings.setViewPort(viewPort);
	    settings.setIconUrl("logo.png");
	    settings.setAddGlosToIcon(true);
	    settings.setFullscreen(true);
	    settings.setPreventScrolling(true);
	    
	   //Sets the default theme
	    MGWTStyle.setTheme(new MyColorTheme());
		
	    MGWT.applySettings(settings);
	    
	    final ClientFactory clientFactory = new ClientFactoryImpl();
		createPhoneDisplay(clientFactory);
		
		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
		
		final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(clientFactory.getPlaceController(), clientFactory.getEventBus(), new LoginPlace());
		historyHandler.handleCurrentHistory();
	}
	
	//final PhoneGap phoneGap = GWT.create(PhoneGap.class);
	private void createPhoneDisplay(final ClientFactory clientFactory) {
		AnimatableDisplay display = GWT.create(AnimatableDisplay.class);
		
		//Activity Mapper
		PhoneActivityMapper appActivityMapper = new PhoneActivityMapper(clientFactory);
		PhoneAnimationMapper appAnimationMapper = new PhoneAnimationMapper();
		AnimatingActivityManager activityManager = new AnimatingActivityManager(appActivityMapper, appAnimationMapper, clientFactory.getEventBus());
		
		//PhoneGap
		final PhoneGap phoneGap = GWT.create(PhoneGap.class);
		
		phoneGap.addHandler(new PhoneGapAvailableHandler(){

	        @Override
	        public void onPhoneGapAvailable(PhoneGapAvailableEvent event) {
	        	deviceName = phoneGap.getDevice().getName().isEmpty()?phoneGap.getDevice().getPlatform():phoneGap.getDevice().getName();
	        	deviceImei = phoneGap.getDevice().getUuid();
	        	
	        	clientFactory.setPhonegap(phoneGap);
	        	
	        	//factory to MyDialogs
	        	MyDialogs.phoneGap=phoneGap;
	        }
		});

		phoneGap.addHandler(new PhoneGapTimeoutHandler() {
		        @Override
		        public void onPhoneGapTimeout(PhoneGapTimeoutEvent event) {
		        	MyDialogs.alert("Problem", "The application failed to read device settings");
		        }
		});

		phoneGap.initializePhoneGap();
		
		
		phoneGap.getEvent().getPauseHandler().addPauseHandler(new PauseHandler() {
			
			@Override
			public void onPause(PauseEvent event) {
				LoginActivity.loggedUserId = null;
				
			}
		});

		activityManager.setDisplay(display);
		
		RootPanel.get().add(display);
	}

	@Override
	public void onModuleLoad() {

		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				//TODO put in your own meaninful handler
				//Window.alert("uncaught: " + e.getMessage());
				e.printStackTrace();

			}
		});

		new Timer() {
			@Override
			public void run() {
				start();

			}
		}.schedule(1);

	}

}
