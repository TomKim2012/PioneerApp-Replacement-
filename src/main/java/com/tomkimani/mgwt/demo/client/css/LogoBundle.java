package com.tomkimani.mgwt.demo.client.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface LogoBundle extends ClientBundle {
	//This is a very nasty workaround because GWT CssResource does not support @media correctly!
	@Source("pioneer_logo.png")
	ImageResource logo();

	public static final LogoBundle INSTANCE = GWT.create(LogoBundle.class);

}
