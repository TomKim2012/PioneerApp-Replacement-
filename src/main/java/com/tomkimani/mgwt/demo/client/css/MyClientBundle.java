package com.tomkimani.mgwt.demo.client.css;

import com.googlecode.mgwt.ui.client.theme.base.ButtonBarCss;
import com.googlecode.mgwt.ui.client.theme.base.HeaderCss;
import com.googlecode.mgwt.ui.client.theme.base.InputCss;
import com.googlecode.mgwt.ui.client.theme.base.ListCss;
import com.googlecode.mgwt.ui.client.theme.base.MGWTClientBundleBaseThemeDesktop;
import com.googlecode.mgwt.ui.client.theme.base.MainCss;
import com.googlecode.mgwt.ui.client.theme.base.TabBarCss;

public interface MyClientBundle extends MGWTClientBundleBaseThemeDesktop{
	
	@Override
	@Source({ "com/googlecode/mgwt/ui/client/theme/base/css/tabbar.css","tabbar.css"})
	TabBarCss getTabBarCss();
	
	@Override
	@Source({ "com/googlecode/mgwt/ui/client/theme/base/css/header.css", "com/googlecode/mgwt/ui/client/theme/base/css/iphone/header.css" })
	HeaderCss getHeaderCss();
	
	@Override
	@Source({ "com/googlecode/mgwt/ui/client/theme/base/css/buttons.css", "buttons.css" })
	ButtonsCss getButtonCss();
	
	@Override
	@Source({ "com/googlecode/mgwt/ui/client/theme/base/css/list.css", "list.css" })
	ListCss getListCss();
	
	
	@Override
	@Source({ "com/googlecode/mgwt/ui/client/theme/base/css/input.css", "input.css" })
	InputCss getInputCss();
	
	@Override
	@Source({ "com/googlecode/mgwt/ui/client/theme/base/css/buttonbar.css", "buttonBar.css" })
	ButtonBarCss getButtonBarCss();
	
	@Override
	@Source("main.css")
	MainCss getMainCss();
	
	/*@Source({"fontawesome/font-awesome.css"})
	FontOwesomeCss getFontOwesomeCss();
	*/
}
