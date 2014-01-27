package com.tomkimani.mgwt.demo.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

public class MyRequestCallback implements RequestCallback{
	
	
	public void onError(Request request, Throwable exception) {
		MyDialogs.alert(
				MyDialogs.NETWORK_ERROR_TITLE,
				MyDialogs.NETWORK_ERROR_MESSAGE
				);
    }

	@Override
	public void onResponseReceived(Request request, Response response) {
		if (200 == response.getStatusCode()) {
		
		}else{
       	  
		  System.err.println("Couldn't retrieve JSON (" + response.getStatusText()
               + ")");
       	  MyDialogs.alert(
       			  		MyDialogs.NETWORK_ERROR_TITLE,
       			  		MyDialogs.NETWORK_ERROR_MESSAGE
						);
       	  return;
		}
	}
}
