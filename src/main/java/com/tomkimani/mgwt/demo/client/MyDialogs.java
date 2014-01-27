package com.tomkimani.mgwt.demo.client;

import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.notification.AlertCallback;
import com.googlecode.gwtphonegap.client.notification.ConfirmCallback;

public class MyDialogs {
	public static PhoneGap phoneGap;
	
	public static void alert(String title, String message){
		phoneGap.getNotification()
		.alert(message,new AlertCallback() {
			@Override
			public void onOkButtonClicked() {
				
			}
		}, title, "OK");
		
	}
	
	public static void alert(String title, String message, AlertCallback callback, String buttonText){
		phoneGap.getNotification()
		.alert(message,callback, title, buttonText);
	}
	
	
	public static void confirm( String title, String message,ConfirmCallback callback){
		phoneGap.getNotification()
		.confirm(message,callback,title);
	}
	
	public static void vibrate(int milliseconds){
		phoneGap.getNotification().vibrate(milliseconds);
	}
	
	public static void Beep(int count){
	    phoneGap.getNotification().beep(count);
	}
	 
	public static String NETWORK_ERROR_MESSAGE = "There is Error communicating with the server. Check your Internet Connection";
	public static String NETWORK_ERROR_TITLE = "Network Error";

}
