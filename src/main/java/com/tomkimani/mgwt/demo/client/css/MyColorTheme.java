package com.tomkimani.mgwt.demo.client.css;

import com.google.gwt.core.shared.GWT;
import com.googlecode.mgwt.ui.client.theme.MGWTClientBundle;
import com.googlecode.mgwt.ui.client.theme.MGWTTheme;

public class MyColorTheme implements MGWTTheme {
	
	private MGWTClientBundle bundle;
	
	public MyColorTheme() {
		bundle=  GWT.create(MyClientBundle.class);
	}
	
	@Override
	public MGWTClientBundle getMGWTClientBundle() {
		return bundle;
	}

}
