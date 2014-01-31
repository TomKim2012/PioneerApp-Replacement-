package com.tomkimani.mgwt.demo.client.ui;

import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.theme.base.TabBarCss;
import com.googlecode.mgwt.ui.client.widget.tabbar.TabBarButton;

public class FeaturedButton extends TabBarButton {
	
	public FeaturedButton(String icon){
		this(MGWTStyle.getTheme().getMGWTClientBundle().getTabBarCss(),icon);
	}
	
	public FeaturedButton(TabBarCss css, String icon) {
		super(css, null);
		
		getElement().getFirstChildElement().addClassName(icon);
	}
}
